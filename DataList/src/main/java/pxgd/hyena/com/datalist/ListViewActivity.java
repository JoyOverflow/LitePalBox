package pxgd.hyena.com.datalist;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import pxgd.hyena.com.datalist.adapter.MobileArrayAdapter;

public class ListViewActivity extends ListActivity {

    static final String[] MOBILE_OS = { "Android", "iOS",
            "WindowsMobile", "Blackberry"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list_view);
        setListAdapter(new MobileArrayAdapter(this, MOBILE_OS));
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String selectedValue = (String) getListAdapter().getItem(position);
        Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
    }
}
