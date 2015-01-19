package com.lumbi.framework.app;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by gabriellumbi on 14-11-26.
 *
 * Custom TextView. Base class for text views in this project.
 */
public class LTextView extends android.widget.TextView {

    public LTextView(Context context) {
        super(context);
    }

    public LTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
