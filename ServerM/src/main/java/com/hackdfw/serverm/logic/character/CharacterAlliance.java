package com.hackdfw.serverm.logic.character;

public enum CharacterAlliance
{
	USA("United States"),
	USSR("Soviet Union")
	;
	
	private final String _name;
	
	private CharacterAlliance(String name)
	{
		_name = name;
	}
	
	public String getName()
	{
		return _name;
	}
}