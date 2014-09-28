package com.torlus.android.circulartimer;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square {
    // Common stuff
    public float x;
    public float y;

    private final FloatBuffer vertexBuffer;
    private final FloatBuffer texCoordsBuffer;
    private final ShortBuffer drawListBuffer;
    private final int mProgram;
    private int mPositionHandle;
    private int mTexCoordsHandle;
    private int mMVPMatrixHandle;
    private int mTimeHandle;
    private int mOthersHandle;

    private static final float squareCoords[] = { -1.0f, 1.0f, 0.0f, // top left
            -1.0f, -1.0f, 0.0f, // bottom left
            1.0f, -1.0f, 0.0f, // bottom right
            1.0f, 1.0f, 0.0f }; // top right
    private static final float texCoords[] = { 0.0f, 0.0f, // top left
            0.0f, 1.0f, // bottom left
            1.0f, 1.0f, // bottom right
            1.0f, 0.0f // top right
    };
    private static final short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    public Square(Context context, int vsh, int fsh) {
        ByteBuffer vbb = ByteBuffer.allocateDirect(squareCoords.length * Misc.SIZEOF_FLOAT);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * Misc.SIZEOF_FLOAT);
        tbb.order(ByteOrder.nativeOrder());
        texCoordsBuffer = tbb.asFloatBuffer();
        texCoordsBuffer.put(texCoords);
        texCoordsBuffer.position(0);

        ByteBuffer dlbb = ByteBuffer.allocateDirect(drawOrder.length * Misc.SIZEOF_SHORT);
        dlbb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlbb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        // prepare shaders and OpenGL program
        String vertexShaderCode = Misc.readTextFileFromRawResource(context, vsh);
        int vertexShader = Misc.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        String fragmentShaderCode = Misc.readTextFileFromRawResource(context, fsh);
        int fragmentShader = Misc.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram(); // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader); // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram); // create OpenGL program executables

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mTexCoordsHandle = GLES20.glGetAttribLocation(mProgram, "vTexCoords");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        mTimeHandle = GLES20.glGetUniformLocation(mProgram, "uTime");
        mOthersHandle = GLES20.glGetUniformLocation(mProgram, "uOthers");
    }

    public void draw(float[] mvpMatrix, float[] others, float time) {
        GLES20.glUseProgram(mProgram);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 3 * Misc.SIZEOF_FLOAT, vertexBuffer);

        GLES20.glEnableVertexAttribArray(mTexCoordsHandle);
        GLES20.glVertexAttribPointer(mTexCoordsHandle, 2, GLES20.GL_FLOAT, false, 2 * Misc.SIZEOF_FLOAT, texCoordsBuffer);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glUniform1f(mTimeHandle, time);

        GLES20.glUniform2fv(mOthersHandle, 4, others, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordsHandle);
    }

}
