/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import static our_project.ReadThreadClient.main;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class HistoryPageController implements Initializable {
    

    @FXML
    private Button backButton;
    @FXML
    public  TextArea historyArea;
    @FXML
    private Button clearButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ReadThreadClient.getController(this);
    }    

    @FXML
    private void backHandle(ActionEvent event) throws  Exception{
        main.showChatBox(clientMain.client);
        
    }

    @FXML
    private void clearHandle(ActionEvent event) {
        historyArea.clear();
        clientMain.query.put("###");
    }
    
    public static clientMain main;
    public static void setMain(clientMain m)
    {
        main = m;
    }
    
}
