package mm.memeonare;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import mm.memeonare.DataC.Question;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private ImageView mem;
    private Button backButton;
    private Button bTip;
    private TextView question;
    private ArrayList<Question> questions= new ArrayList<>();



    private int r =0;
    private int correctButton =2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getQuestions();
        question=findViewById(R.id.question);
        mem = findViewById(R.id.mem);
        bTip = findViewById(R.id.bTip);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(this);

        bTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    public void run(){
                        MediaPlayer mp = MediaPlayer.create(GameActivity.this, R.raw.natalyamp);
                        mp.start();
                    }
                }.start();
            }
        });
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    int translateIdToIndex(int id) {
        int index = -1;
        switch (id) {
            case R.id.button1:
                index = 1;
                break;
            case R.id.button2:
                index = 2;
                break;
            case R.id.button3:
                index = 3;
                break;
            case R.id.button4:
                index = 4;
                break;
        }
        return index;
    }

    @Override
    public void onClick(View view) {
        Toast toast;
        int buttonIndex = translateIdToIndex(view.getId());
        if (buttonIndex == correctButton) {
            toast = Toast.makeText(getApplicationContext(), "CORRECT", Toast.LENGTH_SHORT);
            mem.setImageResource(R.drawable.button1);
            correctButton= (int)(1+Math.random()*4);
            question.setText(Integer.toString(r++));
        } else {
            toast = Toast.makeText(getApplicationContext(), "INCORRECT", Toast.LENGTH_SHORT);
        }
        toast.show();
        mem.setImageResource(R.drawable.natalya);

    }

    public void getQuestions(){
        try {
            Scanner sc = new Scanner(new File("questions.txt"));
            sc.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
