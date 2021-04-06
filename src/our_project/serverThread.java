/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.*;

/**
 *
 * @author DELL
 */
public class serverThread implements Runnable {
    public static ArrayList<String> names=new ArrayList<String>();
    private ServerSocket ServSock;
    public static Hashtable<String,NetworkUtil> table=new Hashtable<>();
    public Thread thr;
    //private static LoginPageClientController controller;
        
	public serverThread() {
		this.thr = new Thread(this);
		thr.start();
	}
        @Override
	public void run() {
		try {
                    ServSock = new ServerSocket(33333);
                    while (true) {
			Socket clientSock = ServSock.accept();
			NetworkUtil nc = new NetworkUtil(clientSock);
                        new readThreadServer(nc);
			}
		}catch(Exception e) {
                    e.printStackTrace();
		}
	}
}
