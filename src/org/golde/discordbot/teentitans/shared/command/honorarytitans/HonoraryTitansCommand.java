package org.golde.discordbot.teentitans.shared.command.honorarytitans;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.command.BaseCommand;
import org.golde.discordbot.teentitans.shared.constants.Roles;

public abstract class HonoraryTitansCommand extends BaseCommand {

	//private static final Category CATEGORY_EVERYONE = new Category("Everyone");
	private static final Category CATEGORY_EVERYONE = new Category(Roles.HONORARY_TITANS);
	
	public HonoraryTitansCommand(@Nonnull AbstractTeenTitanBot bot, @Nonnull String nameIn, @Nullable String argsIn, @Nullable String helpIn, @Nullable String... aliasesIn) {
		super(bot, nameIn, argsIn, helpIn, aliasesIn);
		this.category = CATEGORY_EVERYONE;
	}

}
