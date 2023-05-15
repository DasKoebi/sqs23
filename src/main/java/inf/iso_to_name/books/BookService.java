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
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public String getBookByIsbn(String isbn) throws IOException, InterruptedException {
        try {
            int length = isbn.length();
            //check if ISBN is 10 or 13 digits
            if (length == 10 && is_isbn10_valid(isbn)|| length == 13 && is_isbn13_valid(isbn)) {

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
            return "Not a valid ISBN";

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
            if(object.getItems().length >0){
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
            return "No Book found";
    }

    private void saveBookinDatabase(Book book){
        bookRepository.save(book);
    }

    public boolean is_isbn10_valid(String isbn){
        char[] tmp = isbn.toCharArray();
        int a =0;

        for (int i = 1; i<10; i++){
            a +=Integer.parseInt(String.valueOf(tmp[i-1]))*i;
        }
        int rest = a % 11;
        if(rest == 10) return (tmp[9]=="X".toCharArray()[0] || tmp[9]=="x".toCharArray()[0]);
        return rest == Integer.parseInt(String.valueOf(tmp[9]));
    }

    public boolean is_isbn13_valid(String isbn){
        char[] tmp = isbn.toCharArray();
        int a ;
        int b= 0;
        for (int i = 1; i<13; i++){
            a = Integer.parseInt(String.valueOf(tmp[i-1]));
            if(i%2 == 0) {
                b += a*3;
            } else {
                b += a;
            }
        }
        int rest = b % 10;
        return tmp[12] == 0 || (Integer.parseInt(String.valueOf(tmp[12])) + rest) == 10;
    }


}