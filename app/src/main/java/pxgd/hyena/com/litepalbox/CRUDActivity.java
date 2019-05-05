package pxgd.hyena.com.litepalbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CRUDActivity extends AppCompatActivity implements View.OnClickListener {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CRUDActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        Button mSaveSampleBtn = findViewById(R.id.save_sample_btn);
        Button mUpdateSampleBtn = findViewById(R.id.update_sample_btn);
        Button mDeleteSampleBtn = findViewById(R.id.delete_sample_btn);
        Button mQuerySampleBtn = findViewById(R.id.query_sample_btn);
        mSaveSampleBtn.setOnClickListener(this);
        mUpdateSampleBtn.setOnClickListener(this);
        mDeleteSampleBtn.setOnClickListener(this);
        mQuerySampleBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_sample_btn:
                SaveDataActivity.actionStart(this);
                break;
            case R.id.update_sample_btn:
                UpdateDataActivity.actionStart(this);
                break;
            case R.id.delete_sample_btn:
                DeleteDataActivity.actionStart(this);
                break;
            case R.id.query_sample_btn:
                QueryDataActivity.actionStart(this);
                break;
            default:
                break;
        }
    }
}
