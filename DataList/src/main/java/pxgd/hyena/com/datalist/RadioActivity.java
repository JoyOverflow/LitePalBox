package pxgd.hyena.com.datalist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RadioActivity extends AppCompatActivity {

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private Button btnDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        addListenerOnButton();
    }
    public void addListenerOnButton() {
        radioSexGroup = findViewById(R.id.radioSex);
        btnDisplay = findViewById(R.id.btnDisplay);

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                radioSexButton = findViewById(selectedId);

                Toast.makeText(
                        RadioActivity.this,
                        radioSexButton.getText(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
