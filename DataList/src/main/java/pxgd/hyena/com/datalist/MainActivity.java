package pxgd.hyena.com.datalist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goToClick(View view){
        switch (view.getId()) {
            case R.id.btnGridView:
                startActivity(new Intent(MainActivity.this, GridViewActivity.class));
                break;
            case R.id.btnListView:
                startActivity(new Intent(MainActivity.this, ListViewActivity.class));
                break;
            case R.id.btnSpinner:
                startActivity(new Intent(MainActivity.this, SpinnerActivity.class));
                break;
            case R.id.btnRadio:
                startActivity(new Intent(MainActivity.this, RadioActivity.class));
                break;
            case R.id.btnDatePicker:
                startActivity(new Intent(MainActivity.this, DatePickerActivity.class));
                break;
            case R.id.btnDialog:
                startActivity(new Intent(MainActivity.this, DialogActivity.class));
                break;
            default:
                break;
        }
    }
}
