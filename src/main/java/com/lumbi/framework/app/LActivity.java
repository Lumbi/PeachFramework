package com.lumbi.framework.app;

import android.os.Bundle;

/**
 * Created by gabriellumbi on 14-11-26.
 *
 * Custom Activity. Base class for activities used in this project.
 */
public class LActivity extends android.app.Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(this.getClass().isAnnotationPresent(Layout.class)){
            Layout layout = this.getClass().getAnnotation(Layout.class);
            setContentView(layout.value());
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        FindViewsById.in(this);
    }
}
