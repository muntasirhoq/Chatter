/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

public class clientThread implements  Runnable{
    private String name,id;
	private final String serverAddress = "127.0.0.1";
	private final int serverPort = 33333;
	private Thread thr;
	public clientThread(String name,String id)
	{
                this.id=id;
		this.name = name;
		thr = new Thread(this);
		thr.start();
	}

	@Override
	public void run() {
		try {
			NetworkUtil nc = new NetworkUtil(serverAddress,serverPort);
			new ReadThreadClient(nc,name);//.setMain(main);
			new WriteThreadClient(nc, name,id);
		} catch(Exception e) {
			System.out.println (e);
                        e.printStackTrace();
		}
	}
    public static clientMain main;
    public static void setMain(clientMain m) {main = m;}
}
