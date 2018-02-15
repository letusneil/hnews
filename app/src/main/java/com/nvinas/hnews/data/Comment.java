package com.nvinas.hnews.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by neilvinas on 2/12/18.
 */

public class Comment {

    @SerializedName("by")
    @Expose
    private String by;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("kids")
    @Expose
    private List<Integer> kids = null;
    @SerializedName("parent")
    @Expose
    private int parent;
    @SerializedName("time")
    @Expose
    private int time;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("type")
    @Expose
    private String type;
    private int level = 0;

    public Comment(int id) {
        this.id = id;
    }

    public String getBy() {
        return by;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getKids() {
        return kids;
    }

    public int getParent() {
        return parent;
    }

    public int getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
