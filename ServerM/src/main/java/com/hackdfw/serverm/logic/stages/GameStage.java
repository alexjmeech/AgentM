package com.hackdfw.serverm.logic.stages;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.event.GameListener;
import com.hackdfw.serverm.networking.NetworkedPacket;

public abstract class GameStage implements GameListener
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
	
	public String getName()
	{
		return getType().getName();
	}
	
	public abstract GameStageType getType();
	
	public long getTimeRemaining()
	{
		return (_start + _duration) - System.currentTimeMillis();
	}
	
	public abstract void handlePackets(Map<UUID, List<NetworkedPacket>> incoming);
	public abstract void tick();
	public abstract void beginCustom();
	public abstract void cleanCustom();
	
	public void begin()
	{
		Game.getEventManager().registerEvents(this);
		beginCustom();
	}
	
	public void clean()
	{
		Game.getEventManager().deregisterEvents(this);
		cleanCustom();
	}
}