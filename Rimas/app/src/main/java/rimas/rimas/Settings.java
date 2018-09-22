package rimas.rimas;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

public class Settings extends AppCompatActivity {

    private LinearLayout consonanteList, asonanteList;
    private EditText editCon, editAs;
    private View.OnLongClickListener deleteActionConsonant;
    private View.OnLongClickListener deleteActionAsonant;
    private Button addCon, addAs;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Data", null);
        data = (Data)gson.fromJson(json, Data.class);

        consonanteList = (LinearLayout) findViewById(R.id.consonanteList);
        asonanteList = (LinearLayout) findViewById(R.id.asonanteList);
        addButtonListeners();
        createDeleteActions();
        fillLayouts();
    }

    private void createDeleteActions() {
        final Snackbar snackbar = Snackbar.make(editCon, "Terminacion eliminada", Snackbar.LENGTH_LONG);

        deleteActionConsonant = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                consonanteList.removeView(v);
                String toRemove = v.toString();
                data.removeTerminacionConsonante(toRemove);
                //Refresh table
                v.invalidate();
                v.requestLayout();
                consonanteList.invalidate();
                consonanteList.requestLayout();
                snackbar.show();
                return true;
            }
        };

        deleteActionAsonant = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                asonanteList.removeView(v);
                String toRemove = v.toString();
                data.removeTerminacionAsonante(toRemove);
                //Refresh table
                v.invalidate();
                v.requestLayout();
                asonanteList.invalidate();
                asonanteList.requestLayout();
                snackbar.show();
                return true;
            }
        };

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        commitData();
        Log.d("COMMIT", "commited on Back");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       commitData();
        Log.d("COMMIT", "commited on Destroy");
    }

    private void commitData() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        prefsEditor.putString("Data", json);
        prefsEditor.commit();
    }

    private void fillLayouts() {
        for(String toAdd : data.getTerminacionesConsonantes()){
            TextView tv = new TextView(Settings.this);
            tv.setText(toAdd);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tv.setOnLongClickListener(deleteActionConsonant);
            consonanteList.addView(tv);
        }
        for(String toAdd : data.getTerminacionesAsonantes()){
            TextView tv = new TextView(Settings.this);
            tv.setText(toAdd);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tv.setOnLongClickListener(deleteActionAsonant);
            asonanteList.addView(tv);
        }
    }

    private void addButtonListeners() {
        addCon = (Button) findViewById(R.id.addCon);
        addAs = (Button) findViewById(R.id.addAs);

        editCon = (EditText) findViewById(R.id.editCon);
        editAs = (EditText) findViewById(R.id.editAs);
        final Snackbar snackbar = Snackbar.make(editCon, "Terminacion Agregada", Snackbar.LENGTH_LONG);


        addCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toAdd = editCon.getText().toString();
                toAdd = toAdd.toLowerCase();
                if(data.containsConsonantTermination(toAdd)){
                    snackbar.setText("Terminacion ya agregada");
                    return;
                }
                data.addTerminacionConsonante(toAdd);
                TextView tv = new TextView(Settings.this);
                tv.setText(toAdd);
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                tv.setOnLongClickListener(deleteActionConsonant);
                consonanteList.addView(tv);
                //Refresh table
                tv.invalidate();
                tv.requestLayout();
                consonanteList.invalidate();
                consonanteList.requestLayout();
                snackbar.show();


            }
        });

        addAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toAdd = editAs.getText().toString();
                toAdd = toAdd.toLowerCase();
                if(data.containsAsonanteTermination(toAdd)){
                    snackbar.setText("Terminacion ya agregada");
                    return;
                }
                data.addTerminacionAsonante(toAdd);
                TextView tv = new TextView(Settings.this);
                tv.setText(toAdd);
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                tv.setOnLongClickListener(deleteActionAsonant);
                asonanteList.addView(tv);
                //Refresh table
                tv.invalidate();
                tv.requestLayout();
                asonanteList.invalidate();
                asonanteList.requestLayout();
                snackbar.show();
            }
        });
    }




}
