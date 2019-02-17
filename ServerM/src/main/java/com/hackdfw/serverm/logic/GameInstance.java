package com.hackdfw.serverm.logic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;
import com.hackdfw.serverm.logic.character.CharacterAlliance;
import com.hackdfw.serverm.logic.character.PlayerCharacter;
import com.hackdfw.serverm.logic.character.PlayerDeathEvent;
import com.hackdfw.serverm.logic.chat.GameChatManager;
import com.hackdfw.serverm.logic.chat.PlayerMessagingSystem;
import com.hackdfw.serverm.logic.event.GameEventManager;
import com.hackdfw.serverm.logic.stages.GameStage;
import com.hackdfw.serverm.logic.stages.GameStageType;
import com.hackdfw.serverm.logic.stages.impl.MeetingStage;
import com.hackdfw.serverm.networking.NetworkedPacket;
import com.hackdfw.serverm.networking.Packet;
import com.hackdfw.serverm.networking.TCPNetworkIO;
import com.hackdfw.serverm.networking.packets.ChatInputPacket;
import com.hackdfw.serverm.networking.packets.PacketList;

public class GameInstance
{
	private final long _mainThread;
	private final TCPNetworkIO _network;
	private final Map<UUID, PlayerCharacter> _characters = new ConcurrentHashMap<>();
	private final GameEventManager _eventManager;
	private final GameChatManager _chatManager;
	private final PlayerMessagingSystem _messagingSystem;
	private final AtomicBoolean _running = new AtomicBoolean(true);
	private final AtomicReference<GameStageType> _stageType = new AtomicReference<>();
	private final Map<UUID, List<NetworkedPacket>> _incoming = new ConcurrentHashMap<>();
	private GameStage _currentStage;
	
	public GameInstance(long mainThread, Map<UUID, PlayerCharacter> players, TCPNetworkIO network)
	{
		_mainThread = mainThread;
		_network = network;
		_eventManager = new GameEventManager(this::isMainThread);
		_chatManager = new GameChatManager(this);
		_messagingSystem = new PlayerMessagingSystem(this);
		_characters.putAll(players);
		_characters.values().forEach(character -> character.spawn(this));
		changeStage(new MeetingStage(this, new PlayerDeathEvent[0]));
		
		while (isRunning())
		{
			try
			{
				if (_currentStage != null)
				{
					_currentStage.handlePackets(_incoming);
					_characters.values().forEach(c -> c.getActive(_currentStage.getType()).forEach(a -> a.handlePackets(_incoming.remove(c.getUUID()))));
					_incoming.clear();
					_currentStage.tick();
				}
				
				Thread.sleep(1000 / GameConfig.TPS);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	public long getId()
	{
		return _mainThread;
	}
	
	public boolean isMainThread()
	{
		return _mainThread == Thread.currentThread().getId();
	}
	
	public boolean isRunning()
	{
		return _running.get();
	}
	
	public void end()
	{
		if (_running.compareAndSet(true, false))
		{
			
		}
	}
	
	public GameEventManager getEventManager()
	{
		return _eventManager;
	}
	
	public GameChatManager getChatManager()
	{
		return _chatManager;
	}
	
	public PlayerMessagingSystem getMessagingSystem()
	{
		return _messagingSystem;
	}
	
	public GameStage getGameStage()
	{
		return _currentStage;
	}
	
	public GameStageType getGameStageType()
	{
		return _stageType.get();
	}
	
	public PlayerCharacter getCharacter(UUID uuid)
	{
		return _characters.get(uuid);
	}
	
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
	
	public Set<PlayerCharacter> getGhosts()
	{
		return _characters.values().stream().filter(c -> !c.isAlive()).collect(Collectors.toSet());
	}
	
	public Set<PlayerCharacter> getPlayers(boolean aliveOnly)
	{
		Stream<PlayerCharacter> stream = _characters.values().stream();
		if (aliveOnly)
		{
			stream = stream.filter(PlayerCharacter::isAlive);
		}
		
		return stream.collect(Collectors.toSet());
	}
	
	public void changeStage(GameStage newStage)
	{
		Preconditions.checkState(isMainThread(), "Game stage cannot be changed asynchronously!");
		GameStage old = _currentStage;
		
		if (old == null)
		{
			_stageType.set(newStage.getType());
			_currentStage = newStage;
			_currentStage.begin();
		}
		else if (_stageType.compareAndSet(old.getType(), newStage.getType()))
		{
			getPlayers(true).forEach(p -> p.getActive(old.getType()).forEach(a -> a.use(old.getType())));
			_stageType.set(newStage.getType());
			_currentStage = newStage;
			old.clean();
			_currentStage.begin();
		}
		_characters.values().forEach(PlayerCharacter::cleanAbilities);
	}
	
	public void broadcast(String message)
	{
		_characters.values().forEach(c -> sendMessage(c, message));
	}
	
	public void sendMessage(PlayerCharacter character, String message)
	{
		_messagingSystem.handleOutgoingMessage(character.getUUID(), message);
	}
	
	public void receivePacket(UUID uuid, NetworkedPacket packet)
	{
		if (packet.readInt() == PacketList.CHAT_INPUT_PACKET)
		{
			ChatInputPacket p = new ChatInputPacket();
			_messagingSystem.handleIncomingMessage(uuid, p.getMessage());
			return;
		}
		packet.reset();
		_incoming.computeIfAbsent(uuid, k -> Collections.synchronizedList(new LinkedList<>())).add(packet);
	}
	
	public void writePacket(UUID target, Packet packet)
	{
		_network.writePacket(target, packet);
	}
}