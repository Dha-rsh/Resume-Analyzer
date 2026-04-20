package resumeanalyzer.util;

import java.io.InputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
public class PdfExtractor {
    public static String extractText(InputStream file)
    {
        String text="";
        try{
            PDDocument doc=PDDocument.load(file);
            PDFTextStripper strip = new PDFTextStripper();
            text=strip.getText(doc);
            doc.close();

        }catch(Exception e)
        {
            e.printStackTrace();
            return text;
        }
        return text;
    }
    
}
