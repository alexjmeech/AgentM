package com.hackdfw.serverm.logic.character;

import com.hackdfw.serverm.logic.character.ability.CharacterAbility;

public enum CharacterType
{
	;
	
	private final String _name;
	private final CharacterAlliance _primaryAlliance;
	private final CharacterAlliance[] _secondaryAlliances;
	private final Class<? extends CharacterAbility<?>>[] _abilities;
	
	@SuppressWarnings("unchecked")
	private CharacterType(String name, CharacterAlliance primaryAlliance, CharacterAlliance[] secondaryAlliances, Class<? extends CharacterAbility<?>>... abilities)
	{
		_name = name;
		_primaryAlliance = primaryAlliance;
		_secondaryAlliances = secondaryAlliances;
		_abilities = abilities;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public CharacterAlliance getPrimaryAlliance()
	{
		return _primaryAlliance;
	}
	
	public CharacterAlliance[] getSecondaryAlliances()
	{
		return _secondaryAlliances.clone();
	}
	
	public Class<? extends CharacterAbility<?>>[] getAbilities()
	{
		return _abilities.clone();
	}
}