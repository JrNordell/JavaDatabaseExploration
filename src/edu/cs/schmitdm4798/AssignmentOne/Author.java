package edu.cs.schmitdm4798.AssignmentOne;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave and Jack.
 */
public class Author {

    private List<Book> bookList;
    private String first;
    private String last;

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public Author(String first, String last){
        this.first = first;
        this.last = last;
        bookList = new ArrayList<>();
    }

    public void addBook(Book book){

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
                book.setAuthor(this);
                bookList.add(book);
            }
        }else{
            book.setAuthor(this);
            bookList.add(book);
        }

    }

    public boolean equals(Object o){
        Author author = (Author)o;
        return (first.equals(author.first))&&(last.equals(author.last));
    }

    public List<Book> getBookList(){
        return bookList;
    }

    public String toString(){
        return first + " " + last;
    }

}
