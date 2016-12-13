package br.com.fabricio.appguanabaracasa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FrameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_onibus);

        LayoutInflater inflater = LayoutInflater.from(this);
        View b = inflater.inflate(R.layout.botao, null);
        LinearLayout.LayoutParams b1 = new LinearLayout.LayoutParams(70, 70);
        b1.setMargins(0, 0, 0, 0);
        b.setLayoutParams(b1);

        frameLayout.addView((Button)b);

        LayoutInflater inflater1 = LayoutInflater.from(this);
        View bb = inflater1.inflate(R.layout.botao, null);
        LinearLayout.LayoutParams bb1 = new LinearLayout.LayoutParams(70, 70);
        bb1.setMargins(0, 50, 0, 0);
        bb.setLayoutParams(bb1);

        frameLayout.addView(bb);


        LayoutInflater inflater2 = LayoutInflater.from(this);
        View bbb = inflater2.inflate(R.layout.botao, null);
        LinearLayout.LayoutParams bb2 = new LinearLayout.LayoutParams(70, 70);
        bb2.setMargins(0, 100, 0, 0);
        bbb.setLayoutParams(bb2);

//        frameLayout.addView(bbb);

        Button button = new Button(this);
        button.setText("Bola");
        frameLayout.addView(button, bb2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FrameActivity.this,"kkkkkkkkkkkk", Toast.LENGTH_LONG).show();
            }
        });

    }
}
