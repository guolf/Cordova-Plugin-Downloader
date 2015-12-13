package cn.guolf.cordova.plugins;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Author：guolf on 15/12/13 09:09
 * Email ：guo@guolingfa.cn
 */
public class Downloader extends CordovaPlugin {
    private ProgressDialog progressDialog = null;
    private int currentProgress;

    private String progressInfo = "正在获取数据: ";
    Handler progresshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Runnable runnable = new Runnable() {
                public void run() {
                    if (progressDialog == null) {
                        progressDialog = new ProgressDialog(
                                cordova.getActivity());
                    }
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    progressDialog.setMessage(progressInfo + currentProgress
                            + "%");
                    if (currentProgress >= 100) {
                        progressDialog.hide();
                    }
                }
            };
            Downloader.this.cordova.getActivity().runOnUiThread(runnable);
            // super.handleMessage(msg);
        }
    };

    @Override
    public boolean execute(String action, JSONArray args,
                           CallbackContext callbackContext) {

        if (action.equals("downloadAndOpenFile")) {
            try {
                String fileUrl = args.getString(0);
                JSONObject params = args.getJSONObject(1);
                String fileName = params.has("fileName") ? params
                        .getString("fileName") : fileUrl.substring(fileUrl
                        .lastIndexOf("/") + 1);
                int end = fileUrl.lastIndexOf("/") + 1;
                String encodeFile = URLEncoder.encode(
                        fileUrl.substring(fileUrl.lastIndexOf("/") + 1),
                        "UTF-8");
                fileUrl = fileUrl.substring(0, end) + encodeFile;
                String dirName = "";
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    dirName = params.has("dirName") ? params
                            .getString("dirName") : "sdcard/download";
                } else {
                    dirName = this.cordova.getActivity().getFilesDir()
                            .getPath();
                }

                Boolean overwrite = params.has("overwrite") ? params
                        .getBoolean("overwrite") : false;
                Boolean isShowProgress = params.has("isShowProgress") ? params
                        .getBoolean("isShowProgress") : true;
                progressInfo = params.has("progressInfo") ? params
                        .getString("progressInfo") : "正在获取数据: ";
                // if (isShowProgress) {
                // showProgressInfo();
                // }
                this.downloadAndOpenFile(fileUrl, dirName, fileName, overwrite,
                        callbackContext);
            } catch (JSONException e) {
                callbackContext.sendPluginResult(new PluginResult(
                        PluginResult.Status.ERROR, e.getMessage()));

            } catch (InterruptedException e) {
                callbackContext.sendPluginResult(new PluginResult(
                        PluginResult.Status.ERROR, e.getMessage()));
            } catch (UnsupportedEncodingException e) {
                callbackContext.sendPluginResult(new PluginResult(
                        PluginResult.Status.ERROR, e.getMessage()));
            }
            return true;
        }
        return false;
    }

    private void downloadAndOpenFile(final String fileUrl,
                                     final String dirName, final String fileName,
                                     final Boolean overwrite, CallbackContext callbackContext)
            throws InterruptedException, JSONException {

        Log.d("Downloader", "Downloader start");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("Downloader", "Downloading " + fileUrl + " into "
                            + dirName + "/" + fileName);

                    File dir = new File(dirName);
                    if (!dir.exists()) {
                        Log.d("Downloader", "directory " + dirName
                                + " created");
                        dir.mkdirs();
                    }
                    final File file = new File(dirName, fileName);

                    if (!overwrite && file.exists()) {
                        currentProgress = 100;
                        progresshHandler.sendEmptyMessage(0);

                        Intent intent = IntentFactory.getIntent(file);
                        Downloader.this.cordova.startActivityForResult(
                                Downloader.this, intent, 0);

                    }
                    URL url;
                    url = new URL(fileUrl);
                    HttpURLConnection ucon;
                    ucon = (HttpURLConnection) url.openConnection();
                    ucon.setRequestMethod("GET");
                    // ucon.setDoOutput(true);
                    ucon.connect();

                    InputStream is = ucon.getInputStream();
                    byte[] buffer = new byte[1024];
                    int readed = 0, progress = 0, totalReaded = 0, fileSize = ucon
                            .getContentLength();

                    FileOutputStream fos = new FileOutputStream(file);

                    while ((readed = is.read(buffer)) > 0) {

                        fos.write(buffer, 0, readed);
                        totalReaded += readed;

                        currentProgress = (int) (totalReaded * 100 / fileSize);
                        progresshHandler.sendEmptyMessage(0);

                    }
                    fos.close();

                    Log.d("Downloader", "Downloader finished");

                    Intent intent = IntentFactory.getIntent(file);
                    Downloader.this.cordova.startActivityForResult(
                            Downloader.this, intent, 0);
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                    Log.d("Downloader", "Error: " + e);
                }
            }
        }).start();

    }
}
