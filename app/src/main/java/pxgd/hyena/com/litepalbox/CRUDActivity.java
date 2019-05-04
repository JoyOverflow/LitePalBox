package pxgd.hyena.com.litepalbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CRUDActivity extends AppCompatActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CRUDActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);
    }
}
