package mm.memeonare;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import mm.memeonare.DataC.Question;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, MyListener {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private ImageView mem;
    private TextView level;
    private Button backButton;
    private int buttonIndex;
    private Button b50Tip;
    private Button b2Tip;
    private boolean tip50Pressed = false;
    private TextView qView;
    private ArrayList<ArrayList> questions = new ArrayList<>();
    private int qnum = 0;
    private ArrayList<Button> buttons = new ArrayList<>();
    private int correctButton = 2;
    private boolean doublechance = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        for (int i = 0; i < 11; i++) {
            ArrayList<Question> arrayList = new ArrayList<>();
            questions.add(arrayList);
        }
        getQuestions();
        level=findViewById(R.id.level);
        qView = findViewById(R.id.question);
        mem = findViewById(R.id.mem);
        b50Tip = findViewById(R.id.b50Tip);
        b2Tip = findViewById(R.id.b2Tip);
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
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        DialogF dialogF = new DialogF();
        dialogF.setCancelable(false);
        dialogF.show(getSupportFragmentManager(), "dia1");
        b50Tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    fiftyFifty();
            }
        });
        b2Tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    doublechance=true;
                    Toast toast=Toast.makeText(getApplication(),"DOUBLE CHANCE ACTIVATED",Toast.LENGTH_LONG);
                    toast.show();
                    b2Tip.setClickable(false);
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
                // button1.setBackgroundResource(R.drawable.possiblebutt);
                break;
            case R.id.button2:
                //button2.setBackgroundResource(R.drawable.possiblebutt);
                index = 2;

                break;
            case R.id.button3:
                //button3.setBackgroundResource(R.drawable.possiblebutt);

                index = 3;
                break;
            case R.id.button4:
                index = 4;
                //button4.setBackgroundResource(R.drawable.possiblebutt);
                break;
        }
        //ТУТА ТУТА ТУТА ТУТА

        //buttons.get(index-1).setBackgroundResource(R.drawable.possiblebutt);
        return index;
    }

    @Override
    public void onClick(View view) {
        buttonIndex = translateIdToIndex(view.getId());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
       /* view.invalidate();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //ТУТА ТУТА ТУТА ТУТА
        MyTask myTask = new MyTask();
        myTask.execute();



    }

    private void buttonClick() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (buttonIndex == correctButton) {
            doublechance=false;
            if (qnum != 10) {

                nextquestion();
            } else finishgame();
        } else {
                Toast toast = Toast.makeText(getApplicationContext(), "INCORRECT", Toast.LENGTH_SHORT);
                toast.show();
                wronganswer();

        }
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
                questions.get(Integer.parseInt(strings[7])).add(new Question(strings[0], strings[1], answers, Integer.parseInt(strings[6])));
                line = bufferedReader.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void nextquestion() {
        qnum++;
        level.setText(""+qnum);
        for (Button b : buttons) {
            b.setBackground(getDrawable(R.drawable.button));
            b.setClickable(true);
        }
        tip50Pressed = false;
        Question question = (Question) questions.get(qnum).get((int) (Math.random() * questions.get(qnum).size()));
        if (question != null) {

            Picasso.get().load("file:///android_asset/" + question.getMem() + ".jpg").into(mem);
            button1.setText(question.getAnswers().get(0));
            button2.setText(question.getAnswers().get(1));
            button3.setText(question.getAnswers().get(2));
            button4.setText(question.getAnswers().get(3));
            qView.setText(question.getQuestion());
            correctButton = question.getcIndex();
        }
    }


    private void finishgame() {
        cancel();
    }

    private void wronganswer() {
        cancel();
    }

    private void fiftyFifty() {
        b50Tip.setClickable(false);
        tip50Pressed = true;
        byte c = 0;
        byte b = 0;
        while (c != 2) {
            byte i = (byte) (Math.random() * 4 + 1);
            if (i != correctButton && i != b) {
                c++;
                b = i;
                buttons.get(i - 1).setText("");
                buttons.get(i - 1).setClickable(false);


            }
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

    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttons.get(buttonIndex - 1).setBackgroundResource(R.drawable.possiblebutton);

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(buttonIndex!=correctButton && doublechance) {
                doublechance = false;
                buttons.get(buttonIndex-1).setBackgroundResource(R.drawable.wrongbutton);
                buttons.get(buttonIndex-1).setClickable(false);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            else {
                MyTask2 myTask2 = new MyTask2();
                myTask2.execute();
            }

        }
    }

    class MyTask2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttons.get(correctButton - 1).setBackgroundResource(R.drawable.rightbutton);

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            buttonClick();


        }
    }
}
