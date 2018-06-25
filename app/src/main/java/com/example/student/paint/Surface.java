package com.example.student.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Iterator;


public class Surface extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder surfaceHolder;
    private Thread thread;
    private boolean isRun = false;
    public static Object block = new Object();
    private MyPath path;
    private Bitmap bitmap;
    private Canvas canvas= null;
    private Paint paint;
    private Context context;

    public Surface(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public void resumeDrawing()
    {
        thread = new Thread(this);
        isRun = true;
        thread.start();
        Params.RestoreCanvas=true;
    }
    public void pauseDrawing()
    {
        isRun=false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();

        synchronized (block) {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    path=new MyPath();
                    path.moveTo(event.getX(),event.getY());
                    path.addCircle(event.getX(),event.getY(), 7, MyPath.Direction.CW);
                    return true;

                case MotionEvent.ACTION_UP:
                    path.addCircle(event.getX(),event.getY(), 7, MyPath.Direction.CCW);
                    canvas.drawPath(path, paint);
                    MyPath mp;
                    mp=path;
                    mp.setPaintColor(Params.Color);
                    Params.paths.add(mp);
                    return true;

                case MotionEvent.ACTION_MOVE:
                    path.lineTo(event.getX(),event.getY());
                    canvas.drawPath(path, paint);
                    return true;
                default:
                    break;
            }
        }
        return true;
    }

    public boolean performClick()
    {
        return super.performClick();
    }

    private void Clearing_canvas()
    {
        this.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        Params.ClearCanvas=false;

        if (!Params.paths.isEmpty()) {
            Iterator<MyPath> it = Params.paths.iterator();
            while (it.hasNext())
            {
                MyPath pp = it.next();
                it.remove();
            }
        }
        Params.ClearCanvas=false;
    }

    private void Restoring_canvas()
    {
        for (MyPath  path: Params.paths){
            int color = ContextCompat.getColor(context, path.getPaintColor());
            paint.setColor(color);
            this.canvas.drawPath(path, paint);
        }
        Params.RestoreCanvas=false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        resumeDrawing();
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isRun = false;
        pauseDrawing();
    }

    @Override
    public void run() {
        while (isRun) {
            Canvas canvas = null;
            try {
                synchronized (surfaceHolder)
                {
                    if(!surfaceHolder.getSurface().isValid()) continue;
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (block)
                    {
                        if(isRun)
                        {
                            int color = ContextCompat.getColor(context, Params.Color);
                            paint.setColor(color);
                            paint.setStrokeWidth(Params.StrokeWidth);
                            paint.setStyle(Params.Style);
                            canvas.drawARGB(255,255,255,255);
                            canvas.drawBitmap(bitmap,0,0,null);
                            if(Params.ClearCanvas==true){
                                Clearing_canvas();
                            }
                            if(Params.RestoreCanvas==true){
                                Restoring_canvas();
                            }
                        }

                    }
                }
            }
            finally
            {
                if(canvas != null)
                {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            try {
                Thread.sleep(1000 / 25); // 25
            } catch (InterruptedException e) {
            }
        }

    }
}
