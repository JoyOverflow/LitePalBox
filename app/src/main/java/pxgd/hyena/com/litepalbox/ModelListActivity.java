package pxgd.hyena.com.litepalbox;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.litepal.LitePalApplication;
import org.litepal.exceptions.ParseConfigurationFileException;
import org.litepal.util.Const;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pxgd.hyena.com.litepalbox.adapter.StringArrayAdapter;

public class ModelListActivity extends AppCompatActivity {

    private List<String> mList = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ModelListActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_list);

        ListView mModelListView = findViewById(R.id.model_listview);
        populateMappingClasses();
        StringArrayAdapter mAdapter = new StringArrayAdapter(this, 0, mList);
        mModelListView.setAdapter(mAdapter);
        mModelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
                ModelActivity.actionStart(ModelListActivity.this, mList.get(index));
            }
        });
    }
    private void populateMappingClasses() {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(getInputStream(), "UTF-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        if ("mapping".equals(nodeName)) {
                            String className = xmlPullParser.getAttributeValue("", "class");
                            mList.add(className);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            throw new ParseConfigurationFileException(
                    ParseConfigurationFileException.FILE_FORMAT_IS_NOT_CORRECT);
        } catch (IOException e) {
            throw new ParseConfigurationFileException(ParseConfigurationFileException.IO_EXCEPTION);
        }
    }
    private InputStream getInputStream() throws IOException {
        AssetManager assetManager = LitePalApplication.getContext().getAssets();
        String[] fileNames = assetManager.list("");
        if (fileNames != null && fileNames.length > 0) {
            for (String fileName : fileNames) {
                if (Const.Config.CONFIGURATION_FILE_NAME.equalsIgnoreCase(fileName)) {
                    return assetManager.open(fileName, AssetManager.ACCESS_BUFFER);
                }
            }
        }
        throw new ParseConfigurationFileException(
                ParseConfigurationFileException.CAN_NOT_FIND_LITEPAL_FILE);
    }




}
