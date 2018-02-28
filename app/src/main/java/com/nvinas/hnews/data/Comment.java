package com.nvinas.hnews.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neilvinas on 2/12/18.
 */

public class Comment implements Parcelable {

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

    public Comment(int id, String by, List<Integer> kids, int parent, int time, String text, String type, int level) {
        this.id = id;
        this.by = by;
        this.kids = kids;
        this.parent = parent;
        this.time = time;
        this.text = text;
        this.type = type;
        this.level = level;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.by);
        dest.writeInt(this.id);
        dest.writeList(this.kids);
        dest.writeInt(this.parent);
        dest.writeInt(this.time);
        dest.writeString(this.text);
        dest.writeString(this.type);
        dest.writeInt(this.level);
    }

    protected Comment(Parcel in) {
        this.by = in.readString();
        this.id = in.readInt();
        this.kids = new ArrayList<Integer>();
        in.readList(this.kids, Integer.class.getClassLoader());
        this.parent = in.readInt();
        this.time = in.readInt();
        this.text = in.readString();
        this.type = in.readString();
        this.level = in.readInt();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
