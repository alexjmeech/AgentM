package com.hackdfw.serverm.logic.event;

public abstract class GameEvent
{
	private final boolean _async;
	
	public GameEvent(boolean onMainThread)
	{
		_async = onMainThread;
	}
	
	public boolean onMainThread()
	{
		return _async;
	}
}