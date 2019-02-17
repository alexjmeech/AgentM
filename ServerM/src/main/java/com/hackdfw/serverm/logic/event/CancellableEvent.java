package com.hackdfw.serverm.logic.event;

public interface CancellableEvent
{
	String getCancelReason();
	
	void cancel(String reason);
	void allow();
	
	default boolean isCancelled()
	{
		return getCancelReason() != null;
	}
}