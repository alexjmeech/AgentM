package com.hackdfw.serverm.tests;

import com.hackdfw.serverm.logic.event.GameEventManager;

import junit.framework.TestCase;

public class EventTestCase extends TestCase
{
	public EventTestCase()
	{
		super("Event System Test Case");
	}
	
	public void testCancellableBase()
	{
		GameEventManager manager = new GameEventManager();
		TestListener listener = new TestListener();
		manager.registerEvents(listener);
		TestEvent event = manager.callEvent(new TestEvent());
		assertTrue(event.isCancelled());
	}
	
	public void testCancellableReason()
	{
		GameEventManager manager = new GameEventManager();
		TestListener listener = new TestListener();
		manager.registerEvents(listener);
		TestEvent event = manager.callEvent(new TestEvent());
		assertEquals(event.getCancelReason(), "Completed Test");
	}
}