package com.lumbi.framework.app;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by gabriellumbi on 14-11-28.
 * Custom TextView. Base class for text views in this project.
 */
public class LEditText extends EditText {

    public LEditText(Context context) {
        super(context);
    }

    public LEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
