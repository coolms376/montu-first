package com.school.mailservice;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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


    @PostMapping(value = "/sendOtpViaEmail",consumes = "application/json")
    public ResponseEntity sendOtpViaEmail(@RequestBody HashMap<String,String> mailProperties){
        return ResponseEntity.ok(
                this.mailProcessingService.sendOTPMail(mailProperties.get("name"),mailProperties.get("email")));
    }

    @GetMapping(value = "/locale")
    public ResponseEntity locale(){
        return ResponseEntity.ok(Locale.getISOCountries());
    }


    @GetMapping("/getCertificat")
    public void getcertificate() throws IOException {
        String  name = " Montu Sharma";
        String  course = " Montu Sharma";

        String  filename = "Montu Sharma-html.pdf";
        String date = LocalDate.now().toString();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                // Set font and font size for text
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);

                // Draw the title
                contentStream.beginText();
                contentStream.newLineAtOffset(150, 680);
                contentStream.showText("Certificate of Completion");
                contentStream.endText();

                // Set font and font size for other text
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                // Draw certificate details
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 600);
                contentStream.showText("This is to certify that");
                contentStream.newLine();
                contentStream.showText(name);
                contentStream.newLine();
                contentStream.showText("has successfully completed the course:");
                contentStream.newLine();
                contentStream.showText(course);
                contentStream.newLine();
                contentStream.showText("Date:");
                contentStream.newLine();
                contentStream.showText(date);
                contentStream.endText();
            }
            document.save(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
