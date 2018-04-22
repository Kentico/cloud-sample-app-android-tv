package kentico.kentico_android_tv_app.details.video;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v17.leanback.app.VideoFragment;
import android.support.v17.leanback.app.VideoFragmentGlueHost;
import android.support.v17.leanback.media.MediaPlayerAdapter;
import android.support.v17.leanback.media.PlaybackGlue;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.util.Log;

import kentico.kentico_android_tv_app.MainApplication;


public class VideoConsumptionFragment extends VideoFragment {

    private static final String VIDEO_ID = MainApplication.getVideosList().get(1).getId();
    public static final String TAG = "VideoConsumption";
    private VideoMediaPlayerGlue<MediaPlayerAdapter> mMediaPlayerGlue;
    final VideoFragmentGlueHost mHost = new VideoFragmentGlueHost(this);

    static void playWhenReady(PlaybackGlue glue) {
        if (glue.isPrepared()) {
            glue.play();
        } else {
            glue.addPlayerCallback(new PlaybackGlue.PlayerCallback() {
                @Override
                public void onPreparedStateChanged(PlaybackGlue glue) {
                    if (glue.isPrepared()) {
                        glue.removePlayerCallback(this);
                        glue.play();
                    }
                }
            });
        }
    }

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener
            = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int state) {
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMediaPlayerGlue = new VideoMediaPlayerGlue(getActivity(),
                new MediaPlayerAdapter(getActivity()));
        mMediaPlayerGlue.setHost(mHost);
        AudioManager audioManager = (AudioManager) getActivity()
                .getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN) != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Log.w(TAG, "video player cannot obtain audio focus!");
        }

        mMediaPlayerGlue.setMode(PlaybackControlsRow.RepeatAction.NONE);
        mMediaPlayerGlue.setTitle("Diving with Sharks");
        mMediaPlayerGlue.setSubtitle("A Googler");
        mMediaPlayerGlue.getPlayerAdapter().setDataSource(Uri.parse(getVideoUri(VIDEO_ID)));

        playWhenReady(mMediaPlayerGlue);
        setBackgroundType(BG_LIGHT);
    }

    private String getVideoUri(String videoId) {
        return "https://youtube.com/watch?v=" + videoId;
    }

    @Override
    public void onPause() {
        if (mMediaPlayerGlue != null) {
            mMediaPlayerGlue.pause();
        }
        super.onPause();
    }

}
