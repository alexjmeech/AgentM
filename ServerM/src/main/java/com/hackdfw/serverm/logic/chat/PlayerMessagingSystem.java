package com.hackdfw.serverm.logic.chat;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.networking.packets.ChatOutputPacket;

public class PlayerMessagingSystem
{
	private final GameInstance _game;
	private final BlockingQueue<TransferrablePlayerMessage> _incoming = new LinkedBlockingQueue<>();
	private final BlockingQueue<TransferrablePlayerMessage> _outgoing = new LinkedBlockingQueue<>();
	
	public PlayerMessagingSystem(GameInstance game)
	{
		_game = game;
		new Thread(() ->
		{
			while (_game.isRunning())
			{
				try
				{
					TransferrablePlayerMessage transfer = _incoming.take();
					handleIncomingMessage(transfer._uuid, transfer._message);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}, "Incoming Chat Thread").start();
		new Thread(() ->
		{
			while (_game.isRunning())
			{
				try
				{
					TransferrablePlayerMessage transfer = _outgoing.take();
					ChatOutputPacket packet = new ChatOutputPacket(transfer._message);
					_game.writePacket(transfer._uuid, packet);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}, "Outgoing Chat Thread").start();
	}
	
	public void handleIncomingMessage(UUID sender, String message)
	{
		_game.getChatManager().processChat(sender, message);
	}
	
	public void handleOutgoingMessage(UUID target, String message)
	{
		try
		{
			_outgoing.put(new TransferrablePlayerMessage(target, message));
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private static class TransferrablePlayerMessage
	{
		private final UUID _uuid;
		private final String _message;
		
		private TransferrablePlayerMessage(UUID uuid, String message)
		{
			_uuid = uuid;
			_message = message;
		}
	}
}