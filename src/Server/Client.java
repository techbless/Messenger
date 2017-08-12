package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Client {
    String name;
    String message;
    private DataOutputStream dos;

    public DataOutputStream getDos() {
        return dos;
    }



    Client(String name, DataOutputStream dos, DataInputStream dis) {
        this.name = name;
        this.dos = dos;

        new Thread(() -> {
            try {
                while(true) {
                    message = dis.readUTF();
                    System.out.println(message);
                    List<Client> entry = Server.clients;
                    for (Client cli : entry) {
                        DataOutputStream edos = cli.getDos();
                        edos.writeUTF(message);
                    }
                }
            } catch (IOException E) {
                try {
                    dis.close();
                    dos.close();
                    Server.clients = Server.clients.stream()
                                .filter(e -> {
                                    if(!(e == this)) {
                                        String exit_message = "{ \"name\" : \"" + "[ SERVER NOTICE ]" + "\", \"message\" : \"" + name + " Disconnected" + "\"}";
                                        System.out.println(exit_message);
                                        try {
                                            e.getDos().writeUTF(exit_message);
                                        } catch (IOException err) {
                                            err.printStackTrace();
                                        }
                                    }
                                    return !(e == this);
                                    })
                                .collect(Collectors.toList());

                    System.out.println("[Current User : " + Server.clients.size() + "]");

                } catch(IOException E2) {
                    E2.printStackTrace();
                }
            }
        }).start();
    }



}
