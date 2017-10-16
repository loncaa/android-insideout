package hr.magicpot.app.insideout.userinterface.adapter;

import android.text.format.DateFormat;

import java.util.Date;

/**
 * Created by Antonio on 16.10.2017..
 */

public class UserLogHelper {
    public static CharSequence formatData(Date date) {
        return DateFormat.format("hh:mm:ss", date);
    }

    public static String formatDuration(Date start, Date end) {
        double duration = (end.getTime() - start.getTime())/1000.0;

        String durationText = "";
        if(duration < 60)
            durationText = Math.round(duration * 100)/100.0 +" s";
        else if(duration > 60 && duration < 60*60)
            durationText = Math.round(duration/60.0 * 100)/100.0+" m";
        else if(duration > 60*60)
            durationText =  Math.round(duration/(60.0*60.0) * 100)/100.0+" h";
        return durationText;
    }
}
