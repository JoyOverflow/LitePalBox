package pxgd.hyena.com.threadapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    SeekBar seekBar;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        seekBar = findViewById(R.id.seekBar);
        listView =  findViewById(R.id.listView);

        seekBar.setMax(25);
        seekBar.setProgress(17);
        setTableContent(17);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setTableContent(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    /**
     * 按钮事件处理
     * @param view
     */
    public void setMax(View view){
        int max = Integer.parseInt(((EditText)findViewById(R.id.editText2)).getText().toString());
        seekBar.setMax(max);
        seekBar.setProgress(max);
        setTableContent(max);
    }
    public void setTableContent(int tableNo){
        if(tableNo==0) tableNo++;

        //为列表视设置适配器
        ArrayList<Integer> tableof = new ArrayList<>();
        for(int i=0;i<10;i++){
            tableof.add((i+1)*tableNo);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,tableof);
        listView.setAdapter(adapter);
    }


}
