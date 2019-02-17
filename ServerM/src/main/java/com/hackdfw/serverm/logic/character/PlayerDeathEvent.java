package com.hackdfw.serverm.logic.character;

import com.hackdfw.serverm.logic.event.GameEvent;

public class PlayerDeathEvent extends GameEvent
{
	private final PlayerCharacter _player;
	private final PlayerCharacter[] _killers;
	private final PlayerDeathCause _cause;
	
	public PlayerDeathEvent(PlayerCharacter player, PlayerCharacter[] killers, PlayerDeathCause cause)
	{
		super(true);
		
		_player = player;
		_killers = killers;
		_cause = cause;
	}
	
	public PlayerCharacter getPlayer()
	{
		return _player;
	}
	
	public PlayerCharacter[] getKillers()
	{
		return _killers.clone();
	}
	
	public PlayerDeathCause getCause()
	{
		return _cause;
	}
	
	public static enum PlayerDeathCause
	{
		QUIT,
		PVP,
		EXECUTION
	}
}