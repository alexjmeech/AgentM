package com.hackdfw.serverm.logic.character;

import com.hackdfw.serverm.logic.character.ability.CharacterAbility;
import com.hackdfw.serverm.logic.character.ability.impl.AssassinationAbility;
import com.hackdfw.serverm.logic.character.ability.impl.IndictAbility;
import com.hackdfw.serverm.logic.character.ability.impl.JudgementAbility;
import com.hackdfw.serverm.logic.character.ability.impl.PresidentialJudgementAbility;

public enum CharacterType
{
	PRESIDENT("President", CharacterAlliance.USA, IndictAbility.class, PresidentialJudgementAbility.class),
	FBI("FBI Agent", CharacterAlliance.USA, IndictAbility.class, JudgementAbility.class),
	CIA("CIA Agent", CharacterAlliance.USA, IndictAbility.class, JudgementAbility.class),
	NSA("NSA Agent", CharacterAlliance.USA, IndictAbility.class, JudgementAbility.class),
	DHS("Homeland Security Agent", CharacterAlliance.USA, IndictAbility.class, JudgementAbility.class),
	KGB("KGB Agent", CharacterAlliance.USSR, IndictAbility.class, JudgementAbility.class, AssassinationAbility.class)
	;
	
	private final String _name;
	private final CharacterAlliance _primaryAlliance;
	private final Class<? extends CharacterAbility<?>>[] _abilities;
	
	@SuppressWarnings("unchecked")
	private CharacterType(String name, CharacterAlliance primaryAlliance, Class<? extends CharacterAbility<?>>... abilities)
	{
		_name = name;
		_primaryAlliance = primaryAlliance;
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
	
	public Class<? extends CharacterAbility<?>>[] getAbilities()
	{
		return _abilities.clone();
	}
}