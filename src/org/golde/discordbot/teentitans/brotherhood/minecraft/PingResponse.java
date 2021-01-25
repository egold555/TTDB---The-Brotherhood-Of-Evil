package org.golde.discordbot.teentitans.brotherhood.minecraft;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//http://wiki.vg/Server_List_Ping
@Getter
@ToString
public class PingResponse {

    private Players players = new Players();
    private Version version = new Version();
    //private String favicon;
    @Setter private long ping = -1;

    @Getter
    @ToString
    public class Players {
        private int max = 0;
        private int online = 0;
        private List<Player> sample = new ArrayList<PingResponse.Player>();

    }

    @Getter
    @ToString
    public class Player {
        private String name = "null";
        private String id = "null";
    }

    @Getter
    @ToString
    public class Version {
        private String name = "null";
        private int protocol = -1;
    }

}
