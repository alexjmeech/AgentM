package com.hackdfw.serverm.logic.chat;

import java.util.Set;
import java.util.UUID;

import com.hackdfw.serverm.logic.GameInstance;
import com.hackdfw.serverm.logic.character.PlayerCharacter;
import com.hackdfw.serverm.logic.stages.GameStageType;

public class GameChatManager
{
	private final GameInstance _game;
	
	public GameChatManager(GameInstance game)
	{
		_game = game;
	}
	
	private String getNameFormatting(PlayerCharacter character, boolean night)
	{
		String formatting = character.getName();
		
		if (!character.isAlive())
		{
			formatting = "[DEAD] " + formatting;
		}
		else
		{
			formatting = character.getType().getPrimaryAlliance().getNightPrefix() + formatting;
		}
		
		return formatting;
	}
	
	public void processChat(UUID senderUUID, String message)
	{
		PlayerCharacter sender = _game.getCharacter(senderUUID);
		if (sender == null)
		{
			return;
		}
		Set<PlayerCharacter> recipients = null;
		if (sender.isAlive())
		{
			recipients = _game.getPlayers(false);
		}
		else
		{
			recipients = _game.getGhosts();
		}
		PlayerChatEvent event = _game.getEventManager().callEvent(new PlayerChatEvent(sender, recipients, message));
		
		if (!event.isCancelled() && !event.getRecipients().isEmpty())
		{
			if (!event.getRecipients().contains(sender))
			{
				event.getRecipients().add(sender);
			}
			for (PlayerCharacter character : event.getRecipients())
			{
				_game.sendMessage(character, getNameFormatting(character, _game.getGameStageType() == GameStageType.NIGHT) + "> " + event.getMessage());
			}
		}
		else if (event.isCancelled())
		{
			_game.sendMessage(sender, "You could not send that message: " + event.getCancelReason());
		}
		else
		{
			_game.sendMessage(sender, "There was nobody around to talk to!");
		}
	}
}