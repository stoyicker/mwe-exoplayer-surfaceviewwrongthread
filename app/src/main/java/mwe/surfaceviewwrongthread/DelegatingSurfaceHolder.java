package mwe.surfaceviewwrongthread;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.util.HashMap;
import java.util.Map;

class DelegatingSurfaceHolder implements SurfaceHolder {
    private final SurfaceHolder surfaceHolder;
    private final Handler handler;
    private final Map<Callback, DelegatingSurfaceHolderCallback> callbackMappings = new HashMap<>();

    DelegatingSurfaceHolder(SurfaceHolder surfaceHolder, Handler handler) {
        this.surfaceHolder = surfaceHolder;
        this.handler = handler;
    }

    @Override
    public void addCallback(Callback callback) {
        DelegatingSurfaceHolderCallback delegatingSurfaceHolderCallback =
                new DelegatingSurfaceHolderCallback(callback, handler);
        if (callbackMappings.containsKey(callback)) {
            surfaceHolder.removeCallback(callbackMappings.get(callback));
        }
        callbackMappings.put(callback, delegatingSurfaceHolderCallback);
        surfaceHolder.addCallback(delegatingSurfaceHolderCallback);
    }

    @Override
    public void removeCallback(Callback callback) {
        if (callbackMappings.containsKey(callback)) {
            DelegatingSurfaceHolderCallback delegatingSurfaceHolderCallback =
                    callbackMappings.get(callback);
            surfaceHolder.removeCallback(delegatingSurfaceHolderCallback);
            callbackMappings.remove(callback);
        }
    }

    @Override
    public boolean isCreating() {
        return surfaceHolder.isCreating();
    }

    @Deprecated
    @Override
    public void setType(int type) {
        surfaceHolder.setType(type);
    }

    @Override
    public void setFixedSize(int width, int height) {
        surfaceHolder.setFixedSize(width, height);
    }

    @Override
    public void setSizeFromLayout() {
        surfaceHolder.setSizeFromLayout();
    }

    @Override
    public void setFormat(int format) {
        surfaceHolder.setFormat(format);
    }

    @Override
    public void setKeepScreenOn(boolean screenOn) {
        surfaceHolder.setKeepScreenOn(screenOn);
    }

    @Override
    public Canvas lockCanvas() {
        return surfaceHolder.lockCanvas();
    }

    @Override
    public Canvas lockCanvas(Rect dirty) {
        return surfaceHolder.lockCanvas(dirty);
    }

    @Override
    public void unlockCanvasAndPost(Canvas canvas) {
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public Rect getSurfaceFrame() {
        return surfaceHolder.getSurfaceFrame();
    }

    @Override
    public Surface getSurface() {
        return surfaceHolder.getSurface();
    }

    @Override
    public Canvas lockHardwareCanvas() {
        return surfaceHolder.lockHardwareCanvas();
    }


}
