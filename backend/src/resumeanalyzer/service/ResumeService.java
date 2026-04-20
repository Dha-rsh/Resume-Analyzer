package resumeanalyzer.service;

import java.io.InputStream;

import resumeanalyzer.util.DocExtractor;
import resumeanalyzer.util.DocxExtractor;
import resumeanalyzer.util.PdfExtractor;

public class ResumeService {
    
    public String analyze(InputStream fileContent, String fileName, String jobDesc) {
    String text = "";

    if (fileName.endsWith(".pdf")) {
        text = PdfExtractor.extractText(fileContent);

    } else if (fileName.endsWith(".txt")) {
        try {
            text = new String(fileContent.readAllBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    } else if (fileName.endsWith(".docx")) {
    try {
        text = DocxExtractor.extractText(fileContent);
        System.out.println("DOCX TEXT: " + text);
    } catch (Exception e) {
        e.printStackTrace();
        return "{ \"error\": \"DOCX reading failed\" }";
    }
}else if (fileName.endsWith(".doc")) {
        text = DocExtractor.extractText(fileContent);

    } else {
        return "{ \"error\": \"Unsupported file type\" }";
    }

    return new AIService().analyze(text, jobDesc);
}
}
