package com.lumbi.peach.app;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Gabriel Lumbi 14-11-26.
 */

/**
 * This is a utility class that encapsulate all the findViewById logic.
 * This is similar to dependency injection.
 *
 * The {@code in()} methods will look for fields annotated with {@link Outlet @Outlet(resource id)}.
 * It will then find views or fragments using the id passed into the @Outlet() annotation.
 * </br></br>
 *   Usage example
 * </br></br>
 * <pre>
 * {@code
 * public class MainActivity extends Activity{
 *      @Outlet(R.id.myView) private View myView;
 *      protected void onCreate(Bundle savedInstanceState) {
 *          setContentView(R.layout.main_activity);
 *          FindViewsById.in(this);
 *          //myView is now not null
 *      }
 * }
 * }
 * </pre>
 *
 */
public final class FindViewsById {

    /**
     * Find views inside an activity.</br>
     * <strong>Must be called after</strong> {@link android.app.Activity#setContentView(int) Activity.setContentView(int)}</br>
     */
    public static void in(Activity activity){
        Field[] fields = activity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Outlet.class)) {
                Outlet outlet = field.getAnnotation(Outlet.class);
                try {
                    if(View.class.isAssignableFrom(field.getType())){
                        field.setAccessible(true);
                        field.set(activity, activity.findViewById(outlet.value()));
                    }else if(Fragment.class.isAssignableFrom(field.getType())){
                        field.setAccessible(true);
                        field.set(activity, activity.getFragmentManager().findFragmentById(outlet.value()));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Find views inside a fragment.</br>
     * <strong>Should be called during</strong> {@link android.app.Fragment#onViewCreated(View, android.os.Bundle) Fragment.onViewCreated(View,Bundle)}</br>
     * <strong>Do not call before onViewCreated.</strong>
     */
    public static void in(Fragment fragment){
        Field[] fields = fragment.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Outlet.class)) {
                Outlet outlet = field.getAnnotation(Outlet.class);
                try {
                    if(View.class.isAssignableFrom(field.getType())){
                        field.setAccessible(true);
                        field.set(fragment, fragment.getView().findViewById(outlet.value()));
                    }else if(Fragment.class.isAssignableFrom(field.getType())){
                        field.setAccessible(true);
                        field.set(fragment, fragment.getFragmentManager().findFragmentById(outlet.value()));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Find views inside a view.</br>
     * <strong>Should not be called before the view has been inflated.</strong>
     */
    public static void in(View view){
        FindViewsById.in(view, view);
    }

    /**
     * Similar to {@link FindViewsById#in(android.view.View) FindViewsById.in(View).}
     * Instead of assigning the found views to the view's Outlets, the found views are assigned to
     * the delegate object.
     * @see FindViewsById#in(android.view.View)
     * @param view
     * @param delegate - The delegate object with {@literal @}Outlet annotated fields to fill.
     */
    public static void in(View view, Object delegate){
        Field[] fields = delegate.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Outlet.class)) {
                Outlet outlet = field.getAnnotation(Outlet.class);
                try {
                    if(View.class.isAssignableFrom(field.getType())){
                        field.setAccessible(true);
                        field.set(delegate, view.findViewById(outlet.value()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
