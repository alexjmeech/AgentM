package com.hackdfw.serverm.logic.character.ability;

public class AbilityAction implements Comparable<AbilityAction>
{
	public static final int PRIORITY_UNBLOCKABLE_DEFENSE = 0;
	public static final int PRIORITY_UNBLOCKABLE_OFFENSE = 1;
	public static final int PRIORITY_BLOCKING = 5;
	public static final int PRIORITY_NORMAL_DEFENSE = 10;
	public static final int PRIORITY_NORMAL_OFFENSE = 11;
	
	private final int _priority;
	private final Runnable _run;
	
	public AbilityAction(int priority, Runnable run)
	{
		_priority = priority;
		_run = run;
	}
	
	public int getPriority()
	{
		return _priority;
	}
	
	public Runnable getRun()
	{
		return _run;
	}

	@Override
	public int compareTo(AbilityAction o)
	{
		return Integer.compare(_priority, o._priority);
	}
}
