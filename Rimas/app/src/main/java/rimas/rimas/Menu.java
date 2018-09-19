package rimas.rimas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;



public class Menu extends AppCompatActivity {

    private Spinner beats;
    private Button cambioConsonantes, cambioAsonantes, startButton, stopButton, settings, trainer;
    private TextView palabra1,palabra2,palabra3,palabra4,palabra5,palabra6,palabra7,palabra8;
    String jsonString;
    MediaPlayer mPlayerJazz, mPlayerOld, mPlayerDilated;
    private final static String JAZZBEAT = "Jazz Beat";
    private final static String OLDSCHOOL = "Old School";
    private final static String DILATED = "Dilated";
    Data data;

    @Override
    protected void onResume() {
        super.onResume();
       updateData();
    }

    private void updateData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Data", null);
        data = (Data)gson.fromJson(json, Data.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        updateData();

        if(data == null){
            data = new Data();
            initializeJson();
            data.addTerminacionConsonante("abra");
            /*data.addTerminacionConsonante("ida");
            data.addTerminacionConsonante("iva");
            data.addTerminacionConsonante("ada");
            data.addTerminacionConsonante("oda");
            data.addTerminacionConsonante("ino");
            data.addTerminacionConsonante("ina");
            data.addTerminacionConsonante("ima"); */
            data.addTerminacionAsonante("palabra");
            commitData();
        }
        for(String a : data.getTerminacionesConsonantes()){
            Log.d("TERMIACIONES : ", a);
        }
        addPalabras();
        addButtonsListeners();
        addSpinerListener();
    }



    private void commitData() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        prefsEditor.putString("Data", json);
        prefsEditor.commit();
    }



    private void initializeJson() {
        InputStream is = getResources().openRawResource(R.raw.espanol_sin_tildes);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            jsonString = writer.toString();
            is.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(jsonString == null){
            Log.d("CREAI", "No se parseo");
        }else{
            Log.d("CREAI", "Se parseo");
        }

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                String obj = jsonArray.get(i).toString();
                data.addWordToAllWords(obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addPalabras() {
        palabra1 = (TextView)findViewById(R.id.palabra1);
        palabra2 = (TextView)findViewById(R.id.palabra2);
        palabra3 = (TextView)findViewById(R.id.palabra3);
        palabra4 = (TextView)findViewById(R.id.palabra4);
        palabra5 = (TextView)findViewById(R.id.palabra5);
        palabra6 = (TextView)findViewById(R.id.palabra6);
        palabra7 = (TextView)findViewById(R.id.palabra7);
        palabra8 = (TextView)findViewById(R.id.palabra8);

      /*  palabra1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String toRemove = v.toString();
                allWords.remove(toRemove);
                return true;

            }
        }); */
    }

    private void addSpinerListener() {
        beats = (Spinner) findViewById(R.id.beats);
        String[] arraySpinner = new String[] {
                JAZZBEAT, OLDSCHOOL, DILATED
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        beats.setAdapter(adapter);
    }

    private void addButtonsListeners() {
        cambioConsonantes = (Button) findViewById(R.id.cambioConsonantes);
        cambioAsonantes = (Button) findViewById(R.id.cambioAsonantes);
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        settings = (Button) findViewById(R.id.settings);
        trainer = (Button) findViewById(R.id.trainer);

        mPlayerJazz = MediaPlayer.create(Menu.this, R.raw.coffeecoldjazz);
        mPlayerDilated = MediaPlayer.create(Menu.this, R.raw.dilatedpeople);
        mPlayerOld = MediaPlayer.create(Menu.this, R.raw.oldschool);

        cambioConsonantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] terminaciones = data.getRandomTerminacionesConsonante();

                palabra1.setText(terminaciones[0]);
                palabra2.setText(terminaciones[1]);
                palabra3.setText(terminaciones[2]);
                palabra4.setText(terminaciones[3]);
            }
        });

        cambioAsonantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] terminaciones = data.getRandomTerminacionesAsonante();

                palabra5.setText(terminaciones[0]);
                palabra6.setText(terminaciones[1]);
                palabra7.setText(terminaciones[2]);
                palabra8.setText(terminaciones[3]);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedItem = (String) beats.getSelectedItem();
                assert selectedItem != null : "la selection no puede ser null";
                if(selectedItem.equals(JAZZBEAT)){
                    mPlayerJazz.start();
                    return;
                }
                if(selectedItem.equals(OLDSCHOOL)){
                    mPlayerOld.start();
                    return;
                }
                if(selectedItem.equals(DILATED)){
                    mPlayerDilated.start();
                    return;
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedItem = (String) beats.getSelectedItem();
                assert selectedItem != null : "la selection no puede ser null stop button";

                if(selectedItem.equals(JAZZBEAT) && mPlayerJazz.isPlaying()){
                    mPlayerJazz.pause();
                    return;
                }
                if(selectedItem.equals(OLDSCHOOL) && mPlayerOld.isPlaying()){
                    mPlayerOld.pause();
                    return;
                }
                if(selectedItem.equals(DILATED) && mPlayerDilated.isPlaying()){
                    mPlayerDilated.pause();
                    return;
                }
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Settings.class);
                startActivity(intent);
            }
        });
    }
}
