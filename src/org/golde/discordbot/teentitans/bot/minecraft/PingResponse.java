package org.golde.discordbot.teentitans.bot.minecraft;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//http://wiki.vg/Server_List_Ping
@Getter
@ToString
public class PingResponse {

    private Players players;
    private Version version;
    //private String favicon;
    @Setter private long ping;

    @Getter
    @ToString
    public class Players {
        private int max;
        private int online;
        private List<Player> sample;

    }

    @Getter
    @ToString
    public class Player {
        private String name;
        private String id;
    }

    @Getter
    @ToString
    public class Version {
        private String name;
        private int protocol;
    }

}
