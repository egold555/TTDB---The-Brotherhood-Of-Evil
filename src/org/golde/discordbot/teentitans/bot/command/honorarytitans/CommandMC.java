package org.golde.discordbot.teentitans.bot.command.honorarytitans;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.golde.discordbot.teentitans.bot.minecraft.CachedMCServerPinger;
import org.golde.discordbot.teentitans.bot.minecraft.CachedMCServerPinger.InstanceChangeListener;
import org.golde.discordbot.teentitans.bot.minecraft.PingResponse;
import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.command.ICanHasDatabaseFile;
import org.golde.discordbot.teentitans.shared.command.honorarytitans.HonoraryTitansCommand;
import org.golde.discordbot.teentitans.shared.constants.Channels;
import org.golde.discordbot.teentitans.shared.util.FileUtil;

import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandMC extends HonoraryTitansCommand implements ICanHasDatabaseFile {

	private List<String> idsToPing; //could be a long.. but i want to make sure it doesn't get messed up

	private static final String ID_FILENAME = "mc-pingme";
	
	public CommandMC(AbstractTeenTitanBot bot) {
		super(bot, "mc", "<status | pingme | players>", "Minecraft server ping utilities");

		CachedMCServerPinger.getInstance().setChangeListener(new InstanceChangeListener() {

			@Override
			public void onCacheChanged(PingResponse oldCache, PingResponse newCache) {
				onCacheChanged2(oldCache, newCache);
			}
		});

	}

	@Override
	protected void execute(CommandEvent event, List<String> args) {
		TextChannel tc = event.getTextChannel();
		Member mem = event.getMember();
		
		if(args.size() != 2) {
			replyError(tc, "Please supply a sub argument: " + this.arguments);
			return;
		}

		String subArg = args.get(1).toLowerCase();

		if(subArg.equals("status") || subArg.equals("stats")) {

			PingResponse pingResponse = CachedMCServerPinger.getInstance().getCurrentCache();

			boolean online = isOnlineBecauseAternosIsStrange(pingResponse);

			EmbedBuilder embed = new EmbedBuilder();
			embed.addField("Online", Boolean.toString(online), true);
			embed.addField("Player Count", pingResponse.getPlayers().getOnline() + " / " + pingResponse.getPlayers().getMax(), true);

			embed.addField("MC Version", online ? pingResponse.getVersion().getName() : "???", true);
			embed.addField("IP", CachedMCServerPinger.SERVER_IP, true);

			embed.setColor(online ? Color.GREEN : Color.RED);

			reply(tc, embed);

		}

		if(subArg.contains("pingme")) {

			String playerID = mem.getId();
			
			if(idsToPing.contains(playerID)) {
				idsToPing.remove(playerID);
				replyWarning(tc, "Success", "Removed you from the ping list!");
			}
			else {
				idsToPing.add(playerID);
				replySuccess(tc, "Added you to the ping list!");
			}
			
			FileUtil.saveToFile(idsToPing, ID_FILENAME);
			
		}

		if(subArg.equals("players") || subArg.equals("online")) {

			List<String> players = new ArrayList<String>();
			PingResponse resp = CachedMCServerPinger.getInstance().getCurrentCache();
			if(resp.getPlayers() != null) {
				for(PingResponse.Player player: resp.getPlayers().getSample()) {
					players.add(player.getName());
				}
			}
			
			String allUsers = String.join(",\n", players);
			
			reply(tc, "Players Online", allUsers);
			
		}

	}

	private static Boolean isOnlineBecauseAternosIsStrange(PingResponse pr) {

		if(pr.getPing() == -1) {
			return false;
		}


		return pr.getPlayers().getMax() != 0;
	}

	private void onCacheChanged2(PingResponse oldCache, PingResponse newCache) {

		Boolean oldOnline = isOnlineBecauseAternosIsStrange(oldCache);
		Boolean newOnline = isOnlineBecauseAternosIsStrange(newCache);

//		System.out.println(oldCache + " " + newCache);
//		System.out.println(oldOnline + " " + newOnline);

		if((oldOnline != newOnline)) {
			//ping members, the server is online

			Guild g = bot.getGuild();

			List<String> mentionable = new ArrayList<String>();

			for(String id : idsToPing) {
				mentionable.add(g.getMemberById(id).getAsMention());
			}

			String allUsers = String.join(", ", mentionable);
			 
			if(allUsers == null || allUsers.isEmpty()) {
				allUsers = "Nobody";
			}

			g.getTextChannelById(Channels.TextChannels.MINECRAFT).sendMessage("Hey " + allUsers + " : The server is up!").queue();;

		}

	}

	@Override
	public void reload() {
		if(idsToPing != null) {
			idsToPing.clear();
		}

		idsToPing = null;
		idsToPing = FileUtil.loadArrayFromFile(ID_FILENAME, String[].class);
	}


}
