package org.golde.discordbot.teentitans.shared.command.teentitans;

import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.annotation.Nonnull;

import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;

import com.jagrosh.jdautilities.command.CommandEvent;

public class CommandPing extends TeenTitansCommand {

	public CommandPing(@Nonnull AbstractTeenTitanBot bot) {
		super(bot, "ping", null, "checks the bot's latency", "pong");
        this.guildOnly = false;
	}
	
	@Override
	protected void execute(CommandEvent event, List<String> args) {
		event.reply("Ping: ...", m -> {
            long ping = event.getMessage().getTimeCreated().until(m.getTimeCreated(), ChronoUnit.MILLIS);
            m.editMessage("Ping: " + ping  + "ms | Websocket: " + event.getJDA().getGatewayPing() + "ms").queue();
        });
	}

}
