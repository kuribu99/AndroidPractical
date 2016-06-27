package com.kongmy.androidprac;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tbxName;
    private TextView tbxHp;
    private TextView tbxEmail;
    private ImageButton btnCall;
    private ImageButton btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SalesAgent agent = new SalesAgent("Lee Xiao Ming", "+6012-3827289", "kuribu99@hotmail.com");

        tbxName = (TextView) findViewById(R.id.tbx_name);
        tbxHp = (TextView) findViewById(R.id.tbx_hp);
        tbxEmail = (TextView) findViewById(R.id.tbx_email);
        btnCall = (ImageButton) findViewById(R.id.btn_call);
        btnEmail = (ImageButton) findViewById(R.id.btn_email);

        tbxName.setText(agent.getName());
        tbxHp.setText(agent.getContactNumber());
        tbxEmail.setText(agent.getEmail());

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + agent.getContactNumber());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, agent.getEmail());
                intent.putExtra(Intent.EXTRA_SUBJECT, "Enquiry for Insurance");
                intent.putExtra(Intent.EXTRA_TEXT, String.format(
                        "Hi %s,\n\nI am interested to know more about insurance.\nThanks.\n\nRegards,\nMe",
                        agent.getName()
                ));
                startActivity(intent);
            }
        });

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

        String text = "";
        switch (id) {
            case R.id.action_add:
                text = "Add action";
                break;

            case R.id.action_subtract:
                text = "Subtract action";
                break;

            case R.id.action_multiply:
                text = "Multiply action";
                break;

            case R.id.action_divide:
                text = "Divide action";
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        showDismissibleSnackbar(text);
        return true;
    }

}
