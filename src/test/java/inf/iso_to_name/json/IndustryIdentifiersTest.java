package inf.iso_to_name.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndustryIdentifiersTest {


    private IndustryIdentifiers industryIdentifiers;
    @BeforeEach
    public void setUp(){
        industryIdentifiers = new IndustryIdentifiers();
    }
    @Test
    void setTypeAndGetType() {
        industryIdentifiers.setType("ISBN10");
        assertEquals("ISBN10",industryIdentifiers.getType());
    }
    @Test
    void setIdentifierAndGetIdentifier() {
        industryIdentifiers.setIdentifier("1234567");
        assertEquals("1234567",industryIdentifiers.getIdentifier());
    }
}
