package hr.magicpot.app.insideout.userinterface.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import hr.magicpot.app.insideout.R;
import hr.magicpot.app.insideout.storage.db.model.UserLog;

/**
 * Created by Antonio on 12.9.2017..
 */

public class UserLogListAdapter extends ArrayAdapter<UserLog> {

    HashMap<Integer, UserLog> mIdMap = new HashMap<>();
    int layoutRes;

    public UserLogListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<UserLog> objects) {
        super(context, resource, objects);

        layoutRes = resource;

        objects.set(0, new UserLog());

        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(i, objects.get(i));
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(layoutRes, null);
        } else {
            rowView = convertView;
        }

        TextView textViewL = (TextView) rowView.findViewById(R.id.starttime);
        TextView textViewM = (TextView) rowView.findViewById(R.id.endtime);
        TextView textViewR = (TextView) rowView.findViewById(R.id.duration);

        if(position == 0){
            textViewL.setText("START");
            textViewL.setTypeface(Typeface.DEFAULT_BOLD);

            textViewM.setText("END");
            textViewM.setTypeface(Typeface.DEFAULT_BOLD);

            textViewR.setText("DURATION");
            textViewR.setTypeface(Typeface.DEFAULT_BOLD);
        }
        else {
            UserLog log = mIdMap.get(position);
            textViewL.setText(DateFormat.format("hh:mm:ss", log.getStart()));
            textViewM.setText(DateFormat.format("hh:mm:ss", log.getEnd()));

            double duration = (log.getEnd().getTime() - log.getStart().getTime())/1000;

            textViewR.setText(""+duration+" sec");
        }

        return rowView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
