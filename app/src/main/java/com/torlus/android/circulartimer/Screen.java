package com.torlus.android.circulartimer;

import android.content.Context;
import android.view.MotionEvent;

public interface Screen {

    public Context context();

    public void init() throws Exception;

    public void resize(int width, int height);

    public void draw();

    public boolean touch(MotionEvent e);

}
