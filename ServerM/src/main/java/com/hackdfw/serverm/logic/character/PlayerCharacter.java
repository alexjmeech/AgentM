package com.hackdfw.serverm.logic.character;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.ability.CharacterAbility;

public class PlayerCharacter
{
	private final String _name;
	private final CharacterType _type;
	private final List<CharacterAbility<?>> _abilities = new LinkedList<>();
	private boolean _alive;
	
	public PlayerCharacter(String name, CharacterType type)
	{
		_name = name;
		_type = type;
		_alive = false;
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
		return _alive;
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
			_alive = true;
			return true;
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
			_abilities.clear();
			return false;
		}
	}
}