package com.hornnes.bookdetails.controllers.restAPI;

import com.hornnes.bookdetails.model.Author;
import com.hornnes.bookdetails.model.Book;
import com.hornnes.bookdetails.repository.AuthorRepository;
import com.hornnes.bookdetails.repository.BookRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller // This means that this class is a Controller
public class RestController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Value("${auth0.domain}")
    private String domain;

    @Value("${auth0.clientId}")
    private String clientId;


    @RequestMapping(value = "/restapi/public", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String publicEndpoint() throws JSONException {
        return new JSONObject()
                .put("message", "Hello from a public endpoint! You don\'t need to be authenticated to see this.")
                .toString();
    }

    @RequestMapping(value = "/restapi/add", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String addNewBook (
            @RequestParam String name,
            @RequestParam String author,
            @RequestParam Double userRating,
            @RequestParam Integer reviews,
            @RequestParam Integer price,
            @RequestParam String year,
            @RequestParam String genre) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Author newAuthor = new Author();
        newAuthor.setName(author);
        Book book = new Book();

        book.setName(name);
        book.setAuthor(newAuthor);
        book.setUserRating(userRating);
        book.setReviews(reviews);
        book.setPrice(price);
        book.setYear(year);
        book.setGenre(genre);

        authorRepository.save(newAuthor);
        bookRepository.save(book);
        return "Book successfully added";
    }

    @RequestMapping(value = "/restapi/allBooks", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Iterable<Book> getAllBooks() {
        // This returns a JSON or XML with the users
        return bookRepository.findAll();
    }

    @RequestMapping(value = "/restapi/allAuthors", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Iterable<Author> getAllAuthors() {
        // This returns a JSON or XML with the users
        return authorRepository.findAll();
    }
}