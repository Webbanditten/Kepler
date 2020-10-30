package org.alexdev.kepler.messages.outgoing.ig;

import org.alexdev.kepler.game.ig.MessengerUser;
import org.alexdev.kepler.game.player.Player;
import org.alexdev.kepler.messages.types.MessageComposer;
import org.alexdev.kepler.server.netty.streams.NettyResponse;

import java.util.List;

public class FRIENDLIST extends MessageComposer {
    private final Player player;
    private final List<MessengerUser> friends;

    public FRIENDLIST(Player player, List<MessengerUser> friends) {
        this.player = player;
        this.friends = friends;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.friends.size());

        for (MessengerUser friend : this.friends) {
            friend.serialise(player, response);
        }
    }

    @Override
    public short getHeader() {
        return 263; // "DG"
    }
}