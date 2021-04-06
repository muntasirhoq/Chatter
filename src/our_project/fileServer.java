/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class fileServer implements Runnable {
    public static Hashtable<String,NetworkUtil> table=new Hashtable<>();
    private ServerSocket ServSock;
    public Thread thr;
        
	public fileServer() {
		this.thr = new Thread(this);
		thr.start();
	}
        @Override
	public void run() {
		try {
                    ServSock = new ServerSocket(33330);
                    while (true) {
			Socket fileSock = ServSock.accept();
			NetworkUtil nc = new NetworkUtil(fileSock);
                        new eachFileServer(nc);
			}
		}catch(Exception e) {
                    e.printStackTrace();
		}
	}
}
