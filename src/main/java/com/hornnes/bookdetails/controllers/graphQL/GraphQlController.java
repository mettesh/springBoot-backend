package com.hornnes.bookdetails.controllers.graphQL;

import com.hornnes.bookdetails.model.Author;
import com.hornnes.bookdetails.model.Book;
import com.hornnes.bookdetails.repository.AuthorRepository;
import com.hornnes.bookdetails.repository.BookRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;

@Controller // This means that this class is a Controller
public class GraphQlController {
    @Autowired // This means to get the bean called bookRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;


    /* * * * * * * * *
     *    QUERIES    *
     * * * * * * * * */
    // A data fetcher is responsible for returning a data value back for a given graphql field
    public DataFetcher getAllBooksFetcher() {
        return dataFetchingEnvironment -> bookRepository.findAll();
    }

    public DataFetcher getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            // Gets the argument
            Integer bookId = dataFetchingEnvironment.getArgument("id");
            return bookRepository.findById(bookId);
        };
    }

    public DataFetcher getAllAuthorsDataFetcher() { return dataFetchingEnvironment -> authorRepository.findAll(); }

    public DataFetcher getAuthorsByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            Integer authorId = dataFetchingEnvironment.getArgument("id");
            return authorRepository.findById(authorId);
        };
    }


    /* * * * * * * * *
     *    BINDINGS   *
     * * * * * * * * */
    public DataFetcher getAuthorForBook() {
        return dataFetchingEnvironment -> {
            // Gets the source (The book)
            Book book = dataFetchingEnvironment.getSource();
            // Find the books author
            Integer authorId = book.getAuthor().getId();
            // returns author with the matching id
            return authorRepository.findById(authorId);
        };
    }

    /* * * * * * * * *
     *   MUTATIONS   *
     * * * * * * * * */
    public DataFetcher addBook () {
        return dataFetchingEnvironment -> {
            // Gets the argument
            Map arguments = dataFetchingEnvironment.getArguments();

            Book book = new Book();
            Author author = new Author();

            author.setName((String) arguments.getOrDefault("author", null));

            book.setName((String) arguments.getOrDefault("name", null));
            book.setAuthor(author);
            book.setUserRating((Double) arguments.getOrDefault("userRating", null));
            book.setReviews((Integer) arguments.getOrDefault("reviews", null));
            book.setPrice((Integer) arguments.getOrDefault("price", null));
            book.setYear((String) arguments.getOrDefault("year", null));
            book.setGenre((String) arguments.getOrDefault("genre", null));

            authorRepository.save(author);
            bookRepository.save(book);

            // TODO: Send error messsage if not successsfull
            return book;
        };
    }
}