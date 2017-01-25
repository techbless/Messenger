package client;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by chrischang on 2017. 1. 17..
 */
public class chatroom {

    String name;
    private String msg;
    private String rMsg;

    private Socket socket;

    private DataInputStream dis;
    private DataOutputStream dos;

    public TextArea msgFld;
    public TextField message;

    public chatroom() {
        try {
            System.out.println(data.ip + data.port + data.name);
            socket = new Socket(data.ip, data.port);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            this.name = data.name;
            dos.writeUTF(name);
            _receive();
        } catch(IOException e) {
            //e.printStackTrace();
            System.out.println("Error");
        }
    }

    public void onClickSend() {
        try {
            msg = message.getText();
            //msgFld.appendText(name + " : " + msg + "\n");
            System.out.println(name + " : " + msg);
            dos.writeUTF(msg);
            message.setText("");
            message.requestFocus();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void _receive() {
        Thread th = new Thread() {

            @Override
            public void run() {
                while(true) {
                    try {
                        rMsg = dis.readUTF();
                        msgFld.appendText(rMsg + "\n");
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        th.start();
    }

    public void buttonPressed(KeyEvent e)
    {
        if(e.getCode().toString().equals("ENTER"))
        {
            onClickSend();
        }
    }
}
