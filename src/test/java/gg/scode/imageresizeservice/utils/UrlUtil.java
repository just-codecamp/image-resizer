package gg.scode.imageresizeservice.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UrlUtil {

    public static URL generateLoremUrl(int width, int height) throws MalformedURLException {
        return URI.create(generateLoremUrlString(width, height)).toURL();
    }

    public static String generateLoremUrlString(int width, int height) {
        return String.format("https://picsum.photos/%d/%d", width, height);
    }

    public static URL convertUrl(String urlString) throws MalformedURLException {
        return URI.create(urlString).toURL();
    }

    public static String generateMalformedUrlString() {
        return "https://www.do<unexpected>main.com/path/to/resource?query=param#fragment";
    }

    public static String generateHttpUrlString() {
        return "http://some.unexpected.url/";
    }

    public static List<String> generateLoremUrlStrings(int amount) {
        Random random = new Random();
        List<String> result = new ArrayList<>();
        for ( int i = 0; i < 30; i++ ) {
            result.add(generateLoremUrlString(random.nextInt(100,500), random.nextInt(100,500)));
        }
        return result;
    }

    public static List<String> generateMalformedUrlStrings() {
        List<String> malformedUrls = Arrays.asList(
                "http:/www.example.com",
                "https://",
                "https://www.some<invalid>site.com",
                "https://www.somesite.com/ path",
                "http://www.somesite.com#fragment",
                "https://www.somesite.com/path/ to/resource?query=param#fragment",
                "https://www.somesite.com/path/to/resource?query=param fragment",
                "http:www.somesite.com",
                "https:/www.somesite.com",
                "https://www.some_site.com",
                "https://www.somesite.com/path with space",
                "https://www.somesite.com/path/to/resource? query=param",
                "https://www.somesite.com/path/to/resource?query =param",
                "https://www.somesite.com/path/to/resource?query= param",
                "https://www.somesite.com/path/to/resource?query=param #fragment",
                "https://www.somesite.com/path/to/resource?query=param# fragment",
                "https://www.somesite.com/path/to/resource?query=param#fragment ",
//                "https://www.some@site.com", // except to special letters
                "https://www..somesite.com",
                "https://www.somesite.com//path/to/resource",
                "https:www.somesite.com"
        );
        return malformedUrls;
    }

}
