package com.hackdfw.serverm.logic.character.ability;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.GameStageType;
import com.hackdfw.serverm.logic.character.PlayerCharacter;

public abstract class LimitedCharacterAbility<T extends LimitedAbilityActionBuilder> extends CharacterAbility<T>
{
	private final int _totalUses;
	private int _uses;
	
	public LimitedCharacterAbility(GameInstance game, PlayerCharacter user, int uses)
	{
		super(game, user);
		
		_totalUses = uses;
		_uses = 0;
	}
	
	protected void incrementUses()
	{
		_uses++;
	}
	
	public int getRemainingUses()
	{
		return _totalUses - _uses;
	}
	
	protected abstract boolean availableCustom(GameStageType type);
	
	@Override
	public boolean isAvailable(GameStageType type)
	{
		return getRemainingUses() > 0 && availableCustom(type);
	}
}