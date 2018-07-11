package mm.memeonare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import mm.memeonare.UserData.User;

public class MenuActivity extends AppCompatActivity {
    TextView sLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        sLog=findViewById(R.id.sLog);
        sLog.setText(User.getNickName());
    }
}
