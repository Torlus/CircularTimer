package com.torlus.android.circulartimer;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class TimerActivity extends Activity {

    private GLSurfaceView myGLView;
    private Screen screen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen = new TimerScreen(this);

        myGLView = new MyGLSurfaceView(screen);
        setContentView(myGLView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        myGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myGLView.onResume();
    }
}

class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer myRenderer;
    private final Screen screen;

    boolean isRunning = true;

    public MyGLSurfaceView(Screen screen) {
        super(screen.context());
        this.screen = screen;

        setEGLContextClientVersion(2);
        myRenderer = new MyGLRenderer(screen);
        setRenderer(myRenderer);
        // setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return screen.touch(e);
    }
}

class MyGLRenderer implements GLSurfaceView.Renderer {

    private Screen screen;

    public MyGLRenderer(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        try {
            screen.init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        screen.draw();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        screen.resize(width, height);
    }
}
