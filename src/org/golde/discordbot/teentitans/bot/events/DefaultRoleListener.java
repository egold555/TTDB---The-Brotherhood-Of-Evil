package org.golde.discordbot.teentitans.bot.events;

import java.util.List;

import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.command.ICanHasDatabaseFile;
import org.golde.discordbot.teentitans.shared.constants.Channels;
import org.golde.discordbot.teentitans.shared.constants.Roles;
import org.golde.discordbot.teentitans.shared.event.EventBase;
import org.golde.discordbot.teentitans.shared.util.ArrayUtil;
import org.golde.discordbot.teentitans.shared.util.FileUtil;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

public class DefaultRoleListener extends EventBase implements ICanHasDatabaseFile {

	private static NamesDB names;
	
	public DefaultRoleListener(AbstractTeenTitanBot bot) {
		super(bot);
	}
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		assignName(event.getGuild(), event.getMember());
	}
	
	public static void assignName(Guild g, Member mem) {
		
		
		String pickedName = ArrayUtil.getRandomObjectFromArray(names.remaining);
		names.remaining.remove(pickedName);
		names.used.add(pickedName);
		
		FileUtil.saveToFile(names, "names");
		
		mem.modifyNickname(pickedName).queue(success -> {
			g.getTextChannelById(Channels.TextChannels.TITANS_TOWER).sendMessage("Welcome to the Teen Titans Guild " + mem.getAsMention() + "!").queue();
		});
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
