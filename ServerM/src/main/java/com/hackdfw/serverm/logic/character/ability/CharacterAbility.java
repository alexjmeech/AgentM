package com.hackdfw.serverm.logic.character.ability;

import java.util.List;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.PlayerCharacter;
import com.hackdfw.serverm.logic.stages.GameStageType;
import com.hackdfw.serverm.networking.NetworkedPacket;

public abstract class CharacterAbility<T extends AbilityActionBuilder>
{
	protected final GameInstance Game;
	protected final PlayerCharacter User;
	protected T Builder;
	
	public CharacterAbility(GameInstance game, PlayerCharacter user)
	{
		Game = game;
		User = user;
	}
	
	public abstract String getName();
	
	public abstract boolean isAvailable(GameStageType stage);
	
	public abstract void handlePackets(List<NetworkedPacket> packets);
	
	public AbilityAction use(GameStageType currentStage)
	{
		if (!isAvailable(currentStage))
		{
			return null;
		}
		
		if (Builder.isComplete())
		{
			return Builder.build();
		}
		
		return null;
	}
	
	public void clearBuilder()
	{
		Builder.clear();
	}
}