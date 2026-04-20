package resumeanalyzer.util;

import java.io.InputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class DocExtractor {
    public static String extractText(InputStream file) {
        try {
            HWPFDocument doc = new HWPFDocument(file);
            WordExtractor extractor = new WordExtractor(doc);
            String text = extractor.getText();
            extractor.close();
            doc.close();
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}