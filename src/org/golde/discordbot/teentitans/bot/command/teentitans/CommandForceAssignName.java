package org.golde.discordbot.teentitans.bot.command.teentitans;

import java.util.List;

import org.golde.discordbot.teentitans.bot.BrotherhoodOfEvil;
import org.golde.discordbot.teentitans.bot.events.DefaultRoleListener;
import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.command.ICanHasDatabaseFile;
import org.golde.discordbot.teentitans.shared.command.teentitans.TeenTitansCommand;
import org.golde.discordbot.teentitans.shared.util.FileUtil;

import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandForceAssignName extends TeenTitansCommand {

	
	public CommandForceAssignName(AbstractTeenTitanBot bot) {
		super(bot, "forceAssignName", "<member>", "Incase I mess up again");
	}

	@Override
	protected void execute(CommandEvent event, List<String> args) {
		
		Member m = getMember(event, args, 1);
		Guild g = event.getGuild();
		TextChannel tc = event.getTextChannel();
		
		if(m == null) {
			replyError(tc, "I could not find that user :(");
			return;
		}
		
		DefaultRoleListener.assignName(g, m);
		
		
	}

}
