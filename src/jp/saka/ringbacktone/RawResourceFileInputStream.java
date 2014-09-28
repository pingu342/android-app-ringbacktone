package jp.saka.ringbacktone;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;

import android.util.Log;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.AssetFileDescriptor;

public class RawResourceFileInputStream {

	private AssetFileDescriptor mFileDescriptor = null;
	private InputStream mInputStream = null;
	public long length = -1;
	public String name;

	public RawResourceFileInputStream(Context context, int resid) {
		if (context == null) {
			return;
		}
		try {
			Resources resources = context.getResources();
			name = resources.getResourceName(resid);
			Log.d("test", "raw resource file name = " + name);

			mFileDescriptor = resources.openRawResourceFd(resid);
			length = mFileDescriptor.getLength();
			Log.d("test", "raw resource file data length = " + length);

			mInputStream = new BufferedInputStream(mFileDescriptor.createInputStream());

		} catch (Exception e) {
			Log.d("test", "exception");
			close();
		}
	}

	public int read(byte[] buf, int offset, int size) {
		try {
			if (mInputStream != null) {
				return mInputStream.read(buf, offset, size);
			} else {
				return -1;
			}
		} catch (Exception e) {
			Log.d("test", "exception");
			close();
			return -1;
		}
	}

	public void close() {
		try {
			if (mInputStream != null) {
				mInputStream.close();
				mInputStream = null;
			}
			if (mFileDescriptor != null) {
				mFileDescriptor.close();
				mFileDescriptor = null;
			}
			length = -1;
		} catch (Exception e) {
		}
	}
}

