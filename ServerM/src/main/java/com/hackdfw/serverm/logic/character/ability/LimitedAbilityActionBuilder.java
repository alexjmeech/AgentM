package com.hackdfw.serverm.logic.character.ability;

public abstract class LimitedAbilityActionBuilder implements AbilityActionBuilder
{
	private LimitedCharacterAbility<? extends LimitedAbilityActionBuilder> _ability;
	
	public LimitedAbilityActionBuilder(LimitedCharacterAbility<? extends LimitedAbilityActionBuilder> ability)
	{
		_ability = ability;
	}
	
	protected abstract AbilityAction buildCustom();
	
	@Override
	public AbilityAction build()
	{
		AbilityAction base = buildCustom();
		return new AbilityAction(base.getPriority(), () ->
		{
			_ability.incrementUses();
			base.getRun().run();
		});
	}
}