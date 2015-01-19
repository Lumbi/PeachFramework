package com.lumbi.peach.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by gabriellumbi on 15-01-19.
 */
public abstract class Model {

    protected static transient final Gson GSON = new GsonBuilder().create();

    public String toJson()
    {
        return GSON.toJson(this);
    }

    public static <T> T fromJson(String json, Class clazz)
    {
        return (T) GSON.fromJson(json, clazz);
    }

}
