package com.hackdfw.serverm.tests;

import com.hackdfw.serverm.logic.event.EventHandler;
import com.hackdfw.serverm.logic.event.GameEventManager.ListenerPriority;
import com.hackdfw.serverm.logic.event.GameListener;

public class TestListener implements GameListener
{
	@EventHandler(priority = ListenerPriority.LOW)
	public void onTestEvent(TestEvent event)
	{
		event.cancel("Testing Purposes");
	}
	
	@EventHandler
	public void onTestEventLater(TestEvent event)
	{
		if (event.isCancelled())
		{
			event.allow();
		}
	}
	
	@EventHandler(priority = ListenerPriority.HIGH)
	public void onTestEventLatest(TestEvent event)
	{
		if (!event.isCancelled())
		{
			event.cancel("Completed Test");
		}
	}
}