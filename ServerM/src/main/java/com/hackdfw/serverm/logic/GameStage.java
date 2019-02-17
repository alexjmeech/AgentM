package com.hackdfw.serverm.logic;

public abstract class GameStage
{
	protected final GameInstance Game;
	private final long _duration;
	private final long _start;
	
	protected GameStage(GameInstance game, long duration)
	{
		Game = game;
		_duration = duration;
		_start = System.currentTimeMillis();
	}
	
	public abstract String getName();
	
	public abstract GameStage getType();
	
	public long getTimeRemaining()
	{
		return (_start + _duration) - System.currentTimeMillis();
	}
	
	public abstract void begin();
	public abstract void clean();
}