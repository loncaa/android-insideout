package hr.magicpot.app.insideout.userinterface.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import hr.magicpot.app.insideout.R;
import hr.magicpot.app.insideout.storage.db.model.UserLog;

public class UserLogListAdapter extends ArrayAdapter<UserLog> {

    final HashMap<Integer, UserLog> mIdMap = new HashMap<>();
    final int layoutRes;

    public UserLogListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<UserLog> objects) {
        super(context, resource, objects);

        layoutRes = resource;

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

        UserLog log = mIdMap.get(position);
        textViewL.setText(UserLogHelper.formatData(log.getStart()));

        if(log.getEnd() == null) {
            textViewM.setText("-");
            textViewR.setText("?");
        }
        else {
            textViewM.setText(UserLogHelper.formatData(log.getEnd()));
            textViewR.setText(UserLogHelper.formatDuration(log.getStart(), log.getEnd()));
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
