package com.kongmy.androidprac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jamesooi.computations.Event;

import java.util.List;

/**
 * Created by Kong My on 1/8/2016.
 */
public class EventListAdapter extends BaseAdapter {

    private final Context context;
    private List<Event> eventList;

    public EventListAdapter(Context context) {
        this.context = context;
        refresh();
    }


    public void refresh() {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        eventList = databaseHelper.findAllEvents();
        databaseHelper.close();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return eventList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView titleTextView = null;
        TextView dayTextView = null;
        View view = convertView;

        if (convertView != null) {
            titleTextView = (TextView) convertView.findViewById(R.id.textview_title);
            dayTextView = (TextView) convertView.findViewById(R.id.textview_days);
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_event, parent, false);
            titleTextView = (TextView) view.findViewById(R.id.textview_title);
            dayTextView = (TextView) view.findViewById(R.id.textview_days);
        }

        Event event = eventList.get(position);
        long countdown = event.getCountdown();
        String dayText = "";

        // Already finished countdown
        if (countdown >= 0)
            dayText = "Ended";
        else {
            int days = (int) Math.abs(countdown);
            dayText = String.valueOf(days) + (days == 1 ? " day" : " days");
        }

        titleTextView.setText(event.getTitle());
        dayTextView.setText(dayText);

        return view;
    }

    public void deleteEvent(long id) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.deleteEvent(id);
        databaseHelper.close();
        notifyDataSetChanged();
    }

}
