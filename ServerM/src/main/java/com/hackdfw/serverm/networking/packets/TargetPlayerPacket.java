package com.hackdfw.serverm.networking.packets;

import java.util.UUID;

import com.hackdfw.serverm.networking.NetworkedPacket;
import com.hackdfw.serverm.networking.Packet;

public class TargetPlayerPacket extends Packet
{
	private UUID _uuid;
	
	public TargetPlayerPacket() {}
	
	public TargetPlayerPacket(UUID uuid)
	{
		_uuid = uuid;
	}
	
	public UUID getUUID()
	{
		return _uuid;
	}
	
	@Override
	public void loadData(NetworkedPacket networked)
	{
		long most = networked.readLong();
		long least = networked.readLong();
		_uuid = new UUID(most, least);
	}

	@Override
	public NetworkedPacket storeData()
	{
		return new NetworkedPacket().writeInt(PacketList.TARGET_PLAYER_PACKET).writeLong(_uuid.getMostSignificantBits()).writeLong(_uuid.getLeastSignificantBits());
	}
}