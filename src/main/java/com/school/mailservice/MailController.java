package com.school.mailservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;

@RestController
@RequestMapping("/mail/")
public class MailController {

    MailProcessingService mailProcessingService;

    public MailController(MailProcessingService mailProcessingService) {
        this.mailProcessingService = mailProcessingService;
    }

    @PostMapping(value = "/send",consumes = "application/json")
    public ResponseEntity sendMail(@RequestBody MailRequestBody mailRequestBody){
        return ResponseEntity.ok(Locale.getAvailableLocales().length);
    }

    @PostMapping(value = "/sendAssignmentSubmission",consumes = "application/json")
    public ResponseEntity sendAssignmentSubmission(@RequestBody HashMap<String,String> mailRequestBody){
        return ResponseEntity.ok(
                this.mailProcessingService.sendAssignmentSubmission(mailRequestBody.get("name"),
                        mailRequestBody.get("email"),
                        mailRequestBody.get("assignmentTitle"),
                        mailRequestBody.get("batch")));
    }

    @GetMapping(value = "/locale")
    public ResponseEntity locale(){
        return ResponseEntity.ok(Locale.getISOCountries());
    }

}
