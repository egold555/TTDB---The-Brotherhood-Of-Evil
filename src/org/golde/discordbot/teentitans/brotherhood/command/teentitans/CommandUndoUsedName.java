package org.golde.discordbot.teentitans.brotherhood.command.teentitans;

import java.util.List;

import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.command.teentitans.TeenTitansCommand;

import com.jagrosh.jdautilities.command.CommandEvent;

public class CommandUndoUsedName extends TeenTitansCommand {

	public CommandUndoUsedName(AbstractTeenTitanBot bot) {
		super(bot, "undoUsedName", "<name>", "Undo a prevous used name");
	}

	@Override
	protected void execute(CommandEvent event, List<String> args) {
		// TODO Auto-generated method stub
		
	}

}
