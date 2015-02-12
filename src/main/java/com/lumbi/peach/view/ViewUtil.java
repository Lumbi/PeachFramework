package com.lumbi.peach.view;

import android.view.View;

/**
 * Created by gabriellumbi on 15-02-12.
 */
public class ViewUtil {

    public static float getCenterX(View view){
        if(view != null) {
            return view.getX() + view.getWidth()/2.0f;
        }
        return 0;
    }

    public static float getCenterY(View view){
        if(view != null) {
            return view.getY() + view.getHeight()/2.0f;
        }
        return 0;
    }
}
