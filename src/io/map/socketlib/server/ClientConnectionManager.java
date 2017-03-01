package io.map.socketlib.server;

import java.net.Socket;
import java.util.*;

public class ClientConnectionManager {
    private final Conditions conditions;
    private final Callbacks callbacks;
    private final Map<String, ClientConnection> clientMap;

    public ClientConnectionManager(Conditions conditions, Callbacks callbacks) {
        this.conditions = conditions;
        this.callbacks = callbacks;

        clientMap = Collections.synchronizedMap(new HashMap<String, ClientConnection>());
    }

    public void addClientConnection(Socket socket) {
        try {
            if (conditions.acceptCondition(socket)) {
                String clientId = UUID.randomUUID().toString();

                ClientConnection clientConnection =
                        new ClientConnection(this, clientId, socket);
                clientConnection.start();

                clientMap.put(clientId, clientConnection);
                callbacks.onConnect(clientId);

                callbacks.onAccept(clientConnection);
            } else {
                callbacks.onReject(socket);
            }
        } catch (Throwable throwable) {
            callbacks.onError(throwable);
        }
    }

    public void broadcastData(String data) {
        for (Map.Entry<String, ClientConnection> entry : clientMap.entrySet()) {
            entry.getValue().sendData(data);
        }
    }

    public void clientDisconnected(String clientId) {
        clientMap.remove(clientId);
        callbacks.onDisconnect(clientId);
    }

    public interface Conditions {
        boolean acceptCondition(Socket socket);
    }

    public interface Callbacks {
        void onAccept(ClientConnection clientConnection);

        void onReject(Socket socket);

        void onError(Throwable throwable);

        void onConnect(String clientId);

        void onDisconnect(String clientId);
    }
}
