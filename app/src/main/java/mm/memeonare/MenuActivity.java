package mm.memeonare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mm.memeonare.DataC.User;

public class MenuActivity extends AppCompatActivity {
    Button startButton;
    Button scoresButton;
    TextView sLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        sLog=findViewById(R.id.sLog);
        sLog.setText(User.getNickName());
        scoresButton=findViewById(R.id.scoresButton);
        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showScores();
            }
        });
        startButton=findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void showScores(){
        Intent intent = new Intent(MenuActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
