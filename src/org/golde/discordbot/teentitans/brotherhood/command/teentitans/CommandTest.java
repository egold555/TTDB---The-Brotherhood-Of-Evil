package org.golde.discordbot.teentitans.brotherhood.command.teentitans;

import java.util.List;

import org.golde.discordbot.teentitans.brotherhood.BrotherhoodOfEvil;
import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.command.ICanHasDatabaseFile;
import org.golde.discordbot.teentitans.shared.command.teentitans.TeenTitansCommand;
import org.golde.discordbot.teentitans.shared.util.FileUtil;

import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandTest extends TeenTitansCommand implements ICanHasDatabaseFile {

	private NamesDB names;
	
	public CommandTest(AbstractTeenTitanBot bot) {
		super(bot, "test", null, "Most likely this will break something");
	}

	@Override
	protected void execute(CommandEvent event, List<String> args) {
		
		Member m = event.getMember();
		Guild g = event.getGuild();
		TextChannel tc = event.getTextChannel();
		
		if(m.getIdLong() != 199652118100049921L) {
			replyError(tc, "To prevent damage to the bot, only Eric can do this command. \n\n**That way, if something does break, its only Eric's fault o_0**");
			return;
		}
		
		EmbedBuilder builder = new EmbedBuilder();
		
		builder.setTitle("Valid Names");
		builder.setDescription("*Please type in **" + bot.getPrefix() + "null <name>** to set your name. Keep in mind __this can only be done once!__. Below are the list of names:*" );
		
		for(int i = 0; i < names.remaining.size(); i+=2) {
			
			if(i < names.remaining.size() - 2) {
				
				builder.addField(names.remaining.get(i), names.remaining.get(i + 1), true);
				
			}
			else if(i < names.remaining.size() - 1){
				builder.addField(names.remaining.get(i), " ", true);
			}
			
		}
		
		reply(tc, builder);
		
	}

	@Override
	public void reload() {
		names = null;
		names = FileUtil.loadFromFile("names", NamesDB.class);
	}
	
	static class NamesDB {
		private List<String> used;
		private List<String> remaining;
	}
}
