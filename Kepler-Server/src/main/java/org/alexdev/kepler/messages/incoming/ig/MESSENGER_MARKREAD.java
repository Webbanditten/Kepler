package org.alexdev.kepler.messages.incoming.ig;

import org.alexdev.kepler.dao.mysql.IGDao;
import org.alexdev.kepler.game.player.Player;
import org.alexdev.kepler.messages.types.MessageEvent;
import org.alexdev.kepler.server.netty.streams.NettyRequest;

import java.sql.SQLException;

public class MESSENGER_MARKREAD implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException {
        int messageId = reader.readInt();

        IGDao.markMessageRead(messageId);
        player.getMessenger().getOfflineMessages().remove(messageId);
    }
}
