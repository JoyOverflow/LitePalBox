package pxgd.hyena.com.litepalbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.litepal.LitePal;

import pxgd.hyena.com.litepalbox.model.Singer;

public class MaxSampleActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mAgeEdit;
    private TextView mResultText;


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MaxSampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_sample);

        Button mMaxBtn1 = findViewById(R.id.max_btn1);
        Button mMaxBtn2 = findViewById(R.id.max_btn2);
        mAgeEdit = findViewById(R.id.age_edit);
        mResultText = findViewById(R.id.result_text);
        mMaxBtn1.setOnClickListener(this);
        mMaxBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int result ;
        switch (v.getId()) {
            case R.id.max_btn1:
                result = LitePal.max(
                        Singer.class,
                        "age", Integer.TYPE
                );
                mResultText.setText(String.valueOf(result));
                break;
            case R.id.max_btn2:
                try {
                    result = LitePal.where(
                            "age < ?", mAgeEdit.getText().toString()
                    ).max(Singer.class, "age", Integer.TYPE);
                    mResultText.setText(String.valueOf(result));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
        }
    }
}
