package inf.iso_to_name.books;


import inf.iso_to_name.proxy.GoogleISOApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BookServiceTest {


    private BookService bookService;
    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        Book tmp = new Book();
        tmp.setIsbn10("1569319014");
        tmp.setIsbn13("9781569319017");
        tmp.setName("One Piece, Vol. 1");
        tmp.setId(1);
        List<Book> myBook = new ArrayList<>();
        myBook.add(tmp);
        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        GoogleISOApi googleISOApi = Mockito.mock(GoogleISOApi.class);
        when(googleISOApi.getName("1569319014")).thenReturn("One Piece");
        when(bookRepository.findByisbn10("1569319014")).thenReturn(myBook);
        when(bookRepository.findByisbn13("9781569319017")).thenReturn(myBook);
        when(googleISOApi.getName("354059101x")).thenReturn("{\"items\": [{\"volumeInfo\": {\"title\": \"Einführung in die Wirtschaftsinformatik\",\"industryIdentifiers\": [{\"type\": \"ISBN_10\",\"identifier\": \"354059101X\"},{\"type\": \"ISBN_13\",\"identifier\": \"9783540591016\"}]}}]}");
        when(googleISOApi.getName("9783540591016")).thenReturn("{\"items\": [{\"volumeInfo\": {\"title\": \"Einführung in die Wirtschaftsinformatik\",\"industryIdentifiers\": [{\"type\": \"ISBN_10\",\"identifier\": \"354059101X\"},{\"type\": \"ISBN_13\",\"identifier\": \"9783540591016\"}]}}]}");
        bookService = new BookService(bookRepository,googleISOApi);
    }


    @Test
    void getNameByIsbn13() throws IOException, InterruptedException {
        ResponseEntity<String> response = bookService.getNameByIsbn("9783540591016");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1569319014", "3644009473", "354059101X", "354059101x"})
    void testValidIsbn(String isbn) {
        assertTrue(bookService.isIsbn10Valid(isbn));
    }

    @Test
    void testInvalidIsnb(){
        assertFalse(bookService.isIsbn10Valid("1234567890"));
    }

    @Test
    void testValidIsbn13(){
        assertTrue(bookService.isIsbn13Valid("9781569319017"));
    }

    @Test
    void testInvalidIsbn13(){
        assertFalse(bookService.isIsbn13Valid("9781569318017"));
    }

}
