package inf.iso_to_name.books;


import inf.iso_to_name.proxy.GoogleISOApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private GoogleISOApi googleISOApi;

    @GetMapping("/v1/books/{identifier}")
    public ResponseEntity<String> getIsbn(@PathVariable String identifier) throws IOException, InterruptedException {
            return bookService.getNameByIsbn(identifier);
    }


}
