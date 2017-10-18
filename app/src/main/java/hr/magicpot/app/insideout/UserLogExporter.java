package hr.magicpot.app.insideout;

import android.content.Context;
import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hr.magicpot.app.insideout.storage.db.model.UserLog;
import hr.magicpot.app.insideout.userinterface.adapter.UserLogHelper;

public class UserLogExporter {
    public String export(List<UserLog> logs, Context context) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File docsFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Insideout reports");
            boolean isPresent = true;
            if (!docsFolder.exists()) {
                isPresent = docsFolder.mkdirs();
            }

            if (isPresent) {
                Document document = new Document();
                File pdfFile = new File(docsFolder, "report_" +timeStamp + ".pdf");
                PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

                Font smallfont = new Font(Font.FontFamily.HELVETICA, 10);
                PdfPTable table = new PdfPTable(3); //START		END		DURATION


                PdfPCell cell = new PdfPCell(new Phrase("Start"));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(30);
                cell.setBorder(Rectangle.BOTTOM);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("End"));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(30);
                cell.setBorder(Rectangle.BOTTOM);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Duration"));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(30);
                cell.setBorder(Rectangle.BOTTOM);
                table.addCell(cell);

                for(UserLog log : logs) {
                    cell = new PdfPCell(new Phrase(UserLogHelper.formatData(log.getStart()).toString(), smallfont));
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setFixedHeight(30);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(UserLogHelper.formatData(log.getEnd()).toString(), smallfont));
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(UserLogHelper.formatDuration(log.getStart(), log.getEnd()).toString(), smallfont));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                }

                document.open();
                document.add(table);
                document.close();

                return "Exported to " + pdfFile.toString();
            }

        } catch (IOException e) {
            return e.getLocalizedMessage();
        } catch (DocumentException e) {
            return e.getLocalizedMessage();
        }

        return "Exporting failed.";
    }
}
