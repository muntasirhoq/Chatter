/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.util.Hashtable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import static our_project.serverMain.server;


public class serverMain{
   
    public static serverThread server;
    public static serverThreadAccount accountServer;
    public static fileServer fileThreadServer;
    
    public static void main(String[] args) {
    server=new serverThread();
    accountServer=new serverThreadAccount();
    fileThreadServer=new fileServer();
}
    
}
