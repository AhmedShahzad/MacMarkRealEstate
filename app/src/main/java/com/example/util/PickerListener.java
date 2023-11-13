package com.example.util;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

public interface PickerListener {
    void onPicked(Uri uri, File file, Bitmap bitmap);
}
