package com.dupreeinca.lib_api_rest.util.alert;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.dupreeinca.lib_api_rest.util.preferences.DataStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

import okhttp3.ResponseBody;

/**
 * Created by marwuinh@gmail.com on 3/9/17.
 */

public class DownloadFileAsyncTask extends AsyncTask<ResponseBody, String, Boolean> {

    private Context ctx;
    private DataStore ds;
    public static final String TAG = "DownloadFileAsyncTask";
    public static  final String BROACAST_DATA="broacast_data";
    public static final String DIRECTORY_PINTURES = Environment.DIRECTORY_PICTURES;
    public static final String DIRECTORY_DOCUMENTS = Environment.DIRECTORY_DOCUMENTS;
    private String directoryFile;

    private final String appDirectoryName = "Dupree";
    private final File fileRoot = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), appDirectoryName);
    //final String filename = "catalogos400x3000.jpg";
    private final String filename;

    ProgressDialogHorizontal dialogHorizontal;
    //se trSE ESTANDARIZO EL TIPO DE DOCUMENTP

    private String tagFragment, objectFragment, error;
    public DownloadFileAsyncTask(Context ctx, String tagFragment, String objectFragment, String error, ProgressDialogHorizontal dialogHorizontal, String filename, String directoryFile) {
        this.ctx = ctx;
        this.tagFragment = tagFragment;
        this.objectFragment = objectFragment;
        this.error = error;
        this.dialogHorizontal = dialogHorizontal;
        this.filename = filename;
        this.directoryFile = directoryFile;

        ds = DataStore.getInstance(ctx);
    }

    //private long fileSize=0;
    @Override
    protected Boolean doInBackground(ResponseBody... params) {

        InputStream inputStream = params[0].byteStream();

        //INTENTO PARA CONECER TAMANIO DEL ARCHIVO
        final long fileSize_b = params[0].contentLength();
        Log.e("fileSize", String.valueOf(size(fileSize_b)));
        long currentSize=0;
        //INTENTO PARA CONECER TAMANIO DEL ARCHIVO

        Log.e("fileRoot", fileRoot.getAbsolutePath());
        ds.setPathFiles(fileRoot.getAbsolutePath());

        File file = createNewFile(fileRoot, filename);

        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
            byte[] buffer = new byte[1024]; // or other buffer size
            int read;
            Log.d(TAG, "Attempting to write to: " + fileRoot + "/" + filename);

            while ((read = inputStream.read(buffer)) != -1) {
                //CONTROLANDO ESTADO DE LA TRANSFERENCIA MH
                currentSize+=read;
                //Log.e("currentSize", String.valueOf(size(currentSize)));
                double percentNum = (currentSize * 100.0) / fileSize_b;
                long progressInt = Math.round(percentNum);

                String percent = String.valueOf(progressInt);
                //Log.e("porcent", String.valueOf(Math.round(porcent)).concat("%"));
                //CONTROLANDO ESTADO DE LA TRANSFERENCIA MH

                output.write(buffer, 0, read);
                Log.v(TAG, "Writing to buffer to output stream.");
                publishProgress(percent);
            }
            Log.d(TAG, "Flushing output stream.");
            output.flush();
            Log.d(TAG, "Output flushed.");
        } catch (IOException e) {
            dialogHorizontal.getpDialog().dismiss();
            Log.e(TAG, "IO Exception: " + e.getMessage());

            //elimina lo descargado xq esta incompleto
            Log.e(TAG, "IO Exception: " + "deleteFile(file) init: "+file.getAbsolutePath());
            deleteFile(file);
            Log.e(TAG, "IO Exception: " + "deleteFile(file) end: "+file.getAbsolutePath());

            //eror descargando el archivo
            //msgToast("Error descargando el archivo"+filename+e.getMessage());
            publishError(tagFragment, /*e.getMessage()*/error);


            e.printStackTrace();
            return false;
        } finally {
            try {
                if (output != null) {
                    output.close();
                    Log.d(TAG, "Output stream closed sucessfully.");
                }
                else{
                    Log.d(TAG, "Output stream is null");
                }
            } catch (IOException e){
                Log.e(TAG, "Couldn't close output stream: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        Log.d(TAG, "Download success: " + result);
        // TODO: show a snackbar or a toast
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        dialogHorizontal.getpDialog().setProgress(Integer.parseInt(values[0]));
        publishResult(values[0]);

        Log.e("porcent", values[0]);
    }

    public String size(long size) {
        String hrSize = "";
        long b = size;
        double B = size / 8.0;
        double KB = size / 1024.0;
        double MB = size / 1048576.0;

        DecimalFormat dec = new DecimalFormat("0.00");

        if (MB > 1) {
            hrSize = dec.format(MB).concat("MB");
        }else if (KB > 1) {
            hrSize = dec.format(KB).concat("KB");
        }else if (B > 1) {
            hrSize = dec.format(B).concat("B");
        }else if (b > 1) {
            hrSize = dec.format(b).concat("b");
        }

        return hrSize;
    }

    private File createNewFile(File fileRoot, String filename){

        if(!fileRoot.exists()){
            if(fileRoot.mkdir()){
                Log.d(TAG, fileRoot.getAbsolutePath()+" directory created");
            }
        }
        File file = new File(fileRoot,filename);
        if(file.exists()){
            //this wont be executed
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    private void deleteFile(File file){
        if(file.exists()){
            file.delete();
        }
    }

    private void msgToast(String msg){
        Log.e("onError", msg);
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    private void publishResult(String data){
        Log.i(TAG,"publishResult: "+data);
        LocalBroadcastManager.getInstance(ctx).sendBroadcast(
                new Intent(tagFragment)
                        .putExtra(tagFragment, objectFragment)
                        .putExtra(BROACAST_DATA, data));

    }

    private void publishError(String tagFragment, String error){
        Log.i(TAG,"publishResult: "+error);
        LocalBroadcastManager.getInstance(ctx).sendBroadcast(
                new Intent(tagFragment)
                        .putExtra(tagFragment, error)
                        .putExtra(BROACAST_DATA, error));
    }
}