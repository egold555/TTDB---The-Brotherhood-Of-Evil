package org.golde.discordbot.teentitans.bot.command.teentitans;

import java.util.List;

import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.command.teentitans.TeenTitansCommand;

import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;

public class CommandPurge extends TeenTitansCommand {

	public CommandPurge(AbstractTeenTitanBot bot) {
		super(bot, "purge", "<amount>", "Purges <amount> of messages");
	}

	@Override
	protected void execute(CommandEvent event, List<String> args) {
		TextChannel tc = event.getTextChannel();

		if(event.getArgs().isEmpty())
		{
			replyError(tc, "You must add a number after Prune command to delete an amount of messages.");
			return;
		}
		else {

			//Parse String to int, detect it the input is valid.
			Integer msgs = 0;
			try {
				msgs = Integer.parseInt(args.get(1)); //because of the command message, thanks Crackle <3
			} 
			catch (NumberFormatException nfe) {
				replyError(tc, "Please enter a valid number.");
				return;
			}

			if(msgs <= 1 || msgs > 100) {
				replyError(tc, "Please enter a number between **2 ~ 100**.");
				return;
			}


			event.getTextChannel().getHistory().retrievePast(msgs).queue((List<Message> mess) -> {
				try {
					event.getTextChannel().deleteMessages(mess).queue(
							success ->
							replySuccess(tc, " `" + event.getArgs() + "` messages deleted."),
							error -> replyError(tc, " An Error occurred!")
							);
				} 
				catch (IllegalArgumentException iae) {
					replyError(tc, "Cannot delete messages older than 2 weeks.");
				} 
				catch (PermissionException pe) {
					throw pe;
				}
			});
		}
	}

}
