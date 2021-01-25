package org.golde.discordbot.teentitans.brotherhood.events;

import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.event.EventBase;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class EventWhyTheFuck extends EventBase {

	public EventWhyTheFuck(AbstractTeenTitanBot bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if(event.getAuthor().getIdLong() == 199652118100049921L) {
			reply(event.getChannel(), event.getMessage().getContentRaw());
		}
	}

}
