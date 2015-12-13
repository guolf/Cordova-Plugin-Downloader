package cn.guolf.cordova.plugins;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Author：guolf on 15/12/13 09:14
 * Email ：guo@guolingfa.cn
 */
public class IntentFactory {
    public static String fileType = "";

    public static Intent getIntent(File file) {
        Intent intent = null;
        String filePath = "/" + file.getPath();
        String fileType = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase().trim();
        if (fileType.equals("doc") || fileType.equals("docx")) {
            intent = getWordFileIntent(file);
        } else if (fileType.equals("html") || fileType.equals("htm") || fileType.equals("htl")) {
            intent = getHtmlFileIntent(filePath);
        } else if (fileType.equals("txt") || fileType.equals("c") || fileType.equals("cc") || fileType.equals("h")) {
            intent = getTextFileIntent(file); //no
        } else if (fileType.equals("apk")) {
            intent = getApkIntent(file);
        } else if (fileType.equals("pdf")) {
            intent = getPdfFileIntent(filePath);
        } else if (fileType.equals("ppt")) {
            intent = getPptFileIntent(filePath);
        } else if (fileType.equals("chm")) {
            intent = getChmFileIntent(filePath);
        } else if (fileType.equals("jpg") || fileType.equals("jpeg")
                || fileType.equals("png")
                || fileType.equals("gif") || fileType.equals("jpe")
                || fileType.equals("ras") || fileType.equals("pbm")
                || fileType.equals("ppm") || fileType.equals("xbm")
                || fileType.equals("xwd") || fileType.equals("ief")
                || fileType.equals("tif") || fileType.equals("tiff")
                || fileType.equals("pnm") || fileType.equals("pgm")
                || fileType.equals("rgb") || fileType.equals("xpm")) {
            intent = getImageFileIntent(file);
        } else if (fileType.equals("au") || fileType.equals("mp3")
                || fileType.equals("snd") || fileType.equals("wav")
                || fileType.equals("wma") || fileType.equals("aif")
                || fileType.equals("aiff") || fileType.equals("aifc")) {
            intent = getAudioFileIntent(filePath);
        } else if (fileType.equals("mpeg") || fileType.equals("avi")
                || fileType.equals("rm") || fileType.equals("rmvb")
                || fileType.equals("wmv") || fileType.equals("mpg")
                || fileType.equals("mpe") || fileType.equals("avi")
                || fileType.equals("qt") || fileType.equals("mov")
                || fileType.equals("moov") || fileType.equals("movie")) {
            intent = getVideoFileIntent(filePath);
        } else if (fileType.equals("xls") || fileType.equals("xlsx")) {
            intent = getExcelFileIntent(filePath);
        } else if (fileType.equals("rar")) {
            intent = getRarFileIntent(filePath);
        } else if (fileType.equals("zip")) {
            intent = getZipFileIntent(filePath);
        }
        return intent;
    }

    public static Intent getHtmlFileIntent(String param) {
        Uri uri = Uri.parse(param).buildUpon()
                .encodedAuthority("com.android.htmlfileprovider")
                .scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    public static Intent getImageFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    public static Intent getPdfFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    public static Intent getTextFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri2 = Uri.fromFile(file);
        intent.setDataAndType(uri2, "text/plain");
        return intent;
    }

    public static Intent getAudioFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    public static Intent getVideoFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    public static Intent getChmFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    public static Intent getWordFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    public static Intent getExcelFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    public static Intent getPptFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    public static Intent getApkIntent(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        return intent;
    }

    public static Intent getZipFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-rar-compressed");
        return intent;
    }

    public static Intent getRarFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/zip");
        return intent;
    }
}
