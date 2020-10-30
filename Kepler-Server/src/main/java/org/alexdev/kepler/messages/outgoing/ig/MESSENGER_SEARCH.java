package org.alexdev.kepler.messages.outgoing.ig;

import org.alexdev.kepler.game.player.Player;
import org.alexdev.kepler.game.player.PlayerDetails;
import org.alexdev.kepler.game.player.PlayerManager;
import org.alexdev.kepler.messages.types.MessageComposer;
import org.alexdev.kepler.server.netty.streams.NettyResponse;
import org.alexdev.kepler.util.DateUtil;

import java.util.List;

public class MESSENGER_SEARCH extends MessageComposer {
    private final List<PlayerDetails> friends;
    private final List<PlayerDetails> others;

    public MESSENGER_SEARCH(List<PlayerDetails> friends, List<PlayerDetails> others) {
        this.friends = friends;
        this.others = others;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.friends.size());

        for (PlayerDetails playerDetails : this.friends) {
            this.serialiseSearch(response, playerDetails);
        }

        response.writeInt(this.others.size());

        for (PlayerDetails playerDetails : this.others) {
            this.serialiseSearch(response, playerDetails);
        }
    }

    private void serialiseSearch(NettyResponse response, PlayerDetails playerDetails) {
        response.writeInt(playerDetails.getId());
        response.writeString(playerDetails.getName());
        response.writeString(playerDetails.getMotto());

        Player player = PlayerManager.getInstance().getPlayerById(playerDetails.getId());
        boolean isOnline = player != null;

        response.writeBool(isOnline);
        response.writeBool(isOnline && player.getRoomUser().getRoom() != null);
        response.writeString((isOnline && player.getRoomUser().getRoom() != null) ? player.getRoomUser().getRoom().getData().getName() : "");

        response.writeBool(Character.toString(playerDetails.getSex()).equals("M"));
        response.writeString(isOnline ? playerDetails.getFigure() : "");
        response.writeString(DateUtil.getDateAsString(playerDetails.getLastOnline()));
    }

    @Override
    public short getHeader() {
        return 435; // Fs
    }
}
