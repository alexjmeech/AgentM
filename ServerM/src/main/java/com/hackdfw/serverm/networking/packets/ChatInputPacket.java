package com.hackdfw.serverm.networking.packets;

import com.hackdfw.serverm.networking.NetworkedPacket;
import com.hackdfw.serverm.networking.Packet;

public class ChatInputPacket extends Packet
{
	private String _message;
	
	public ChatInputPacket() {}
	
	public ChatInputPacket(String message)
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
		return new NetworkedPacket().writeInt(PacketList.CHAT_INPUT_PACKET).writeString(_message);
	}
}