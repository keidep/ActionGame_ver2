package jp.kait.actiongame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private TextView maintext;
    private Button mainstartbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        maintext = findViewById(R.id.maintext);
        mainstartbtn = findViewById(R.id.mainstartbtn);

        mainstartbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
            Intent intent = new Intent(MainActivity.this,GameActivity.class);
            startActivity(intent);
        }
    }