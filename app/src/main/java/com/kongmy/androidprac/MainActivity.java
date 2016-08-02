package com.kongmy.androidprac;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ThemedActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    private EventListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new EventListAdapter(this);
        listView = (ListView) findViewById(R.id.list_view_events);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddEventActivity.newIntent(getApplicationContext()));
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    private void showDismissibleSnackbar(String s) {
        final Snackbar snackbar = Snackbar.make(findViewById(R.id.toolbar), s, Snackbar.LENGTH_LONG);

        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.actiom_settings:
                startActivity(SettingActivity.newIntent(this));
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onRefresh() {
        adapter.refresh();
        swipeRefreshLayout.setRefreshing(false);
        updateTheme();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        adapter.deleteEvent(id);
                        adapter.refresh();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage("Do you want to delete this event?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(ViewEventActivity.newIntent(this, id));
    }

    @Override
    protected void updateTextColor(int color) {
        for (int i = 0; i < listView.getChildCount(); i++) {
            View view = listView.getChildAt(i);
            TextView titleTextView = (TextView) view.findViewById(R.id.textview_title);
            TextView dayTextView = (TextView) view.findViewById(R.id.textview_days);

            titleTextView.setTextColor(color);
            dayTextView.setTextColor(color);
        }
    }

    @Override
    protected void updateBackgroundColor(int color) {
        findViewById(R.id.swipe_layout).setBackgroundColor(color);
    }

}
