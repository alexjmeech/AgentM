package com.hackdfw.serverm.logic.character.ability.impl;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.PlayerCharacter;
import com.hackdfw.serverm.logic.character.PlayerDeathEvent.PlayerDeathCause;
import com.hackdfw.serverm.logic.character.ability.AbilityAction;
import com.hackdfw.serverm.logic.character.ability.AbilityActionBuilder;

public class AssassinationAbilityBuilder implements AbilityActionBuilder
{
	private UUID _target;
	private PlayerCharacter _user;
	private GameInstance _game;
	private double _chance = 0;
	
	@Override
	public boolean isComplete()
	{
		return _target != null && _user != null && _game != null;
	}

	@Override
	public AbilityAction build()
	{
		return new AbilityAction(AbilityAction.PRIORITY_UNBLOCKABLE_OFFENSE, () ->
		{
			if (ThreadLocalRandom.current().nextDouble() < _chance)
			{
				PlayerCharacter target = _game.getCharacter(_target);
				target.kill(new PlayerCharacter[] { _user }, PlayerDeathCause.PVP);
			}
			else
			{
				_game.sendMessage(_user, "Your assassination attempt failed!");
			}
		});
	}

	@Override
	public void clear()
	{
		_target = null;
		_user = null;
		_game = null;
		_chance = 0;
	}
	
	public UUID getTarget()
	{
		return _target;
	}
	
	public void setTarget(UUID target)
	{
		_target = target;
	}
	
	public void setUser(PlayerCharacter user)
	{
		_user = user;
	}
	
	public void setGame(GameInstance game)
	{
		_game = game;
	}
	
	public void setChance(double chance)
	{
		_chance = chance;
	}
}