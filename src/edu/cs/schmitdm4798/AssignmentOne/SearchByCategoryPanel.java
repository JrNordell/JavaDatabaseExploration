package edu.cs.schmitdm4798.AssignmentOne;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

/**
 * Created by Dave and Jack.
 */

public class SearchByCategoryPanel extends JPanel {

    private HenryDAO dao;

    private JComboBox<Book> comboBoxBook;
    private String type;
    private List<Book> bookList;

    public SearchByCategoryPanel(HenryDAO dao){
        this.dao = dao;
        Color color = new Color(46,125,50);

        List<String> categoryList = dao.getCategoryList();
        type = categoryList.get(0);

        //Adds the distinct list of category abbreviations to the box
        JComboBox<String> comboBoxCat = new JComboBox<>(new Vector<>(categoryList));
        comboBoxCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox tempCombo = (JComboBox) e.getSource();
                String type = (String) tempCombo.getSelectedItem();
                bookList = dao.getCategoryBookData(type);
                setDefault();
            }
        });
        this.add(comboBoxCat);


        comboBoxBook = new JComboBox<>();

        comboBoxBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //grab the combobox and book
                JComboBox tempCombo = (JComboBox) e.getSource();
                Book book = (Book)tempCombo.getSelectedItem();
                setBookInfo(book);

            }
        });
        this.add(comboBoxBook);

        bookList = dao.getCategoryBookData(categoryList.get(0));
        setDefault();


        this.setBackground(color);
    }

    private void setDefault(){
        Book book = bookList.get(0);
        System.out.println(bookList.size());
        DefaultComboBoxModel<Book> bookComboBoxModel = new DefaultComboBoxModel<Book>(new Vector<Book>(bookList));
        comboBoxBook.setModel(bookComboBoxModel);

    }

    private void setBookInfo(Book book){

    }
}
