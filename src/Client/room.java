package Client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class room {
    public static Thread th;
    Socket sock;
    DataOutputStream dos;
    DataInputStream dis;

    @FXML
    public TextField myMsg;
    @FXML
    public TextArea chatLog;

    public room() {
        try {

            sock = new Socket(data.ip, data.port);
            dos = new DataOutputStream(sock.getOutputStream());
            dis = new DataInputStream(sock.getInputStream());

            dos.writeUTF(data.name);
            /*
            * This Thread let the client recieve the message from the server for any time;
            */
            th = new Thread(() -> {
                try {

                    JSONParser parser = new JSONParser();

                    while(true) {
                        String newMsgJson = dis.readUTF();

                        System.out.println("RE : " + newMsgJson);
                        Message newMsg = new Message();

                        Object obj = parser.parse(newMsgJson);
                        JSONObject msg = (JSONObject) obj;

                        newMsg.setName((String) msg.get("name"));
                        newMsg.setMessage((String) msg.get("message"));

                        chatLog.appendText(newMsg.getName() + " : " + newMsg.getMessage() + "\n");
                    }
                } catch(Exception E) {
                    E.printStackTrace();
                }

            });

            th.start();

        } catch(IOException E) {
            E.printStackTrace();
        }

    }

    public void onClickSend() {
        try {
            String msg = myMsg.getText();

            //String json = "{" + " 'name' : '" + data.name + "', 'message' : '" + msg + "'" + "}";

            JSONObject js = new JSONObject();
            js.put("name", data.name);
            js.put("message", msg);

            String json = js.toJSONString();


            System.out.println(json);

            dos.writeUTF(json);
            myMsg.setText("");
            myMsg.requestFocus();

        } catch(IOException E) {
            E.printStackTrace();
        }

    }

    public void buttonPressed(KeyEvent e) {
        if(e.getCode().toString().equals("ENTER"))
        {
            onClickSend();
        }
    }
}

