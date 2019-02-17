package com.hackdfw.serverm.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.hackdfw.serverm.logic.character.CharacterAlliance;
import com.hackdfw.serverm.logic.character.PlayerCharacter;

public class GameInstance
{
	private final Map<UUID, PlayerCharacter> _characters = new HashMap<>();
	private GameStage _currentStage;
	
	public int getRemainingMembers(CharacterAlliance alliance)
	{
		int remaining = 0;
		for (PlayerCharacter c : _characters.values())
		{
			if (c.getType().getPrimaryAlliance() == alliance && c.isAlive())
			{
				remaining++;
			}
		}
		
		return remaining;
	}
	
	public Set<PlayerCharacter> getMembers(CharacterAlliance alliance, boolean aliveOnly)
	{
		Stream<PlayerCharacter> stream = _characters.values().stream().filter(c -> c.getType().getPrimaryAlliance() == alliance);
		if (aliveOnly)
		{
			stream = stream.filter(PlayerCharacter::isAlive);
		}
		return stream.collect(Collectors.toSet());
	}
}