package com.hackdfw.serverm.logic.stages.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.CharacterAlliance;
import com.hackdfw.serverm.logic.character.PlayerCharacter;
import com.hackdfw.serverm.logic.character.PlayerDeathEvent.PlayerDeathCause;
import com.hackdfw.serverm.logic.chat.PlayerChatEvent;
import com.hackdfw.serverm.logic.event.EventHandler;
import com.hackdfw.serverm.logic.stages.GameStage;
import com.hackdfw.serverm.logic.stages.GameStageType;
import com.hackdfw.serverm.networking.NetworkedPacket;

public class ExecutionStage extends GameStage
{
	private final UUID _target;
	
	public ExecutionStage(GameInstance game, UUID target)
	{
		super(game, TimeUnit.SECONDS.toMillis(5));
		
		_target = target;
	}

	@Override
	public GameStageType getType()
	{
		return GameStageType.EXECUTION;
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
			PlayerCharacter target = Game.getCharacter(_target);
			Set<PlayerCharacter> killers = Game.getPlayers(true);
			killers.remove(target);
			target.kill(killers.toArray(new PlayerCharacter[killers.size()]), PlayerDeathCause.EXECUTION);
			if (target.getType().getPrimaryAlliance() == CharacterAlliance.USSR)
			{
				Game.broadcast("You have successfully killed a traitor!");
			}
			else
			{
				Game.broadcast("Unfortunately, you killed a loyal patriot by mistake!");
			}
			
			Game.changeStage(new NightStage(Game));
		}
	}

	@Override
	public void handlePackets(Map<UUID, List<NetworkedPacket>> incoming) {}
	
	@EventHandler
	public void onChat(PlayerChatEvent event)
	{
		if (getTimeRemaining() > 0 && event.getSender().isAlive())
		{
			event.cancel("This is a solemn occasion!");
		}
	}
}