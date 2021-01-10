package org.golde.discordbot.teentitans.bot.events;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.golde.discordbot.teentitans.bot.BrotherhoodOfEvil;
import org.golde.discordbot.teentitans.bot.minecraft.MinecraftPing;
import org.golde.discordbot.teentitans.bot.minecraft.PingOptions;
import org.golde.discordbot.teentitans.bot.minecraft.PingResponse;
import org.golde.discordbot.teentitans.shared.constants.Channels;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.ChannelManager;

public class MinecraftServerPinger {

	private boolean lastState = true;
	private final BrotherhoodOfEvil bot;
	private final Timer timer = new Timer();

	public MinecraftServerPinger(BrotherhoodOfEvil bot) {
		this.bot = bot;
	}

	public void run() {

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				pingServer();
			}
		}, 5000, 1000 * 60);



	}

	private void pingServer() {
		try {
			PingResponse resp = MinecraftPing.getPing(PingOptions.builder().hostname("rjd_gamer.aternos.me").build());
			boolean isOnline = resp.getPlayers().getMax() != 0; //Aternos o_0

			if(lastState != isOnline) {
				lastState = isOnline;
				TextChannel mcChannel = bot.getGuild().getTextChannelById(Channels.TextChannels.MINECRAFT);
				mcChannel.sendMessage("<@!199652118100049921> Server changed status: " + (isOnline ? "ONLINE" : "OFFLINE")).queue();
//				ChannelManager cm = mcChannel.getManager();
//				cm.setTopic("Online: " + isOnline + " | Players: " + resp.getPlayers().getOnline() + " / " + resp.getPlayers().getMax());
//				cm.queue();
			}

			System.out.println(resp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
