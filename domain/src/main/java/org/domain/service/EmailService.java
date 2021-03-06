package org.domain.service;

import com.sendgrid.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.domain.dto.EmailDto;
import org.domain.model.Consumer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final Email FROM = new Email("ma224wv@student.lnu.se");

    public String send(List<Consumer> consumers, EmailDto emailDto) throws Exception {
        Mail mail = new Mail(FROM, emailDto.getSubject(), FROM, new Content("text/plain", emailDto.getBody()));
        Personalization personalization = new Personalization();
        consumers.forEach(consumer -> personalization.addBcc(new Email(consumer.getEmail())));
        personalization.addTo(FROM);
        mail.addPersonalization(personalization);
        SendGrid sendGrid = new SendGrid(getEmailApiKey());
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sendGrid.api(request);
        return "{\"message\": \"Sent successfully\"}";
    }

    private String getEmailApiKey() {
        return Dotenv.load()
                .get("email.api.key");
    }
}
