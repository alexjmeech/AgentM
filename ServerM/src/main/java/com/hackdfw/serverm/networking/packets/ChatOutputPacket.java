package com.hackdfw.serverm.networking.packets;

import com.hackdfw.serverm.networking.NetworkedPacket;
import com.hackdfw.serverm.networking.Packet;

public class ChatOutputPacket extends Packet
{
	private String _message;
	
	public ChatOutputPacket() {}
	
	public ChatOutputPacket(String message)
	{
		_message = message;
	}
	
	public String getMessage()
	{
		return _message;
	}
	
	@Override
	public void loadData(NetworkedPacket networked)
	{
		_message = networked.readString();
	}

	@Override
	public NetworkedPacket storeData()
	{
		return new NetworkedPacket().writeInt(PacketList.CHAT_OUTPUT_PACKET).writeString(_message);
	}
}