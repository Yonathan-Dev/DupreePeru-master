package com.dupreincaperu.dupree.mh_fragments_main;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dupreincaperu.dupree.mh_pasa_prod.dato_gene;
import com.imageperu.lib_image.MyProviderImage;
import com.dupreeinca.lib_api_rest.controller.UploadFileController;
import com.dupreeinca.lib_api_rest.model.dto.response.UrlCatalogoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.UrlsCatalogosDTO;
import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.dupreincaperu.dupree.CatalogoViewerActivity;
import com.dupreincaperu.dupree.MenuActivity;
import com.dupreincaperu.dupree.MenuListener;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentCatalogosBinding;
import com.dupreincaperu.dupree.mh_dialogs.ListString_Check;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog3Button;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog_01Button;
import com.dupreincaperu.dupree.mh_dialogs.SingleListDialog;
import com.dupreincaperu.dupree.mh_http.Http;
import com.dupreincaperu.dupree.mh_required_api.ListBoolean;
import com.dupreeinca.lib_api_rest.util.alert.DownloadFileAsyncTask;
import com.dupreincaperu.dupree.mh_utilities.PinchZoomImageView;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.dupreincaperu.dupree.Constants.MY_DEFAULT_TIMEOUT;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogosFragment extends BaseFragment {
    private final String TAG = CatalogosFragment.class.getName();

    private final String BROACAST_CATALOGOS_DOWNLOADING_STATUS = "broadcast_catalogos_downloading_status";
    public static final String BROACAST_CATALOGOS_DOWNLOADING_ERROR = "broadcast_catalogos_downloading_error";

    private UrlCatalogoDTO urlCatalogo;
    UrlsCatalogosDTO responseUrlCatalogos;
    private ImageLoader img;
    private FragmentCatalogosBinding binding;

    private UploadFileController fileController;

    //String URL_EMPRESA="";
    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;
    String code_cata_prim="", urls_port_prim="", nomb_cata_prim="", urls_pdfs_prim="", urls_cata_prim="";
    String code_cata_segu="", urls_port_segu="", nomb_cata_segu="", urls_pdfs_segu="", urls_cata_segu="";
    private ProgressDialog pdp = null;

    public CatalogosFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_catalogos;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentCatalogosBinding) view;

        //dato_gene URL = new dato_gene();
        //URL_EMPRESA   = URL.getURL_EMPRESA();
        request       = Volley.newRequestQueue(getContext());
        carga_catalogo();

        String jsonUrlCatalogos = dataStore.getJSON_UrlCatalodos();

        binding.appBar.toolbar.setNavigationIcon(null);
        binding.appBar.toolbar.setTitle("");

        if(menuListener == null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(binding.appBar.toolbar);//en main, tiene tootlbar propia
        }
        binding.appBar.toolbar.setVisibility(menuListener == null ? View.VISIBLE : View.GONE);

        if(jsonUrlCatalogos!=null) {
            /*responseUrlCatalogos = new Gson().fromJson(jsonUrlCatalogos, UrlsCatalogosDTO.class);
            binding.txtNameCampA.setText(responseUrlCatalogos.getCatalogos().get(0).getCatalogo1().getName());
            binding.txtNameCampB.setText(responseUrlCatalogos.getCatalogos().get(0).getCatalogo2().getName());
            binding.txtNumCampA.setText(responseUrlCatalogos.getCatalogos().get(0).getCatalogo1().getCode());
            binding.txtNumCampB.setText(responseUrlCatalogos.getCatalogos().get(0).getCatalogo2().getCode());

            img = ImageLoader.getInstance();
            img.init(PinchZoomImageView.configurarImageLoader(getActivity()));
            img.displayImage(responseUrlCatalogos.getCatalogos().get(0).getCatalogo1().getImage(), binding.imgCampA);
            img.displayImage(responseUrlCatalogos.getCatalogos().get(0).getCatalogo2().getImage(), binding.imgCampB);*/


            img = ImageLoader.getInstance();
            img.init(PinchZoomImageView.configurarImageLoader(getActivity()));
            img.displayImage("", binding.imgCampA);
            img.displayImage("", binding.imgCampB);

        }

        binding.imgCampA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ver online o descargar
                /*urlCatalogo = responseUrlCatalogos.getCatalogos().get(0).getCatalogo1();
                testOnlineDownload(urlCatalogo.getName());*/
                testOnlineDownload(nomb_cata_prim, urls_cata_prim, urls_pdfs_prim);
            }
        });

        binding.imgCampB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*urlCatalogo = responseUrlCatalogos.getCatalogos().get(0).getCatalogo2();
                testOnlineDownload(urlCatalogo.getName());*/
                testOnlineDownload(nomb_cata_segu, urls_cata_segu, urls_pdfs_segu);
            }
        });

        localBroadcastReceiver = new LocalBroadcastReceiver();

    }

    @Override
    protected void onLoadedView() {

    }

    public static CatalogosFragment newInstance() {
        Bundle args = new Bundle();
        
        CatalogosFragment fragment = new CatalogosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void showList(String title, List<String> data, String[] itemsSelected){
        ListString_Check dialogList = new ListString_Check();
        dialogList.loadData(title, data, itemsSelected, new ListString_Check.ListenerResult() {
            @Override
            public void result(ListBoolean listBoolean) {
                shareApp(listBoolean);
            }
        });
        dialogList.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    public void showListRadio(String title, List<ModelList> data, String itemsSelected){
        SingleListDialog d = new SingleListDialog();
        d.loadData(title, data, "", new SingleListDialog.ListenerResponse() {
            @Override
            public void result(ModelList item) {
                downloadPDF(item);
            }
        });
        d.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    private void testDownloadFile(){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(fileName, getString(R.string.desea_sobreescribir_archivo));
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    downloadFile();
            }
        });
        simpleDialog.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    public void testOnlineDownload(String titleCatalg){
        SimpleDialog3Button simpleDialog = new SimpleDialog3Button();
        simpleDialog.loadData(titleCatalg, getString(R.string.desea_ver_catalogo), getString(R.string.ver_previa_descarga), getString(R.string.dialog_view_online));
        simpleDialog.setListener(new SimpleDialog3Button.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status){
                    openCatalogoPDF(urlCatalogo.getPdf());
                } else {
                    viewCatalogOnline(urlCatalogo.getUrl());
                }
            }
        });
        simpleDialog.show(getChildFragmentManager(),"mDialog");
    }



    public void testOnlineDownload(String titleCatalg, String urls_cata, String urls_pdfs ){
        SimpleDialog3Button simpleDialog = new SimpleDialog3Button();
        simpleDialog.loadData(titleCatalg, getString(R.string.desea_ver_catalogo), getString(R.string.ver_previa_descarga), getString(R.string.dialog_view_online));
        simpleDialog.setListener(new SimpleDialog3Button.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status){
                    try {
                        openCatalogoPDF(urls_pdfs);
                    } catch (Exception e){
                        msgToast("PDF no disponible");
                    }
                } else {
                    try {
                        viewCatalogOnline(urls_cata);
                    } catch (Exception e){
                        msgToast("Catalogo no disponible");
                    }
                }
            }
        });
        simpleDialog.show(getChildFragmentManager(),"mDialog");
    }

    public void showMessageAlert(int iconId, String title, String msg){
        SimpleDialog_01Button simpleDialog = new SimpleDialog_01Button();
        simpleDialog.loadData(iconId, title, msg);
        //simpleDialog.activateBroadcast(TAG, BROACAST_CATALOGOS_DECIDE_DOWNLOAD_ONLINE);
        simpleDialog.show(getChildFragmentManager(),"mDialog");
    }



    public void sharedCatalog(){
        List<String> data = new ArrayList<>();
        /*data.add(binding.txtNameCampB.getText().toString());
        data.add(binding.txtNameCampA.getText().toString());*/

        data.add(nomb_cata_segu);
        data.add(nomb_cata_prim);

        showList(getString(R.string.share), data, null);
    }

    public void downloadCatalog(){
        List<ModelList> data = new ArrayList<>();
        /*data.add(new ModelList(0, binding.txtNameCampB.getText().toString()));
        data.add(new ModelList(1, binding.txtNameCampA.getText().toString()));*/
        data.add(new ModelList(0, nomb_cata_segu));
        data.add(new ModelList(1, nomb_cata_prim));

        showListRadio(getString(R.string.download), data, null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share_download_cat, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuDownload:
                downloadCatalog();
                break;
            case R.id.menuShare:
                sharedCatalog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerBroadcat();
        Log.i(TAG,"onResume()");
        //setSelectedItem(oldItem);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterBroadcat();
        Log.i(TAG,"onPause()");
    }

    public void registerBroadcat(){
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                localBroadcastReceiver,
                new IntentFilter(TAG));
    }

    public void unregisterBroadcat(){
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                localBroadcastReceiver);
    }

    private long lastTime=0;
    private BroadcastReceiver localBroadcastReceiver;
    private class LocalBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // safety check
            Log.i(TAG, "BroadcastReceiver");
            if (intent == null || intent.getAction() == null) {
                return;
            }

            if(Calendar.getInstance().getTimeInMillis()-lastTime<500) {// si no ha pasado medio segundo puede ser un rebote
                Log.e(TAG,"intentRepeat()");
                return;
            }

            lastTime=Calendar.getInstance().getTimeInMillis();

            if (intent.getAction().equals(TAG)) {
                switch (intent.getStringExtra(TAG)) {//pregunta cual elemento envio este broadcast
                    case BROACAST_CATALOGOS_DOWNLOADING_STATUS:
                        String jsonDownloading = intent.getStringExtra(Http.BROACAST_DATA);
                        if(jsonDownloading!=null){
                            binding.progress.setProgress(Integer.parseInt(jsonDownloading));
                        }
                        break;
                    case BROACAST_CATALOGOS_DOWNLOADING_ERROR:
                        binding.progress.setProgress(0);
                        String error = intent.getStringExtra(Http.BROACAST_DATA);
                        if(error!=null){
                            showMessageAlert(R.drawable.ic_notifications_black_24dp, "Error!", "Ocurrió un error descargando el catálogo. Debe verificar su conexión a internet y reanudar la descarga.");
                            //msgToast("Ocurrio un error descargando el archivo: "+error);
                        }
                        break;
                }
            }
        }
    }

    private void shareApp(ListBoolean listBoolean){
        boolean share_on = false;
        String share="Me gustaría compartir:\n\n";


        for(int i = 0; i < listBoolean.getBooleanList().size(); i++) {
            if (listBoolean.getBooleanList().get(i)){
                //problemas de la forma de enviar la data, no lo hace por arreglos
                switch (i){
                    case 0:
                        /*share += responseUrlCatalogos.getCatalogos().get(0).getCatalogo2().getName()
                                .concat("\n").concat(responseUrlCatalogos.getCatalogos().get(0).getCatalogo2().getUrl().concat("\n\n"));*/

                        share += nomb_cata_segu.concat("\n").concat(urls_cata_segu.concat("\n\n"));

                        break;
                    case 1:
                        /*share += responseUrlCatalogos.getCatalogos().get(0).getCatalogo1().getName()
                                .concat("\n").concat(responseUrlCatalogos.getCatalogos().get(0).getCatalogo1().getUrl().concat("\n\n"));*/

                        share += nomb_cata_prim.concat("\n").concat(urls_cata_prim.concat("\n\n"));
                        break;
                }

                share_on = true;
            }
        }

        if(share_on) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, share);
            startActivity(Intent.createChooser(intent, "Compartir con"));
            Log.e(TAG, "Compartir");
        }
    }

    private void downloadPDF(ModelList item){
        switch (item.getId()){
            case 0:
                //url = responseUrlCatalogos.getCatalogos().get(0).getCatalogo2().getPdf();
                url = urls_pdfs_segu;
                permissionPDF();
                break;
            case 1:
                //url = responseUrlCatalogos.getCatalogos().get(0).getCatalogo1().getPdf();
                url = urls_pdfs_prim;
                permissionPDF();
                break;
        }
    }

    String url;
    public static final int CONST_PERMISSION_WRITE=123;
    @AfterPermissionGranted(CONST_PERMISSION_WRITE)
    private void permissionPDF() {
        //private void connect(int mode, String roomId, String nameCall, String numberCall, String idDevice) {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            //hacer
            Log.e(TAG,"----------------------------------");
            trydownloadFile(url);
        } else {
            EasyPermissions.requestPermissions(this, "Necesita habilitar permisos", CONST_PERMISSION_WRITE, perms);
        }
    }

    /**
     * Respuestas a permisos
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private String fileName;
    public void trydownloadFile(String url){
        Log.e(TAG, "trydownloadFile, try: "+url);
        //EXAMPLE URL "https://alcor.dupree.co/image/banner/catalogos400x300.jpg"
        String[] separate = url.split("/");//divide trama en un arreglo separado por "/"
        fileName = separate[separate.length-1];

        String pathFiles = dataStore.getPathFiles();
        if(pathFiles!=null) {
            String pathFileCurrent = pathFiles.concat("/").concat(fileName);
            File file = new File(pathFileCurrent);
            if(file.exists()){//ya la ruta existe
                Log.e(TAG,pathFileCurrent+", ya existe");
                testDownloadFile();
                return;
            }
        }

        downloadFile();

    }

    private void downloadFile(){
        new Http(getActivity()).downloadFile(TAG, BROACAST_CATALOGOS_DOWNLOADING_STATUS, BROACAST_CATALOGOS_DOWNLOADING_ERROR, url, fileName, DownloadFileAsyncTask.DIRECTORY_DOCUMENTS/*"https://alcor.dupree.co/image/banner/catalogos400x300.jpg"*/);
    }

    private void openCatalogoPDF(String url){
        Log.e(TAG, "openCatalogoPDF, try: "+url);
        //url = responseUrlCatalogos.getCatalogos().get(0).getCatalogo2().getUrl();
        String[] separate = url.split("/");//divide trama en un arreglo separado por "/"
        String fileName = separate[separate.length-1];


        String pathFiles = dataStore.getPathFiles();
        Log.e(TAG, "pathFiles, "+pathFiles);
        if(pathFiles!=null) {
            String pathFileCurrent = pathFiles.concat("/").concat(fileName);
            File file = new File(pathFileCurrent);
            if(file.exists()){//ya la ruta existe
                Log.e(TAG,pathFileCurrent+", ya existe");
                //testDownloadFile();
                //return;

                Log.i("PROVIDER", MyProviderImage.PROVIDER.getKey() + ".provider");

                Uri internal = FileProvider.getUriForFile(getActivity(), MyProviderImage.PROVIDER.getKey() + ".provider", file);

                Log.e(TAG, "file: "+new Gson().toJson(file));
                Log.e(TAG, "internal: "+new Gson().toJson(internal));

//                Uri internal = Uri.fromFile(file);
                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(internal, "application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                target = Intent.createChooser(target, "Open File");
                try {
                    startActivity(target);
                } catch (ActivityNotFoundException e) {
                    msgToast("Error! PDF viewer");
                }
//                startActivity(target);
            } else {
                msgToast("Debe descargar el catálogo");
            }
        } else {
            msgToast("Debe descargar el catálogo");
        }
    }


    private void carga_catalogo() {

        String url = getString(R.string.url_empr)+"catalogo/cata?codi_pais=PER";
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                try {
                    for (int i =0; i<response.length(); i++){
                        JSONObject data = response.getJSONObject(i);
                        if (i==0){
                            nomb_cata_prim = data.getString("nomb_cata");
                            code_cata_prim = data.getString("code_cata");
                            urls_port_prim = data.getString("urls_port");
                            urls_cata_prim = data.getString("urls_cata");
                            urls_pdfs_prim = data.getString("urls_pdfs");
                        } else{
                            nomb_cata_segu = data.getString("nomb_cata");
                            code_cata_segu = data.getString("code_cata");
                            urls_port_segu = data.getString("urls_port");
                            urls_cata_segu = data.getString("urls_cata");
                            urls_pdfs_segu = data.getString("urls_pdfs");
                        }
                    }
                    binding.txtNameCampA.setText(nomb_cata_prim);
                    binding.txtNameCampB.setText(nomb_cata_segu);
                    binding.txtNumCampA.setText(code_cata_prim);
                    binding.txtNumCampB.setText(code_cata_segu);
                    img = ImageLoader.getInstance();
                    if (!PinchZoomImageView.configurarImageLoader(getActivity()).equals(null))
                        img.init(PinchZoomImageView.configurarImageLoader(getActivity()));

                    img.displayImage(urls_port_prim, binding.imgCampA);
                    img.displayImage(urls_port_segu, binding.imgCampB);
                    Picasso.get().load(urls_port_prim).into(binding.imgCampA);
                    Picasso.get().load(urls_port_segu).into(binding.imgCampB);

                } catch (JSONException e) {
                    e.printStackTrace();
                    pdp.dismiss();
                }
                pdp.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                //Toast.makeText(getContext(),""+error, Toast.LENGTH_SHORT).show();
            }
        });

        pdp = new ProgressDialog(getContext());
        pdp.show();
        pdp.setContentView(R.layout.progress_dialog);
        pdp.setCancelable(false);
        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);
    }

    private void viewCatalogOnline(String urlFile){
        Intent intenCatalog = new Intent(getActivity(), CatalogoViewerActivity.class);
        intenCatalog.putExtra(CatalogoViewerActivity.URL_FILE, urlFile);
        startActivity(intenCatalog);
    }

    public Toolbar getToolbar() {
        return binding.appBar.toolbar;
    }

    MenuListener menuListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
        if (context instanceof MenuActivity) {
            menuListener = (MenuActivity) context;
        } else {
            //throw new RuntimeException(context.toString().concat(" is not OnInteractionActivity"));
        }

    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach()");
        setHasOptionsMenu(false);
    }
}
