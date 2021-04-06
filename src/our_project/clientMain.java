/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Hashtable;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class clientMain extends Application{
    public static ObservableList<Information> clientList=FXCollections.observableArrayList();
    public static Hashtable<Button, String> clientActionTable=new Hashtable<Button,String>();
    public static Hashtable<Button, String> fileActionTable=new Hashtable<Button,String>();
    public static Hashtable<String, Information> InfoTable=new Hashtable<String,Information>();
    public static Q query=new Q(); 
    String Client_Name;
    Stage stage;
    public static String fileName="N";//null
    public static String filePath;
    public static int log=0;
    public static String client="",store="";
    
    void openPage() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("newAccount.fxml"));
        Parent root = loader.load();
        NewAccountController.setMain(this);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
    
    void loginPage() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("loginPageClient.fxml"));
        Parent root = loader.load();
        LoginPageClientController.setMain(this);
        stage.setTitle("welcome");
        stage.setScene(new Scene(root, 500, 350));
        stage.show();
    }
    
    void historyPage() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("historyPage.fxml"));
        Parent root = loader.load();
        HistoryPageController.setMain(this);
        stage.setTitle(client);
        stage.setScene(new Scene(root, 500, 350));
        stage.show();
    }
    
    void showChatBox(String name) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("chatBoxClient.fxml"));
        Parent root = loader.load();
        ChatBoxClientController.setMain(this);
        stage.setTitle(name);
        stage.setScene(new Scene(root, 800, 400));
        stage.show();
    }
    public void showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(stage);
        fileName=file.getName();
        filePath=file.getAbsolutePath();

    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Information.setMain(this);
        Parent root = FXMLLoader.load(getClass().getResource("loginPageClient.fxml"));
        LoginPageClientController.setMain(this);
        ReadThreadClient.setMain(this);
        clientThread.setMain(this);
        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.show();
        stage = primaryStage;
    }

    @Override
    public void stop()
    {
        System.exit(0);
    }
    public static void main(String[] args) {
        launch(args);
        clientList= FXCollections.observableArrayList();
        clientActionTable= new Hashtable<>();
    }
}
