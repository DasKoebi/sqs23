package inf.isoToName.proxy;

import java.io.IOException;

public class GoogleISOApi {
    private static String baseUri="https://www.googleapis.com/books/v1/volumes?q=isbn:";
    //order getProperty("");
    private String apiKey = "";
    public String getName(String isbn) throws IOException, InterruptedException {
        System.out.println(apiKey);
        String url = baseUri+isbn+"&key="+apiKey;
        String name = Proxy.getMessage(url);
        return  name;
    }
}
