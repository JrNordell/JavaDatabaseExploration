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

    private static final Color BACKGROUND_COLOR = new Color(33,150,243);

    private HenryDAO dao;
    private Author author;

    private JComboBox<Book> comboBoxBook;
    private JTextPane price;
    private JPanel locationPanel;
    private DefaultComboBoxModel<Book> bookModel;

    private boolean isSetUp;

    public  SearchByAuthorPanel(HenryDAO dao){
        this.dao = dao;


        author = null;
        comboBoxBook = null;
        price = null;
        locationPanel = null;
        isSetUp = false;

            //get a list of authors
        List<Author> authorList = dao.getAuthorList();
        author = authorList.get(0);
        dao.getAuthorBookData(author);

            //get the book data for the list of authors


            //create a model for the combobox that will hold the author objects and can be changed
        DefaultComboBoxModel<Author> authorModel = new DefaultComboBoxModel<>(new Vector<>(authorList));
        JComboBox<Author> comboBoxAuthor = new JComboBox<>(authorModel);

            //listen for clicks on the combobox
        comboBoxAuthor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    //grab the combo box and then get the author that was selected
                JComboBox tempCombo = (JComboBox) e.getSource();
                author = (Author)tempCombo.getSelectedItem();
                author = dao.getAuthorBookData(new Author(author.getFirst(), author.getLast()));

                    //set default information for the author
                setDefault();

            }
        });
            //add the combobox
        this.add(comboBoxAuthor);


            //create a model for the books so that the combobox can hold book objects and can change dynamically
        comboBoxBook = new JComboBox<Book>();

            //listen for actions on the book combobox
        comboBoxBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    //grab the combobox and book
                JComboBox tempCombo = (JComboBox) e.getSource();
                Book book = (Book)tempCombo.getSelectedItem();
                setBookInfo(book);

            }
        });
        this.add(comboBoxBook);

            //set the initial book and price
        price = new JTextPane();
        price.setEditable(false);
        this.add(price);

        setDefault();
        isSetUp = true;

        this.setBackground(BACKGROUND_COLOR);

    }
        //sets the default book, price and location panel for when an author is picked
    private void setDefault(){
        bookModel = new DefaultComboBoxModel<Book>(new Vector<Book>(author.getBookList()));
        comboBoxBook.setModel(bookModel);

        Book book = author.getBookList().get(0);

        setBookInfo(book);

    }

    private void setBookInfo(Book book){
        price.setText("$ " + book.getPrice().toString());

        if(isSetUp){
            this.remove(locationPanel);
        }


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
