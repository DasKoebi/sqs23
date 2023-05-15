package inf.iso_to_name.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VolumeInfoTest {
    private VolumeInfo volumeInfo;
    @BeforeEach
    public void setUp(){
        volumeInfo = new VolumeInfo();
    }

    @Test
    void setTitelAndGetTitel(){
        volumeInfo.setTitle("Hans");
        assertEquals("Hans", volumeInfo.getTitle());
    }
    @Test
    void setIndustryIdentifiersAndGetIndustryIdentifiers(){
        IndustryIdentifiers[] tmp = new IndustryIdentifiers[1];
        tmp[0] = new IndustryIdentifiers();
        tmp[0].setType("ISBN");
        tmp[0].setIdentifier("Hans");
        volumeInfo.setIndustryIdentifiers(tmp);
        System.out.println(tmp);
        assertArrayEquals(tmp, volumeInfo.getIndustryIdentifiers());
    }
}