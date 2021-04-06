/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.io.FileOutputStream;
import java.io.IOException;

public class fileReceiveThread implements Runnable{
    private  NetworkUtil nc;
    String threadNoString;
    String fileName;
    private Thread thr;
    private final String serverAddress = "127.0.0.1";
    private final int serverPort = 33330;
    public fileReceiveThread(String threadNoString,String fileName)
	{
                this.threadNoString=threadNoString;
                this.fileName=fileName;
		this.thr = new Thread(this);
		thr.start();
	}
    
    @Override
	public void run() 
        {
            try {
			nc = new NetworkUtil(serverAddress,serverPort);
                        //System.out.println("Connection Established");
                        nc.write("receiver");//  ..................
                        nc.write(threadNoString);//   ................
		} catch(Exception e) {
                        e.printStackTrace();
		}
            int size;
            String sizeString=(String)nc.read();
            size=Integer.parseInt(sizeString);//  ............................
            System.out.println(size);
                        
                        int totalBlock=(size/1024)+1;
                        try{
                        FileOutputStream fout=new FileOutputStream(fileName);                       

                        byte [] buffer;                   

                        for(int i=1;i<=totalBlock;i++)
                        {
                                if(i==totalBlock)
                                {

                                        buffer=new byte[size%1024];
                                }
                                else
                                {

                                        buffer=new byte[1024];
                                }
                                buffer=(byte[])nc.read();
                                fout.write(buffer);        
                        }
                        fout.close();
                        clientMain.log--;
                        nc.closeConnection();
                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        //nc.closeConnection();
	}
    
}
