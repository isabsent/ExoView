package com.github.isabsent.exoview.media;

import android.net.Uri;

public class SimpleMediaSource implements ExoMediaSource {

    private String displayName;

    private Uri uri;

    public SimpleMediaSource(String url) {
        this.uri = Uri.parse(url);
    }

    public SimpleMediaSource(Uri uri) {
        this.uri = uri;
    }

    @Override
    public String name() {
        return displayName;
    }

    @Override
    public String extension() {
        return null;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public Uri uri() {
        return uri;
    }

}
