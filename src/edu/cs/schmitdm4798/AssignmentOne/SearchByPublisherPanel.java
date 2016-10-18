package edu.cs.schmitdm4798.AssignmentOne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Dave and Jack.
 */
public class SearchByPublisherPanel extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(0,230,118);
    private HenryDAO dao;
    private boolean isSetUp;
    private List<Book> bookList;
    private JComboBox<Book> comboBoxBook;
    private JTextPane authorText;
    private JPanel locationPanel;
    private JTextPane price;

    public SearchByPublisherPanel(HenryDAO dao) {
        this.dao = dao;
        isSetUp = false;

        //Adds the list of publisher names to the box
        List<String> publisherList = dao.getPublisherList();
        JComboBox<String> comboBoxPub = new JComboBox<>(new Vector<>(publisherList));
        comboBoxPub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox tempCombo = (JComboBox) e.getSource();
                String publisher = (String) tempCombo.getSelectedItem();

                bookList = dao.getPublisherBookData(publisher);
                setDefault();

            }
        });
        this.add(comboBoxPub);

        comboBoxBook = new JComboBox<Book>();
        comboBoxBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //grab the combobox and book
                JComboBox tempCombo = (JComboBox) e.getSource();
                Book book = (Book)tempCombo.getSelectedItem();
                setBookInfo(book);
            }
        });
        this.add(comboBoxBook);
        this.setBackground(BACKGROUND_COLOR);

        authorText = new JTextPane();
        authorText.setEditable(false);
        this.add(authorText);

        price = new JTextPane();
        price.setEditable(false);
        this.add(price);

        bookList = dao.getPublisherBookData(publisherList.get(0));

        setDefault();

        isSetUp = true;
    }

    private void setDefault(){
        DefaultComboBoxModel<Book> bookComboBoxModel = new DefaultComboBoxModel<Book>(new Vector<Book>(bookList));
        comboBoxBook.setModel(bookComboBoxModel);
        setBookInfo(bookList.get(0));

    }

    private void setBookInfo(Book book){

        price.setText("$ " + book.getPrice().toString());

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
