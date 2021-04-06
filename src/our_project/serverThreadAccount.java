/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class serverThreadAccount implements Runnable {
    private ServerSocket ServSock;
    public Thread thr;
        
	public serverThreadAccount() {
		this.thr = new Thread(this);
		thr.start();
	}
        @Override
	public void run() {
		try {
                    ServSock = new ServerSocket(33332);
                    while (true) {
			Socket accountSock = ServSock.accept();
			NetworkUtil nc = new NetworkUtil(accountSock);
                        new readAccount(nc);
			}
		}catch(Exception e) {
                    e.printStackTrace();
		}
	}
}
