/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.io.FileInputStream;

/**
 *
 * @author DELL
 */
public class fileSendThread implements Runnable{
    private  NetworkUtil nc;
    String sendNoString,recNoString;
    String filePath;
    private Thread thr;
    private final String serverAddress = "127.0.0.1";
    private final int serverPort = 33330;
    public fileSendThread(String sendNo,String recNo,String filePath)
	{
                this.sendNoString=sendNo;
                this.recNoString=recNo;
                this.filePath=filePath;
		this.thr = new Thread(this);
		thr.start();
	}
    
    @Override
	public void run() 
        {
            try {
                        //System.out.println("yes");
			nc = new NetworkUtil(serverAddress,serverPort);
                        nc.write("sender");//  ..................
                        nc.write(sendNoString);//  ................
                        nc.write(recNoString);//  ...................
                        
		} catch(Exception e) {
                        e.printStackTrace();
		}
            FileInputStream fin;
            int size;
            try {
                Thread.sleep(2000);
                nc.write("done");
                fin = new FileInputStream(filePath);
                size=fin.available();
                String sizeString=""+size;
                nc.write(sizeString);//  ..................
		int totalBlock=(size/1024)+1;
                                
                byte [] b;
                for(int i=1;i<=totalBlock;i++)
                {
                    if(i==totalBlock)
                    {
                        b=new byte[size%1024];
                         //fin.read(b);
                    }
                    else
                    {	
                        b=new byte[1024];
                        //fin.read(b);
                    }
                    fin.read(b);
                    //outToServer.write(b);
                    nc.write(b);
                }
                fin.close();
                clientMain.log--;
                //nc.closeConnection();
                                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
	}
    
}
