package org.alexdev.kepler.messages.incoming.ig;

import org.alexdev.kepler.game.player.Player;
import org.alexdev.kepler.messages.outgoing.ig.FRIEND_REQUESTS;
import org.alexdev.kepler.messages.types.MessageEvent;
import org.alexdev.kepler.server.netty.streams.NettyRequest;

public class MESSENGER_GETREQUESTS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        player.send(new FRIEND_REQUESTS(player.getMessenger().getRequests()));
    }
}
