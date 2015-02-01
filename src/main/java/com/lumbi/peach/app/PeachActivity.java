package com.lumbi.peach.app;

import android.os.Bundle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriellumbi on 14-11-26.
 *
 * Custom Activity. Base class for activities used in this project.
 */
public class PeachActivity extends android.app.Activity {

    protected final List<Controller> controllers = new ArrayList<Controller>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(this.getClass().isAnnotationPresent(Layout.class)){
            Layout layout = this.getClass().getAnnotation(Layout.class);
            setContentView(layout.value());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        controllers.clear();

        for(Field field : this.getClass().getFields()) {
            if(field.isAnnotationPresent(Controls.class)) {
                field.setAccessible(true);
                try {
                    Controller controller = (Controller) field.get(this);
                    Controls controls = field.getAnnotation(Controls.class);
                    controller.control(findViewById(controls.value()));
                    controllers.add(controller);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassCastException e){
                    System.err.println("@Controls annotation may only be used on Controllers.");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        for(Controller controller : controllers){
            controller.resign();
        }

        controllers.clear();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        FindViewsById.in(this);
    }
}
