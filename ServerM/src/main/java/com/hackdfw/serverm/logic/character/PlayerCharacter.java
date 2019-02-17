package com.hackdfw.serverm.logic.character;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.PlayerDeathEvent.PlayerDeathCause;
import com.hackdfw.serverm.logic.character.ability.CharacterAbility;
import com.hackdfw.serverm.logic.stages.GameStageType;

public class PlayerCharacter
{
	private final UUID _uuid;
	private final String _name;
	private final CharacterType _type;
	private final List<CharacterAbility<?>> _abilities = new LinkedList<>();
	private final AtomicBoolean _alive;
	private GameInstance _host;
	
	public PlayerCharacter(UUID uuid, String name, CharacterType type)
	{
		_uuid = uuid;
		_name = name;
		_type = type;
		_alive = new AtomicBoolean();
	}
	
	public UUID getUUID()
	{
		return _uuid;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public CharacterType getType()
	{
		return _type;
	}
	
	public boolean isAlive()
	{
		return _alive.get();
	}
	
	public void kill(PlayerCharacter[] killers, PlayerDeathCause cause)
	{
		_host.getEventManager().callEvent(new PlayerDeathEvent(this, killers, cause));
		_alive.compareAndSet(true, false);
	}
	
	public boolean spawn(GameInstance game)
	{
		try
		{
			for (Class<? extends CharacterAbility<?>> ability : _type.getAbilities())
			{
				Constructor<? extends CharacterAbility<?>> constructor = ability.getDeclaredConstructor(GameInstance.class, PlayerCharacter.class);
				if (!constructor.isAccessible())
				{
					constructor.setAccessible(true);
				}
				_abilities.add(constructor.newInstance(game, this));
			}
			_alive.set(true);
			_host = game;
			return true;
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
			_abilities.clear();
			return false;
		}
	}
	
	public List<CharacterAbility<?>> getActive(GameStageType current)
	{
		return _abilities.stream().filter(a -> a.isAvailable(current)).collect(Collectors.toList());
	}
	
	public void cleanAbilities()
	{
		_abilities.forEach(CharacterAbility::clearBuilder);
	}
}