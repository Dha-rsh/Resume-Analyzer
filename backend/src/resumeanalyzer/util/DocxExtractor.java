 package resumeanalyzer.util;


import java.io.InputStream;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class DocxExtractor {

    public static String extractText(InputStream file) {
        try (XWPFDocument doc = new XWPFDocument(file);
             XWPFWordExtractor extractor = new XWPFWordExtractor(doc)) {

            return extractor.getText();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}