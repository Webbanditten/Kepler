package org.alexdev.kepler.messages.incoming.ig;

import org.alexdev.kepler.game.ig.Messenger;
import org.alexdev.kepler.game.ig.MessengerManager;
import org.alexdev.kepler.game.player.Player;
import org.alexdev.kepler.messages.outgoing.ig.MESSENGER_INIT;
import org.alexdev.kepler.messages.types.MessageEvent;
import org.alexdev.kepler.server.netty.streams.NettyRequest;

public class MESSENGERINIT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        Messenger messenger = MessengerManager.getInstance().getMessengerData(player.getDetails().getId());
        player.send(new MESSENGER_INIT(player, messenger));
    }
}
