package com.hackdfw.serverm.logic.character.ability.impl;

import java.util.List;
import java.util.UUID;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.PlayerCharacter;
import com.hackdfw.serverm.logic.character.ability.AbilityActionBuilder;
import com.hackdfw.serverm.logic.character.ability.CharacterAbility;
import com.hackdfw.serverm.logic.stages.GameStageType;
import com.hackdfw.serverm.logic.stages.impl.DeliberationStage;
import com.hackdfw.serverm.networking.NetworkedPacket;
import com.hackdfw.serverm.networking.packets.PacketList;
import com.hackdfw.serverm.networking.packets.TargetPlayerPacket;

public class IndictAbility extends CharacterAbility<AbilityActionBuilder>
{
	private UUID _previous;
	
	public IndictAbility(GameInstance game, PlayerCharacter user)
	{
		super(game, user);
	}

	@Override
	public String getName()
	{
		return "Indictment";
	}

	@Override
	public boolean isAvailable(GameStageType stage)
	{
		return stage == GameStageType.DELIBERATION;
	}

	@Override
	public void handlePackets(List<NetworkedPacket> packets)
	{
		for (NetworkedPacket packet : packets)
		{
			if (packet.readInt() == PacketList.TARGET_PLAYER_PACKET)
			{
				TargetPlayerPacket p = new TargetPlayerPacket();
				p.loadData(packet);
				
				if (p.getUUID().equals(User.getUUID()))
				{
					Game.sendMessage(User, "You cannot indict yourself!");
					continue;
				}
				
				if (Game.getCharacter(p.getUUID()).isAlive())
				{
					if (_previous != null && !_previous.equals(p.getUUID()))
					{
						((DeliberationStage)Game.getGameStage()).Votes.merge(_previous, -1, Integer::sum);
						((DeliberationStage)Game.getGameStage()).Votes.merge(p.getUUID(), 1, Integer::sum);
						_previous = p.getUUID();
						Game.broadcast(User.getName() + " has changed their vote!");
					}
					else if (_previous.equals(p.getUUID()))
					{
						((DeliberationStage)Game.getGameStage()).Votes.merge(p.getUUID(), -1, Integer::sum);
						Game.broadcast(User.getName() + " has rescinded their vote!");
						_previous = null;
					}
					else
					{
						_previous = p.getUUID();
						((DeliberationStage)Game.getGameStage()).Votes.merge(p.getUUID(), 1, Integer::sum);
						Game.broadcast(User.getName() + " has voted!");
					}
				}
			}
			else
			{
				packet.reset();
			}
		}
	}
}