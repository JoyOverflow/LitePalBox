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

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import pxgd.hyena.com.litepalbox.adapter.DataArrayAdapter;
import pxgd.hyena.com.litepalbox.model.Singer;

public class DeleteDataActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mSingerIdEdit;
    private EditText mNameToDeleteEdit;
    private EditText mAgeToDeleteEdit;
    private ProgressBar mProgressBar;
    private DataArrayAdapter mAdapter;
    private List<List<String>> mList = new ArrayList<>();


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DeleteDataActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_data);

        mProgressBar = findViewById(R.id.progress_bar);
        mSingerIdEdit = findViewById(R.id.singer_id_edit);
        mNameToDeleteEdit = findViewById(R.id.name_to_delete);
        mAgeToDeleteEdit = findViewById(R.id.age_to_delete);
        Button mDeleteBtn1 = findViewById(R.id.delete_btn1);
        Button mDeleteBtn2 = findViewById(R.id.delete_btn2);
        ListView mDataListView = findViewById(R.id.data_list_view);
        mDeleteBtn1.setOnClickListener(this);
        mDeleteBtn2.setOnClickListener(this);
        mAdapter = new DataArrayAdapter(this, 0, mList);
        mDataListView.setAdapter(mAdapter);
        populateDataFromDB();
    }
    private void populateDataFromDB() {
        mProgressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mList.clear();

                List<String> columnList = new ArrayList<>();
                columnList.add("id");
                columnList.add("name");
                columnList.add("age");
                columnList.add("ismale");
                mList.add(columnList);

                Cursor cursor = null;
                try {
                    //读取数据表记录
                    cursor = Connector.getDatabase().rawQuery(
                            "select * from singer order by id",
                            null
                    );
                    if (cursor.moveToFirst()) {
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_btn1:
                try {
                    //删除指定记录
                    int rowsAffected = LitePal.delete(
                            Singer.class,
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
                    Toast.makeText(this, getString(R.string.error_param_is_not_valid),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.delete_btn2:
                try {
                    //批量删除
                    int rowsAffected = LitePal.deleteAll(
                            Singer.class,
                            "name=? and age=?",
                            mNameToDeleteEdit.getText().toString(),
                            mAgeToDeleteEdit.getText().toString()
                    );
                    Toast.makeText(
                            this,
                            String.format(
                                    getString(R.string.number_of_rows_affected),
                                    String.valueOf(rowsAffected)
                            ), Toast.LENGTH_SHORT
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
