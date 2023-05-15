package inf.iso_to_name.books;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/v1/books/{identifier}")
    public String getIsbn(@PathVariable String identifier) throws IOException, InterruptedException {
            return bookService.getBookByIsbn(identifier);
    }


}
