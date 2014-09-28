package jp.saka.ringbacktone;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.media.AudioManager;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class RingbacktoneActivity extends Activity
{
	private AudioManager mAudioManager;
	private Ringbacktone mRingbacktone;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mRingbacktone = Ringbacktone.getInstance(this);
		mAudioManager = ((AudioManager) getSystemService(Context.AUDIO_SERVICE));

		mAudioManager.setSpeakerphoneOn(true);
		mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);

		Button button;
		button = (Button)findViewById(R.id.PlayButton);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mRingbacktone.play();
			}
		});
		button = (Button)findViewById(R.id.StopButton);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mRingbacktone.stop();
			}
		});
		button = (Button)findViewById(R.id.SpeakerphoneOn);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mAudioManager.setSpeakerphoneOn(true);
			}
		});
		button = (Button)findViewById(R.id.SpeakerphoneOff);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mAudioManager.setSpeakerphoneOn(false);
			}
		});

	}

}
