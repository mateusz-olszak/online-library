package com.library.scheduler;

import com.library.config.AdminConfig;
import com.library.domain.Mail;
import com.library.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailScheduler {

    private static final String SUBJECT = "Library: Books info";

    private final SimpleEmailService emailService;
    private final AdminConfig adminConfig;

    @Scheduled(cron = "0 0 12 * * MON")
    public void sendInformationEmail() {
        log.info("preparing to send weekly database summary email");
        emailService.send(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                "Weekly summary of books status in the database.",
                null
        ));
        log.info("email has been sent");
    }

}
