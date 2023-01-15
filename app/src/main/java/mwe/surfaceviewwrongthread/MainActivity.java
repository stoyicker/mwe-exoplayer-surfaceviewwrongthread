package mwe.surfaceviewwrongthread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceView;

import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.EventHandlerRequestCallback;
import androidx.media3.exoplayer.ExoPlayer;

import java.util.concurrent.Phaser;

public class MainActivity extends Activity implements EventHandlerRequestCallback {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SurfaceView surfaceView = new SurfaceView(this);
    setContentView(surfaceView);
    HandlerThread exoPlayerThread = new HandlerThread("ExoPlayerThread");
    exoPlayerThread.start();
    ExoPlayer exoPlayer = new ExoPlayer.Builder(this)
            .setLooper(exoPlayerThread.getLooper(), this)
            .build();
    Handler commandHandler = new Handler(exoPlayerThread.getLooper());
    commandHandler.post(() -> {
      exoPlayer.setVideoSurfaceHolder(surfaceView.getHolder());
      MediaItem mediaItem = MediaItem.fromUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
      exoPlayer.addMediaItem(mediaItem);
      exoPlayer.setPlayWhenReady(true);
      exoPlayer.prepare();
    });
  }

  @Override
  public void onRunnablePosted(Phaser remainingPhaser) {
    Log.d("HOLA", "onRunnablePosted invoked");
  }
}
