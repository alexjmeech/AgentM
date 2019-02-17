package com.hackdfw.serverm.tests;

import com.hackdfw.serverm.logic.event.CancellableEvent;
import com.hackdfw.serverm.logic.event.GameEvent;

public class TestEvent extends GameEvent implements CancellableEvent
{
	private String _cancel = null;
	
	@Override
	public String getCancelReason()
	{
		return _cancel;
	}

	@Override
	public void cancel(String reason)
	{
		_cancel = reason;
	}

	@Override
	public void allow()
	{
		_cancel = null;
	}
}