package pxgd.hyena.com.litepalbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.litepal.tablemanager.Connector;
import org.litepal.tablemanager.model.ColumnModel;
import org.litepal.tablemanager.model.TableModel;
import org.litepal.util.DBUtility;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {

    private String mTableName;
    private List<ColumnModel> mList = new ArrayList<>();


    public static final String TABLE_NAME = "table_name";
    public static void actionStart(Context context, String tableName) {
        Intent intent = new Intent(context, TableActivity.class);
        intent.putExtra(TABLE_NAME, tableName);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_table);

        mTableName = getIntent().getStringExtra(TABLE_NAME);
        ListView mTableStructureListView = findViewById(R.id.table_structure_listview);
        analyzeTableStructure();
        ArrayAdapter<ColumnModel> mAdapter = new MyArrayAdapter(this, 0, mList);
        mTableStructureListView.setAdapter(mAdapter);
    }
    private void analyzeTableStructure() {
        TableModel tableMode = DBUtility.findPragmaTableInfo(mTableName, Connector.getDatabase());
        List<ColumnModel> columnModelList = tableMode.getColumnModels();
        mList.addAll(columnModelList);
    }
    class MyArrayAdapter extends ArrayAdapter<ColumnModel> {

        public MyArrayAdapter(Context context, int textViewResourceId, List<ColumnModel> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ColumnModel columnModel = getItem(position);
            String columnName = columnModel.getColumnName();
            String columnType = columnModel.getColumnType();
            boolean nullable = columnModel.isNullable();
            boolean unique = columnModel.isUnique();
            String defaultValue = columnModel.getDefaultValue();
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.table_item, null);
            } else {
                view = convertView;
            }
            TextView text1 = view.findViewById(R.id.text_1);
            text1.setText(columnName);
            TextView text2 = view.findViewById(R.id.text_2);
            text2.setText(columnType);
            TextView text3 = view.findViewById(R.id.text_3);
            text3.setText(String.valueOf(nullable));
            TextView text4 = view.findViewById(R.id.text_4);
            text4.setText(String.valueOf(unique));
            TextView text5 = view.findViewById(R.id.text_5);
            text5.setText(defaultValue);
            return view;
        }
    }






}
