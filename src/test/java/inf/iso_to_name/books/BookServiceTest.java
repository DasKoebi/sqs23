package inf.iso_to_name.books;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BookServiceTest {


    private BookService bookService;
    @BeforeEach
    public void setUp(){
        Book tmp = new Book();
        tmp.setIsbn10("1569319014");
        tmp.setIsbn13("9781569319017");
        tmp.setName("One Piece, Vol. 1");
        tmp.setId(1);
        List<Book> myBook = new ArrayList<>();
        myBook.add(tmp);
        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        when(bookRepository.findByisbn10("1569319014")).thenReturn(myBook);
        when(bookRepository.findByisbn13("9781569319017")).thenReturn(myBook);
        bookService = new BookService(bookRepository);
    }

    @Test
    void testGetBookByIsbn10() {
        try {
            assertEquals("One Piece, Vol. 1", bookService.getBookByIsbn("1569319014"));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testValidIsbn(){
        assertTrue(bookService.isIsbn10Valid("1569319014"));
    }
    @Test
    void testValidIsbn2(){
        assertTrue(bookService.isIsbn10Valid("3644009473"));
    }
    @Test
    void testValidIsbnWithX(){
        assertTrue(bookService.isIsbn10Valid("354059101X"));
    }
    @Test
    void testValidIsbnWithSmallX(){
        assertTrue(bookService.isIsbn10Valid("354059101x"));
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