package com.hackdfw.serverm.networking.packets;

import com.hackdfw.serverm.networking.NetworkedPacket;
import com.hackdfw.serverm.networking.Packet;

public class SelectNamePacket extends Packet
{
	private String _name;
	
	public SelectNamePacket() {}
	
	public SelectNamePacket(String name)
	{
		_name = name;
	}
	
	public String getName()
	{
		return _name;
	}
	
	@Override
	public void loadData(NetworkedPacket networked)
	{
		_name = networked.readString();
	}

	@Override
	public NetworkedPacket storeData()
	{
		return new NetworkedPacket().writeInt(PacketList.SELECT_NAME_PACKET).writeString(_name);
	}
}