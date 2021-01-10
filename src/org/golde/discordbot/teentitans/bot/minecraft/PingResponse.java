package org.golde.discordbot.teentitans.bot.minecraft;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//http://wiki.vg/Server_List_Ping
@Getter
@ToString
public class PingResponse {

    /**
     * @return @{link Players}
     */
    private Players players;

    /**
     * @return @{link Version}
     */
    private Version version;

    /**
     * @return Base64 encoded favicon image
     */
    private String favicon;

    /**
     * @return Ping in ms.
     */
    @Setter
    private long ping;

    @Getter
    @ToString
    public class Players {

        /**
         * @return Maximum player count
         */
        private int max;

        /**
         * @return Online player count
         */
        private int online;

        /**
         * @return List of some players (if any) specified by server
         */
        private List<Player> sample;

    }

    @Getter
    @ToString
    public class Player {

        /**
         * @return Name of player
         */
        private String name;

        /**
         * @return Unknown
         */
        private String id;

    }

    @Getter
    @ToString
    public class Version {

        /**
         * @return Version name (ex: 13w41a)
         */
        private String name;
        /**
         * @return Protocol version
         */
        private int protocol;

    }

}
