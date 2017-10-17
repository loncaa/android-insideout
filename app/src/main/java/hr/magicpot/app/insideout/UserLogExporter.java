package hr.magicpot.app.insideout;

import android.content.Context;
import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
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

                document.open();
                document.add(new Paragraph("START\t\tEND\t\tDURATION"));

                for(UserLog log : logs)
                    document.add(new Paragraph(UserLogHelper.formatData(log.getStart()) + "\t\t" + UserLogHelper.formatData(log.getEnd()) + "\t\t" + UserLogHelper.formatDuration(log.getStart(), log.getEnd())));

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
