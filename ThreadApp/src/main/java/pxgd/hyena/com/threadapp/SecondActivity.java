package pxgd.hyena.com.threadapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import pxgd.hyena.com.threadapp.task.DownloadImage;
import pxgd.hyena.com.threadapp.task.Task;
import pxgd.hyena.com.threadapp.task.TaskInterface;

public class SecondActivity extends AppCompatActivity implements TaskInterface {

    private ImageView iv_downloaded;
    private static final String URL_IMAGE = "http://icon-icons.com/icons2/691/PNG/128/android_n_icon-icons.com_61479.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //查找到图像视图引用
        iv_downloaded = findViewById(R.id.iv_downloaded);
    }

    @Override
    public void beforeDownload(Bitmap bitmap) {
        iv_downloaded.setImageBitmap(bitmap);
    }


    /**
     * 按钮事件处理（使用普通线程和runOnUiThread）
     * @param view
     */
    public void imageDownload(View view){
        DownloadImage.getImgWeb(SecondActivity.this, iv_downloaded);
    }
    /**
     * 按钮事件处理（使用AsyncTask下载图像）
     * @param view
     */
    public void imageDownloadAsyncTask(View view) {
        Task task = new Task(this, this);
        task.execute(URL_IMAGE);
    }
}
