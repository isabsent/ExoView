package com.github.isabsent.exoview.media;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;

public class MediaSourceCreator {
    private Context context;
    private String userAgent;
    private DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    public MediaSourceCreator(Context context) {
        this(context, "exoplayer_jetico");
    }

    public MediaSourceCreator(Context context, String userAgent) {
        this.userAgent = userAgent;
        this.context = context;
    }

    public MediaSource buildMediaSource(Uri uri, String overrideExtension) {
        @C.ContentType int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri) : Util.inferContentType("." + overrideExtension);
        switch (type) {
            case C.TYPE_SS:
                SsMediaSource.Factory factory = new SsMediaSource.Factory(buildDataSourceFactory(false));
                factory.createMediaSource(uri);
                return factory.createMediaSource(uri);
            case C.TYPE_DASH:
                DashMediaSource.Factory factory1 = new DashMediaSource.Factory(buildDataSourceFactory(false));
                return factory1.createMediaSource(uri);
            case C.TYPE_HLS:
                HlsMediaSource.Factory factory2 = new HlsMediaSource.Factory(buildDataSourceFactory(false));
                return factory2.createMediaSource(uri);
            case C.TYPE_OTHER:
                ProgressiveMediaSource.Factory factory3 = new ProgressiveMediaSource.Factory(buildDataSourceFactory(false));
                return factory3.createMediaSource(uri);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }


    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    private DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(context, bandwidthMeter,
                buildHttpDataSourceFactory(bandwidthMeter));
    }

    private HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
    }

}
