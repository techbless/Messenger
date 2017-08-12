package Client;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class login {
    @FXML public TextField server_ip;
    @FXML public TextField port;
    @FXML public TextField name;
    @FXML public String sPort;




    public void onClick() throws IOException{
        System.out.println("Clicked");
        data.ip = server_ip.getText();
        this.sPort = port.getText();
        data.name = name.getText();
        data.port = Integer.parseInt(sPort);


        Stage stage;
        stage = (Stage) server_ip.getScene().getWindow();
        //Parent parent = FXMLLoader.load(getClass().getResource("room.fxml"));
        Parent root = FXMLLoader.load(login.class.getResource("room.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle(data.name);
        stage.setOnCloseRequest(e-> {
            //e.consume();
            room.th.stop();
            System.exit(0);
        });
        stage.setResizable(false);

        stage.show();
    }


}
