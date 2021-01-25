package org.golde.discordbot.teentitans.brotherhood;

import java.util.List;

import org.golde.discordbot.teentitans.brotherhood.command.honorarytitans.CommandMC;
import org.golde.discordbot.teentitans.brotherhood.command.honorarytitans.CommandRandomQuote;
import org.golde.discordbot.teentitans.brotherhood.command.teentitans.CommandForceAssignName;
import org.golde.discordbot.teentitans.brotherhood.command.teentitans.CommandPurge;
import org.golde.discordbot.teentitans.brotherhood.command.teentitans.CommandTest;
import org.golde.discordbot.teentitans.brotherhood.events.DefaultRoleListener;
import org.golde.discordbot.teentitans.brotherhood.events.EventWhyTheFuck;
import org.golde.discordbot.teentitans.brotherhood.events.JordanFuckedUpEventListener;
import org.golde.discordbot.teentitans.brotherhood.minecraft.CachedMCServerPinger;
import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.command.honorarytitans.HonoraryTitansCommand;
import org.golde.discordbot.teentitans.shared.command.rosequartz.RoseQuartzCommand;
import org.golde.discordbot.teentitans.shared.command.teentitans.TeenTitansCommand;
import org.golde.discordbot.teentitans.shared.event.EventBase;

public class BrotherhoodOfEvil extends AbstractTeenTitanBot {

	public static void main(String[] args) throws Exception {
		new BrotherhoodOfEvil().run();
	}
	
	@Override
	public String getPrefix() {
		return ";";
	}

	@Override
	public void onReady() {
		CachedMCServerPinger.getInstance().run();
	}

	@Override
	public void onLoad() {

	}

	@Override
	public void onReload() {

	}

	@Override
	public void registerEventListeners(List<EventBase> events) {
		events.add(new DefaultRoleListener(this));
		events.add(new JordanFuckedUpEventListener(this));
	}

	@Override
	public void registerHonoraryTitansCommand(List<HonoraryTitansCommand> cmds) {
		cmds.add(new CommandRandomQuote(this));
		cmds.add(new CommandMC(this));
	}

	@Override
	public void registerTeenTitansCommand(List<TeenTitansCommand> cmds) {
		cmds.add(new CommandPurge(this));
		cmds.add(new CommandTest(this));
		cmds.add(new CommandForceAssignName(this));
	}

	@Override
	public void registerRoseQuartzCommand(List<RoseQuartzCommand> cmds) {
		
	}

}
