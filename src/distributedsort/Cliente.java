package distributedsort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente implements Serializable{
    public static void main (String args[]){

        try {
            String serverAddress = "127.0.0.1";
            int serverPort = 19000;  
            String linha;
            List<Integer> linhaout = new ArrayList<>();          
            Socket socket = new Socket(serverAddress, serverPort);
            InputStreamReader fRead = new InputStreamReader(new FileInputStream("files/data.txt"), "UTF-8");            
            BufferedReader fReader = new BufferedReader(fRead);
            ObjectOutputStream  objwriter = new ObjectOutputStream(socket.getOutputStream());
            DataOutputStream  datawriter = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream  objreader = new ObjectInputStream(socket.getInputStream());
            
            
            while((linha = fReader.readLine()) != null)
            {   
                if(linha.isEmpty()) 
                {   
                    datawriter.writeInt(1);
                    objwriter.writeObject(linhaout);
                    linhaout = new ArrayList<>();
                    continue;
                }
                linhaout.add(Integer.parseInt(linha));
            }
            
            datawriter.writeInt(0);
            
            List<List<Integer>> listas = (List<List<Integer>>)objreader.readObject();
            
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream("files/dataout.txt"), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            
            for(var l: listas){
                for (var i : l) {
                    writer.write(i.toString() + "\n");                    
                }
                writer.write("\n");
            }
            socket.close();
        }
        catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}
