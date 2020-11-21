package org.golde.discordbot.teentitans.shared.command.rosequartz;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.command.BaseCommand;
import org.golde.discordbot.teentitans.shared.constants.Roles;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public abstract class RoseQuartzCommand extends BaseCommand {
	
	private static final Category CATEGORY_OWNER = new Category(Roles.ROSE_QUARTZ);
	
	public RoseQuartzCommand(@Nonnull AbstractTeenTitanBot bot, @Nonnull String nameIn, @Nullable String argsIn, @Nullable String helpIn, @Nullable String... aliasesIn) {
		
		super(bot, nameIn, argsIn, helpIn, aliasesIn);

		this.category = CATEGORY_OWNER;
		this.requiredRole = CATEGORY_OWNER.getRoleIdLong();
	}
	
	public final static boolean isOwner(Member person) {
		for(Role r : person.getRoles()) {
			if(r.getIdLong() == CATEGORY_OWNER.getRoleIdLong()) {
				return true;
			}
		}
		return false;
	}
	
}
