package jp.saka.ringbacktone;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;

import android.util.Log;
import android.content.Context;
import android.content.res.AssetFileDescriptor;


public class WAVFileData {

	private static int WAV_FILE_HEADER_SIZE = 44;
	private RawResourceFileInputStream mInputStream = null;
	private byte[] mHeader = new byte[WAV_FILE_HEADER_SIZE];
	public byte[] data = null;

	public WAVFileData(Context context, int resid) {
		mInputStream = new RawResourceFileInputStream(context, resid);
		if (!readHeader()) {
			Log.d("test", "read wav header error");
			close();
			data = null;
			return;
		}
		if (!readData()) {
			Log.d("test", "read wav data error");
			close();
			data = null;
			return;
		}
		Log.d("test", "read wav data success");
		Log.d("test", "wav data size = " + data.length + " bytes");
		close();
	}

	private boolean readHeader() {
		if (mInputStream != null) {
			return (mInputStream.read(mHeader, 0, mHeader.length) == mHeader.length);
		}
		return false;
	}

	private boolean readData() {
		if (mInputStream != null) {
			int dataSize = (int)mInputStream.length - WAV_FILE_HEADER_SIZE;
			if (dataSize <= 0) {
				return false;
			}
			data = new byte[dataSize];
			return (mInputStream.read(data, 0, (int)data.length) == data.length);
		}
		return false;
	}

	private void close() {
		if (mInputStream != null) {
			mInputStream.close();
		}
	}
}

