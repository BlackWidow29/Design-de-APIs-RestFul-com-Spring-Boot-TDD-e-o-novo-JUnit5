package br.com.escorpion.libraryapi.api.service;

import br.com.escorpion.libraryapi.api.model.entity.Loan;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleService {

    private static final String CRON_LATE_LOANS = "0 0 0 1/1 * ?";

    @Value("${application.mail.lateloans.message}")
    private String message;
    private final LoanService loanService;
    private final EmailService emailService;

    @Scheduled
    public void sendEmailToLateLoans() {
        List<Loan> allLateLoans = loanService.getAllLateLoans();

        List<String> mailsList = allLateLoans.stream()
                .map(Loan::getCustomerEmail)
                .collect(Collectors.toList());

        emailService.sendEmails(mailsList, message);
    }

}
