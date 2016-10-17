package edu.cs.schmitdm4798.AssignmentOne;

/**
 * Created by Dave and Jack.
 *
 * The purpose of this class is to be a connector for the GUI and a Database
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HenryDAO {

        //JDBC driver identifier and database URL
    private static final String DB_URL = "jdbc:oracle:thin:@dario.cs.uwec.edu:1521:csdev";

        //Database credentials
    private static final String USER = "SCHMITDM4798";
    private static final String PASS = "NTT4SWUF";

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private boolean isConnected;


    public HenryDAO(){

            // Create holder objects


//        ResultSet resultSet = null;

        try{
                //Open the connection to the database
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            isConnected = true;
                //TESTING

//            String sql = "SELECT * FROM mlb_team";
//
//            resultSet = statement.executeQuery(sql);
//
//            while(resultSet.next()){
//                System.out.println(resultSet.getString("name"));
//            }
//

        }catch(SQLException ex){
            ex.printStackTrace();
            isConnected = false;
        }

    }

    public List<Author> getAuthorList(){


        String sql = "SELECT author_last, author_first FROM henry_author";
        List<Author> authorList = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                Author author = new Author(resultSet.getString("author_first").trim(), resultSet.getString("author_last").trim());
//                authorList.add(resultSet.getString("author_first").trim() + " " +resultSet.getString("author_last").trim());

                authorList.add(author);

            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }


        return authorList;
    }

    public Author getAuthorBookData(Author author){

        String firstName = author.getFirst();
        String lastName = author.getLast();

        firstName = firstName.replace("'","''");
        lastName = lastName.replace("'","''");
        String sql = "SELECT book_code, title, price, branch_name, on_hand FROM henry_book INNER JOIN henry_wrote on henry_book.book_code = henry_wrote.book_code " +
                "INNER JOIN henry_inventory ON henry_book.book_code = henry_inventory.book_code " +
                "INNER JOIN henry_branch ON henry_inventory.branch_num = henry_branch.branch_num " +
                "INNER JOIN henry_author ON henry_author.author_num = henry_wrote.author_num" +
                " WHERE author_first = '"+firstName+"' AND author_last = '"+lastName+"'";



        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                String bookCode = resultSet.getString("book_code");
                String title = resultSet.getString("title");
                Float price = resultSet.getFloat("price");
                String location = resultSet.getString("branch_name");
                int onHand = resultSet.getInt("on_hand");

                Book book = new Book(bookCode,title,price,location,onHand);
                author.addBook(book);

            }

        }catch (SQLException ex){
            ex.printStackTrace();

        }


        return author;
    }

    public List<Book> getCategoryBookData(String type){

        String sql = "SELECT author_first, author_last, book_code, title, price, branch_name, on_hand FROM henry_book INNER JOIN henry_wrote on henry_book.book_code = henry_wrote.book_code " +
        "INNER JOIN henry_inventory ON henry_book.book_code = henry_inventory.book_code " +
                "INNER JOIN henry_branch ON henry_inventory.branch_num = henry_branch.branch_num " +
                "INNER JOIN henry_author ON henry_author.author_num = henry_wrote.author_num" +
                " WHERE type = '" + type + "' order by title";


        List<Book>  bookList = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);


            while(resultSet.next()){

                String authorFirst = resultSet.getString("author_first").trim();
                String authorLast = resultSet.getString("author_last").trim();

                Author author = new Author(authorFirst, authorLast);

                String bookCode = resultSet.getString("book_code");
                String title = resultSet.getString("title");
                Float price = resultSet.getFloat("price");
                String location = resultSet.getString("branch_name");
                int onHand = resultSet.getInt("on_hand");

                Book book = new Book(bookCode,title,price,location,onHand);

                author.addBook(book);


                boolean isDone = false;
                int i = 0;

                if(bookList.size() != 0){


                    while(!(isDone || bookList.size() <= i)){

                        if(bookList.get(i).equals(book)){

                            bookList.get(i).addLocation(book.getLocation().get(0));
                            isDone = true;

                        }else{

                            ++i;
                        }
                    }

                    if(i == bookList.size()){

                        bookList.add(book);
                    }
                }else{

                    bookList.add(book);
                }


            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        System.out.println(bookList.size());
        return bookList;
    }

    //retrieves the list of category abbreviations in alphabetical order
    public List<String> getCategoryList(){

        String sql = "SELECT DISTINCT type FROM henry_book ORDER BY type";
        List<String> genre = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                genre.add(resultSet.getString("type"));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return genre;
    }

    //Retrieves the list of publisher names in alphabetical order
    public List<String> getPublisherList(){

        String sql = "SELECT publisher_name\n" +
                "FROM henry_publisher " +
                "ORDER BY publisher_name";

        List<String> publisherName = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                publisherName.add(resultSet.getString("publisher_name"));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return publisherName;
    }


    public boolean getIsConnected(){
        return isConnected;
    }


    public static void main(String[] args) {
        new HenryDAO();
    }

}
