package com.school.mailservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail/")
public class MailController {

    MailProcessingService mailProcessingService;

    public MailController(MailProcessingService mailProcessingService) {
        this.mailProcessingService = mailProcessingService;
    }

    @PostMapping(value = "/send",consumes = "application/json")
    public ResponseEntity sendMail(@RequestBody MailRequestBody mailRequestBody){
        return  this.mailProcessingService.sendMail(mailRequestBody.getName(),mailRequestBody.getEmail());
    }
}
