package com.hackdfw.serverm.logic.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class GameEventManager
{
	private final Map<Class<? extends GameEvent>, HandlerGroup> _listeners = new HashMap<>();
	
	public <T extends GameEvent> T callEvent(T event) 
	{
		HandlerGroup group = _listeners.get(event.getClass());
		
		if (group != null)
		{
			group.processEvent(event);
		}
		
		return event;
	}

	public void registerEvents(GameListener listener)
	{
		Map<Class<? extends GameEvent>, Set<GameEventListener>> generated = new HashMap<>();
		Set<Method> methods;
		try
		{
			Method[] publicMethods = listener.getClass().getMethods();
			Method[] privateMethods = listener.getClass().getDeclaredMethods();
			methods = new HashSet<>(publicMethods.length + privateMethods.length, 1.0f);
			for (Method method : publicMethods)
			{
				methods.add(method);
			}
			for (Method method : privateMethods)
			{
				methods.add(method);
			}
		}
		catch (NoClassDefFoundError e)
		{
			e.printStackTrace();
			return;
		}

		for (final Method method : methods)
		{
			final EventHandler eh = method.getAnnotation(EventHandler.class);
			if (eh == null) continue;
			if (method.isBridge() || method.isSynthetic())
			{
				continue;
			}
			final Class<?> checkClass;
			if (method.getParameterTypes().length != 1 || !GameEvent.class.isAssignableFrom(checkClass = method.getParameterTypes()[0]))
			{
				continue;
			}
			final Class<? extends GameEvent> eventClass = checkClass.asSubclass(GameEvent.class);
			method.setAccessible(true);
			Set<GameEventListener> eventSet = generated.computeIfAbsent(eventClass, k -> new HashSet<>());
			
			Consumer<GameEvent> handler = event ->
			{
				try
				{
					if (!eventClass.isAssignableFrom(event.getClass()))
					{
						return;
					}
					method.invoke(listener, event);
				}
				catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
				{
					e.printStackTrace();
				}
			};
			
			eventSet.add(new GameEventListener(listener, eh.priority(), eh.ignoreCancelled(), handler));
		}
		
		generated.forEach((clazz, listeners) -> _listeners.computeIfAbsent(clazz, k -> new HandlerGroup()).registerListeners(listeners));
	}
	
	public void deregisterEvents(GameListener listener)
	{
		_listeners.values().forEach(group -> group.deregisterListeners(listener));
	}

	private static class HandlerGroup
	{
		/*
		 * We use this "bake-first" setup to make the handlers an array because it is much faster to iterate an Array than an ArrayList
		 */
		private final List<GameEventListener> _registered = new ArrayList<>();
		private GameEventListener[] _handlers = null;

		protected boolean registerListener(GameEventListener listener)
		{
			_registered.add(listener);
			Collections.sort(_registered);
			_handlers = _registered.toArray(new GameEventListener[_registered.size()]);
			return true;
		}
		
		protected boolean registerListeners(Set<GameEventListener> listeners)
		{
			if (listeners.isEmpty())
			{
				return false;
			}
			for (GameEventListener listener : listeners)
			{
				_registered.add(listener);
			}
			Collections.sort(_registered);
			_handlers = _registered.toArray(new GameEventListener[_registered.size()]);
			return true;
		}
		
		protected boolean deregisterListeners(GameListener host)
		{
			if (_registered.removeIf(l -> l.isHostedBy(host)))
			{
				_handlers = _registered.toArray(new GameEventListener[_registered.size()]);
				return true;
			}
			
			return false;
		}

		protected boolean deregisterListener(GameEventListener listener)
		{
			if (_registered.remove(listener))
			{
				_handlers = _registered.toArray(new GameEventListener[_registered.size()]);
				return true;
			}
			return false;
		}
		
		protected void processEvent(GameEvent event)
		{
			for (GameEventListener listener : _handlers)
			{
				listener.execute(event);
			}
		}
	}

	public static enum ListenerPriority
	{
		LOW,
		NORMAL,
		HIGH
	}
}