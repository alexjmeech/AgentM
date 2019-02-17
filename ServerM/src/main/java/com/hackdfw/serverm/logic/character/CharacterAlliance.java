package com.hackdfw.serverm.logic.character;

public enum CharacterAlliance
{
	USA("United States", ""),
	USSR("Soviet Union", "*")
	;
	
	private final String _name, _nightPrefix;
	
	private CharacterAlliance(String name, String nightPrefix)
	{
		_name = name;
		_nightPrefix = nightPrefix;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String getNightPrefix()
	{
		return _nightPrefix;
	}
}