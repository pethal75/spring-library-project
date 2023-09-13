package sk.library.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReminderTask {

    @Scheduled(fixedRate = 3000)
    public void runTask() {
        log.info("Running task");
    }
}
