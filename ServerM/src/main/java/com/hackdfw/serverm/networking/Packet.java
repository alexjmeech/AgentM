package com.hackdfw.serverm.networking;

public abstract class Packet
{
	public abstract void loadData(NetworkedPacket networked);
	public abstract NetworkedPacket storeData();
}