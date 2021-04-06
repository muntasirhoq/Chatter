/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriteThreadClient implements Runnable {
        public static String toSend;
        public static String confirm="0";
	private Thread thr;
	private NetworkUtil nc;
	String name,id;
        private static ChatBoxClientController controller;
        public static int isAccept=0,sendThreadNumber=0;

	public WriteThreadClient(NetworkUtil nc, String name,String id) {
                this.id=id;
		this.nc = nc;
		this.name=name;
		this.thr = new Thread(this);
		thr.start();
	}
	
	public void run() {
            nc.write("000" + id);
            //while(ChatBoxClientController.done==0)
            while(true){
                if(ChatBoxClientController.done==0)
                {
                    toSend = clientMain.query.get();//name pabar jonno boshe ase
                    if(ChatBoxClientController.done==0)
                    {
                        String msg="";
                        msg=msg+controller.sendMsg.getText();
                        if(toSend.equals(",,,"))
                        {
                            nc.write(",,,"+name+" : "+msg);
                            controller.recMsg.appendText("\n");
                            controller.recMsg.appendText("Myself To All"+" : "+msg);
                            controller.sendMsg.clear();
                        }
                        else if(toSend.equals(";;;"))
                        {
                            nc.write(";;;");
                        }
                        else if(toSend.startsWith("###"))
                        {
                            //System.out.println("###");
                            nc.write("###");
                        }
                        else if(toSend.startsWith("999"))
                        {
                            clientMain.log++;
                            controller.recMsg.appendText("\n");
                            controller.recMsg.appendText("Server Is Trying To Share Your File...");
                            nc.write("999"+" "+toSend.substring(3)+" "+clientMain.fileName);
                            confirm="0";
                            confirm=clientMain.query.get();
                            
                            //System.out.println(confirm);
                            
                            nc.write(confirm);
                            if(confirm.startsWith("1"))
                            {
                                String [] s1=confirm.split(" ");
                                //int recNo=Integer.parseInt(s1[1]);
                                String recString=s1[1];
                                sendThreadNumber++;
                                String senderString=sendThreadNumber+name;
                                new fileSendThread(senderString,recString,clientMain.filePath);
                            }
                            else
                            {
                                clientMain.log--;
                            }
                            
                            
                        }
                        else if(toSend.startsWith("0,"))//confirmation if file will
                        {//be received or not
                            String tosendString=toSend.substring(2);
                            isAccept=1;//see chatboxclientcontroller
                            String msgString=clientMain.query.get();
                            
                            //System.out.println(msgString);
                            
                            nc.write("0,"+tosendString);
                            nc.write(msgString);
                            nc.write(ReadThreadClient.receiveThreadNumber);
                            nc.write(name);
                            
                        }
                        else
                        {
                            nc.write("666"+" "+toSend+" "+name+" : "+msg );
                            controller.recMsg.appendText("\n");
                            controller.recMsg.appendText("Myself To "+toSend+" : "+msg);
                            controller.sendMsg.clear(); 
                        }
                    }
                    else
                    {
                        nc.write("222"+" "+name);
                        String msg=controller.recMsg.getText();
                        nc.write("\n"+msg);
                        ChatBoxClientController.done=0;
                        break;
                    }
                }
            }
        }
        public static void getController(ChatBoxClientController c)
        {
            controller = c;
        }
}
