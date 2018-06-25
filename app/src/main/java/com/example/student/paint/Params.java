package com.example.student.paint;

import android.graphics.Paint;

import java.util.ArrayList;

public class Params {
    public static int Color = R.color.colorBlue;
    public static int StrokeWidth=10;
    public static Paint.Style Style= Paint.Style.STROKE;
    public static boolean ClearCanvas=false;
    public static ArrayList<MyPath> paths = new ArrayList<MyPath>();
    public static boolean RestoreCanvas=false;
}
