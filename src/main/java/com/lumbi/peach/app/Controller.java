package com.lumbi.peach.app;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriellumbi on 15-02-01.
 */
public abstract class Controller implements ViewTreeObserver.OnGlobalLayoutListener {

    protected ViewGroup view;
    private int lastViewVisibility;

    protected final Context context;

    protected ControllerListener listener;

    private final List<Controller> childControllers = new ArrayList<Controller>();

    public Controller(final Context context) {
        this.context = context;
    }

    public ViewGroup getView(){
        return view;
    }

    public final void control(final ViewGroup view) {
        this.view = view;
        FindViewsById.in(view, this);
        lastViewVisibility = view.getVisibility();
        view.getViewTreeObserver().addOnGlobalLayoutListener(this);
        onControlView();
    }

    public void show(){
        view.setVisibility(View.VISIBLE);
    }

    public void showAfter(long delay){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                show();
            }
        },delay);
    }

    public void onViewAppear(){
    }

    public void hide(){
        hide(true);
    }

    public void hide(boolean gone){
        view.setVisibility(gone ? View.GONE : View.INVISIBLE);
    }

    public void onViewDisappear(){
    }

    public final void resign(){
        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        onResignView();
    }

    public void onControlView() {
        for(Field field : this.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(Controls.class)) {
                field.setAccessible(true);
                try {
                    Controller controller = (Controller) field.get(this);
                    Controls controls = field.getAnnotation(Controls.class);
                    controller.control((ViewGroup) view.findViewById(controls.value()));
                    childControllers.add(controller);
                }catch (NullPointerException e){
                    e.printStackTrace();
                    Log.e("PeachFramework", String.format("Could not control view [%s] with %s", view, field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassCastException e){
                    System.err.println("@Controls annotation may only be used on Controllers and Controllers must control a ViewGroup.");
                    e.printStackTrace();
                }
            }
        }
    }

    public void onResignView() {
        for(Controller controller : childControllers){
            controller.resign();
        }
        childControllers.clear();
    }

    public ControllerListener getListener(){
        return listener;
    }

    public void setListener(ControllerListener listener){
        this.listener = listener;
    }

    public void notifyListener(int actionCode){
        if(listener != null){
            listener.onAction(this, actionCode);
        }
    }

    @Override
    public void onGlobalLayout() {
        if(lastViewVisibility != view.getVisibility()){
            if(view.getVisibility() == View.VISIBLE){
                onViewAppear();
            }else{
                onViewDisappear();
            }
        }
        lastViewVisibility = view.getVisibility();
    }

    public static abstract class ControllerListener {
        public void onAction(Controller controller, int actionCode) {}
        public void onAction(Controller controller, int actionCode, int arg) {}
        public void onAction(Controller controller, int actionCode, String arg) {}
        public void onAction(Controller controller, int actionCode, float arg) {}
    }
}
