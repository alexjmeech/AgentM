package com.hackdfw.serverm.logic.chat;

import java.util.HashSet;
import java.util.Set;

import com.hackdfw.serverm.logic.character.PlayerCharacter;
import com.hackdfw.serverm.logic.event.CancellableEvent;
import com.hackdfw.serverm.logic.event.GameEvent;

public class PlayerChatEvent extends GameEvent implements CancellableEvent
{
	private String _denyReason = null;
	private final PlayerCharacter _sender;
	private final Set<PlayerCharacter> _recipients;
	private String _message;
	
	public PlayerChatEvent(PlayerCharacter sender, Set<PlayerCharacter> recipients, String message)
	{
		super(false);
		_sender = sender;
		_recipients = new HashSet<>(recipients);
		_message = message;
	}
	
	public PlayerCharacter getSender()
	{
		return _sender;
	}
	
	public Set<PlayerCharacter> getRecipients()
	{
		return _recipients;
	}
	
	public String getMessage()
	{
		return _message;
	}
	
	public void setMessage(String message)
	{
		_message = message;
	}
	
	@Override
	public String getCancelReason()
	{
		return _denyReason;
	}

	@Override
	public void cancel(String reason)
	{
		_denyReason = reason;
	}

	@Override
	public void allow()
	{
		_denyReason = null;
	}
}