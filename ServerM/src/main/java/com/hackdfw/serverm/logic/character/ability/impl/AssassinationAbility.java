package com.hackdfw.serverm.logic.character.ability.impl;

import java.util.List;
import java.util.UUID;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.CharacterAlliance;
import com.hackdfw.serverm.logic.character.CharacterType;
import com.hackdfw.serverm.logic.character.PlayerCharacter;
import com.hackdfw.serverm.logic.character.ability.CharacterAbility;
import com.hackdfw.serverm.logic.stages.GameStageType;
import com.hackdfw.serverm.networking.NetworkedPacket;
import com.hackdfw.serverm.networking.packets.PacketList;
import com.hackdfw.serverm.networking.packets.TargetPlayerPacket;

public class AssassinationAbility extends CharacterAbility<AssassinationAbilityBuilder>
{	
	public AssassinationAbility(GameInstance game, PlayerCharacter user)
	{
		super(game, user);
		
		Builder = new AssassinationAbilityBuilder();
	}
	
	private double getChance(UUID target)
	{
		if (Game.getCharacter(target).getType() == CharacterType.PRESIDENT)
		{
			if (Game.getRemainingMembers(CharacterAlliance.USA) > 1)
			{
				return 0;
			}
		}
		
		return 0.65;
	}

	@Override
	public String getName()
	{
		return "Assassinate";
	}

	@Override
	public boolean isAvailable(GameStageType stage)
	{
		return stage == GameStageType.NIGHT;
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
				
				Builder.setUser(User);
				Builder.setGame(Game);
				
				if (p.getUUID().equals(User.getUUID()))
				{
					Game.sendMessage(User, "You cannot assassinate yourself!");
					continue;
				}
				
				if (Game.getCharacter(p.getUUID()).isAlive())
				{
					if (Builder.getTarget() != null && !Builder.getTarget().equals(p.getUUID()))
					{
						Builder.setTarget(p.getUUID());
						Builder.setChance(getChance(p.getUUID()));
						Game.sendMessage(User, "You have changed your target!");
					}
					else if (Builder.getTarget().equals(p.getUUID()))
					{
						Builder.setTarget(null);
						Builder.setChance(0.0);
						Game.sendMessage(User, "You have stopped targeting anyone!");
					}
					else
					{
						Builder.setTarget(p.getUUID());
						Builder.setChance(getChance(p.getUUID()));
						Game.sendMessage(User, "You have selected a target!");
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