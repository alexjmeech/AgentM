package com.hackdfw.serverm.networking.packets;

import com.hackdfw.serverm.networking.NetworkedPacket;
import com.hackdfw.serverm.networking.Packet;

public class GameEndPacket extends Packet
{
	private int _winner;
	
	public GameEndPacket() {}
	
	public GameEndPacket(int winner)
	{
		_winner = winner;
	}
	
	public int getWinner()
	{
		return _winner;
	}
	
	@Override
	public void loadData(NetworkedPacket networked)
	{
		_winner = networked.readInt();
	}

	@Override
	public NetworkedPacket storeData()
	{
		return new NetworkedPacket().writeInt(PacketList.GAME_END_PACKET).writeInt(_winner);
	}
}