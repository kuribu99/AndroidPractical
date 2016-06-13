package com.kongmy.androidprac;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText tbxP1X;
    private TextInputEditText tbxP1Y;
    private TextInputEditText tbxP2X;
    private TextInputEditText tbxP2Y;
    private TextInputEditText tbxMidX;
    private TextInputEditText tbxMidY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tbxP1X = (TextInputEditText) findViewById(R.id.tbx_p1_x);
        tbxP1Y = (TextInputEditText) findViewById(R.id.tbx_p1_y);
        tbxP2X = (TextInputEditText) findViewById(R.id.tbx_p2_x);
        tbxP2Y = (TextInputEditText) findViewById(R.id.tbx_p2_y);
        tbxMidX = (TextInputEditText) findViewById(R.id.tbx_mid_x);
        tbxMidY = (TextInputEditText) findViewById(R.id.tbx_mid_y);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       if (tbxP1X.getText().toString().isEmpty()
                                               || tbxP1Y.getText().toString().isEmpty()
                                               || tbxP2X.getText().toString().isEmpty()
                                               || tbxP1Y.getText().toString().isEmpty()) {

                                           tbxMidX.setText("-");
                                           tbxMidY.setText("-");

                                           final Snackbar snackbar = Snackbar.make(
                                                   view,
                                                   "Please fill up all the coordinates for \nPoint 1 and Point 2",
                                                   Snackbar.LENGTH_LONG);

                                           snackbar.setAction("Dismiss", new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   snackbar.dismiss();
                                               }
                                           });
                                           snackbar.show();

                                       } else {
                                           HttpAsyncTask task = new HttpAsyncTask();
                                           try {
                                               String result = task.execute(
                                                       tbxP1X.getText().toString(),
                                                       tbxP1Y.getText().toString(),
                                                       tbxP2X.getText().toString(),
                                                       tbxP2Y.getText().toString()).get();

                                               if (result == null || result.isEmpty() || result.equals("null")) {
                                                   tbxMidX.setText("-");
                                                   tbxMidY.setText("-");
                                               }
                                               JSONObject obj = new JSONObject(result);
                                               tbxMidX.setText(String.format("%.3f", obj.getDouble("x")));
                                               tbxMidY.setText(String.format("%.3f", obj.getDouble("y")));

                                           } catch (Exception e) {
                                               e.printStackTrace();

                                               tbxMidX.setText("-");
                                               tbxMidY.setText("-");
                                           }

                                           final Snackbar snackbar = Snackbar.make(
                                                   view,
                                                   "Midpoint coordinates updated",
                                                   Snackbar.LENGTH_LONG);
                                           snackbar.setAction("Dismiss", new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   snackbar.dismiss();
                                               }
                                           });
                                           snackbar.show();
                                       }
                                   }
                               }
        );
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (strings.length != 4) return null;
            String param = String.format(
                    "p1x=%s&p1y=%s&p2x=%s&p2y=%s",
                    strings[0], strings[1],
                    strings[2], strings[3]
            );
            try {
                URL url = new URL("http://192.168.56.2:8080/midpoint");

                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setDoInput(true);
                httpConn.setDoOutput(true);
                httpConn.setRequestMethod("POST");

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(httpConn.getOutputStream()));
                writer.write(param);
                writer.flush();
                writer.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }

                return builder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
