package pxgd.hyena.com.litepalbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //查找按钮视图设置事件侦听
        Button mManageTableBtn = findViewById(R.id.manage_table_btn);
        Button mCrudBtn = findViewById(R.id.crud_btn);
        Button mAggregateBtn = findViewById(R.id.aggregate_btn);
        mManageTableBtn.setOnClickListener(this);
        mCrudBtn.setOnClickListener(this);
        mAggregateBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manage_table_btn:
                ManageActivity.actionStart(this);
                break;
            case R.id.crud_btn:
                CRUDActivity.actionStart(this);
                break;
            case R.id.aggregate_btn:
                AggregateActivity.actionStart(this);
                break;
            default:
                break;
        }
    }
}
