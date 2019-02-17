package com.hackdfw.serverm.logic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum GameStageType
{
	DAY(null),
	NIGHT(null),
	TRIAL(null),
	EXECUTION(null),
	;
	
	private final Class<? extends GameStage> _clazz;
	
	private GameStageType(Class<? extends GameStage> clazz)
	{
		_clazz = clazz;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GameStage> T create(GameInstance game, long duration)
	{
		try
		{
			Constructor<? extends GameStage> constructor = _clazz.getDeclaredConstructor(GameInstance.class, long.class);
			if (!constructor.isAccessible())
			{
				constructor.setAccessible(true);
			}
			return (T) constructor.newInstance(game, duration);
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}