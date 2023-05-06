package inf.isoToName.books;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import inf.isoToName.json.JsonObject;
import inf.isoToName.proxy.GoogleISOApi;
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

    public String getBookByIsbn(String isbn){
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
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
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
            tmp.setName(object.items[0].volumeInfo.title);
            for (int j=0; j<object.items[0].volumeInfo.industryIdentifiers.length; j++){
                if(object.items[0].volumeInfo.industryIdentifiers[j].type.equals("ISBN_13"))
                    tmp.setIsbn_13(object.items[0].volumeInfo.industryIdentifiers[j].identifier);
                else if (object.items[0].volumeInfo.industryIdentifiers[j].type.equals("ISBN_10")) {
                    tmp.setIsbn_10(object.items[0].volumeInfo.industryIdentifiers[j].identifier);
                }
            }
            saveBookinDatabase(tmp);
            return object.items[0].volumeInfo.title;
    }

    private void saveBookinDatabase(Book book){
        bookRepository.save(book);
    }
}
