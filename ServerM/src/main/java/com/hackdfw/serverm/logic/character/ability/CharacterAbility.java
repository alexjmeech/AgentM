package com.hackdfw.serverm.logic.character.ability;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.GameStageType;
import com.hackdfw.serverm.logic.character.PlayerCharacter;

public abstract class CharacterAbility<T extends AbilityActionBuilder>
{
	protected GameInstance Game;
	protected PlayerCharacter User;
	protected T Builder;
	
	public CharacterAbility(GameInstance game, PlayerCharacter user)
	{
		Game = game;
		User = user;
	}
	
	public abstract String getName();
	
	public abstract boolean isAvailable(GameStageType stage);
	
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