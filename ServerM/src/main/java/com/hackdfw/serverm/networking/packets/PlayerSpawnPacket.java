package com.hackdfw.serverm.networking.packets;

import java.util.UUID;

import com.hackdfw.serverm.networking.NetworkedPacket;
import com.hackdfw.serverm.networking.Packet;

public class PlayerSpawnPacket extends Packet
{
	private UUID _uuid;
	private int _type;
	
	public PlayerSpawnPacket() {}
	
	public PlayerSpawnPacket(UUID uuid, int type)
	{
		_uuid = uuid;
		_type = type;
	}
	
	public UUID getUUID()
	{
		return _uuid;
	}
	
	public int getType()
	{
		return _type;
	}
	
	@Override
	public void loadData(NetworkedPacket networked)
	{
		long most = networked.readLong();
		long least = networked.readLong();
		_type = networked.readInt();
		_uuid = new UUID(most, least);
		
	}

	@Override
	public NetworkedPacket storeData()
	{
		return new NetworkedPacket().writeInt(PacketList.PLAYER_SPAWN_PACKET).writeLong(_uuid.getMostSignificantBits()).writeLong(_uuid.getLeastSignificantBits()).writeInt(_type);
	}
}