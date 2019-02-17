package com.hackdfw.serverm.logic.stages.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.hackdfw.serverm.logic.GameConfig;
import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.PlayerCharacter;
import com.hackdfw.serverm.logic.stages.GameStage;
import com.hackdfw.serverm.logic.stages.GameStageType;
import com.hackdfw.serverm.networking.NetworkedPacket;

public class DeliberationStage extends GameStage
{
	public Map<UUID, Integer> Votes = new HashMap<>();
	
	public DeliberationStage(GameInstance game)
	{
		super(game, TimeUnit.SECONDS.toMillis(25));
	}
	
	private PlayerCharacter select()
	{
		Set<PlayerCharacter> characters = Game.getPlayers(true);
		for (PlayerCharacter character : characters)
		{
			if (Votes.getOrDefault(character.getUUID(), 0) > Math.floor(characters.size() * GameConfig.VotePopulationMultiplier))
			{
				return character;
			}
		}
		return null;
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
			PlayerCharacter selected = select();
			
			if (selected != null)
			{
				Game.broadcast("The accused will now have the opportunity to defend themselves.");
				next = new TrialStage(Game, selected.getUUID());
			}
			else
			{
				Game.broadcast("There were not enough votes to start a trial.");
				next = new NightStage(Game);
			}
			
			Game.changeStage(next);
		}
	}

	@Override
	public void handlePackets(Map<UUID, List<NetworkedPacket>> incoming) {}
}