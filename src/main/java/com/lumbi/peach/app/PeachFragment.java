package com.lumbi.peach.app;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lumbi.peach.model.Model;

/**
 * Created by gabriellumbi on 14-11-26.
 *
 * Custom Fragment. Base class for fragments in this project.
 */
public class PeachFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = null;

        if(this.getClass().isAnnotationPresent(Layout.class)){
            Layout layout = this.getClass().getAnnotation(Layout.class);
            view = inflater.inflate(layout.value(), container, false);
        }

        LFrameLayout cFrameLayout = new LFrameLayout(getActivity());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        cFrameLayout.setLayoutParams(lp);

        if(view != null){
            cFrameLayout.addView(view);
        }

        return cFrameLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FindViewsById.in(this);
    }

    /*
        Convenience methods for handling fragment arguments
     */

    public void addArgument(String key, String arg){
        Bundle bundle = getArguments();
        if(bundle == null) bundle = new Bundle();
        bundle.putString(key, arg);
        setArguments(bundle);
    }

    public void addArgument(String key, Model model){
        Bundle bundle = getArguments();
        if(bundle == null) bundle = new Bundle();
        bundle.putString(key, model.toJson());
        setArguments(bundle);
    }

    public <T> T getModelArgument(String key, Class clazz){
        Bundle bundle = getArguments();
        if(bundle == null) return null;
        if(bundle.get(key) == null) return null;
        return Model.fromJson(bundle.getString(key), clazz);
    }

    /*
        Custom frame layout.
     */

    public class LFrameLayout extends FrameLayout{

        public LFrameLayout(Context context) {
            super(context);
        }

        public LFrameLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public LFrameLayout(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        //Enables relative translation animation.
        public float getRelativeTranslationX(){
            return getX() / getWidth();
        }

        //Enables relative translation animation.
        public void setRelativeTranslationX(float relativeTranslationX){
            setX(relativeTranslationX * getWidth());
        }
    }
}
