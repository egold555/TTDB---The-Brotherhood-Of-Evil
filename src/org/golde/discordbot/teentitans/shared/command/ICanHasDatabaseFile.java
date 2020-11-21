package org.golde.discordbot.teentitans.shared.command;

public interface ICanHasDatabaseFile {

	public default void loadOnce() {reload();};
	public void reload();
	
}
