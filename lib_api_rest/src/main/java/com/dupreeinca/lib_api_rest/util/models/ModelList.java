package com.dupreeinca.lib_api_rest.util.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


/**
 * Created by cloudemotion on 24/9/17.
 */

public class ModelList extends BaseObservable implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    public ModelList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected ModelList(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<ModelList> CREATOR = new Creator<ModelList>() {
        @Override
        public ModelList createFromParcel(Parcel in) {
            return new ModelList(in);
        }

        @Override
        public ModelList[] newArray(int size) {
            return new ModelList[size];
        }
    };

    @Bindable
    public int getId() {
        return id;
    }

    @Bindable
    public String getName() {
        return name==null ? "" : name;
    }

    @Override
    public String toString() {
        return name==null ? "" : name;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }
}
