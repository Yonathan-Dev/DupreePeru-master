package com.dupreincaperu.dupree.mh_fragments_menu;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.imageperu.lib_image.MyProviderImage;
import com.dupreeinca.lib_api_rest.controller.CatalogosController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemFolleto;
import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.dupreincaperu.dupree.CatalogoViewerActivity;
import com.dupreincaperu.dupree.MenuActivity;
import com.dupreincaperu.dupree.MenuListener;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentCatalogoPremiosBinding;
import com.dupreincaperu.dupree.mh_adapters.CatalogoPremiosListAdapter;
import com.dupreincaperu.dupree.mh_dialogs.ListString_Check;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog3Button;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog_01Button;
import com.dupreincaperu.dupree.mh_dialogs.SingleListDialog;
import com.dupreincaperu.dupree.mh_holders.CatalogoPremiosHolder;
import com.dupreincaperu.dupree.mh_http.Http;
import com.dupreincaperu.dupree.mh_required_api.ListBoolean;
import com.dupreeinca.lib_api_rest.model.dto.response.CatalogoPremiosList;
import com.dupreeinca.lib_api_rest.util.alert.DownloadFileAsyncTask;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogoPremiosFragment extends BaseFragment implements CatalogoPremiosHolder.Events{
    private String TAG = CatalogoPremiosFragment.class.getName();
    private FragmentCatalogoPremiosBinding binding;
    private CatalogosController catalogosController;

    private final String BROACAST_CATALOGOS_DOWNLOADING_STATUS = "broadcast_catalogos_downloading_status_advance";
    public static final String BROACAST_CATALOGOS_DOWNLOADING_ERROR="broadcast_catalogos_downloading_error_advance";

    public CatalogoPremiosFragment() {
        // Required empty public constructor
    }

    private List<ItemFolleto> list;
    private CatalogoPremiosListAdapter listAdapter;
    private StaggeredGridLayoutManager mLayoutManager;

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_catalogo_premios;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentCatalogoPremiosBinding) view;

        binding.appBar.toolbar.setNavigationIcon(null);
        binding.appBar.toolbar.setTitle("");

        if(menuListener == null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(binding.appBar.toolbar);//en main, tiene tootlbar propia
        }
        binding.appBar.toolbar.setVisibility(menuListener == null ? View.VISIBLE : View.GONE);

        binding.recycler.setHasFixedSize(true);

        list = new ArrayList<>();
        listAdapter = new CatalogoPremiosListAdapter(list, this);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.recycler.setLayoutManager(mLayoutManager);
        binding.recycler.setAdapter(listAdapter);

        localBroadcastReceiver = new LocalBroadcastReceiver();
    }

    @Override
    protected void onLoadedView() {
        catalogosController = new CatalogosController(getContext());
        obtainCatalogoPremios();
    }

    @Override
    public void onClickRoot(ItemFolleto dataRow, int row) {
        testOnlineDownload(dataRow);
    }

    private void obtainCatalogoPremios(){
        showProgress();
        catalogosController.getCatalogoPremios(new TTResultListener<CatalogoPremiosList>() {
            @Override
            public void success(CatalogoPremiosList result) {
                dismissProgress();

                list.clear();
                list.addAll(result.getFolleto());
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
    }

    public void sharedCatalog(){
        List<String> data = new ArrayList<>();
        for(int i = 0; i< list.size(); i++){
            data.add(list.get(i).getName());
        }

        Log.e(TAG, new Gson().toJson(data));
        showList(getString(R.string.share), data, null);
    }

    public void downloadCatalog(){
        List<ModelList> data = new ArrayList<>();
        for(int i = 0; i< list.size(); i++){
            data.add(new ModelList(0, list.get(i).getName()));
        }

        showListRadio(getString(R.string.download), data, null);
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
        d.loadData(title, data, itemsSelected, new SingleListDialog.ListenerResponse() {
            @Override
            public void result(ModelList item) {
                downloadPDF(item);
            }
        });
        d.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.e(TAG, "onOptionsItemSelected");
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_share_download_cat, menu);
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
                share += list.get(i).getName()
                        .concat("\n").concat(list.get(i).getUrl().concat("\n\n"));
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
        if(item.getId()>-1 && item.getId()< list.size()){
            url = list.get(item.getId()).getPdf();
            permissionPDF();
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
        //url = responseUrlCatalogos.getCatalogos().get(0).getCatalogo3().getUrl();
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

                //String filename = "blabla.pdf";
                //File file = new File(filename);
                Uri internal = FileProvider.getUriForFile(getActivity(), MyProviderImage.PROVIDER.getKey() + ".provider", file);
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
                startActivity(target);
            } else {
                msgToast("Debe descargar el catálogo");
            }
        } else {
            msgToast("Debe descargar el catálogo");
        }
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

    public void testOnlineDownload(ItemFolleto dataRow){
        SimpleDialog3Button simpleDialog = new SimpleDialog3Button();
        simpleDialog.loadData(dataRow.getName(), getString(R.string.desea_ver_catalogo), getString(R.string.ver_previa_descarga), getString(R.string.dialog_view_online));
        simpleDialog.setListener(new SimpleDialog3Button.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status){
                    if (!dataRow.getPdf().equalsIgnoreCase(""))
                    openCatalogoPDF(dataRow.getPdf());
                }
                else{
                    if (!dataRow.getUrl().equalsIgnoreCase(""))
                    viewCatalogOnline(dataRow.getUrl());
                }
            }
        });
        simpleDialog.show(getChildFragmentManager(),"mDialog");
    }

    public void showMessageAlert(int iconId, String title, String msg){
        SimpleDialog_01Button simpleDialog = new SimpleDialog_01Button();
        simpleDialog.loadData(iconId, title, msg);
        simpleDialog.show(getChildFragmentManager(),"mDialog");
    }

    private void viewCatalogOnline(String urlFile){
        Intent intenCatalog = new Intent(getActivity(), CatalogoViewerActivity.class);
        intenCatalog.putExtra(CatalogoViewerActivity.URL_FILE, urlFile);
        startActivity(intenCatalog);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    MenuListener menuListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MenuActivity) {
            setHasOptionsMenu(true);
            menuListener = (MenuActivity) context;
        } else {
            throw new RuntimeException(context.toString().concat(" is not OnInteractionActivity"));
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach()");
        setHasOptionsMenu(false);
    }
}
