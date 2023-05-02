package inf.isoToName.books;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import inf.isoToName.json.JsonObject;
import inf.isoToName.proxy.GoogleISOApi;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class BookService {

    //@Autowired
    private BookRepository bookRepository;

    public String getBookByIsbn(String isbn){
        GoogleISOApi googleISOApi1 = new GoogleISOApi();
        try {
            int length = isbn.length();
            //check if ISBN is 10 or 13 digits
            if (length == 10 || length == 13) {

            //check if ISBN is stored in Database
            //TODO call Database
            String response = "";
            String isbnFromDatabase = "";
            try{
                isbnFromDatabase = bookRepository.findByisbn10(isbn).toString();
            }catch (NullPointerException e){
                //TODO logger
                System.out.println("isbn 10 not found");
            }
            try{
                isbnFromDatabase = bookRepository.findByisbn13(isbn).toString();
            }catch (NullPointerException e){
                //TODO logger
                System.out.println("isbn 13 not found");
            }

            //check if ISBN is stored in Database, if null it's not stored
            if (isbnFromDatabase == null || isbnFromDatabase.length()==0){
                //call Google API
                response= googleISOApi1.getName(isbn);
                //Gson
                Gson gson = new GsonBuilder().create();
                JsonObject object = gson.fromJson(response, JsonObject.class);

                System.out.println(object.items[0].volumeInfo.title);
                System.out.println(object.items[0].volumeInfo.industryIdentifiers[0].identifier);

            }




            return response;
            }
            return "The ISBN must have 10 or 13 digits.";

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw  new RuntimeException(e);
        }
    }

    private void searchIsbnInDatabase(int isbn){

    }

}
