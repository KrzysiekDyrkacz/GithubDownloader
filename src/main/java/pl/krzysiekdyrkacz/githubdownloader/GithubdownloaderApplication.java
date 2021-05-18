package pl.krzysiekdyrkacz.githubdownloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.krzysiekdyrkacz.githubdownloader.downloader.Download;

@SpringBootApplication
@EnableScheduling
public class GithubdownloaderApplication {






    public static void main(String[] args) {
        SpringApplication.run(GithubdownloaderApplication.class, args);


    }

}
