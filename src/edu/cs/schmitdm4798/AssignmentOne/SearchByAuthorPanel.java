package edu.cs.schmitdm4798.AssignmentOne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Dave and Jack.
 *
 * This class sets up the search by author panel for the program
 */
public class SearchByAuthorPanel extends JPanel {

    private HenryDAO dao;
    private Author author;

    private JComboBox<Book> comboBoxBook;
    private JTextPane price;
    private JPanel locationPanel;
    private DefaultComboBoxModel<Book> bookModel;

    private SearchByAuthorPanel instance;

    public  SearchByAuthorPanel(HenryDAO dao){
        this.dao = dao;
        Color color = new Color(63,81,181);

        author = null;
        comboBoxBook = null;
        price = null;
        instance = this;
        locationPanel = null;

            //get a list of authors
        List<Author> authorList = dao.getAuthorList();

            //get the book data for the list of authors
        for(Author author: authorList){
            dao.getBookData(author);
        }

            //create a model for the combobox that will hold the author objects and can be changed
        DefaultComboBoxModel<Author> authorModel = new DefaultComboBoxModel<>(new Vector<>(authorList));
        JComboBox<Author> comboBoxAuthor = new JComboBox<>(authorModel);

            //listen for clicks on the combobox
        comboBoxAuthor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    //grab the combo box and then get the author that was selected
                JComboBox tempCombo = (JComboBox) e.getSource();
                author = (Author)tempCombo.getSelectedItem();

                    //set default information for the author
                setDefault(author);

            }
        });
            //add the combobox
        this.add(comboBoxAuthor);

        author = authorList.get(0);

            //create a model for the books so that the combobox can hold book objects and can change dynamically
        bookModel = new DefaultComboBoxModel<Book>(new Vector<Book>(author.getBookList()));
        comboBoxBook = new JComboBox<Book>(bookModel);

            //listen for actions on the book combobox
        comboBoxBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    //grab the combobox and book
                JComboBox tempCombo = (JComboBox) e.getSource();
                Book book = (Book)tempCombo.getSelectedItem();

                    //set the price JLabel to what the book says the price is
                price.setText("$ " + book.getPrice().toString());

                    //remove the location panel that is already there and and create a new one
                instance.remove(locationPanel);
                locationPanel = new JPanel(new GridLayout(book.getLocation().size(),2));

                    //add every location and inventory to the locationPanel
                for(Location location:book.getLocation()){
                    locationPanel.add(new JLabel(location.getName()));
                    locationPanel.add(new JLabel(location.getOnHand()+""));
                }

                    //add the locationPanel to the instance, revalidate the instance to show the new panel, and repaint
                    //so that any graphical bugs from removing and adding the panel will be gone
                instance.add(locationPanel);
                instance.revalidate();
                instance.repaint();

            }
        });
        this.add(comboBoxBook);

            //set the initial book and price
        Book book = author.getBookList().get(0);
        price = new JTextPane();
        price.setEditable(false);
        price.setText(book.getPrice().toString());
        this.add(price);

            //set the initial location panel
        locationPanel = new JPanel(new GridLayout(book.getLocation().size(),2));
        for(Location location:book.getLocation()){
            locationPanel.add(new JLabel(location.getName()));
            locationPanel.add(new JLabel(location.getOnHand()+""));
        }
        this.add(locationPanel);

        this.setBackground(color);

    }
        //sets the default book, price and location panel for when an author is picked
    public void setDefault(Author author){
        bookModel = new DefaultComboBoxModel<Book>(new Vector<Book>(author.getBookList()));
        comboBoxBook.setModel(bookModel);

        Book book = author.getBookList().get(0);
        price.setText(book.getPrice().toString());

        this.remove(locationPanel);
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
