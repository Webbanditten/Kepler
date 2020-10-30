package org.alexdev.kepler.messages.incoming.ig;

import org.alexdev.kepler.game.ig.MessengerUser;
import org.alexdev.kepler.game.player.Player;
import org.alexdev.kepler.messages.types.MessageEvent;
import org.alexdev.kepler.server.netty.streams.NettyRequest;

public class MESSENGER_DECLINEBUDDY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        boolean declineAll = reader.readBoolean();

        if (declineAll) {
            player.getMessenger().declineAllRequests();
            return;
        }

        int amount = reader.readInt();

        for (int i = 0; i < amount; i++) {
            int userId = reader.readInt();

            if (!player.getMessenger().hasRequest(userId)) {
                continue;
            }

            MessengerUser requester = player.getMessenger().getRequest(userId);
            player.getMessenger().declineRequest(requester);
        }
    }
}
