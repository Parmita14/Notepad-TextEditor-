/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.notepad;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author hp
 */
public class NotePad extends JFrame implements ActionListener,WindowListener {
JTextArea jta = new JTextArea();
File fnameContainer;

public NotePad(){
    Font fnt = new Font("Arial" , Font.PLAIN,15); // name,style and size are specified.
    Container con = getContentPane();  // layer holds object in container**
    JMenuBar jmb = new JMenuBar();     //holdsjmenu
    JMenu jmfile = new JMenu("File"); // JMenu here will help to show many menu items
    JMenu jmedit = new JMenu("edit");
    JMenu jmhelp = new JMenu("Help");
    con.setLayout(new BorderLayout()); // gives borderlayout **
    JScrollPane sbrText = new JScrollPane(jta);// here i have added component text area n given it a scroll bar.
    sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//JScrollPane->class->
    sbrText.setVisible(true);// to view the scrollbar
    
    jta.setFont(fnt); // textarea font
    jta.setLineWrap(true); // to display text in multiple line rather than in one single line
    jta.setWrapStyleWord(true);// word is used so that it wraps by word not by character
    
    con.add(sbrText); //to add scroll bar in container
    
    createMenuItem(jmfile,"NEW");// created menu item in file
    createMenuItem(jmfile,"Open");
    createMenuItem(jmfile,"Save");
    jmfile.addSeparator();// to separate these menu items
    createMenuItem(jmfile,"Exit");
     //pop up menu of edit 
    createMenuItem(jmedit,"Exit");
    createMenuItem(jmedit,"Cut");
    createMenuItem(jmedit,"Copy");
    createMenuItem(jmedit,"Paste");
    
    createMenuItem(jmhelp,"About NotePad");
    // file edit and help are added in menubar
    jmb.add(jmfile);
    jmb.add(jmedit);
    jmb.add(jmhelp);
    
    setJMenuBar(jmb);
    
    setIconImage(Toolkit.getDefaultToolkit().getImage("notepad.gif"));
    // to perform window events
    // used for notepad window
    addWindowListener(this);
    setSize(500,500);
    setTitle("Untitled.txt - Notepad");
    setVisible(true);
     
}
 
public void createMenuItem(JMenu jm,  String txt){
 JMenuItem jmi = new JMenuItem(txt); // creates menu item of specified text.
 jmi.addActionListener(this);// so that whenever i click it will take it as an action
 jm.add(jmi); // added in menu
}
public void actionPerformed(ActionEvent e){ // as actionperformed is method of actionlistener it is invoked when action occurs
    JFileChooser jfc = new JFileChooser(); // file chooser is dialog window from which user will choose file
    if(e.getActionCommand().equals("New")){// getaction will return me string
        this.setTitle("Untitled.txt-Notepad");//to form new notepad
        jta.setText("");
        fnameContainer = null;
    }else if(e.getActionCommand().equals("Open")){
        int ret = jfc.showDialog(null,"Open");
        if(ret == JFileChooser.APPROVE_OPTION){ // if ok is selected then value is return whith approve option
            // for calling filechooser
            try{
                File fyl = jfc.getSelectedFile();// Returns the selected file
                OpenFile(fyl.getAbsolutePath());//absolute file path name is passed in openfile
                this.setTitle(fyl.getName() +" - Notepad" );//give same title 
                fnameContainer = fyl;
            }catch(IOException ets){}
            
        }
    }else if(e.getActionCommand().equals("Save")){
        if(fnameContainer != null){
            jfc.setCurrentDirectory(fnameContainer);// contains info need to access the file
            jfc.setSelectedFile(fnameContainer);
        }else{
            jfc.setSelectedFile(new File("Untitled.txt"));
        }
        int ret = jfc.showSaveDialog(null);
        if(ret == JFileChooser.APPROVE_OPTION){
            try{
                File fyl = jfc.getSelectedFile();
                saveFile(fyl.getAbsolutePath());
                this.setTitle(fyl.getName()+ " - Notepad");
                fnameContainer = fyl;
            }catch(IOException ers){}
        }
    }else if(e.getActionCommand().equals("Exit")){
        Exiting();
    }else if(e.getActionCommand().equals("copy")){
        jta.copy();
    }else if(e.getActionCommand().equals("Paste")){
        jta.paste();
    }else if(e.getActionCommand().equals("About NotePad")){
        // for displaying dialog
    JOptionPane.showMessageDialog(this, "Created by : Parmita Upadhyay","Notepad",JOptionPane.INFORMATION_MESSAGE);
    
    }else if(e.getActionCommand().equals("Cut")){
        jta.cut();
    }
}
// adding method for open file and close file
 public void OpenFile(String fname)throws IOException{
     //we will read file with cursor
     BufferedReader d = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
     String l;
     jta.SetText("");
     setCursor(new Cursor(Cursor.WAIT_CURSOR));
     // while loop to read all data
     while((l = d.readLine()) != null){
         jta.setText(jta.getText() +l +"\r\n");
     }
     setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
     d.close();
 }
 // adding method for save file
 public void saveFile(String fname)throws IOException{
     setCursor(new Cursor(Cursor.WAIT_CURSOR));
     DataOutputStream o = new DataOutputStream(new FileOutputStream(fname));
     o.writeBytes(jta.getText());
     o.close();
     setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
 }
 
  public void windowDeactivated(WindowEvent e){}
     
  public void windowActivated(WindowEvent e){}
  
  public void windowDeiconified(WindowEvent e){}
  
  public void windowIconified(WindowEvent e){}
  
  public void windowClosed(WindowEvent e){}
  
  public void windowClosing(WindowEvent e){
      Exiting();
  }
 
  public void windowOpened(WindowEvent e){}
  
  public void Exiting(){
      System.exit(0);
  }
  
}
