package pxgd.hyena.com.litepalbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ManageActivity extends AppCompatActivity implements View.OnClickListener {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ManageActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        //查找按钮视图
        Button mCurrentModelStructureBtn = findViewById(R.id.current_model_structure_btn);
        Button mOperateDatabaseBtn = findViewById(R.id.operate_database_btn);
        mCurrentModelStructureBtn.setOnClickListener(this);
        mOperateDatabaseBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.current_model_structure_btn:
                ModelListActivity.actionStart(this);
                break;
            case R.id.operate_database_btn:
                TableListActivity.actionStart(this);
                break;
            default:
                break;
        }
    }





}
