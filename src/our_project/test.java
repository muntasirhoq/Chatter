/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
public class test {
    static String nameString="1";
    public static void main(String[] args) throws FileNotFoundException, IOException {
       /*Hashtable<Integer,String> table=new Hashtable<>();
       table.put(1,"r" );
        System.out.println(table.get(1));*/
       /* a=2;
       String s="roi"+" "+a;
       String [] s1=s.split(" ");
       int b=Integer.parseInt(s1[1]);
       bc System.out.println(b);*/
       File userFile=new File("2.txt");
       if(userFile.exists()==false)
            userFile.createNewFile();
       File user=new File("2.txt");
       user.delete();
        System.out.println(user.exists());
       File userFile1=new File("2.txt");
       if(userFile1.exists()==false)
            userFile1.createNewFile();
        //Scanner scn;
        //scn=new Scanner(new File("1.txt"));
        //String string=" "+scn.nextLine();
        //System.out.println(string);
}
}