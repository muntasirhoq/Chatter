/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package our_project;
//yes
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import javafx.event.EventHandler;

public class Information {
    private StringProperty name;
    private Button action;
    private Button actionFile;
    Information personInformation;
    Information(String name)
    {
        personInformation=this;
        this.name = new SimpleStringProperty(name);
        action = new Button("send");
        actionFile=new Button("send File");
        action.setOnAction(new EventHandler <ActionEvent> () {
            @Override
            public void handle(ActionEvent event)
            {
                String cName = clientMain.clientActionTable.get(event.getSource());
                clientMain.query.put(cName);
            }
        });
        actionFile.setOnAction(new EventHandler <ActionEvent> () {
            @Override
            public void handle(ActionEvent event)
            {
                String cName = clientMain.fileActionTable.get(event.getSource());
                //clientMain.query.put(cName);
                main.showFileChooser();
                if(!clientMain.fileName.equals("N"))
                {
                    clientMain.query.put("999"+cName);
                }
            }
        });
    }

    public String getName()
    {
        return name.get();
    }

    public Button getAction()
    {
        return action;
    }
    public Button getActionfile()
    {
        return actionFile;
    }
    public void setName(String fName)
    {
        name.set(fName);
    }
    public void setAction(String fAction)
    {
        action.setText(fAction);
    }
    public void setActionfile(String fAction)
    {
        actionFile.setText(fAction);
    }
    public static clientMain main;
    public static void setMain(clientMain m)
    {
        main = m;
    }
}
