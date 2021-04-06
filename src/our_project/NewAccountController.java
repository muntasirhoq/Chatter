/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Formatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class NewAccountController implements Initializable {
    
    String nameString,passwordString,passwordcheck;
    @FXML
    private TextField usernameNew;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordCheck;
    @FXML
    private Button finish;
    @FXML
    private Label accountLabel;
    @FXML
    private Button back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void finishHandle(ActionEvent event) throws Exception{
        nameString=usernameNew.getText();
        passwordString=password.getText();
        passwordcheck=passwordCheck.getText();
        if(!passwordString.equals(passwordcheck))
        {
            usernameNew.setText("");
            password.setText("");
            passwordCheck.setText("");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Incorrect Credentials");
            alert.setContentText("rewrite password correctly");
            alert.showAndWait();
        }
        else
        {
            new newAccountThread(nameString,passwordString);
            String determine=clientMain.query.get();
            if(determine.startsWith("1000") && passwordString.equals(passwordcheck))
            {
                main.loginPage();
            }
            else if(determine.startsWith("1001"))
            {
                String wrongString=determine.substring(4);
                usernameNew.setText("");
                password.setText("");
                passwordCheck.setText("");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText(wrongString);
                alert.showAndWait();
            }
        }
        
    }
    public static clientMain main;
    public static void setMain(clientMain m) {main = m;}

    @FXML
    private void backHandle(ActionEvent event) throws Exception {
        main.loginPage();
    }
}
