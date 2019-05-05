package pxgd.hyena.com.litepalbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AggregateActivity extends AppCompatActivity implements View.OnClickListener {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AggregateActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggregate);

        Button mCountSampleBtn = findViewById(R.id.count_sample_btn);
        Button mMaxSampleBtn = findViewById(R.id.max_sample_btn);
        Button mMinSampleBtn = findViewById(R.id.min_sample_btn);
        Button mAverageSampleBtn = findViewById(R.id.average_sample_btn);
        Button mSumSampleBtn = findViewById(R.id.sum_sample_btn);
        mCountSampleBtn.setOnClickListener(this);
        mMaxSampleBtn.setOnClickListener(this);
        mMinSampleBtn.setOnClickListener(this);
        mAverageSampleBtn.setOnClickListener(this);
        mSumSampleBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.count_sample_btn:
                CountSampleActivity.actionStart(this);
                break;
            case R.id.max_sample_btn:
                MaxSampleActivity.actionStart(this);
                break;
            case R.id.min_sample_btn:
                MinSampleActivity.actionStart(this);
                break;
            case R.id.average_sample_btn:
                AverageSampleActivity.actionStart(this);
                break;
            case R.id.sum_sample_btn:
                SumSampleActivity.actionStart(this);
                break;
            default:
                break;
        }
    }
}
