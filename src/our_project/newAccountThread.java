/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

public class newAccountThread implements  Runnable{
        private String name,password;
	private final String serverAddress = "127.0.0.1";
	private final int serverPort = 33332;
	private Thread thr;
	public newAccountThread(String name,String password)
	{
                this.name=name;
		this.password = password;
		thr = new Thread(this);
		thr.start();
	}

	@Override
	public void run() {
		try {
			NetworkUtil nc = new NetworkUtil(serverAddress,serverPort);
                        nc.write(name+","+password);
                        String det=(String)nc.read();
                        clientMain.query.put(det);
                        nc.closeConnection();
                        
		} catch(Exception e) {
			System.out.println (e);
                        e.printStackTrace();
		}
	}
}
