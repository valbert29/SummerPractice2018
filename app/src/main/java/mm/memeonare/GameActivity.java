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

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import mm.memeonare.DataC.Question;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, MyListener {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private ImageView mem;
    private Button backButton;
    private Button bTip;
    private TextView qView;
    private ArrayList<Question> questions = new ArrayList<>();
    private int qnum=-1;

    private int r = 0;
    private int correctButton = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getQuestions();
        qView = findViewById(R.id.question);
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
        DialogF dialogF = new DialogF();
        dialogF.setCancelable(false);
        dialogF.show(getSupportFragmentManager(),"dia1");
        bTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    public void run() {
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
            nextquestion();
        } else {
            toast = Toast.makeText(getApplicationContext(), "INCORRECT", Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public void getQuestions() {
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            getAssets().open("questions.csv"))
                    );
            String line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (line != null) {
                ArrayList<String> answers = new ArrayList<>();
                String[] strings = line.split(",");
                answers.add(strings[2]);
                answers.add(strings[3]);
                answers.add(strings[4]);
                answers.add(strings[5]);
                questions.add(new Question(strings[0], strings[1], answers, Integer.parseInt(strings[6])));
                line = bufferedReader.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void nextquestion(){
        qnum++;
        Question question = questions.get(qnum);
        if(question!=null){

            Picasso.get().load("file:///android_asset/"+question.getMem()+".jpg").into(mem);
            button1.setText(question.getAnswers().get(0));
            button2.setText(question.getAnswers().get(1));
            button3.setText(question.getAnswers().get(2));
            button4.setText(question.getAnswers().get(3));
            qView.setText(question.getQuestion());
            correctButton=question.getcIndex();
        }
    }

    @Override
    public void callback() {
        nextquestion();
    }

    @Override
    public void cancel() {
        Intent intent = new Intent(GameActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();

    }
}
