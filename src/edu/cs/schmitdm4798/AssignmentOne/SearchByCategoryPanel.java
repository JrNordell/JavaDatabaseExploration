package edu.cs.schmitdm4798.AssignmentOne;

import javax.swing.*;

import java.awt.*;
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
    private JTextPane authorText;
    private JPanel locationPanel;
    private boolean isSetUp;

    public SearchByCategoryPanel(HenryDAO dao){
        this.dao = dao;
        Color color = new Color(46,125,50);
        isSetUp = false;

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

        authorText = new JTextPane();
        authorText.setEditable(false);
        this.add(authorText);




        setDefault();
        isSetUp = true;
        this.setBackground(color);
    }

    private void setDefault(){
        DefaultComboBoxModel<Book> bookComboBoxModel = new DefaultComboBoxModel<Book>(new Vector<Book>(bookList));
        comboBoxBook.setModel(bookComboBoxModel);
        setBookInfo(bookList.get(0));


    }

    private void setBookInfo(Book book){

        if(isSetUp){
            this.remove(locationPanel);
        }

        Author tempAuthor = book.getAuthor();
        authorText.setText(tempAuthor.getFirst() + " " + tempAuthor.getLast());

        locationPanel = new JPanel(new GridLayout(book.getLocation().size(),2));
        for(Location location:book.getLocation()){
            locationPanel.add(new JLabel(location.getName()));
            locationPanel.add(new JLabel(location.getOnHand()+""));
        }
        this.add(locationPanel);
        this.revalidate();
        this.repaint();
    }
}
