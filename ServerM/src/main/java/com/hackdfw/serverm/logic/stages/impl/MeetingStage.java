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

public class MeetingStage extends GameStage
{
	private final PlayerDeathEvent[] _nightDeaths;
	
	public MeetingStage(GameInstance game, PlayerDeathEvent[] deaths)
	{
		super(game, TimeUnit.SECONDS.toMillis(deaths.length > 0 ? 30 : 10));
		
		_nightDeaths = deaths;
	}

	@Override
	public GameStageType getType()
	{
		return GameStageType.MEETING;
	}

	@Override
	public void beginCustom()
	{
		if (_nightDeaths.length > 0)
		{
			StringBuilder inform = new StringBuilder("Some deaths occurred last night:");
			if (_nightDeaths[0].getCause() == PlayerDeathCause.PVP)
			{
				inform.append(_nightDeaths[0].getPlayer().getName() + " (Killed by " + _nightDeaths[0].getKillers()[0].getType().getName() + ")");
			}
			else if (_nightDeaths[0].getCause() == PlayerDeathCause.QUIT)
			{
				inform.append(_nightDeaths[0].getPlayer().getName() + " (Committed Suicide)");
			}
			for (int i = 1; i < _nightDeaths.length; i++)
			{
				String died = _nightDeaths[i].getPlayer().getName();
				if (_nightDeaths[i].getCause() == PlayerDeathCause.PVP)
				{
					String killer = _nightDeaths[i].getKillers()[0].getType().getName();
					inform.append(", " + died + " (Killed by " + killer + ")");
				}
				else if (_nightDeaths[i].getCause() == PlayerDeathCause.QUIT)
				{
					inform.append(", " + died + " (Committed Suicide)");
				}
			}
			Game.broadcast(inform.toString());
		}
		else
		{
			Game.broadcast("Nobody died last night!");
		}
	}

	@Override
	public void cleanCustom() {}

	@Override
	public void tick()
	{
		if (getTimeRemaining() <= 0)
		{
			GameStage next = null;
			
			if (_nightDeaths.length > 0)
			{
				next = new DeliberationStage(Game);
			}
			else
			{
				next = new NightStage(Game);
			}
			
			Game.changeStage(next);
		}
	}

	@Override
	public void handlePackets(Map<UUID, List<NetworkedPacket>> incoming) {}
}