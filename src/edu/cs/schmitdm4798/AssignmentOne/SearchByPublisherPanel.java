package edu.cs.schmitdm4798.AssignmentOne;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Dave and Jack.
 */
public class SearchByPublisherPanel extends JPanel {

    private HenryDAO dao;

    public SearchByPublisherPanel(HenryDAO dao) {
        this.dao = dao;
        Color color = new Color(255,110,64);
        //Adds the list of publisher names to the box
        JComboBox<String> comboBoxPub = new JComboBox<>(new Vector<>(dao.getPublisherList()));
        JComboBox<String> comboBoxBook = new JComboBox<>();
        this.add(comboBoxPub);
        this.add(comboBoxBook);
        this.setBackground(color);
    }
}
