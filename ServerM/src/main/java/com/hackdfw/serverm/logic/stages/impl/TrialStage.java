package com.hackdfw.serverm.logic.stages.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.PlayerCharacter;
import com.hackdfw.serverm.logic.chat.PlayerChatEvent;
import com.hackdfw.serverm.logic.event.EventHandler;
import com.hackdfw.serverm.logic.stages.GameStage;
import com.hackdfw.serverm.logic.stages.GameStageType;
import com.hackdfw.serverm.networking.NetworkedPacket;

public class TrialStage extends GameStage
{
	private final UUID _target;
	public int Votes = 0;
	
	public TrialStage(GameInstance game, UUID target)
	{
		super(game, TimeUnit.SECONDS.toMillis(15));
		
		_target = target;
	}

	@Override
	public String getName()
	{
		return "Trial";
	}

	@Override
	public GameStageType getType()
	{
		return GameStageType.TRIAL;
	}

	@Override
	public void beginCustom()
	{
		Game.broadcast("Do they deserve to die?");
	}

	@Override
	public void cleanCustom() {}

	@Override
	public void tick()
	{
		if (getTimeRemaining() <= 0)
		{
			GameStage next = null;
			
			if (Votes > (Game.getPlayers(true).size() - Votes))
			{
				PlayerCharacter target = Game.getCharacter(_target);
				Game.broadcast("The jury has found " + target + " guilty! God rest their soul.");
			}
			else
			{
				Game.broadcast("The jury has voted: NOT GUILTY");
				next = new NightStage(Game);
			}
			
			Game.changeStage(next);
		}
	}

	@Override
	public void handlePackets(Map<UUID, List<NetworkedPacket>> incoming) {}
	
	@EventHandler
	public void onChat(PlayerChatEvent event)
	{
		if (getTimeRemaining() > 0 && !event.getSender().getUUID().equals(_target) && event.getSender().isAlive())
		{
			event.cancel("SHHH! The accused is defending themselves!");
		}
	}
}