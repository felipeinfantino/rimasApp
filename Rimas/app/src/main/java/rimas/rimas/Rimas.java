package rimas.rimas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Rimas extends AppCompatActivity {

    EditText terminacion;
    Button button;
    ScrollView scrollView;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rimas);
        addListeners();

    }

    private void addListeners() {
        terminacion = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        layout = (LinearLayout) findViewById(R.id.layout);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toAdd = terminacion.getText().toString();
                toAdd = toAdd.toLowerCase();
                TextView textView = new TextView(Rimas.this);
                textView.setText(toAdd);
                layout.addView(textView);
                Log.d("ADD", "AADA");
            }
        });


    }
}
