package com.hackdfw.serverm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import com.hackdfw.serverm.logic.GameConfig;
import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.CharacterType;
import com.hackdfw.serverm.logic.character.PlayerCharacter;
import com.hackdfw.serverm.networking.TCPNetworkIO;
import com.hackdfw.serverm.networking.packets.PacketList;
import com.hackdfw.serverm.networking.packets.SelectNamePacket;

public class GameServer 
{
	public static void main(String[] args)
	{
		AtomicReference<GameInstance> game = new AtomicReference<>();
		Map<UUID, PlayerCharacter> characters = Collections.synchronizedMap(new HashMap<>());
		List<CharacterType> types = Collections.synchronizedList(new ArrayList<>(Arrays.asList(CharacterType.values())));
		Collections.shuffle(types);
		TCPNetworkIO network = new TCPNetworkIO(GameConfig.SERVER_PORT, (uuid, packet) ->
		{
			if (packet.readInt() == PacketList.SELECT_NAME_PACKET)
			{
				if (!characters.containsKey(uuid))
				{
					SelectNamePacket p = new SelectNamePacket();
					p.loadData(packet);
					characters.put(uuid, new PlayerCharacter(uuid, p.getName(), types.remove(0)));
				}
			}
			else if (game.get() != null)
			{
				packet.reset();
				game.get().receivePacket(uuid, packet);
			}
		});
		while (characters.size() < GameConfig.MIN_PLAYERS)
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		game.set(new GameInstance(Thread.currentThread().getId(), characters, network));
	}
}