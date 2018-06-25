package com.example.student.paint;

import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;

public class MyPath extends Path implements Parcelable {


    private int paintColor;

    public MyPath() {
    }

    public MyPath(Parcel in) {
        paintColor = in.readInt();
    }

    public static final Creator<MyPath> CREATOR = new Creator<MyPath>() {
        @Override
        public MyPath createFromParcel(Parcel in) {
            return new MyPath(in);
        }

        @Override
        public MyPath[] newArray(int size) {
            return new MyPath[size];
        }
    };

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }
    public int getPaintColor() {
        return paintColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(paintColor);
    }
}
