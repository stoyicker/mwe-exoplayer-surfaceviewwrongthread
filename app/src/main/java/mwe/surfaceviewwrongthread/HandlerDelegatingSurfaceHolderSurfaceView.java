package mwe.surfaceviewwrongthread;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
class HandlerDelegatingSurfaceHolderSurfaceView extends SurfaceView {
    private final SurfaceHolder surfaceHolder;

    HandlerDelegatingSurfaceHolderSurfaceView(Context context, Handler handler) {
        super(context);
        surfaceHolder = new DelegatingSurfaceHolder(super.getHolder(), handler);
    }

    @Override
    public SurfaceHolder getHolder() {
        return surfaceHolder;
    }
}
