package inf.iso_to_name.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book myBook;
    @BeforeEach
    public void setUp(){
        myBook = new Book();
    }
    @Test
    void setIdAndGetId() {
        myBook.setId(1);
        assertEquals(1,myBook.getId());
    }
    @Test
    void setNameAndGetName() {
        myBook.setName("Hans");
        assertEquals("Hans",myBook.getName());
    }
    @Test
    void setIsbn_10AndgetIsbn_10() {
        myBook.setIsbn10("1234567890");
        assertEquals("1234567890",myBook.getIsbn10());
    }

    @Test
    void setIsbn_13AndgetIsbn_13() {
        myBook.setIsbn13("1234567890123");
        assertEquals("1234567890123",myBook.getIsbn13());
    }
}
