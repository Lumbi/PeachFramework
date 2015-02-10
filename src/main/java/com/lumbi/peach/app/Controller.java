package com.lumbi.peach.app;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriellumbi on 15-02-01.
 */
public abstract class Controller {

    protected View view;
    protected final Context context;
    protected ControllerListener listener;
    private final List<Controller> childControllers = new ArrayList<Controller>();

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

    public void onControlView() {
        for(Field field : this.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(Controls.class)) {
                field.setAccessible(true);
                try {
                    Controller controller = (Controller) field.get(this);
                    Controls controls = field.getAnnotation(Controls.class);
                    controller.control(view.findViewById(controls.value()));
                    childControllers.add(controller);
                }catch (NullPointerException e){
                    e.printStackTrace();
                    Log.e("PeachFramework", String.format("Could not control view [%s] with %s", view, field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassCastException e){
                    System.err.println("@Controls annotation may only be used on Controllers.");
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

    public static abstract class ControllerListener {
        public void onAction(Controller controller, int actionCode) {}
        public void onAction(Controller controller, int actionCode, int arg) {}
        public void onAction(Controller controller, int actionCode, String arg) {}
        public void onAction(Controller controller, int actionCode, float arg) {}
    }
}
