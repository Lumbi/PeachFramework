package com.lumbi.peach.app;

import android.content.Context;
import android.view.View;

/**
 * Created by gabriellumbi on 15-02-01.
 */
public abstract class Controller {

    protected View view;
    protected final Context context;
    protected ControllerListener listener;

    public Controller(final Context context) {
        this.context = context;
    }

    public final void control(final View view) {
        this.view = view;
        FindViewsById.in(view, this);
        onControlView();
    }

    public final void resign(){
        onResignView();
    }

    public void onControlView() {}

    public void onResignView() {}

    public ControllerListener getListener(){
        return listener;
    }

    public void setListener(ControllerListener listener){
        this.listener = listener;
    }

    public abstract class ControllerListener {
        public void onAction(Controller controller, int actionCode) {}
        public void onAction(Controller controller, int actionCode, int arg) {}
        public void onAction(Controller controller, int actionCode, String arg) {}
        public void onAction(Controller controller, int actionCode, float arg) {}
    }
}
