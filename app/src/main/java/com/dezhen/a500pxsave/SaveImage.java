package com.dezhen.a500pxsave;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import java.io.File;

/**
 * Created by root on 24/07/17.
 */

public class SaveImage {
    public static long saveImg(Context context, String name, String path, String url) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, name);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationUri(Uri.fromFile(file));
        request.setTitle(name);
        request.setDescription(file.getAbsolutePath());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("image/jpeg");
        request.allowScanningByMediaScanner();
        return downloadManager.enqueue(request);
    }
}
