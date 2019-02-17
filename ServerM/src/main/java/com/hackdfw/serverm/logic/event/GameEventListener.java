package com.hackdfw.serverm.logic.event;

import java.util.function.Consumer;

import com.hackdfw.serverm.logic.event.GameEventManager.ListenerPriority;

public class GameEventListener implements Comparable<GameEventListener>
{
	private final GameListener _host;
	private final Consumer<GameEvent> _handler;
	private final ListenerPriority _priority;
	private final boolean _ignoreCancelled;
	
	public GameEventListener(GameListener host, ListenerPriority priority, boolean ignoreCancelled, Consumer<GameEvent> handler)
	{
		_host = host;
		_priority = priority;
		_ignoreCancelled = ignoreCancelled;
		_handler = handler;
	}
	
	public int compareTo(GameEventListener o)
	{
		return Integer.compare(_priority.ordinal(), o._priority.ordinal());
	}
	
	public boolean isHostedBy(GameListener l)
	{
		return _host.equals(l);
	}
	
	public void execute(GameEvent event)
	{
		if (_ignoreCancelled && event instanceof CancellableEvent)
		{
			if (((CancellableEvent)event).isCancelled())
			{
				return;
			}
		}
		
		_handler.accept(event);
	}
}