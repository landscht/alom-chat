package fr.projet.alom;

import fr.projet.alom.request.Request;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private final static int PORT = 1234;
    private boolean running = false;

    public void run() throws IOException {
        System.out.println("\u001B[32mLe serveur à démarrer sur le port " + PORT + "...\u001B[32m");
        ServerSocket serverSocket = new ServerSocket(PORT);
        this.running = true;
        while(this.running) {
            Request request = new Request(serverSocket.accept());
            new Thread(new Session(request)).start();
        }
    }

}
