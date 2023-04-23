package inf.isoToName.proxy;

import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GoogleISOApi {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GoogleISOApi.class);
    private static String baseUri="https://www.googleapis.com/books/v1/volumes?q=isbn:";
    //order getProperty("");
    private String apiKey = System.getenv("API_KEY");
    public String getName(String isbn) throws IOException, InterruptedException {
        String url = baseUri+isbn+"&key="+apiKey;
        String name = Proxy.getMessage(url);
        return  name;
    }
}
