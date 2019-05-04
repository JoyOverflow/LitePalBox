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

import org.litepal.util.BaseUtility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ModelActivity extends AppCompatActivity {

    private String mClassName;
    private List<Field> mList = new ArrayList<>();

    public static final String CLASS_NAME = "class_name";
    public static void actionStart(Context context, String className) {
        Intent intent = new Intent(context, ModelActivity.class);
        intent.putExtra(CLASS_NAME, className);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_model);

        mClassName = getIntent().getStringExtra(CLASS_NAME);


        ListView mModelStructureListView = findViewById(R.id.model_structure_listview);
        analyzeModelStructure();
        ArrayAdapter<Field> mAdapter = new MyArrayAdapter(this, 0, mList);
        mModelStructureListView.setAdapter(mAdapter);
    }
    private void analyzeModelStructure() {
        Class<?> dynamicClass = null;
        try {
            dynamicClass = Class.forName(mClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = dynamicClass.getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (Modifier.isPrivate(modifiers) && !Modifier.isStatic(modifiers)) {
                Class<?> fieldTypeClass = field.getType();
                String fieldType = fieldTypeClass.getName();
                if (BaseUtility.isFieldTypeSupported(fieldType)) {
                    mList.add(field);
                }
            }
        }
    }

    class MyArrayAdapter extends ArrayAdapter<Field> {
        public MyArrayAdapter(Context context, int textViewResourceId, List<Field> objects) {
            super(context, textViewResourceId, objects);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            Field field = getItem(position);
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.model_item, null);
            } else {
                view = convertView;
            }
            TextView text1 = view.findViewById(R.id.text_1);
            text1.setText(field.getName());
            TextView text2 = view.findViewById(R.id.text_2);
            text2.setText(field.getType().getName());
            return view;
        }
    }




}
