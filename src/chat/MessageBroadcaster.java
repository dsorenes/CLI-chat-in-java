package chat;

import java.util.HashSet;
import java.util.Set;

class MessageBroadcaster {

    static Set<ClientHandler> clients = new HashSet<>();
    private static MessageBroadcaster broadcaster = null;

    static MessageBroadcaster getInstance() {
        if (broadcaster == null) {
            broadcaster = new MessageBroadcaster();
        }

        return broadcaster;
    }

    void broadcast(ClientHandler sender, String message) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.getClientOutput().println(message);
            }
        }
    }

    void connectedClients(Set<ClientHandler> clients) {
        MessageBroadcaster.clients = clients;
    }
}
