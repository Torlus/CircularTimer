package com.torlus.android.circulartimer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.view.MotionEvent;

public class TimerScreen implements Screen {

    public Context context;

    @Override
    public Context context() {
        return context;
    }

    public TimerScreen(Context context) {
        this.context = context;
    }

    // GFX stuff
    private final float scaleX = 3.0f;
    private float scaleY = scaleX;
    private float width = 240.0f;
    private float height = 320.0f;

    private final float[] mPos = new float[16];
    private final float[] mScale = new float[16];
    private final float[] mFinal = new float[16];

    private Square target;
    private Square[] players = new Square[2];

    private float mTime = 0;
    float others[] = new float[8];

    @Override
    public void init() throws Exception {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        target = new Square(context, R.raw.target_v, R.raw.target_f);
        players[0] = new Square(context, R.raw.player_v, R.raw.player_f);
        players[1] = new Square(context, R.raw.player_v, R.raw.player_f);
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;

        if (height <= 0)
            return;

        GLES20.glViewport(0, 0, width, height);

        Matrix.setIdentityM(mScale, 0);
        Matrix.scaleM(mScale, 0, 1.0f / scaleX, (-1.0f / scaleX) * width / height, 1.0f);

        scaleY = scaleX * this.height / this.width;

        // target.x = 0;
        // target.y = 0;
    }

    @Override
    public void draw() {
        mTime += 0.5f;
        if (mTime >= 360.0f)
            mTime = 0.0f;

        float zoom = 3.0f;
        Square p;

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        p = players[0];
        others[0] = 0.0f;
        others[1] = 0.8f;
        others[2] = 0.0f;
        others[3] = 0.0f;
        others[4] = 135.0f;

        Matrix.setIdentityM(mPos, 0);
        Matrix.translateM(mPos, 0, p.x, p.y, 0.0f);
        Matrix.rotateM(mPos, 0, 90f + 15.0f, 0.0f, 0.0f, 1.0f);
        Matrix.scaleM(mPos, 0, zoom, zoom, 1.0f);
        Matrix.multiplyMM(mFinal, 0, mScale, 0, mPos, 0);
        p.draw(mFinal, others, mTime);

        p = players[1];
        others[0] = 0.0f;
        others[1] = 0.0f;
        others[2] = 0.8f;
        others[3] = 135.0f;
        others[4] = 270.0f;

        Matrix.setIdentityM(mPos, 0);
        Matrix.translateM(mPos, 0, p.x, p.y, 0.0f);
        Matrix.rotateM(mPos, 0, 90f + 15.0f, 0.0f, 0.0f, 1.0f);
        Matrix.scaleM(mPos, 0, zoom, zoom, 1.0f);
        Matrix.multiplyMM(mFinal, 0, mScale, 0, mPos, 0);
        p.draw(mFinal, others, mTime);


        p = target;

        Matrix.setIdentityM(mPos, 0);
        Matrix.translateM(mPos, 0, p.x, p.y, 0.0f);
        // Matrix.rotateM(mPos, 0, mTime * 10.0f * (float) Math.sin(mTime), 0.0f, 0.0f, 1.0f);
        // Matrix.rotateM(mPos, 0, mTime * 10.0f, 0.0f, 0.0f, 1.0f);
        Matrix.rotateM(mPos, 0, 90.0f + 15.0f, 0.0f, 0.0f, 1.0f);
        Matrix.scaleM(mPos, 0, zoom, zoom, 1.0f);
        Matrix.multiplyMM(mFinal, 0, mScale, 0, mPos, 0);
        p.draw(mFinal, others, mTime);

    }


    @Override
    public boolean touch(MotionEvent e) {

        //Log.e("Touch", "pc " + e.getPointerCount());

        float x = e.getX();
        float y = e.getY();
        x = (x - width / 2) * scaleX / (width / 2);
        y = (y - height / 2) * scaleX / (width / 2);

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return true;
    }

}
