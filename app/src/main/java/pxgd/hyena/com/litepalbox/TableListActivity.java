package pxgd.hyena.com.litepalbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.litepal.tablemanager.Connector;
import org.litepal.util.DBUtility;

import java.util.ArrayList;
import java.util.List;

import pxgd.hyena.com.litepalbox.adapter.StringArrayAdapter;

public class TableListActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private StringArrayAdapter mAdapter;
    private List<String> mList = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TableListActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_list);

        mProgressBar = findViewById(R.id.progress_bar);
        ListView mTableListView = findViewById(R.id.table_listview);
        mAdapter = new StringArrayAdapter(this, 0, mList);
        mTableListView.setAdapter(mAdapter);
        populateTables();
        mTableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
                TableActivity.actionStart(TableListActivity.this, mList.get(index));
            }
        });
    }

    private void populateTables() {
        mProgressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> tables = DBUtility.findAllTableNames(Connector.getDatabase());
                for (String table : tables) {
                    if (table.equalsIgnoreCase("android_metadata")
                            || table.equalsIgnoreCase("sqlite_sequence")
                            || table.equalsIgnoreCase("table_schema")) {
                        continue;
                    }
                    mList.add(table);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }


}
