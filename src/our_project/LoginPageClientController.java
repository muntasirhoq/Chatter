/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginPageClientController implements Initializable {
    
    String nameString;
    public static String sendName;
    @FXML
    public TextField userName;
    @FXML
    public PasswordField password;
    @FXML
    private Button newAccount;
    @FXML
    private Button reset;
    @FXML
    private Button login;
    //Alert alert = new Alert(Alert.AlertType.ERROR);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //ChatBoxClientController.getController(this);
        ReadThreadClient.getController(this);
    }    

    @FXML
    private void newAccountHandle(ActionEvent event) throws Exception {
        main.openPage();
    }

    @FXML
    private void resetHandle(ActionEvent event) {
        userName.setText("");
        password.setText("");
    }

    @FXML
    private void loginHandle(ActionEvent event) throws Exception{
        String strUser=userName.getText()+","+password.getText();
        nameString=userName.getText();
        sendName=nameString;
        new clientThread(nameString,strUser);           
    }
    /*public void showError(String errorString) throws Exception
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText(errorString);
        alert.showAndWait();
    }*/
    public static clientMain main;
    public static void setMain(clientMain m) {main = m;}
    public static String send_name()
    {
        return sendName;
    }
}
