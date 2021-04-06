/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class readAccount implements Runnable {
    private Thread thr;
    private NetworkUtil nc;
    String name, password;
    int sameName=0;
    int nullSituation=0;
    public readAccount(NetworkUtil nc) {
        this.nc = nc;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try 
        {
            int count=0;
            String check=(String)nc.read();
            Scanner scn;
            scn=new Scanner(new File("userRecord.txt"));
            String strFile;
            strFile=scn.nextLine();
            String[] strArray = strFile.split(" ");
            for(int i=0;i<check.length();i++)
            {
                if(check.charAt(i)==',')
                {
                    count++;
                }
            }
            if(count==1)
            {
                for(int i=0;i<check.length();i++)
                {
                    if(check.charAt(i)==',' && ((i==0)||(i==(check.length()-1))))
                    {
                        nullSituation=1;
                        break;
                    }
                }
            }
            if(nullSituation==0)
            {
                String[] array=check.split(",");
                name=array[0];
                password=array[1];
                for(String x : strArray)
                {
                    String []array1=x.split(",");
                    String username=array1[0];
                        if(username.equals(name))
                        {
                            sameName=1;//aaki name er arekjon ase
                            break;
                        } 
                }
            }
            if(count>1)//, use korse username or password e
                nc.write("1001"+"cannot use ',' in username or password");
            else if(nullSituation==1)
            {
                nc.write("1001"+"no character in name or password");
                nullSituation=0;
            }
            else if(sameName==1)
            {
                nc.write("1001"+"user of same name exists");
                sameName=0;
            }
            else
            {
                nc.write("1000");
                String str=name+","+password+" ";
                File UserRecord=new File("UserRecord.txt");
                FileWriter fileWriter=new FileWriter(UserRecord,true);
                BufferedWriter buffer=new BufferedWriter(fileWriter);
                PrintWriter printWriter=new PrintWriter(buffer);
                if(UserRecord.exists()==false)
                    UserRecord.createNewFile();
                printWriter.printf(str);
                printWriter.close(); 
            }
        }catch(Exception e) {
            e.printStackTrace();
            }
    }
}
