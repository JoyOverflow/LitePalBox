package pxgd.hyena.com.threadapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class FirstActivity extends AppCompatActivity {

    private TextView tv_number;
    private Handler handler = new Handler();
    private Random random   = new Random();
    private String number;
    private int delay = 10000;
    private int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        tv_number = findViewById(R.id.tv_numer);
    }
    @Override
    protected void onResume() {
        super.onResume();

        //每10秒执行一次run内的代码
        handler.postDelayed(new Runnable(){
            public void run(){

                //启动线程
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        generateNumber();
                    }
                };
                new Thread(runnable).start();

                handler.postDelayed(this, delay);
            }
        }, delay);
    }



    /**
     * 按钮事件处理（使用Handler更改UI界面）
     * @param view
     */
    public void showNumbers(View view) {
        //启动线程得到随机数
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                generateNumber();
            }
        };
        new Thread(runnable).start();
    }


    public void generateNumber(){
        for (int i = 0; i < 2; i++) {

            //得到随机数字串
            x = random.nextInt(100) + 1;
            number = String.valueOf(x);

            //休眠一下
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //使用Handler立即更改UI界面
            handler.post(new Runnable() {
                @Override
                public void run() {
                    tv_number.setText(number);
                }
            });
        }
    }


    /**
     转到另外的活动
     */
    public void goToDownload(View view){
        startActivity(
                new Intent(
                        FirstActivity.this,
                        SecondActivity.class)
        );
    }
}
