package com.aabidmulani.tictacmutual.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.aabidmulani.tictacmutual.R;
import com.aabidmulani.tictacmutual.utils.Utils;


public class RobotoButton extends AppCompatButton {

    public RobotoButton(Context context) {
        super(context);
    }

    public RobotoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RobotoButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.customTextView, 0, 0);
            try {
                int textStyleIndex = typedArray.getInt(R.styleable.customTextView_textStyle, 0);
                setTextFontStyle(context, textStyleIndex);
            } finally {
                typedArray.recycle();
            }
        }
    }

    public void setTextFontStyle(Context context, int textStyleIndex) {
        setTypeface(Utils.getThisFont(context, textStyleIndex));
    }

}
