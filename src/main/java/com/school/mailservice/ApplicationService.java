package com.school.mailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Service
public class ApplicationService {

    @Autowired
    SpringTemplateEngine springTemplateEngine;

    public String get(String name){
        Context context = new Context();
        context.setVariable("studentName",name);
        String mailString = springTemplateEngine.process("index",context);
        return  mailString;
    }
}
