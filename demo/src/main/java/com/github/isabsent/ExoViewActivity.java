package com.github.isabsent;

import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.github.isabsent.exoview.media.SimpleMediaSource;
import com.github.isabsent.exoview.ui.ExoVideoView;

import org.apache.commons.io.IOUtils;

import static com.github.isabsent.exoview.orientation.OnOrientationChangedListener.SENSOR_LANDSCAPE;
import static com.github.isabsent.exoview.orientation.OnOrientationChangedListener.SENSOR_PORTRAIT;

public class ExoViewActivity extends Activity {
    private ExoVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_view);

        initVideoView();
//        initCustomViews();
    }

    private void initVideoView() {
        SimpleMediaSource mediaSource;
        Uri uri = getIntent().getData();

        videoView = findViewById(R.id.videoView);
        videoView.setPortrait(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
        videoView.setBackListener((view, isPortrait) -> {
            if (isPortrait)
                ExoViewActivity.this.finish();
            return false;
        });

        videoView.setOrientationListener(orientation -> {
            if (orientation == SENSOR_PORTRAIT || orientation == SENSOR_LANDSCAPE) {
                WindowManager.LayoutParams attr = getWindow().getAttributes();
                Window window = getWindow();
                window.setAttributes(attr);
                window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
        });

        if (uri == null)
            uri = Uri.parse("https://filesamples.com/samples/video/3gp/sample_1280x720.3gp");
        String fileName = getFileName(uri);
        mediaSource = new SimpleMediaSource(uri);
        mediaSource.setDisplayName(fileName);

        videoView.play(mediaSource, false);

    }

//    private void initCustomViews() {
//        findViewById(R.id.addToTop).setOnClickListener(v -> {
//            View view = getLayoutInflater().inflate(R.layout.cutom_view_top, null, false);
//            videoView.addCustomView(ExoVideoPlaybackControlView.CUSTOM_VIEW_TOP, view);
//        });
//
//        findViewById(R.id.addToTopLandscape).setOnClickListener(v -> {
//            View view = getLayoutInflater().inflate(R.layout.cutom_view_top_landscape, null, false);
//            videoView.addCustomView(ExoVideoPlaybackControlView.CUSTOM_VIEW_TOP_LANDSCAPE, view);
//        });
//
//
//        findViewById(R.id.addToBottomLandscape).setOnClickListener(v -> {
//            View view = getLayoutInflater().inflate(R.layout.cutom_view_bottom_landscape, null, false);
//            videoView.addCustomView(ExoVideoPlaybackControlView.CUSTOM_VIEW_BOTTOM_LANDSCAPE, view);
//        });
//    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 23) {
            videoView.resume();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((Build.VERSION.SDK_INT <= 23)) {
            videoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            videoView.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            videoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.releasePlayer();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return videoView.onKeyDown(keyCode, event);
        return super.onKeyDown(keyCode, event);
    }

    private String getFileName(Uri uri) {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(uri, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst())
                return cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (cursor != null)
                IOUtils.closeQuietly(cursor);
        }
        return uri.getLastPathSegment();
    }
}
