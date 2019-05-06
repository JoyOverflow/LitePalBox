package pxgd.hyena.com.threadapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);

        //创建一个线程并启动
        Thread thread = new Thread(new Runnable() {
            //run方法执行完成，即子线程结束
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    runOnUiThread(run1);
                    TimeUnit.SECONDS.sleep(1);
                    textView.post(run2);
                    TimeUnit.SECONDS.sleep(1);
                    textView.post(run3);
                    TimeUnit.SECONDS.sleep(1);
                    textView.post(run4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }



    Runnable run1 = new Runnable() {
        @Override
        public void run() {
            textView.setText(R.string.text1);
            textView.setTextColor(Color.RED);
        }
    };
    Runnable run2 = new Runnable() {
        @Override
        public void run() {
            textView.setText(R.string.text2);
            textView.setTextColor(Color.GREEN);
        }
    };
    Runnable run3 = new Runnable() {
        @Override
        public void run() {
            textView.setText(R.string.text3);
            textView.setTextColor(Color.BLUE);
        }
    };
    Runnable run4 = new Runnable() {
        @Override
        public void run() {
            textView.setText(R.string.text4);
            textView.setTextColor(Color.BLACK);
        }
    };


}
