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

public class SaveDataActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mSingerNameEdit;
    private EditText mSingerAgeEdit;
    private EditText mSingerGenderEdit;
    private ProgressBar mProgressBar;
    private ListView mDataListView;
    private DataArrayAdapter mAdapter;
    private List<List<String>> mList = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SaveDataActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_data);

        mProgressBar = findViewById(R.id.progress_bar);
        mSingerNameEdit = findViewById(R.id.singer_name_edit);
        mSingerAgeEdit = findViewById(R.id.singer_age_edit);
        mSingerGenderEdit = findViewById(R.id.singer_gender_edit);

        //提交按钮
        Button mSaveBtn = findViewById(R.id.save_btn);
        mSaveBtn.setOnClickListener(this);

        //为列表视设置适配器
        mAdapter = new DataArrayAdapter(this, 0, mList);
        mDataListView = findViewById(R.id.data_list_view);
        mDataListView.setAdapter(mAdapter);

        //取数据库记录填充列表视
        populateDataFromDB();
    }


    /**
     * 通过线程取数据表记录
     */
    private void populateDataFromDB() {
        mProgressBar.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mList.clear();

                //为数据源加入一行（字段列表）
                List<String> columnList = new ArrayList<>();
                columnList.add("id");
                columnList.add("name");
                columnList.add("age");
                columnList.add("ismale");
                mList.add(columnList);

                Cursor cursor = null;
                try {
                    //取出全部表记录
                    cursor = Connector.getDatabase().rawQuery(
                            "select * from singer order by id",
                            null
                    );
                    //先移动到第一行
                    if(cursor.moveToFirst()) {
                        //遍历并加入所有数据行
                        do {
                            long id = cursor.getLong(cursor.getColumnIndex("id"));
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            int age = cursor.getInt(cursor.getColumnIndex("age"));
                            int isMale = cursor.getInt(cursor.getColumnIndex("ismale"));
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

    /**
     * 按钮点击事件处理
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                try {

                    //获取视图数据并保存
                    Singer singer = new Singer();
                    singer.setName(mSingerNameEdit.getText().toString());
                    singer.setAge(Integer.parseInt(mSingerAgeEdit.getText().toString()));
                    singer.setMale(Boolean.parseBoolean(mSingerGenderEdit.getText().toString()));
                    singer.save();

                    //刷新列表视
                    refreshListView(
                            singer.getId(),
                            singer.getName(),
                            singer.getAge(),
                            singer.isMale() ? 1 : 0
                    );
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

    /**
     * 刷新列表视
     * @param id
     * @param name
     * @param age
     * @param isMale
     */
    private void refreshListView(long id, String name, int age, int isMale) {
        //更新数据源（加入一行记录）
        List<String> stringList = new ArrayList<>();
        stringList.add(String.valueOf(id));
        stringList.add(name);
        stringList.add(String.valueOf(age));
        stringList.add(String.valueOf(isMale));
        mList.add(stringList);

        //通知适配器数据已更改
        mAdapter.notifyDataSetChanged();

        //更新列表视选中项
        mDataListView.setSelection(mList.size());
    }


}
