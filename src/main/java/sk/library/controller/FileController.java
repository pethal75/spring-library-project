package sk.library.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.library.service.StorageService;

@RestController
@Slf4j
public class FileController {

    @Autowired
    private StorageService storageService;

    @PostMapping(value = "/saveData")
    public String saveDataToFile(@RequestBody String data) throws IOException {

        log.info("saveDataToFile called with data: " + data);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        LocalDateTime dateTime = LocalDateTime.now();
        String formattedDateTime = dateTime.format(formatter);

        storageService.saveFile("data" + formattedDateTime + ".txt", data);

        log.info("saveDataToFile ended");

        return "OK";
    }
}
