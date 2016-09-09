package com.palmsnipe.parkmandemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palmsnipe.parkmandemo.R;

import java.util.Locale;

/**
 * Created by cyril on 08/09/16.
 */
public class PinView extends RelativeLayout {

    private TextView mTvText;

    public PinView(Context context) {
        super(context);
    }

    public PinView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.pin, this);

        mTvText = (TextView) findViewById(R.id.text);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Pin);
        String text = a.getString(R.styleable.Pin_text);

        a.recycle();

        if (text != null && !text.isEmpty()) {
            setText(text);
        }
    }

    public void setText(String text) {
        if (text == null) return;

        mTvText.setText(text);
    }

    public void setAmount(double amount, String currency) {
        mTvText.setText(String.format(Locale.getDefault(), "%.2f %s", amount, currency != null ? currency : ""));
    }

}
