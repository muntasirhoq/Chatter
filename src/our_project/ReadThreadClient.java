/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ReadThreadClient implements Runnable {
    Information pInformation;
    public static ArrayList<String> names = new ArrayList<String>();
    int flag=0;
    String name;
    private Thread thr;
    private NetworkUtil nc;
    private static ChatBoxClientController controller1;
    private static LoginPageClientController controller2;
    private static HistoryPageController controller3;
    public static int receiveThreadNumber=0;
    String showString="";

    public ReadThreadClient(NetworkUtil nc,String name) {
        this.name=name;
        this.nc = nc;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run()  {
        while(true){
            String msgCheck=(String) nc.read();
            if(msgCheck.startsWith("000"))
            {
                if(msgCheck.equals("000..1"))
                {
                    clientMain.client=name;
                    flag=1;
                    ChatBoxClientController.done=0;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                main.showChatBox(name);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });    
                }
                else if(msgCheck.startsWith("000..."))
                {
                    String errorString=msgCheck.substring(6);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Incorrect Credentials");
                                alert.setHeaderText("Incorrect Credentials");
                                alert.setContentText(errorString);
                                alert.showAndWait();
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                    controller2.userName.setText("");
                    controller2.password.setText("");
                }
                break;
            }
        }
        if(flag==1)
        {
            int next=0;//not time to log out yet
            try {
            while(true){
                String s=(String)nc.read();
                if(s.startsWith("111"))
                {
                    String[] nameArray = s.split(" ");
                    for(String x : nameArray){
                    if(!names.contains(x) && !x.equals("111") && !x.equals(name))
                    {
                        names.add(x);
                        pInformation = new Information(x);
                        main.clientList.add(pInformation);
                        main.clientActionTable.put(pInformation.getAction(),pInformation.getName());
                        main.fileActionTable.put(pInformation.getActionfile(), pInformation.getName());
                        main.InfoTable.put(x, pInformation);
                    }
                   }
                }
                //aakhono porer part ta kaj kore nai
                else if(s.startsWith("110"))//remove
                {
                    int flagg=0;
                    String[] nameArray = s.split(" ");
                    for(int i=0;i<names.size();i++)
                    {
                        String str=names.get(i);
                        for(String x : nameArray)
                        {
                            if(str.equals(x))
                            {
                                flagg=1;
                                break;
                            }
                        }
                        if(flagg==0)
                        {
                            Information pInformation=clientMain.InfoTable.get(str);
                            //names.remove(i);
                            names.remove(str);
                            main.clientList.remove(pInformation);
                            main.clientActionTable.remove(pInformation.getAction());
                            main.fileActionTable.remove(pInformation.getActionfile());
                            
                        }
                        flagg=0;
                    }                  
                }
                else if(s.startsWith("555") /*&& next==1*/)
                {
                    ChatBoxClientController.done=0;
                    nc.closeConnection();
                    break;
                }
                else if(s.startsWith("777")) // getting normal msg
                {
                    String rec=s.substring(3);
                    controller1.recMsg.appendText("\n");
                    controller1.recMsg.appendText(rec);
                }
                else if(s.startsWith("999"))
                {
                    clientMain.log++;
                    String s1=s.substring(4);
                    String s2=(String)nc.read();
                    String s3=(String)nc.read();
                    String s4=(String)nc.read();
                    String []arrays=s.split(" ");
                    String sender=arrays[1];
                    String fileName=(String)nc.read();
                    controller1.recMsg.appendText("\n");
                    controller1.recMsg.appendText(s1);
                    controller1.recMsg.appendText("\n");
                    controller1.recMsg.appendText(s2);
                    controller1.recMsg.appendText("\n");
                    controller1.recMsg.appendText(s3);
                    controller1.recMsg.appendText("\n");
                    controller1.recMsg.appendText(s4);
                    clientMain.query.put("0,"+sender);
                    
                    String recConfirm=(String)nc.read();
                    controller1.recMsg.appendText("\n");
                    controller1.recMsg.appendText(recConfirm);
                    
                    if(recConfirm.equals("Receiving File..."))
                    {
                        receiveThreadNumber++;
                        String recString=receiveThreadNumber+name;
                        new fileReceiveThread(recString,fileName);
                        
                    }
                    else
                    {
                        clientMain.log--;
                    }
                    
                }
                else if(s.startsWith("0,"))
                {
                    int recNo=(int)nc.read();
                    String receiver=(String)nc.read();
                    String message=s.substring(2);
                    //System.out.println(message);
                    if(message.equals("YES"))
                    {
                        recNo=recNo+1;
                        clientMain.query.put("1"+" "+recNo+receiver);
                        controller1.recMsg.appendText("\n");
                        controller1.recMsg.appendText("File Accepted...");
                    }
                    else
                    {
                        clientMain.query.put("0");
                        controller1.recMsg.appendText("\n");
                        controller1.recMsg.appendText("File Sending Denied...");
                    }
                }
                else if(s.startsWith(";;;"))
                {
                    do{
                        showString=(String)nc.read();
                        if(!showString.equals("+++"))
                        {
                            controller3.historyArea.appendText(showString);
                            controller3.historyArea.appendText("\n");
                        }
                    }while(!showString.equals("+++"));
                    
                }
            }
        } catch(Exception e) {
            System.out.println (e);
            e.printStackTrace();
        }
        }
        //nc.closeConnection();
    }

    public static void getController(ChatBoxClientController c)
    {
        controller1 = c;
    }
    public static void getController(LoginPageClientController c)
    {
        controller2 = c;
    }
    public static void getController(HistoryPageController c)
    {
        controller3 = c;
    }
    public static clientMain main;
    public static void setMain(clientMain m) {main = m;}
}
