package com.skyrain.software.k;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;

@KActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @KBind(R.id.tv)
    private TextView textView;
    @KBind(R.id.textView2)
    private TextView textView2;
    @KBind(R.id.textView3)
    private TextView textView3;
    @KBind(R.id.button)
    private Button button;
    @KBind(R.id.button2)
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        textView.setText("this is TextView one");
        textView2.setText("this is TextView tow");
        textView3.setText("this is TextView three");
    }

    @KListener({R.id.button2, R.id.button})
    private void showTost2() {
        Toast.makeText(this, "测试按钮2", Toast.LENGTH_SHORT).show();
    }
}
