package mwe.surfaceviewwrongthread;

import android.os.Handler;
import android.view.SurfaceHolder;

class DelegatingSurfaceHolderCallback implements SurfaceHolder.Callback {
    private final SurfaceHolder.Callback delegate;
    private final Handler handler;


    DelegatingSurfaceHolderCallback(SurfaceHolder.Callback delegate, Handler handler) {
        this.delegate = delegate;
        this.handler = handler;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        handler.post(() -> delegate.surfaceCreated(holder));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        handler.post(() -> delegate.surfaceChanged(holder, format, width, height));
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        handler.post(() -> delegate.surfaceDestroyed(holder));
    }
}
