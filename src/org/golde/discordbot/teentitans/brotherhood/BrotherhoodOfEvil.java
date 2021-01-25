package org.golde.discordbot.teentitans.brotherhood;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.golde.discordbot.teentitans.brotherhood.command.honorarytitans.CommandMC;
import org.golde.discordbot.teentitans.brotherhood.command.honorarytitans.CommandRandomQuote;
import org.golde.discordbot.teentitans.brotherhood.command.teentitans.CommandForceAssignName;
import org.golde.discordbot.teentitans.brotherhood.command.teentitans.CommandPurge;
import org.golde.discordbot.teentitans.brotherhood.command.teentitans.CommandTest;
import org.golde.discordbot.teentitans.brotherhood.events.DefaultRoleListener;
import org.golde.discordbot.teentitans.brotherhood.events.JordanFuckedUpEventListener;
import org.golde.discordbot.teentitans.brotherhood.minecraft.CachedMCServerPinger;
import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.command.honorarytitans.HonoraryTitansCommand;
import org.golde.discordbot.teentitans.shared.command.rosequartz.RoseQuartzCommand;
import org.golde.discordbot.teentitans.shared.command.teentitans.TeenTitansCommand;
import org.golde.discordbot.teentitans.shared.event.EventBase;

import net.dv8tion.jda.api.entities.Activity;

public class BrotherhoodOfEvil extends AbstractTeenTitanBot {

	public static void main(String[] args) throws Exception {
		new BrotherhoodOfEvil().run();
	}

	private static final String PREFIX = ";";
	
	private static final Activity[] ACTIVITIES = {
			Activity.listening(PREFIX + "help"),
			Activity.watching("Cyborg struggle to debug his code"),
			Activity.watching("Beast Boy work on his word game"),
			Activity.watching("Teen Titans"),
			Activity.playing("$10 that Robin watching sports currently"),
			Activity.playing("Slade has nothing on me"),
			Activity.listening("Evil Villain Music")
			
	};

	//randomize these messages
	static {
		List<Activity> statusList = Arrays.asList(ACTIVITIES);

		Collections.shuffle(statusList);

		statusList.toArray(ACTIVITIES);
	}

	private int currentActivity;

	@Override
	public String getPrefix() {
		return PREFIX;
	}

	@Override
	public void onReady() {
		CachedMCServerPinger.getInstance().run();
		getJda().getPresence().setActivity(Activity.watching("Cyborg debug my codebase"));
		
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				
				if(currentActivity > ACTIVITIES.length - 1) {
					currentActivity = 0;
				}
				
				getJda().getPresence().setActivity(ACTIVITIES[currentActivity]);
				
				currentActivity++;
				
			}
		}, 0, 60000);
		
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
