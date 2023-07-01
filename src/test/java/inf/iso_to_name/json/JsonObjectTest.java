package inf.iso_to_name.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonObjectTest {


    private JsonObject jsonObject;
    @BeforeEach
    public void setUp(){
        jsonObject = new JsonObject();
    }
    @Test
    void setItemAndGetItem(){

        VolumeInfo volumeInfo = new VolumeInfo();
        IndustryIdentifiers[] tmp = new IndustryIdentifiers[1];
        Item[] item = new Item[1];
        //create IndustryIdentifier
        tmp[0] = new IndustryIdentifiers();
        tmp[0].setType("ISBN");
        tmp[0].setIdentifier("Hans");

        //create VolumeInfo
        volumeInfo.setTitle("Titel");
        volumeInfo.setIndustryIdentifiers(tmp);

        //Create Item
        item[0] = new Item();
        item[0].setVolumeInfo(volumeInfo);

        jsonObject.setItems(item);
        assertArrayEquals(item, jsonObject.getItems());
    }
}
