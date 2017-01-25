package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class login {

    public TextField ipFld;
    public TextField portFld;
    public TextField nameFld;
    public String sPort;




    public void onClickConn() throws IOException{
        data.ip = ipFld.getText();
        this.sPort = portFld.getText();
        data.name = nameFld.getText();
        data.port = Integer.parseInt(sPort);

        System.out.println(data.ip + data.name + data.port);
        Stage stage;
        stage = (Stage) ipFld.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("chatroom.fxml"));
        stage.setScene(new Scene(parent, 600  , 400));
        stage.setTitle(data.name);
        stage.show();
    }


}
