package inf.iso_to_name.proxy;

import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class GoogleISOApi {

    private static final String API_KEY = System.getenv("API_KEY");

    private static final String BASE_URI = System.getenv().getOrDefault("BASE_URI", "https://www.googleapis.com/books/v1/volumes?q=isbn:");
    public String getName(String isbn) throws IOException, InterruptedException {
        String url = BASE_URI+isbn+"&key="+API_KEY;
        return Proxy.getMessage(url);
    }
}
