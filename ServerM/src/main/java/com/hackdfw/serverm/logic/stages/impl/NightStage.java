package com.hackdfw.serverm.logic.stages.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.PlayerDeathEvent;
import com.hackdfw.serverm.logic.event.EventHandler;
import com.hackdfw.serverm.logic.stages.GameStage;
import com.hackdfw.serverm.logic.stages.GameStageType;
import com.hackdfw.serverm.networking.NetworkedPacket;

public class NightStage extends GameStage
{
	private final List<PlayerDeathEvent> _deaths = new LinkedList<>();
	
	public NightStage(GameInstance game)
	{
		super(game, TimeUnit.SECONDS.toMillis(45));
	}

	@Override
	public String getName()
	{
		return "Night";
	}

	@Override
	public GameStageType getType()
	{
		return GameStageType.NIGHT;
	}

	@Override
	public void beginCustom()
	{
		Game.broadcast("Night falls...");
	}

	@Override
	public void cleanCustom() {}

	@Override
	public void tick()
	{
		if (getTimeRemaining() <= 0)
		{
			GameStage next = new MeetingStage(Game, _deaths.toArray(new PlayerDeathEvent[_deaths.size()]));
			Game.changeStage(next);
		}
	}

	@Override
	public void handlePackets(Map<UUID, List<NetworkedPacket>> incoming) {}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		_deaths.add(event);
	}
}