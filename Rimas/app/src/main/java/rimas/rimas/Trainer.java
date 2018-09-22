package rimas.rimas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Trainer extends AppCompatActivity {

    Button juegos, rimas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        addButtonListeners();
    }

    private void addButtonListeners() {
        rimas = (Button) findViewById(R.id.rimas);
        juegos =(Button) findViewById(R.id.juegos);
        rimas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trainer.this, Rimas.class);
                Log.d("ACT","RIMAS");
                startActivity(intent);
            }
        });
        juegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trainer.this, Juegos.class);
                startActivity(intent);
            }
        });
    }
}
