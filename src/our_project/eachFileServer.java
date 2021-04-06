/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

/**
 *
 * @author DELL
 */
public class eachFileServer implements  Runnable{
    String sendNoString,recNoString;
     private Thread thr;
    private NetworkUtil nc;
    public eachFileServer(NetworkUtil nc)
    {
        this.nc=nc;
        this.thr = new Thread(this);
        thr.start();
    }
    @Override
    public void run()
    {
        String check=(String)nc.read();
        if(check.equals("sender"))
        {
            try{
            sendNoString=(String)nc.read();
            recNoString=(String)nc.read();
            //System.out.println(recNo);
            fileServer.table.put(sendNoString, nc);
            //Thread.sleep(2000);
            String wait=(String)nc.read();
            NetworkUtil nc1=fileServer.table.get(recNoString);
            String sizeString=(String)nc.read();
            int size=Integer.parseInt(sizeString);
                            System.out.println(size);
                            nc1.write(sizeString);
                            //sending=new fileSendThreadServer(nc,nc1,size);
                            int totalBlock=(size/1024)+1;
                            //char [] c;
                            byte [] b;
                            for(int i=1;i<=totalBlock;i++)
                            {
                                    if(i==totalBlock)
                                    {
                                            b=new byte[size%1024];
                                    }
                                    else
                                    {
                                            b=new byte[1024];
                                    }
                                    b=(byte[]) nc.read();
                                    nc1.write(b);
                            }
                            nc.closeConnection();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(check.equals("receiver"))
        {
            String threadNoString=(String)nc.read();
            fileServer.table.put(threadNoString, nc);
        }
        //nc.closeConnection();
    }
    
}
