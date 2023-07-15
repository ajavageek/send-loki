package ch.frankel.blog.loki;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        var template = """
                '{'
                  "streams": [
                    '{'
                      "stream": '{'
                        "app": "{0}"
                      '}',
                      "values": [
                        [ "{1}", "{2}" ]
                      ]
                    '}'
                  ]
                '}'""";
        var now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
        var nowInEpochNanos = NANOSECONDS.convert(now.getEpochSecond(), SECONDS) + now.getNano();
        var payload = MessageFormat.format(template, "demo", String.valueOf(nowInEpochNanos), "Hello from Java App");
        System.out.println(payload);
        var request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:3100/loki/api/v1/push"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response);
    }
}
