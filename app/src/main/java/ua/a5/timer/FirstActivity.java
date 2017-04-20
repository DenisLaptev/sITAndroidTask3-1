package ua.a5.timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class FirstActivity extends AppCompatActivity {

    public static final int ID = 1;

    TextView tvInstructions;

    EditText etSecondsNumber;

    Button btnStart;

    TextView tvTimerScreen;

    Button btnPauseResume;

    String secondsNumber;
    int count;

    boolean isPaused = false;

    Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            if (msg.what == ID) {

                tvTimerScreen.setText(String.valueOf(count));

                tvTimerScreen.setVisibility(View.VISIBLE);
                btnPauseResume.setVisibility(View.VISIBLE);
                btnPauseResume.setClickable(true);
                btnPauseResume.setText("Pause");

                if (isPaused == false) {

                    if (count >= 0) {
                        handler.sendEmptyMessageDelayed(ID, 1000);
                        count--;
                    } else {
                        Toast.makeText(getApplicationContext(), "Time is off", Toast.LENGTH_SHORT).show();

                        tvTimerScreen.setVisibility(View.INVISIBLE);
                        btnPauseResume.setVisibility(View.INVISIBLE);
                        btnPauseResume.setClickable(false);

                        tvInstructions.setVisibility(View.VISIBLE);
                        etSecondsNumber.setText("");
                        etSecondsNumber.setVisibility(View.VISIBLE);
                        btnStart.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        tvInstructions = (TextView) findViewById(R.id.tv_instructions);

        tvTimerScreen = (TextView) findViewById(R.id.tv_timer_screen);

        etSecondsNumber = (EditText) findViewById(R.id.et_seconds_number);

        btnStart = (Button) findViewById(R.id.btn_start);

        btnPauseResume = (Button) findViewById(R.id.btn_pauseresume);
        btnPauseResume.setVisibility(View.INVISIBLE);
        btnPauseResume.setClickable(false);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                secondsNumber = etSecondsNumber.getText().toString();

                count = Integer.parseInt(secondsNumber);

                tvInstructions.setVisibility(View.INVISIBLE);
                etSecondsNumber.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.INVISIBLE);


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(ID);
                    }
                }).start();
            }
        });


        btnPauseResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaused == false) {
                    isPaused = true;
                    btnPauseResume.setText("Resume");
                    handler.removeMessages(ID);
                } else if (isPaused == true) {
                    isPaused = false;
                    btnPauseResume.setText("Pause");
                    handler.sendEmptyMessageDelayed(ID, 1000);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeMessages(ID);
    }
}
