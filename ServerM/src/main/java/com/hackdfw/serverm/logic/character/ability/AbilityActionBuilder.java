package com.hackdfw.serverm.logic.character.ability;

public interface AbilityActionBuilder
{
	boolean isComplete();
	AbilityAction build();
	void clear();
}