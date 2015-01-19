package com.lumbi.peach.app;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by gabriellumbi on 14-11-26.
 *
 * Custom TextView. Base class for text views in this project.
 */
public class PeachTextView extends android.widget.TextView {

    public PeachTextView(Context context) {
        super(context);
    }

    public PeachTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PeachTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
