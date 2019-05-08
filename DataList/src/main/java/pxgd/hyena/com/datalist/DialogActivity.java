package pxgd.hyena.com.datalist;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView text;
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        text = findViewById(R.id.textView1);


        startBtn = findViewById(R.id.button1);
        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == startBtn) {
            CustomDialogFragment dialog = new CustomDialogFragment();
            dialog.show(getSupportFragmentManager(), "dialog");
        }
    }

    /**
     * 自定义对话框
     */
    //Fragment pxgd.hyena.com.datalist.DialogActivity.CustomDialogFragment must be a public static class to be  properly recreated from instance state.
    @SuppressLint("ValidFragment")
    public class CustomDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = new Dialog(getActivity());

            //设置对话框样式
            dialog.getWindow().getAttributes().windowAnimations = R.style.Animation_CustomDialog;
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            );
            //对话框布局文件
            dialog.setContentView(R.layout.dialog_custom);


            //背景设置为透明
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);

            //设置对话框内容
            TextView message =  dialog.findViewById(R.id.message);
            message.setText(text.getText());

            //设置对换框按钮事件
            dialog.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            return dialog;
        }
    }


}
