package pxgd.hyena.com.litepalbox;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import pxgd.hyena.com.litepalbox.adapter.DataArrayAdapter;
import pxgd.hyena.com.litepalbox.model.Singer;

public class UpdateDataActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mSingerIdEdit;
    private EditText mSingerNameEdit;
    private EditText mSingerAgeEdit;
    private EditText mNameToUpdateEdit;
    private EditText mAgeToUpdateEdit;
    private ProgressBar mProgressBar;
    private DataArrayAdapter mAdapter;
    private List<List<String>> mList = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UpdateDataActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        mProgressBar = findViewById(R.id.progress_bar);
        mSingerIdEdit =  findViewById(R.id.singer_id_edit);
        mSingerNameEdit = findViewById(R.id.singer_name_edit);
        mSingerAgeEdit = findViewById(R.id.singer_age_edit);
        mNameToUpdateEdit = findViewById(R.id.name_to_update);
        mAgeToUpdateEdit = findViewById(R.id.age_to_update);

        //按钮事件的侦听
        Button mUpdateBtn1 = findViewById(R.id.update_btn1);
        Button mUpdateBtn2 = findViewById(R.id.update_btn2);
        mUpdateBtn1.setOnClickListener(this);
        mUpdateBtn2.setOnClickListener(this);

        //为列表视设置适配器
        mAdapter = new DataArrayAdapter(this, 0, mList);
        ListView mDataListView = findViewById(R.id.data_list_view);
        mDataListView.setAdapter(mAdapter);
        populateDataFromDB();
    }
    /**
     * 通过线程取数据表记录
     */
    private void populateDataFromDB() {
        mProgressBar.setVisibility(View.VISIBLE);
        mList.clear();
        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                //mList.clear();
                List<String> columnList = new ArrayList<>();
                columnList.add("id");
                columnList.add("name");
                columnList.add("age");
                columnList.add("ismale");
                mList.add(columnList);

                //取数据表记录
                Cursor cursor = null;
                try {
                    cursor = Connector.getDatabase().rawQuery(
                            "select * from singer order by id",
                            null
                    );
                    //遍历游标
                    if (cursor.moveToFirst()) {
                        do {
                            long id = cursor.getLong(cursor.getColumnIndex("id"));
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            int age = cursor.getInt(cursor.getColumnIndex("age"));
                            int isMale = cursor.getInt(cursor.getColumnIndex("ismale"));

                            //加入当前行记录
                            List<String> stringList = new ArrayList<>();
                            stringList.add(String.valueOf(id));
                            stringList.add(name);
                            stringList.add(String.valueOf(age));
                            stringList.add(String.valueOf(isMale));
                            mList.add(stringList);

                        } while (cursor.moveToNext());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                    //Activity方法用来在UI主线程中更新UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.GONE);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_btn1:
                try {
                    Singer singer = new Singer();
                    singer.setName(mSingerNameEdit.getText().toString());
                    singer.setAge(Integer.parseInt(mSingerAgeEdit.getText().toString()));

                    //更新指定记录
                    int rowsAffected = singer.update(
                            Long.parseLong(mSingerIdEdit.getText().toString())
                    );
                    Toast.makeText(
                            this,
                            String.format(
                                    getString(R.string.number_of_rows_affected),
                                    String.valueOf(rowsAffected)
                            ),
                            Toast.LENGTH_SHORT
                    ).show();
                    populateDataFromDB();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(
                            this,
                            getString(R.string.error_param_is_not_valid),
                            Toast.LENGTH_SHORT
                    ).show();
                }
                break;
            case R.id.update_btn2:
                try {
                    Singer singer = new Singer();
                    singer.setName(mSingerNameEdit.getText().toString());
                    singer.setAge(Integer.parseInt(mSingerAgeEdit.getText().toString()));

                    //更新批量记录
                    int rowsAffected = singer.updateAll(
                            "name=? and age=?",
                            mNameToUpdateEdit.getText().toString(),
                            mAgeToUpdateEdit.getText().toString()
                    );
                    Toast.makeText(
                            this,
                            String.format(
                                    getString(R.string.number_of_rows_affected),
                                    String.valueOf(rowsAffected)
                            ),
                            Toast.LENGTH_SHORT
                    ).show();
                    populateDataFromDB();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(
                            this,
                            getString(R.string.error_param_is_not_valid),
                            Toast.LENGTH_SHORT
                    ).show();
                }
                break;
            default:
                break;
        }
    }
}
