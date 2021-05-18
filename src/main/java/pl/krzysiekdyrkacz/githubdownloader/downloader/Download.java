package pl.krzysiekdyrkacz.githubdownloader.downloader;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;


@Service
public class Download {

    RestTemplate restTemplate = new RestTemplate();
    List<Map> responses = restTemplate.getForObject(
            "https://api.github.com/repos/{owner}/{repo}/contents", List.class, "KrzysiekDyrkacz",
            "GithubDownloader");




    @Scheduled(fixedDelay = 5000)
    public void start() throws IOException {




        for (Map fileMetaData : responses) {
            System.out.println(responses + "\\/n");

            if(fileMetaData.get("type").equals("file")){
                String fileName = (String) fileMetaData.get("name");
                String downloadUrl = (String) fileMetaData.get("download_url");

                if (downloadUrl != null) {

                    File file = new File("source/" + fileName);
                    try {
                        FileUtils.copyURLToFile(new URL(downloadUrl), file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }else if (fileMetaData.get("type").equals("dir")){
                String dirName = (String) fileMetaData.get("name");
                if(!Files.exists(Path.of("source/" + dirName))){
                    Files.createDirectory(Path.of("source/" + dirName));
                }

            }

        }

        if(!Files.exists(Path.of("source/successFlag.txt"))){
            Files.createFile(Path.of("source/successFlag.txt"));
        }

    }


}
