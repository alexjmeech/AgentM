package com.hackdfw.serverm.logic.stages;

public enum GameStageType
{
	MEETING("Meeting"),
	DELIBERATION("Deliberation"),
	TRIAL("Trial"),
	EXECUTION("Execution"),
	NIGHT("Night")
	;
	
	private final String _name;
	
	private GameStageType(String name)
	{
		_name = name;
	}
	
	public String getName()
	{
		return _name;
	}
}