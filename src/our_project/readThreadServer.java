/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class readThreadServer implements Runnable {
    int flag=0,continuee=0;
    private Thread thr;
    private NetworkUtil nc;
    String clientName;
    //File userFile;
    

    public readThreadServer(NetworkUtil nc) {
        this.nc = nc;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try 
        {  
            while(true){
            String msgCheck=(String) nc.read();
            if(msgCheck.startsWith("000"))
            {
                String str=msgCheck.substring(3);
                String getStringname[]=str.split(",");
                String userName=getStringname[0];
                Scanner scn;
                scn=new Scanner(new File("userRecord.txt"));
                String strFile;
                strFile=scn.nextLine();
                String[] strArray = strFile.split(" ");
                for(String x : strArray){
                    x="000"+x;
                    if(msgCheck.equals(x))
                    {
                        flag=1;//logged in
                        break;
                    }
                }
                if(flag==0 || serverThread.names.contains(userName))//aaki lok jate aak barer
                {//beshi at a time login korte na pare
                    if(flag==0)
                        nc.write("000..."+"wrong username or password");
                    else
                        nc.write("000..."+"you are already logged in");
                }
                else if(flag==1)
                {
                    nc.write("000..1");
                    String get=msgCheck.substring(3);
                    String getname[]=get.split(",");
                    serverThread.table.put(getname[0], nc);
                    serverThread.names.add(getname[0]);
                    
                    clientName=getname[0];
                    String msgString="111 ";
                    String msgLogin="111 ";
                    Enumeration names1= serverThread.table.keys();
                    while(names1.hasMoreElements()) {
                        String str1 = (String) names1.nextElement();
                        msgString=msgString+str1+" ";
                        if(!str1.equals(clientName))
                            msgLogin=msgLogin+str1+" ";
                    }
                    Enumeration names2= serverThread.table.keys();
                    while(names2.hasMoreElements()) {
                        String str2=(String) names2.nextElement();
                        NetworkUtil ncc=serverThread.table.get(str2);
                        if(!str2.equals(clientName))
                            ncc.write(msgString);
                        else if(str2.equals(clientName))
                            ncc.write(msgLogin);
                    }
                }
                break;
            }
           }
            while(true)
            {
                String msg = (String) nc.read();
                if(flag==1) {
                    
                    
                    if(msg.startsWith("222"))
                    {
                        String str=(String)nc.read();
                        File userFile=new File(clientName+".txt");
                        FileWriter fileWriter=new FileWriter(userFile,true);
                        BufferedWriter buffer=new BufferedWriter(fileWriter);
                        PrintWriter printWriter=new PrintWriter(buffer);
                        if(userFile.exists()==false)
                            userFile.createNewFile();
                        
                        printWriter.printf(str);
                        printWriter.close(); 
                        //nc.write("555");last e likhbo
                        String []array=msg.split(" ");
                        String nameDel=array[1];
                        //serverThread.table.remove(nameDel);
                        String msgString="110 ";
                        Enumeration names1= serverThread.table.keys();
                        while(names1.hasMoreElements()) {
                            String str1 = (String) names1.nextElement();
                            if(!str1.equals(nameDel))
                                msgString=msgString+str1+" ";
                        }
                        Enumeration names2= serverThread.table.keys();
                        while(names2.hasMoreElements()) {
                            String str2=(String) names2.nextElement();
                            NetworkUtil ncc=serverThread.table.get(str2);
                            ncc.write(msgString);
                        }
                        serverThread.table.remove(nameDel);
                        serverThread.names.remove(nameDel);
                        nc.write("555"+nameDel);
                        flag=0;
                    }
                    else if(msg.startsWith(";;;"))
                    {
                        String showString="";
                        File userFile=new File(clientName+".txt");
                        if(userFile.exists()==false)
                            userFile.createNewFile();
                        Scanner scn;
                        scn=new Scanner(new File(clientName+".txt"));
                        nc.write(";;;");
                        while(scn.hasNextLine())
                        {
                            showString=" "+scn.nextLine();
                            nc.write(showString);
                            
                        }
                        
                        nc.write("+++");
                    }
                    else if(msg.startsWith("###"))
                    {
                        String fileName=clientName+".txt";
                        File userFile=new File(fileName);
                        if(userFile.exists()==false)
                             userFile.createNewFile();
                        File user=new File(fileName);
                        user.delete();
                        System.out.println(user.exists());
                    }
                    else if(msg.startsWith("666"))//sending normal msg
                    {
                        String tobeSent="777";
                        String[] array=msg.split(" ");
                        NetworkUtil nc1 = serverThread.table.get(array[1]);
                        int length=array[0].length()+1+array[1].length()+1;
                 
                        tobeSent=tobeSent+msg.substring(length);
                        nc1.write(tobeSent);
                    }
                    else if(msg.startsWith("999"))
                    {
                        String []arrays=msg.split(" ");
                        String tobeSent=arrays[1];
                        int length=arrays[1].length()+5;
                        String fileName=msg.substring(length);
                        
                        NetworkUtil nc1 = serverThread.table.get(tobeSent);
                        nc1.write("999"+" "+clientName+" has sent you a file : "+fileName);
                        nc1.write("Do you want to recieve it?");
                        nc1.write("Select ACCEPT Button to accept file");
                        nc1.write("Select REJECT Button to reject file");
                        nc1.write(fileName);
                        //.write(fileName);
                        String confirm=(String)nc.read();
                        //System.out.println(confirm);
                        if(confirm.startsWith("1"))
                        {
                            nc1.write("Receiving File...");
                        }
                        else
                        {
                            nc1.write("Sending failed");
                        }
                       
                    }
                    else if(msg.startsWith("0,"))
                    {
                        String tosend=msg.substring(2);
                        String message=(String)nc.read();
                        int recNo=(int)nc.read();
                        String receiver=(String)nc.read();
                        NetworkUtil nc1 = serverThread.table.get(tosend);
                        //System.out.println(tosend);
                        nc1.write("0,"+message);
                        nc1.write(recNo);
                        nc1.write(receiver);
                        
                    }
                    else if(msg.startsWith(",,,"))//sending msg to all
                    {
                        String tobeSent="777"+msg.substring(3);
                        Enumeration names= serverThread.table.keys();
                        while(names.hasMoreElements()) {
                            String str2=(String) names.nextElement();
                            if(!clientName.equals(str2))
                            {
                                NetworkUtil ncc=serverThread.table.get(str2);
                                ncc.write(tobeSent);
                            }
                        }
                    }
                }
                if(flag==0)
                    break;
            }
        }catch(Exception e) {
            e.printStackTrace();
            }
            nc.closeConnection();
    }
}
