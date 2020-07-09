package com.image.lib_image.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ConvertUtil {
    private static final String TAG = ConvertUtil.class.getName();
    private static String part1;
    private static String part2;
    private static RelativeLayout.LayoutParams lp;

    public static void getSplitString(String text, String regex) {
        String[] parts = text.split(regex);
        part1 = parts[0];
        part2 = parts[1];
    }

    public static String firstLetterUpper(String convert) {
        Character c = Character.toUpperCase(convert.charAt(0));
        return c + convert.substring(1);
    }

    public static String getPart1() {
        return part1;
    }

    public static String getPart2() {
        return part2;
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String[] validateURL(String... urls) {
        String[] nameUrl = new String[urls.length];
        for (int i = 0; i < urls.length; i++) {
            URI cn = URI.create(urls[i]);
            nameUrl[i] = cn.toASCIIString();
        }
        return nameUrl;
    }

    public static String[] URIRemoveSpaces(String... urls) {
        String[] validateURL = new String[urls.length];
        for (int i = 0; i < urls.length; i++) {
            if (urls[i] == null)
                validateURL[i] = "";
            else
                validateURL[i] = urls[i].replaceAll(" ", "%20").trim();
        }
        return validateURL;
    }

    @NonNull
    public static RelativeLayout.LayoutParams getLayoutParams(Context context, int leftParam, int topParam,
                                                              int rightParam, int bottomParam, Integer height, Integer width) {

        if (height != null && width == null) {
            int heightRelative = ConvertUtil.dpToPx(context, height);
            lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, heightRelative);
        } else if (height != null && width != null) {
            int heightRelative = ConvertUtil.dpToPx(context, height);
            lp = new RelativeLayout.LayoutParams(width, heightRelative);
        } else
            lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int left = ConvertUtil.dpToPx(context, leftParam);
        int top = ConvertUtil.dpToPx(context, topParam);
        int right = ConvertUtil.dpToPx(context, rightParam);
        int bottom = ConvertUtil.dpToPx(context, bottomParam);
        lp.setMargins(left, top, right, bottom);
        return lp;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap rotateSamsung(Context inContext, Uri imageUri){
        try {
            Bitmap b = SamsungCamera.handleSamplingAndRotationBitmap(inContext, imageUri);
            return b;
        } catch (IOException | NullPointerException e) {
            Log.e(TAG, "error to uri image");
            return null;
        }
    }
    // Bind views //////////////////////////////////////////////////////////////////////////////////

    //la guara en Galeria
    public static Uri createNewUri(Context context, Bitmap.CompressFormat format) {
        long currentTimeMillis = System.currentTimeMillis();
        Date today = new Date(currentTimeMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String title = dateFormat.format(today);
        String dirPath = getDirPath();
        String fileName = "scv" + title + "." + getMimeType(format);
        String path = dirPath + "/" + fileName;
        File file = new File(path);

        Uri uri = null;
        uri = Uri.fromFile(file);


        return uri;
    }


    public static File getPath(Context ctx, Uri uri)
    {
        String result;
        Cursor cursor = ctx.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);//DATA --> Deprecator
            result = cursor.getString(idx);
            cursor.close();
        }

        return new File(result);
    }


    public static String getMimeType(Bitmap.CompressFormat format) {
        Log.i("getMimeType", " CompressFormat = "+ format);
        switch (format) {
            case JPEG:
                return "jpeg";
            case PNG:
                return "png";
        }
        return "png";
    }

    //Crea una carpeta locla en el telefono
    public static String getDirPath() {
        String dirPath = "";
        File imageDir = null;
        File extStorageDir = Environment.getExternalStorageDirectory();
        if (extStorageDir.canWrite()) {
            imageDir = new File(extStorageDir.getPath() + "/simplecropview");
        }
        if (imageDir != null) {
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }
            if (imageDir.canWrite()) {
                dirPath = imageDir.getPath();
            }
        }
        return dirPath;
    }

//    public static File getFileAudio(Uri uri){
//        try {
//            AssetFileDescriptor videoAsset = BaseAPP.getContext().getContentResolver()
//                    .openAssetFileDescriptor(uri, "r");
//            FileInputStream fis;
//
//            fis = videoAsset.createInputStream();
//
//            File root = new File(Environment
//                    .getExternalStorageDirectory().getAbsolutePath() + "/simpleaudio/", "AUDIO"); // you
//            if (!root.exists()) {
//                root.mkdirs();
//            }
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
//                    .format(new Date());
//            File file;
//            file = new File(root.getPath() + "/" + "AUD_" + timeStamp
//                    + ".mp3");
//            FileOutputStream fos = new FileOutputStream(file);
//
//            byte[] buf = new byte[1024];
//            int len;
//            while ((len = fis.read(buf)) > 0) {
//                fos.write(buf, 0, len);
//            }
//            fis.close();
//            fos.close();
//            Uri fileuri = Uri.fromFile(file);
//            System.out.println("you Audio path"+fileuri.getPath());
//
//            return file;
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static String[] removeNull(String... text) {
        String[] texts = new String[text.length];
        for (int i = 0; i < text.length; i++) {
            texts[i] = text[i] == null ? "" : text[i];
        }
        return texts;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        String debugTag = "MemoryInformation";
        // Image nin islenmeden onceki genislik ve yuksekligi
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(debugTag,"image height: "+height+ "---image width: "+ width);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(debugTag,"inSampleSize: "+inSampleSize);
        return inSampleSize;
    }

    public static Bitmap resizeAndCompressImageBeforeSend(Context context, String filePath, String fileName){
        final int MAX_IMAGE_SIZE = 1 * 1024 * 1024; // max final file size in kilobytes

        // First decode with inJustDecodeBounds=true to check dimensions of image
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath,options);

        // Calculate inSampleSize(First we are going to resize the image to 800x800 image, in order to not have a big but very low quality image.
        //resizing the image will already reduce the file size, but after resizing we will check the file size and start to compress image
        options.inSampleSize = calculateInSampleSize(options, 800, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig= Bitmap.Config.ARGB_8888;

        Bitmap bmpPic = BitmapFactory.decodeFile(filePath,options);


        int compressQuality = 100; // quality decreasing by 5 every loop.
        int streamLength;
        do{
            ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
            Log.d("compressBitmap", "Quality: " + compressQuality);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
            byte[] bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
            compressQuality -= 5;
            Log.d("compressBitmap", "Size: " + streamLength/1024+" kb");
        }while (streamLength >= MAX_IMAGE_SIZE);

        try {
            //save the resized and compressed file to disk cache
            Log.d("compressBitmap","cacheDir: "+context.getCacheDir());
            FileOutputStream bmpFile = new FileOutputStream(context.getCacheDir()+fileName);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile);
            bmpFile.flush();
            bmpFile.close();
        } catch (Exception e) {
            Log.e("compressBitmap", "Error on saving file");
        }
        //return the path of resized and compressed file
        return  bmpPic;//context.getCacheDir()+fileName;
    }

    public static Bitmap resizeAndCompressImageBeforeSend(Context context, Uri fileUri, Bitmap.CompressFormat mCompressFormat){
        String filePath = fileUri.getPath();
        String fileName = "scv" + "evymrc" + "." + getMimeType(mCompressFormat);
        //OLD
        //String path = dirPath + "/" + fileName;
        //File file = new File(resizeAndCompressImageBeforeSend(context, filePath, fileName));


        //return Uri.fromFile(file);
        return resizeAndCompressImageBeforeSend(context, filePath, fileName);
    }
}