package org.golde.discordbot.teentitans.brotherhood.minecraft;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nullable;

import org.golde.discordbot.teentitans.brotherhood.BrotherhoodOfEvil;
import org.golde.discordbot.teentitans.shared.constants.Channels;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.ChannelManager;

public class CachedMCServerPinger {

	private final Timer timer = new Timer();

	@Getter private PingResponse currentCache = getNonNullPing();
	
	private static CachedMCServerPinger instance;
	
	public static final String SERVER_IP = "rjd_gamer.aternos.me";
	//public static final String SERVER_IP = "jumpcraft.mcalias.com"; //testing IP
	
	@Nullable @Setter private InstanceChangeListener changeListener; //should be a list
	
	public static CachedMCServerPinger getInstance() {
		if(instance == null) {
			instance = new CachedMCServerPinger();
		}
		return instance;
	}
	
	private CachedMCServerPinger() {}

	public void run() {

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				pingServer();
			}
		}, 1000, 1000 * 60);



	}

	private void pingServer() {
		try {
			PingResponse newCache = MinecraftPing.getPing(PingOptions.builder().hostname(SERVER_IP).build());
			if(changeListener != null) {
				changeListener.onCacheChanged(currentCache, newCache);
			}
			this.currentCache = newCache;
		} 
		catch (IOException e) {
			e.printStackTrace();
			changeListener.onCacheChanged(this.currentCache, getNonNullPing());
			this.currentCache = getNonNullPing();
		}
	}
	
	private static PingResponse getNonNullPing() {
		PingResponse nonNull = new PingResponse();
		return nonNull;
	}
	
	public static interface InstanceChangeListener {
		public void onCacheChanged(PingResponse oldCache, PingResponse newCache);
	}

}
