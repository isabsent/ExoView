package com.github.isabsent.exoview.media;

import android.net.Uri;

public interface ExoMediaSource {

    Uri uri();

    String name();

    String extension();
}
