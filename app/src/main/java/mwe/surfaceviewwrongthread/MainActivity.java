package mwe.surfaceviewwrongthread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.SurfaceView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SurfaceView surfaceView = new SurfaceView(this);
    setContentView(surfaceView);
    HandlerThread exoPlayerThread = new HandlerThread("ExoPlayerThread");
    exoPlayerThread.start();
    ExoPlayer exoPlayer = new SimpleExoPlayer.Builder(this)
            .setLooper(exoPlayerThread.getLooper())
            .build();
    Handler commandHandler = new Handler(exoPlayerThread.getLooper());
    commandHandler.post(() -> {
      exoPlayer.setVideoSurfaceView(surfaceView);
      MediaItem mediaItem = MediaItem.fromUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
      exoPlayer.addMediaItem(mediaItem);
      exoPlayer.setPlayWhenReady(true);
      exoPlayer.prepare();
    });
  }
}
