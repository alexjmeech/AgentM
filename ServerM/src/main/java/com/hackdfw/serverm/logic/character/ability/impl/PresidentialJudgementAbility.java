package com.hackdfw.serverm.logic.character.ability.impl;

import java.util.List;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.PlayerCharacter;
import com.hackdfw.serverm.logic.character.ability.AbilityActionBuilder;
import com.hackdfw.serverm.logic.character.ability.CharacterAbility;
import com.hackdfw.serverm.logic.stages.GameStageType;
import com.hackdfw.serverm.logic.stages.impl.TrialStage;
import com.hackdfw.serverm.networking.NetworkedPacket;
import com.hackdfw.serverm.networking.packets.PacketList;
import com.hackdfw.serverm.networking.packets.TargetPlayerPacket;

public class PresidentialJudgementAbility extends CharacterAbility<AbilityActionBuilder>
{
	private boolean _voted = false;
	
	public PresidentialJudgementAbility(GameInstance game, PlayerCharacter user)
	{
		super(game, user);
	}

	@Override
	public String getName()
	{
		return "Presidential Judgement";
	}

	@Override
	public boolean isAvailable(GameStageType stage)
	{
		return stage == GameStageType.TRIAL;
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
					if (_voted)
					{
						_voted = false;
						((TrialStage)Game.getGameStage()).Votes -= 2;
						Game.broadcast(User.getName() + " has withdrawn their decision!");
					}
					else
					{
						_voted = true;
						((TrialStage)Game.getGameStage()).Votes += 2;
						Game.broadcast(User.getName() + " has come to a decision!");
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