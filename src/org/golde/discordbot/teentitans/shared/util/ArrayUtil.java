package org.golde.discordbot.teentitans.shared.util;

import java.util.List;

import org.golde.discordbot.teentitans.shared.AbstractTeenTitanBot;

public class ArrayUtil {

	public static final <T> T getRandomObjectFromArray(List<T> arr) {
		return arr.get(AbstractTeenTitanBot.RAND.nextInt(arr.size()));
	}
	
}
