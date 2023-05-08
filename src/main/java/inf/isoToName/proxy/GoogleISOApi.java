package inf.isoToName.proxy;

import java.io.IOException;

public class GoogleISOApi {
    private static final String apiKey = System.getenv().get("API_KEY");
    private static final String baseUri = System.getenv().getOrDefault("BASE_URI", "https://www.googleapis.com/books/v1/volumes?q=isbn:");
    //order getProperty("");
    public String getName(String isbn) throws IOException, InterruptedException {
        System.out.println(apiKey);
        String url = baseUri+isbn+"&key="+apiKey;
        String name = Proxy.getMessage(url);
        return  name;
    }
}
