/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;

public class Q {
    public String name;
    public boolean value = false;
    synchronized void put(String s)
    {
        while(value)
        {
            try{
                wait();
            }catch(InterruptedException e){
            }
        }
        name = s;
        if(value==true)
            value=false;
        else
            value=true;
        notifyAll();
    }

    synchronized String get()
    {
        //if(ChatBoxClientController.done==0)
        //{
            while(!value)
            {
                try{
                    wait();
                }catch(InterruptedException e)
                {
                    System.out.println("Query error");
                }
            }
            if(value==true)
                value=false;
            else
                value=true;
            notifyAll();
            return name;
        //}
        /*else 
        {
            notifyAll();
            return " ";
        }*/
    }
    
}
