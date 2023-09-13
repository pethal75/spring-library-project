package sk.library.service;

import java.io.FileWriter;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StorageService {

    @Value("${adresar}")
    private String directory;

    public void saveFile(String fileName, String data) throws IOException {

        log.info("saveFile {} to directory: {}", fileName, directory);

        FileWriter fileWriter = new FileWriter(directory + fileName);
        fileWriter.write(data);
        fileWriter.close();

        log.info("saveFile saved");
    }
}
