package org.golde.discordbot.teentitans.shared.command.teentitans;

import java.util.List;

import javax.annotation.Nonnull;

import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;

import com.jagrosh.jdautilities.command.CommandEvent;

public class CommandReload extends TeenTitansCommand {

	public CommandReload(@Nonnull AbstractTeenTitanBot bot) {
		super(bot, "reload", null, "reloads some config files", "rl");
	}

	@Override
	protected void execute(CommandEvent event, List<String> args) {
		
		final long currentTime = System.currentTimeMillis();
		bot.onReloadBase();
		long timeItTook = System.currentTimeMillis() - currentTime;
		replySuccess(event.getChannel(), "Reloaded bot in " + timeItTook + "ms.");
	}

}
