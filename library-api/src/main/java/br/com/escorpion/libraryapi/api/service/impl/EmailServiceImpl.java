package br.com.escorpion.libraryapi.api.service.impl;

import br.com.escorpion.libraryapi.api.service.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendEmails(List<String> mailsList, String message) {

    }
}
