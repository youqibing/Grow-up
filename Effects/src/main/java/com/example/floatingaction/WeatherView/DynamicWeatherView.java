package com.example.floatingaction.WeatherView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AnimationUtils;

/**
 * Created by root on 16-12-5.
 *
 */

public class DynamicWeatherView extends SurfaceView implements SurfaceHolder.Callback {

    private int mWidth;
    private int mHeight;
    private BaseDrawer preDrawer;
    private BaseDrawer curDrawer;
    private DrawThread drawThread;
    private BaseDrawer.Type curType = BaseDrawer.Type.DEFAULT;
    private float curDrawerAlpha = 0f;

    public DynamicWeatherView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        Log.e("testDynamicWeatherView","6666666666666666");
        init(context);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        synchronized (drawThread) {
            drawThread.mSurface = holder;
            drawThread.notify();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (drawThread) {
            drawThread.mSurface = holder;
            drawThread.notify();
            while (drawThread.mActive) {
                try {
                    drawThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        holder.removeCallback(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // updateDrawerSize(w, h);
        mWidth = w;
        mHeight = h;
    }


    public void init(Context context){

        Log.e("testInit","4444444444444444");
        curDrawerAlpha = 0f;
        drawThread = new DrawThread();
        final SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.RGBA_8888);
        drawThread.start();
    }

    public void setDrawerType(BaseDrawer.Type type) {
        if (type == null) {
            return;
        }
        if (type != curType) {
            curType = type;
            Log.e("testDrawerType","222222222222");
            setDrawer(BaseDrawer.makeDrawerByType(getContext(), curType));
        }
    }

    private void setDrawer(BaseDrawer baseDrawer) {
        if (baseDrawer == null) {
            return;
        }
        Log.e("testDrawer","33333333333333");
        curDrawerAlpha = 0f;
        if (this.curDrawer != null) {
            this.preDrawer = curDrawer;
        }
        this.curDrawer = baseDrawer;
        // updateDrawerSize(getWidth(), getHeight());
        // invalidate();
    }


    private class DrawThread extends Thread {
        // These are protected by the Thread's lock.
        SurfaceHolder mSurface;
        boolean mRunning;
        boolean mActive;
        boolean mQuit;

        @Override
        public void run() {
            while (true) {
                // Log.i(TAG, "DrawThread run..");
                // Synchronize with activity: block until the activity is ready
                // and we have a surface; report whether we are active or
                // inactive
                // at this point; exit thread when asked to quit.
                Log.e("testDrawThread","55555555555555555");
                synchronized (this) {
                    while (mSurface == null || !mRunning) {
                        if (mActive) {
                            mActive = false;
                            notify();
                        }
                        if (mQuit) {
                            return;
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                        }
                    }

                    if (!mActive) {
                        mActive = true;
                        notify();
                    }
                    final long startTime = AnimationUtils.currentAnimationTimeMillis();
                    //TimingLogger logger = new TimingLogger("DrawThread");
                    // Lock the canvas for drawing.
                    Canvas canvas = mSurface.lockCanvas();
                    //logger.addSplit("lockCanvas");

                    if (canvas != null) {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        // Update graphics.

                        drawSurface(canvas);
                        //logger.addSplit("drawSurface");
                        // All done!
                        mSurface.unlockCanvasAndPost(canvas);
                        //logger.addSplit("unlockCanvasAndPost");
                        //logger.dumpToLog();
                    } else {
                        //Log.i(TAG, "Failure locking canvas");
                    }
                    final long drawTime = AnimationUtils.currentAnimationTimeMillis() - startTime;
                    final long needSleepTime = 16 - drawTime;
                    //Log.i(TAG, "drawSurface drawTime->" + drawTime + " needSleepTime->" + Math.max(0, needSleepTime));// needSleepTime);
                    if (needSleepTime > 0) {
                        try {
                            Thread.sleep(needSleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

    private boolean drawSurface(Canvas canvas) {
        final int w = mWidth;
        final int h = mHeight;
        if (w == 0 || h == 0) {
            return true;
        }
        Log.e("testDrawSurface","77777777777777777");
        boolean needDrawNextFrame = false;
        // Log.d(TAG, "curDrawerAlpha->" + curDrawerAlpha);
        if (curDrawer != null) {
            curDrawer.setSize(w, h);
            needDrawNextFrame = curDrawer.draw(canvas, curDrawerAlpha);
        }
        if (preDrawer != null && curDrawerAlpha < 1f) {
            needDrawNextFrame = true;
            preDrawer.setSize(w, h);
            preDrawer.draw(canvas, 1f - curDrawerAlpha);
        }
        if (curDrawerAlpha < 1f) {
            curDrawerAlpha += 0.04f;
            if (curDrawerAlpha > 1) {
                curDrawerAlpha = 1f;
                preDrawer = null;
            }
        }
        // if (needDrawNextFrame) {
        // ViewCompat.postInvalidateOnAnimation(this);
        // }
        return needDrawNextFrame;
    }

    public void onResume() {
        // Let the drawing thread resume running.
        synchronized (drawThread) {
            drawThread.mRunning = true;
            drawThread.notify();
        }
    }

    public void onPause() {
        // Make sure the drawing thread is not running while we are paused.
        synchronized (drawThread) {
            drawThread.mRunning = false;
            drawThread.notify();
        }
    }

    public void onDestroy() {
        // Make sure the drawing thread goes away.
        synchronized (drawThread) {
            drawThread.mQuit = true;
            drawThread.notify();
        }
    }


}
