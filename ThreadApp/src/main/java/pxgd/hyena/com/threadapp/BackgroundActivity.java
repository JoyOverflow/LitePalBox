package pxgd.hyena.com.threadapp;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class BackgroundActivity extends AppCompatActivity {

    private SurfaceView surfaceview;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);

        surfaceview = findViewById(R.id.surfaceView);
        surfaceview.getHolder().setKeepScreenOn(true);
        surfaceview.getHolder().addCallback(new SurfaceViewLis());

        mediaPlayer = new MediaPlayer();
    }


    private class SurfaceViewLis implements SurfaceHolder.Callback {
        @Override
        public void surfaceChanged(
                SurfaceHolder holder,
                int format,
                int width,
                int height) {
        }
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
                try {
                    play();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) { }
    }
    public void play() throws
            IllegalArgumentException,
            SecurityException,
            IllegalStateException,
            IOException
    {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        AssetFileDescriptor fd = this.getAssets().openFd("edc3094.mp4");
        mediaPlayer.setDataSource(
                fd.getFileDescriptor(),
                fd.getStartOffset(),
                fd.getLength()
        );
        mediaPlayer.setLooping(true);
        mediaPlayer.setDisplay(surfaceview.getHolder());

        //通过异步的方式装载媒体资源
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //装载完毕回调
                        mediaPlayer.start();
                    }
                }
        );
    }
}
