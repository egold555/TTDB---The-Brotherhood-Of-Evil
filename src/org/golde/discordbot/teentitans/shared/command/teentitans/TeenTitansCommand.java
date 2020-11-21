package org.golde.discordbot.teentitans.shared.command.teentitans;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.command.BaseCommand;
import org.golde.discordbot.teentitans.shared.constants.Roles;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public abstract class TeenTitansCommand extends BaseCommand {

	private static final Category CATEGORY_CHAT_MODERATOR = new Category(Roles.TEEN_TITANS);
	
	public TeenTitansCommand(@Nonnull AbstractTeenTitanBot bot, @Nonnull String nameIn, @Nullable String argsIn, @Nullable String helpIn, @Nullable String... aliasesIn) {
		super(bot, nameIn, argsIn, helpIn, aliasesIn);
		this.category = CATEGORY_CHAT_MODERATOR;
		this.requiredRole = CATEGORY_CHAT_MODERATOR.getRoleIdLong();
	}
	
	public final static boolean isModerator(Member person) {
		for(Role r : person.getRoles()) {
			if(r.getIdLong() == CATEGORY_CHAT_MODERATOR.getRoleIdLong()) {
				return true;
			}
		}
		return false;
	}
	
	
}
