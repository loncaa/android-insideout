package hr.magicpot.app.insideout;

import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hr.magicpot.app.insideout.storage.db.model.UserLog;
import hr.magicpot.app.insideout.userinterface.adapter.UserLogHelper;

/**
 * Created by Antonio on 16.10.2017..
 */
public class UserLogExporter {
    public String export(List<UserLog> logs) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "report_" +timeStamp + ".pdf");
            if(path.mkdirs())
                return "mkdirs error";

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));

            document.open();
            document.add(new Paragraph("START\t\tEND\t\tDURATION"));

            for(UserLog log : logs)
                document.add(new Paragraph(UserLogHelper.formatData(log.getStart()) + "\t\t" + UserLogHelper.formatData(log.getEnd()) + "\t\t" + UserLogHelper.formatDuration(log.getStart(), log.getEnd())));

            document.close();
            return "Exported";
        } catch (DocumentException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
    }
}
