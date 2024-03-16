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
        String mailString = springTemplateEngine.process("new-email",context);
        return  mailString;
    }

    public String getAssignmentSubmission(String name,String assignmentTitle,String batch){
        Context context = new Context();
        context.setVariable("studentName",name);
        context.setVariable("assignmentTitle",assignmentTitle);
        context.setVariable("batch",batch);
        String mailString = springTemplateEngine.process("submition",context);
        return  mailString;
    }
}
