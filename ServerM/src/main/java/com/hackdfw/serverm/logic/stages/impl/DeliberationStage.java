package com.hackdfw.serverm.logic.stages.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.PlayerDeathEvent;
import com.hackdfw.serverm.logic.character.PlayerDeathEvent.PlayerDeathCause;
import com.hackdfw.serverm.logic.stages.GameStage;
import com.hackdfw.serverm.logic.stages.GameStageType;
import com.hackdfw.serverm.networking.NetworkedPacket;

public class DeliberationStage extends GameStage
{
	public DeliberationStage(GameInstance game)
	{
		super(game, TimeUnit.SECONDS.toMillis(25));
	}

	@Override
	public String getName()
	{
		return "Deliberation";
	}

	@Override
	public GameStageType getType()
	{
		return GameStageType.DELIBERATION;
	}

	@Override
	public void beginCustom()
	{
		Game.broadcast("Who do you think are enemy agents?");
	}

	@Override
	public void cleanCustom() {}

	@Override
	public void tick()
	{
		if (getTimeRemaining() <= 0)
		{
			GameStage next = null;
			
			
			
			Game.changeStage(next);
		}
	}

	@Override
	public void handlePackets(Map<UUID, List<NetworkedPacket>> incoming) {}
}