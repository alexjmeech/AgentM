package com.hackdfw.serverm.networking.packets;

import com.hackdfw.serverm.networking.NetworkedPacket;
import com.hackdfw.serverm.networking.Packet;

public class StageChangePacket extends Packet
{
	private int _type;
	private long _duration;
	
	public StageChangePacket() {}
	
	public StageChangePacket(int type, long duration)
	{
		_type = type;
		_duration = duration;
	}
	
	public int getType()
	{
		return _type;
	}
	
	public long getDuration()
	{
		return _duration;
	}
	
	@Override
	public void loadData(NetworkedPacket networked)
	{
		_type = networked.readInt();
		_duration = networked.readLong();
	}

	@Override
	public NetworkedPacket storeData()
	{
		return new NetworkedPacket().writeInt(PacketList.STAGE_CHANGE_PACKET).writeInt(_type).writeLong(_duration);
	}
}