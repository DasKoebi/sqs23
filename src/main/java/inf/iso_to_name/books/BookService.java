package inf.iso_to_name.books;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import inf.iso_to_name.json.JsonObject;
import inf.iso_to_name.proxy.GoogleISOApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BookService {
    private static final Logger logger = Logger.getLogger(BookService.class.getName());

    //@Autowired
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public String getBookByIsbn(String isbn) throws IOException, InterruptedException {
        try {
            int length = isbn.length();
            //check if ISBN is 10 or 13 digits
            if (length == 10 || length == 13) {

            Double.parseDouble(isbn);
            //check if ISBN is stored in Database

            List<Book> myBook = bookRepository.findByisbn10(isbn);

            if(myBook.isEmpty()){
                logger.log(Level.INFO, "ISBN_10 not found in Database");
                myBook = bookRepository.findByisbn13(isbn);
                if(myBook.isEmpty()){
                    logger.log(Level.INFO, "ISBN_13 not found in Database");
                    logger.log(Level.INFO, "Calling Google...");
                    return callGoogleAPI(isbn);
                }return myBook.get(0).getName();
            }return myBook.get(0).getName();
            }
            return "The ISBN must have 10 or 13 digits.";

        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new IOException(e);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new InterruptedException();
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return "ISBN only contains Numbers";
        }
    }

    private String callGoogleAPI(String isbn) throws IOException, InterruptedException {

        GoogleISOApi googleISOApi = new GoogleISOApi();
        String response= googleISOApi.getName(isbn);

        Gson gson = new GsonBuilder().create();
        JsonObject object = gson.fromJson(response, JsonObject.class);

            Book tmp = new Book();
            if(object.getItems().length ==1){
                tmp.setName((object.getItems()[0].getVolumeInfo().getTitle()));
                for (int j = 0; j< object.getItems()[0].getVolumeInfo().getIndustryIdentifiers().length; j++) {
                    if(object.getItems()[0].getVolumeInfo().getIndustryIdentifiers()[j].getType().equals("ISBN_13"))
                        tmp.setIsbn13(object.getItems()[0].getVolumeInfo().getIndustryIdentifiers()[j].getIdentifier());
                    else if (object.getItems()[0].getVolumeInfo().getIndustryIdentifiers()[j].getType().equals("ISBN_10")) {
                        tmp.setIsbn10(object.getItems()[0].getVolumeInfo().getIndustryIdentifiers()[j].getIdentifier());
                    }
                }
                saveBookinDatabase(tmp);
                return object.getItems()[0].getVolumeInfo().getTitle();
            }
            return "ISBN needs to be unique";
    }

    private void saveBookinDatabase(Book book){
        bookRepository.save(book);
    }
}
