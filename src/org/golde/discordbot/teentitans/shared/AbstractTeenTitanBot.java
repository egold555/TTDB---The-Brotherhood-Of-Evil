package org.golde.discordbot.teentitans.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.golde.discordbot.teentitans.shared.command.BaseCommand;
import org.golde.discordbot.teentitans.shared.command.ICanHasDatabaseFile;
import org.golde.discordbot.teentitans.shared.command.honorarytitans.CommandHelp;
import org.golde.discordbot.teentitans.shared.command.honorarytitans.HonoraryTitansCommand;
import org.golde.discordbot.teentitans.shared.command.rosequartz.RoseQuartzCommand;
import org.golde.discordbot.teentitans.shared.command.teentitans.CommandPing;
import org.golde.discordbot.teentitans.shared.command.teentitans.CommandReload;
import org.golde.discordbot.teentitans.shared.command.teentitans.TeenTitansCommand;
import org.golde.discordbot.teentitans.shared.event.EventBase;
import org.golde.discordbot.teentitans.shared.util.FileUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public abstract class AbstractTeenTitanBot {

	private JDA jda;

	private Guild guild;

	private static final long OWNER_ID = 338884828936142849L;
	
	private EventWaiter waiter;
	
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	public static final Random RAND = new Random();
	
	private List<ICanHasDatabaseFile> databaseCommands = new ArrayList<ICanHasDatabaseFile>();

	public void run() throws Exception {
		
		String token = FileUtil.readGenericConfig("config", false).get(0);

		// define an eventwaiter, dont forget to add this to the JDABuilder!
		waiter = new EventWaiter();

		// define a command client
		CommandClientBuilder client = new CommandClientBuilder();

		client.useHelpBuilder(false);

		// sets the owner of the bot
		client.setOwnerId(String.valueOf(OWNER_ID));

		// sets emojis used throughout the bot on successes, warnings, and failures
		client.setEmojis("\uD83D\uDE03", "\uD83D\uDE2E", "\uD83D\uDE26");

		// sets the bot prefix
		client.setPrefix(getPrefix());
		
		//HonoraryTitans
		List<HonoraryTitansCommand> honoraryTitansCommand = new ArrayList<HonoraryTitansCommand>();
		honoraryTitansCommand.add(new CommandHelp(this));
		
		registerHonoraryTitansCommand(honoraryTitansCommand);
		for(HonoraryTitansCommand cmd : honoraryTitansCommand) {
			client.addCommand(cmd);
			tryInterfaceThings(cmd);
		}
		
		//TeenTitans
		List<TeenTitansCommand> teenTitansCommand = new ArrayList<TeenTitansCommand>();
		teenTitansCommand.add(new CommandPing(this));
		teenTitansCommand.add(new CommandReload(this));
		registerTeenTitansCommand(teenTitansCommand);
		for(TeenTitansCommand cmd : teenTitansCommand) {
			client.addCommand(cmd);
			tryInterfaceThings(cmd);
		}
		
		//RoseQuartz
		List<RoseQuartzCommand> roseQuartzCommands = new ArrayList<RoseQuartzCommand>();
		registerRoseQuartzCommand(roseQuartzCommands);
		for(RoseQuartzCommand cmd : roseQuartzCommands) {
			client.addCommand(cmd);
			tryInterfaceThings(cmd);
		}

		// start getting a bot account set up
		JDABuilder builder = JDABuilder.createDefault(token, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_INVITES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_PRESENCES)

				.setMemberCachePolicy(MemberCachePolicy.ALL)
				// set the game for when the bot is loading
				.setStatus(OnlineStatus.DO_NOT_DISTURB)
				.setActivity(Activity.playing("Loading..."))
				// add the listeners
				.addEventListeners(waiter, client.build())

				.addEventListeners(new ListenerAdapter() {

					@Override
					public void onReady(ReadyEvent event) {

						jda.getPresence().setActivity(Activity.listening(getPrefix() + "help"));
						guild = event.getJDA().getGuilds().get(0); //only one guild
						AbstractTeenTitanBot.this.onReady();
					}

				});

		List<EventBase> eventList = new ArrayList<EventBase>();
		registerEventListeners(eventList);
		for(EventBase listener : eventList) {
			builder.addEventListeners(listener);
			tryInterfaceThings(listener);
		}
		onLoadInternal();
		// start it up!
		jda = builder.build();
	}

	

	private void tryInterfaceThings(EventBase listener) {
		if(listener instanceof ICanHasDatabaseFile) {
			ICanHasDatabaseFile ican = (ICanHasDatabaseFile) listener;
			databaseCommands.add(ican);
		}
	}

	private void tryInterfaceThings(BaseCommand cmd) {
		if(cmd instanceof ICanHasDatabaseFile) {
			ICanHasDatabaseFile ican = (ICanHasDatabaseFile) cmd;
			databaseCommands.add(ican);
		}
	}

	public abstract String getPrefix();
	public abstract void onReady();
	public abstract void onLoad();
	public abstract void onReload();
	public abstract void registerEventListeners(List<EventBase> events);
	public abstract void registerHonoraryTitansCommand(List<HonoraryTitansCommand> cmds);
	public abstract void registerTeenTitansCommand(List<TeenTitansCommand> cmds);
	public abstract void registerRoseQuartzCommand(List<RoseQuartzCommand> cmds);
	

	public final JDA getJda() {
		return jda;
	}

	public final Guild getGuild() {
		return guild;
	}

	public static final long getOwnerId() {
		return OWNER_ID;
	}
	
	public final EventWaiter getWaiter() {
		return waiter;
	}

	public void onReloadBase() {
		onReload();
		for(ICanHasDatabaseFile ican : databaseCommands) {
			ican.reload();
		}
	}

	private void onLoadInternal() {
		onLoad();
		for(ICanHasDatabaseFile ican : databaseCommands) {
			ican.loadOnce();
		}
	}
	
}

