package edu.cs.schmitdm4798.AssignmentOne;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Dave and Jack.
 */

public class SearchByCategoryPanel extends JPanel {

    private HenryDAO dao;

    public SearchByCategoryPanel(HenryDAO dao){
        this.dao = dao;
        Color color = new Color(46,125,50);

        //Adds the distinct list of category abbreviations to the box
        JComboBox<String> comboBoxCat = new JComboBox<>(new Vector<>(dao.getCategoryList()));
        JComboBox<String> comboBoxBook = new JComboBox<>();
        this.add(comboBoxCat);
        this.add(comboBoxBook);
        this.setBackground(color);
    }
}
