package com.meng.rippleeffectlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setPadding(100, 100, 100, 100);
        textView.setText("一碗胡辣汤");
        RippleEffectLayout rippleEffectLayout = new RippleEffectLayout.Builder(this)
                .childView(textView)
                .minRadius(100)
                .maxRadius(1500)
                .color(Color.BLACK)
                .alpha((float) 0.2)
                .useCenter(false)
                .create();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.parent);
        linearLayout.addView(rippleEffectLayout);


    }
}
