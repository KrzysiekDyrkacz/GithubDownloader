package pl.krzysiekdyrkacz.githubdownloader.downloader;

import jdk.jfr.Event;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
public class Download {

    private final URL url = new URL(String.format("https://api.github.com/repos/KrzysiekDyrkacz/GithubDownloader/contents?ref=main"));

    WebClient client = WebClient.create();
    WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
    WebClient.RequestBodySpec bodySpec = (WebClient.RequestBodySpec) uriSpec.uri( "https://api.github.com/repos/{owner}/{repo}/contents?ref={branch}",  "KrzysiekDyrkacz",
            "GithubDownloader","main");
    WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.body(BodyInserters.fromValue("data"));

    public Download() throws MalformedURLException {
    }


    @Scheduled(fixedDelay = 5000)
    public void start() throws IOException {

        Mono<Event> response = headersSpec.retrieve().bodyToMono(Event.class);

        try(
                InputStream inputStream = url.openStream();
                ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
                FileOutputStream f = new FileOutputStream("sourced")) {
            f.getChannel().transferFrom(readableByteChannel,0,Long.MAX_VALUE);
            Files.createFile(Path.of("successFlag"));
        } catch (Exception e){
            e.printStackTrace();
        }









    }


}
