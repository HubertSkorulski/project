
package com.finalproject.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.mail.SimpleMailMessage;


@Getter
@AllArgsConstructor
public class Mail extends SimpleMailMessage {

    private final String mailTo;
    private final String subject;
    private final String message;

    public static class MailBuilder {
        private String mailTo;
        private String subject;
        private String message;


        public MailBuilder mailTo(String mailTo) {
            this.mailTo = mailTo;
            return this;
        }

        public MailBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public MailBuilder message(String message) {
            this.message = message;
            return this;
        }

        public Mail create() {
            return new Mail(mailTo, subject, message);
        }
    }
}

