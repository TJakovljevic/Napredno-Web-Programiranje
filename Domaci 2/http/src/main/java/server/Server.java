package server;

import di.DIEngine;
import di.DependencyContainer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int TCP_PORT = 8080;

    public static void main(String[] args) throws IOException {


        //morao sam ovako da odradim jer ukoliko bi ovo radio unutar serverThread klase, onda bi mi svaki put pravio novu instancu DiEngine-a
        DependencyContainer container = new DependencyContainer();
        DIEngine diEngine = new DIEngine(container);



        try {
            ServerSocket serverSocket = new ServerSocket(TCP_PORT);
            System.out.println("Server is running at http://localhost:"+TCP_PORT);
            //Opet je fora da se inicijalizuje samo 1, a ne svaki put pri pozivu serverThread-a
            diEngine.initialize();
            while(true){
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket, diEngine)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
