package org.lpw.photon.pdf;

import java.io.OutputStream;

import javax.inject.Inject;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;

import org.lpw.photon.util.Logger;
import org.springframework.stereotype.Component;

@Component("photon.pdf.converter")
public class PdfConverterImpl implements PdfConverter {
    @Inject
    private Logger logger;
    private ConverterProperties properties;

    @Override
    public boolean html2pdf(String html, OutputStream outputStream) {
        try {
            if (properties == null) {
                properties = new ConverterProperties();
                properties.setFontProvider(new FontProvider());
                properties.getFontProvider().addSystemFonts();
            }

            PdfDocument document = new PdfDocument(new PdfWriter(outputStream));
            document.setDefaultPageSize(PageSize.A4);
            HtmlConverter.convertToPdf(html, document, properties);
            document.close();
            outputStream.close();

            return true;
        } catch (Exception e) {
            logger.warn(e, "HTML转化为PDF时发生异常！");

            return false;
        }
    }
}
