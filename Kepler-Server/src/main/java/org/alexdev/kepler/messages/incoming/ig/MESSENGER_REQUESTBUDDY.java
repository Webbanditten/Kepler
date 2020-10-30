package org.alexdev.kepler.messages.incoming.ig;

import org.alexdev.kepler.game.ig.Messenger;
import org.alexdev.kepler.game.ig.MessengerError;
import org.alexdev.kepler.game.ig.MessengerErrorType;
import org.alexdev.kepler.game.ig.MessengerManager;
import org.alexdev.kepler.game.player.Player;
import org.alexdev.kepler.messages.outgoing.ig.MESSENGER_ERROR;
import org.alexdev.kepler.messages.types.MessageEvent;
import org.alexdev.kepler.server.netty.streams.NettyRequest;

public class MESSENGER_REQUESTBUDDY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        String username = reader.readString();
        Messenger target = MessengerManager.getInstance().getMessengerData(username);

        if (target == null) {
            // Error type in external texts has it defined as "There was an error finding the user for the friend request"
            player.send(new MESSENGER_ERROR(new MessengerError(MessengerErrorType.FRIEND_REQUEST_NOT_FOUND)));
            return;
        }

        Messenger callee = player.getMessenger();

        if (username.toLowerCase().equals(player.getDetails().getName())) {
            return;
        }

        if (callee.isFriendsLimitReached()) {
            player.send(new MESSENGER_ERROR(new MessengerError(MessengerErrorType.FRIENDLIST_FULL)));
            return;
        }

        if (target.hasFriend(player.getDetails().getId())) {
            return;
        }

        if (target.hasRequest(player.getDetails().getId())) {
            return;
        }

        if (target.isFriendsLimitReached()) {
            player.send(new MESSENGER_ERROR(new MessengerError(MessengerErrorType.TARGET_FRIEND_LIST_FULL)));
            return;
        }

        if (!target.allowsFriendRequests()) {
            player.send(new MESSENGER_ERROR(new MessengerError(MessengerErrorType.TARGET_DOES_NOT_ACCEPT)));
            return;
        }


        target.addRequest(callee.getMessengerUser());
    }
}
