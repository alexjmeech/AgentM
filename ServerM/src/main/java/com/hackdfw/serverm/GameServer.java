package com.hackdfw.serverm;

public class GameServer 
{
	private static boolean SHUT_DOWN = false;

	public static void main(String[] args)
	{
		while (!shutdownRequested())
		{
			try
			{
				System.out.println("LOADING GAME");
				shutDown();
				Thread.sleep(2000);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static synchronized boolean shutdownRequested()
	{
		return SHUT_DOWN;
	}

	public static synchronized void shutDown()
	{
		SHUT_DOWN = true;
	}
}
