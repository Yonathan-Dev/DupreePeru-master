package com.imageperu.lib_image.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by marwuinh@gmail.com on 3/5/19.
 */

public class FileFromBitmap extends AsyncTask<Void, Integer, String> {
    private ResponseListener listener;
    private File file;
    private String path;
    private Bitmap bitmap;
    public FileFromBitmap(Bitmap bitmap, String path, ResponseListener listener) {
        this.path = path;
        this.bitmap = bitmap;
        this.listener = listener;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
        //si la quieres en disco-> file = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        file = new File(path);//en cache
        try {
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.flush();
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(listener !=null)
            listener.saveFile(file);
    }

    public interface ResponseListener {
        void saveFile(File file);
    }
}
