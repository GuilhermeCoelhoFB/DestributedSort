package distributedsort;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Servidor {
    public static void main (String args[]) {
        try {
                ServerSocket server = new ServerSocket(19000);
                
                System.out.println("Aguardando conexões...");
                Socket socket = server.accept();
                System.out.println("Conexão estabelecida com " + socket.getInetAddress());
                DataInputStream  datareader = new DataInputStream(socket.getInputStream());
                ObjectInputStream objreader = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream  objwriter = new ObjectOutputStream(socket.getOutputStream());

               
                List<Integer> lista;
                List<List<Integer>> listas =  new ArrayList<>();
                while(datareader.readInt() == 1){
                    lista = (List<Integer>) objreader.readObject();
                    Collections.sort(lista);
                    listas.add(lista);
                }
                
                objwriter.writeObject(listas);
                socket.close();
                server.close();
        }
        catch(ClassNotFoundException | IOException ex) {
        }
    }
}
