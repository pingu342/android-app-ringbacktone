package jp.saka.ringbacktone;

import android.media.AudioManager;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.media.MediaPlayer;

import android.content.Context;

import jp.saka.ringbacktone.R;


public class Ringbacktone
{
	private static int mWAVFileResourceId = R.raw.pcmsound_02_12k;
	private static int mSampleRateInHz = 12000;
	private static int mChannelConfig = AudioFormat.CHANNEL_OUT_STEREO;
	private static int mStreamType = AudioManager.STREAM_VOICE_CALL;
	private static int mAudioFormat = AudioFormat.ENCODING_PCM_16BIT;
	private static int mBytesPerFrame = 4;
	private int mBufferSizeInBytes = 0;
	private AudioTrack mAudioTrack = null;

	private static Ringbacktone mInstance = null;
	private WAVFileData mWAVFileData = null;

	private boolean mPlaying = false;

	private Ringbacktone(Context context) {
		mWAVFileData = new WAVFileData(context, mWAVFileResourceId);
		mBufferSizeInBytes = mWAVFileData.data.length;
	}

	public static synchronized Ringbacktone getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new Ringbacktone(context);
		}
		return mInstance;
	}

	public synchronized void play() {
		if (mAudioTrack == null && mWAVFileData != null) {
			mAudioTrack = new AudioTrack(mStreamType, mSampleRateInHz, mChannelConfig, mAudioFormat, mBufferSizeInBytes, AudioTrack.MODE_STATIC);
			mAudioTrack.write(mWAVFileData.data, 0, mBufferSizeInBytes);
			mAudioTrack.setLoopPoints(0, mBufferSizeInBytes / mBytesPerFrame, -1);
			mAudioTrack.play();
		}
	}

	public synchronized void stop() {
		if (mAudioTrack != null) {
			mAudioTrack.release();
			mAudioTrack = null;
		}
	}
}
