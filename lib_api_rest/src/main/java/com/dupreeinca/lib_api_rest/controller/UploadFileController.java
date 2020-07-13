package com.dupreeinca.lib_api_rest.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.dupreeinca.lib_api_rest.R;
import com.dupreeinca.lib_api_rest.controller.base.TTGenericController;
import com.dupreeinca.lib_api_rest.dao.UploadFilesDAO;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreeinca.lib_api_rest.util.alert.DownloadFileAsyncTask;
import com.dupreeinca.lib_api_rest.util.alert.ProgressDialogHorizontal;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static com.dupreeinca.lib_api_rest.util.alert.DownloadFileAsyncTask.BROACAST_DATA;


public class UploadFileController extends TTGenericController {

    public UploadFileController(Context context) {
        super(context);
    }

    public void uploadImage(File file, final TTResultListener<GenericDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        RequestBody reqFile = RequestBody.create(MediaType.parse("imageperu/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imagen", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

        UploadFilesDAO dao = new UploadFilesDAO(getContext());
        dao.uploadImage(body, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }

    public void uploadImage(String pathFile, final TTResultListener<GenericDTO> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        File file = new File(pathFile);
        RequestBody reqFile = RequestBody.create(MediaType.parse("imageperu/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imagen", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

        UploadFilesDAO dao = new UploadFilesDAO(getContext());
        dao.uploadImage(body, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                listener.error(error);
            }
        });
    }

    public void getFile(final String tagFragment, final String objectFragment, final String error, String urlFile, final String fileName, final String fileDirectory, final TTResultListener<ResponseBody> listener){
        if(!this.isNetworkingOnline(getContext())){
            listener.error(TTError.errorFromMessage(context.getResources().getString(R.string.http_datos_no_disponibles)));
            return;
        }

        final ProgressDialogHorizontal dialogHorizontal = new ProgressDialogHorizontal(context);
        dialogHorizontal.showProgressDialog("Descargando archivo");

        UploadFilesDAO dao = new UploadFilesDAO(getContext());
        dao.getFile(urlFile, new TTResultListener<ResponseBody>() {
            @Override
            public void success(ResponseBody result) {
                DownloadFileAsyncTask downloadFileAsyncTask = new DownloadFileAsyncTask(context, tagFragment, objectFragment, error, dialogHorizontal, fileName, fileDirectory);
                downloadFileAsyncTask.execute(result);

//                listener.success(result);
            }

            @Override
            public void error(TTError error) {
                publishError(tagFragment, error.getMessage());

                dialogHorizontal.dismissProgressDialog();
                listener.error(error);
            }
        });
    }


    private void publishError(String tagFragment, String error){
        LocalBroadcastManager.getInstance(context).sendBroadcast(
                new Intent(tagFragment)
                        .putExtra(tagFragment, error)
                        .putExtra(BROACAST_DATA, error));
    }



}
