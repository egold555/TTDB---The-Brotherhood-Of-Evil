package org.golde.discordbot.teentitans.brotherhood.minecraft;

import lombok.Builder;
import lombok.Getter;

@Builder
public class PingOptions {

    @Getter
    private String hostname;

    @Getter
    @Builder.Default
    private int port = 25565;

    @Getter
    @Builder.Default
    private int timeout = 5000;

}
