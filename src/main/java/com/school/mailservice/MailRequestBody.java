package com.school.mailservice;

public class MailRequestBody {
    String email;
    String name;

    public MailRequestBody() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MailRequestBody(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
