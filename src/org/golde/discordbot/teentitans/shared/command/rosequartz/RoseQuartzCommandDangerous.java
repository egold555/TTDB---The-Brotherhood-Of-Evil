package org.golde.discordbot.teentitans.shared.command.rosequartz;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;

public abstract class RoseQuartzCommandDangerous extends RoseQuartzCommand {

	public RoseQuartzCommandDangerous(@Nonnull AbstractTeenTitanBot bot, @Nonnull String nameIn, @Nullable String argsIn, @Nullable String helpIn, @Nullable String... aliasesIn) {

		super(bot, nameIn, argsIn, helpIn, aliasesIn);

		this.ownerCommand = true; //Makes it so only from my user account, I can run the command. Disreguards any roles what so ever.
	}

}
