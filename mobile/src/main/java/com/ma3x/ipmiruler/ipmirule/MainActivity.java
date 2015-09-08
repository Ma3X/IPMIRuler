package com.ma3x.ipmiruler.ipmirule;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import de.mindpipe.android.logging.log4j.LogConfigurator;
import org.apache.log4j.Level;

public class MainActivity extends ActionBarActivity {
    Button btnOnOff = null;
    Button btnReset = null;
    MainActivity My = null;
    AlertDialog.Builder ad;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        My = this;
        context = MainActivity.this;

        final LogConfigurator logConfigurator = new LogConfigurator();

        logConfigurator.setFileName(Environment.getExternalStorageDirectory() + File.separator + "myapp.log");
        logConfigurator.setRootLevel(Level.DEBUG);
        // Set log level of a specific logger
        logConfigurator.setLevel("org.apache", Level.ERROR);
        logConfigurator.configure();

        setTitle("IPMI: 10.0.0.11 is ...");
        btnOnOff = (Button) findViewById(R.id.button);
        btnOnOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // ON/OFF
                ad = new AlertDialog.Builder(context);
                ad.setTitle("Внимание");  // заголовок
                ad.setMessage("Подтвердите команду: " + btnOnOff.getText().toString()); // сообщение
                ad.setPositiveButton("Выполнить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        new RetrieveInfoTask(My).execute("192.168.1.50", btnOnOff.getText().toString());
                        Toast.makeText(context, "Команда " + btnOnOff.getText() + " отправлена",
                                Toast.LENGTH_LONG).show();
                    }
                });
                ad.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(context, "Отбой команды " + btnOnOff.getText(),
                                Toast.LENGTH_LONG).show();
                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(context, "Отбой. Вы ничего не выбрали",
                                Toast.LENGTH_LONG).show();
                    }
                });
                ad.show();
            }
        });
        btnReset = (Button) findViewById(R.id.button2);
        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // RESET
                ad = new AlertDialog.Builder(context);
                ad.setTitle("Внимание");  // заголовок
                ad.setMessage("Подтвердите команду: " + btnReset.getText().toString()); // сообщение
                ad.setPositiveButton("Выполнить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        new RetrieveInfoTask(My).execute("192.168.1.50", btnReset.getText().toString());
                        Toast.makeText(context, "Команда " + btnReset.getText() + " отправлена",
                                Toast.LENGTH_LONG).show();
                    }
                });
                ad.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(context, "Отбой команды " + btnReset.getText(),
                                Toast.LENGTH_LONG).show();
                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(context, "Отбой. Вы ничего не выбрали",
                                Toast.LENGTH_LONG).show();
                    }
                });
                ad.show();
            }
        });

        new RetrieveInfoTask(this).execute("192.168.1.50", "hello");
        String info = "Test sync.";
        Context ctx1 = getApplicationContext();
        Toast toast1 = Toast.makeText(ctx1, info, Toast.LENGTH_SHORT);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
    }

    public boolean PostRetrieveInfoTask(String info) {
        switch (info) {
            case "UP":
                this.btnOnOff.setText("OFF");
                this.btnOnOff.setEnabled(true);
                this.btnReset.setEnabled(true);
                break;
            case "DOWN":
                this.btnOnOff.setText("ON");
                this.btnOnOff.setEnabled(true);
                this.btnReset.setEnabled(false);
                break;
            default:
                info = "unknown";
                this.btnOnOff.setEnabled(false);
                this.btnReset.setEnabled(false);
                break;
        }
        this.setTitle("IPMI: 10.0.0.11 is " + info);
        return true;
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
}
