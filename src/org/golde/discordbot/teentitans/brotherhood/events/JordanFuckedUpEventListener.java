package org.golde.discordbot.teentitans.brotherhood.events;

import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.constants.Channels;
import org.golde.discordbot.teentitans.shared.constants.Roles;
import org.golde.discordbot.teentitans.shared.event.EventBase;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.AllowedMentions;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class JordanFuckedUpEventListener extends EventBase {

	public static final long USER_ID = 700409966464598028L;
	
	private static final String[] WORDS = {
		"substance",
		"substenance",
		"substences"
	};
	
	public JordanFuckedUpEventListener(AbstractTeenTitanBot bot) {
		super(bot);
	}

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		Member mem = event.getMember();
		Guild g = event.getGuild();
		
		if(mem.getIdLong() == USER_ID) {
			mem.modifyNickname("Terra").queue(success -> {
				g.getTextChannelById(Channels.TextChannels.TITANS_TOWER).sendMessage("Welcome to the Teen Titans Guild " + mem.getAsMention() + "!").queue();
			});
		}
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		Guild g = event.getGuild();
		final Message msg = event.getMessage();
		Member mem = event.getMember();
		TextChannel tc = event.getChannel();
		
		if(mem == null || mem.getUser() == null || mem.getUser().isBot() || mem.getUser().isFake()) {
			return;
		}
		
		if(tc.getIdLong() == Channels.TextChannels.AZERATH) {
			return;
		}
		
		if(mem.getRoles().contains(g.getRoleById(Roles.TEEN_TITANS))) {
			
			for(String word : WORDS) {
				if(msg.getContentStripped().toLowerCase().contains(word)) {
					
					try {
						sendAsWebhook(g.getTextChannelById(Channels.TextChannels.AZERATH), mem, msg.getContentRaw());
					}
					catch(Throwable e) {
						g.getTextChannelById(Channels.TextChannels.TITANS_TOWER).sendMessage("Perms are fucked: " + e.getMessage()).queue();
					}
					msg.delete().queue();
					
				}
			}
			
		}
		
		
		
	}
	
	private void sendAsWebhook(TextChannel tc, Member sender, String text) {
		tc.createWebhook(sender.getEffectiveName()).queue(onWebHookComplete -> {

			

			WebhookClientBuilder builder = new WebhookClientBuilder(onWebHookComplete.getUrl());
			builder.setThreadFactory((job) -> {
				Thread thread = new Thread(job);
				thread.setName("WebHook Thread");
				thread.setDaemon(true);
				return thread;
			});
			builder.setWait(true);

			WebhookClient client = builder.build();

			WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();
			
			messageBuilder.setAvatarUrl(sender.getUser().getEffectiveAvatarUrl());
			
			messageBuilder.setContent(text);
			
			messageBuilder.setAllowedMentions(AllowedMentions.all());
			
			
			
			WebhookMessage message = messageBuilder.build();
			client.send(message).thenAccept(onSuccess -> {
				
				client.close();
				onWebHookComplete.delete().queue();

			});

		});
	}
	
}
