package org.golde.discordbot.teentitans.brotherhood.command.honorarytitans;

import java.util.ArrayList;
import java.util.List;

import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;
import org.golde.discordbot.teentitans.shared.command.ICanHasDatabaseFile;
import org.golde.discordbot.teentitans.shared.command.honorarytitans.HonoraryTitansCommand;
import org.golde.discordbot.teentitans.shared.util.ArrayUtil;
import org.golde.discordbot.teentitans.shared.util.FileUtil;

import com.jagrosh.jdautilities.command.CommandEvent;

public class CommandRandomQuote extends HonoraryTitansCommand implements ICanHasDatabaseFile {

	private List<Quote> quotes = new ArrayList<Quote>();
	
	public CommandRandomQuote(AbstractTeenTitanBot bot) {
		super(bot, "quote", null, "Give me an insperational quote please!", "randomquote");
	}

	@Override
	protected void execute(CommandEvent event, List<String> args) {
		
		Quote quote = ArrayUtil.getRandomObjectFromArray(quotes);
		
		reply(event.getChannel(), quote.toString());
		
	}

	@Override
	public void reload() {
		quotes.clear();
		quotes = null;
		quotes = FileUtil.loadArrayFromFile("quotes", Quote[].class);
	}

	public static class Quote {
		private String text;
		private String author;
		
		@Override
		public String toString() {
			
			if(author == null) {
				author = "Unknown";
			}
			return text + "\n\n*By: " + author + "*";
		}
	}
	
}
