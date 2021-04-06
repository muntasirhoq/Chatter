/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class ChatBoxClientController implements Initializable {
    
    private static LoginPageClientController controller;
    @FXML
    private TableView<Information> table;
    @FXML
    private TableColumn<Information,String> friendName;
    @FXML
    private TableColumn<Information,Button> messageAction;
    @FXML
    private Button logOut;
    @FXML
    public TextArea recMsg;
    @FXML
    public TextArea sendMsg;
    @FXML
    private Button sendAll;
    @FXML
    private TableColumn<Information,Button> fileColumn;
    @FXML
    private Button acceptButton;
    @FXML
    private Button rejectButton;
    @FXML
    private Button historyButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        friendName.setCellValueFactory(new PropertyValueFactory<Information, String>("name"));
        messageAction.setCellValueFactory(new PropertyValueFactory<Information, Button>("action"));
        fileColumn.setCellValueFactory(new PropertyValueFactory<Information, Button>("actionfile"));
        table.setItems(clientMain.clientList);
        ReadThreadClient.getController(this);
        WriteThreadClient.getController(this);
        recMsg.setText(main.store);
        main.store ="";
    }    

    public static int done=0;
    @FXML
    private void logOutHandle(ActionEvent event) throws  Exception{
        if(clientMain.log==0){
        done=1;
        main.loginPage();
        String name=LoginPageClientController.send_name();
        Information pInformation=clientMain.InfoTable.get(name);//exception
        clientMain.query.put(name);}
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("wait till the file is sent or received");
        alert.showAndWait();
        }
        
    }   
    public static clientMain main;
    public static void setMain(clientMain m)
    {
        main = m;
    }

    @FXML
    private void sendAllHandle(ActionEvent event) {
        clientMain.query.put(",,,");
    }

    @FXML
    private void fileAcceptHandle(ActionEvent event) {
        if(WriteThreadClient.isAccept==1)
        {
            clientMain.query.put("YES");
            WriteThreadClient.isAccept=0;
        }
    }

    @FXML
    private void fileRejectHandle(ActionEvent event) {
        if(WriteThreadClient.isAccept==1)
        {
            clientMain.query.put("NO");
            WriteThreadClient.isAccept=0;
        }
    }

    @FXML
    private void historyHandle(ActionEvent event)  {
        clientMain.store=recMsg.getText();
        try {
            main.historyPage();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        clientMain.query.put(";;;");
    }
}
