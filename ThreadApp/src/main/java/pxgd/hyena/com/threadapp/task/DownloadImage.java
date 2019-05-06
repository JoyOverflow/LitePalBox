package pxgd.hyena.com.threadapp.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImage {

    private static final String URL_IMAGE = "http://icon-icons.com/icons2/691/PNG/128/android_n_icon-icons.com_61479.png";
    private static Bitmap bitmap;

    /**
     * 通过线程下载图像
     */
    public static void getImgWeb(final Activity activity, final ImageView imageView){

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Carregando imagem...");
        progressDialog.show();

        new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(URL_IMAGE);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);

                }catch(IOException e){
                    e.printStackTrace();
                }

                //更新界面
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.setMessage("Imagem carregada!");
                        imageView.setImageBitmap(bitmap);
                        progressDialog.dismiss();
                    }
                });
            }
        }.start();
    }
}
