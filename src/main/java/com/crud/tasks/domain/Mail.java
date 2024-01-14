package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    private String mailTo;
    private String subject;
    private String message;
    private String toCc;

    public void mailBuilder(final String mailTo, final String subject, final String message, final String cc) {
        Mail.builder()
                .mailTo(mailTo)
                .subject(subject)
                .message(message)
                .toCc(cc)
                .build();
    }
}
