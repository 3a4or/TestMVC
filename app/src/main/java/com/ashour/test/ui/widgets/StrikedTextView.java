package com.ashour.test.ui.widgets;

import android.content.Context;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

public class StrikedTextView extends AppCompatTextView {

    public StrikedTextView(Context context) {
        super(context);
        setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public StrikedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public StrikedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
