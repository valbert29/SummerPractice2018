package mm.memeonare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mm.memeonare.DataC.User;

public class LogInActivity extends AppCompatActivity {
    private String log;
    private EditText eLog;
    private Button bLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        bLog = findViewById(R.id.bLogin);
        eLog = findViewById(R.id.nickName);
        bLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log = eLog.getText().toString();
                Pattern pattern = Pattern.compile("^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{0,19}$");
                /*

    (?=.*[A-Za-z0-9]$) Asserts that the match must ends with a letter or digit.
    [A-Za-z] Must starts with a letter.
    [A-Za-z\d.-]{0,19} matches the chars according to the pattern present inside the char class. And the number of matched chars must be from 0 to 19.

                 */
                Matcher matcher = pattern.matcher(log);
                if (matcher.matches()) {
                    User.setNickName(log);
                    Intent intent = new Intent(LogInActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "INCORRECT LOGIN", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });

    }
}
