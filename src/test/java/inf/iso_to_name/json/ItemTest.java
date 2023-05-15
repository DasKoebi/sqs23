package inf.iso_to_name.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private Item item;
    @BeforeEach
    public void setUp(){
        item = new Item();
    }

    @Test
    void setVolumeInfoAndGetVolumeInfo(){
        VolumeInfo volumeInfo = new VolumeInfo();

        //create IndustryIdentifier
        IndustryIdentifiers[] tmp = new IndustryIdentifiers[1];
        tmp[0] = new IndustryIdentifiers();
        tmp[0].setType("ISBN");
        tmp[0].setIdentifier("Hans");

        //create VolumeInfo
        volumeInfo.setTitle("Titel");
        volumeInfo.setIndustryIdentifiers(tmp);

        item.setVolumeInfo(volumeInfo);
        assertEquals(volumeInfo, item.getVolumeInfo());
    }
}