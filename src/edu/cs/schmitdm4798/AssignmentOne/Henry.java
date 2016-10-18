package edu.cs.schmitdm4798.AssignmentOne;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/**
 * Created by Dave and Jack.
 *
 * The Goal of this class is to set up the GUI for the thing
 *
 */
public class Henry {

    public static void main(String[] args) {
        new Henry();
    }

    public Henry(){
        JFrame frame = new JFrame();
        frame.setSize(600,200);
        Color color = new Color(63,81,181);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HenryDAO dao = new HenryDAO();


        if (dao.getIsConnected()){
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Search by Author", new SearchByAuthorPanel(dao));
            tabbedPane.addTab("Search by Category", new SearchByCategoryPanel(dao));
            tabbedPane.addTab("Search by Publisher", new SearchByPublisherPanel(dao));

            frame.add(tabbedPane);
        }else{
            JLabel notConnected = new JLabel();
            notConnected.setFont(new Font(notConnected.getFont().getName(), notConnected.getFont().getStyle(), 20));
            notConnected.setText("Sorry, you are not connected to the server.");

            frame.add(notConnected);

        }



        frame.setVisible(true);
    }
}
