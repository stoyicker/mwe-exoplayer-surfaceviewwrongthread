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
    HandlerThread exoPlayerThread = new HandlerThread("ExoPlayerThread");
    exoPlayerThread.start();
    ExoPlayer exoPlayer = new SimpleExoPlayer.Builder(this)
            .setLooper(exoPlayerThread.getLooper())
            .build();
    Handler handler = new Handler(exoPlayerThread.getLooper());
    SurfaceView surfaceView = new HandlerDelegatingSurfaceHolderSurfaceView(this, handler);
    setContentView(surfaceView);
    handler.post(() -> {
      exoPlayer.setVideoSurfaceView(surfaceView);
      MediaItem mediaItem = MediaItem.fromUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
      exoPlayer.addMediaItem(mediaItem);
      exoPlayer.setPlayWhenReady(true);
      exoPlayer.prepare();
    });
    handler.postDelayed(exoPlayer::release, 5_000);
  }
}
