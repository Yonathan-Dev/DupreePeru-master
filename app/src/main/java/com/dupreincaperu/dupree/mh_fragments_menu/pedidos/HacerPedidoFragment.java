package com.dupreincaperu.dupree.mh_fragments_menu.pedidos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dupreeinca.lib_api_rest.controller.BannerController;
import com.dupreeinca.lib_api_rest.controller.PedidosController;
import com.dupreeinca.lib_api_rest.enums.EnumLiquidar;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.request.LiquidarProducto;
import com.dupreeinca.lib_api_rest.model.dto.request.LiquidarSend;
import com.dupreeinca.lib_api_rest.model.dto.response.EstadoPedidoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.LiquidarDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ListProductCatalogoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ProductCatalogoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ResultEdoPedido;
import com.dupreeinca.lib_api_rest.model.dto.response.realm.Catalogo;
import com.dupreeinca.lib_api_rest.model.dto.response.realm.ItemCarrito;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.dupreincaperu.dupree.FullscreenActivity;
import com.dupreincaperu.dupree.MenuActivity;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentPanelAsesoraBinding;
import com.dupreincaperu.dupree.databinding.FragmentPanelAsesoraBindingImpl;
import com.dupreincaperu.dupree.databinding.FragmentPedidosHacerBinding;
import com.dupreincaperu.dupree.mh_adapters.CatalogoListAdapter;
import com.dupreincaperu.dupree.mh_adapters.IncorporacionPagerAdapter;
import com.dupreincaperu.dupree.mh_adapters.NuevasPagerAdapter;
import com.dupreincaperu.dupree.mh_adapters.PedidosPagerAdapter;
import com.dupreincaperu.dupree.mh_adapters.base.TabViewPager.TabManagerFragment;
import com.dupreincaperu.dupree.mh_dial_peru.cuadro_confirma;
import com.dupreincaperu.dupree.mh_dial_peru.dialogoPedido;
import com.dupreincaperu.dupree.mh_dialogs.InputDialog;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog;
import com.dupreincaperu.dupree.mh_fragments_asesora.Fragmento_repo_ases_deud;
import com.dupreincaperu.dupree.mh_fragments_cobranza.Fragmento_ubic_ases;
import com.dupreincaperu.dupree.mh_fragments_distribucion.Fragmento_geoc_inve;
import com.dupreincaperu.dupree.mh_fragments_distribucion.Fragmento_proc_dist_conf_manu;
import com.dupreincaperu.dupree.mh_fragments_main.CatalogosAdvanceFragment;
import com.dupreincaperu.dupree.mh_fragments_main.CatalogosFragment;
import com.dupreincaperu.dupree.mh_fragments_main.ContactFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.BandejaEntradaFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.CatalogoPremiosFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.ConfigModPerfilFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.IncentivosConsultaPtosFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.IncentivosRedimirFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.IncentivosReferidoFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.PanelGerenteFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.PedidosEdoPedidoFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.PedidosFaltantesFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.ReporteRetenidosFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.Servicios_PagosOnLine_Fragment;
import com.dupreincaperu.dupree.mh_fragments_menu.UbicacionFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.Incorp_Nuevas_Fragment;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.Incorp_Todos_Fragment;
import com.dupreincaperu.dupree.mh_fragments_menu.panel_asesoras.PanelAsesoraFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.pedidos.ofertas.OffersFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.reportes.ReportesFragment;
import com.dupreincaperu.dupree.mh_fragments_ventas.Fragmento_indi_vent;
import com.dupreincaperu.dupree.mh_fragments_ventas.Fragmento_list_ases;
import com.dupreincaperu.dupree.mh_holders.CatalogoHolder;
import com.dupreincaperu.dupree.mh_utilities.KeyBoard;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class HacerPedidoFragment extends TabManagerFragment implements dialogoPedido.cierroDialogo, BasePedido, CatalogoHolder.Events{

    public static final String TAG = HacerPedidoFragment.class.getName();
    public FragmentPedidosHacerBinding binding;
    private PedidosController pedidosController;

    public final static String PEDIDO_FACTURADO = "2";
    public final static String PEDIDO_NUEVO = "0";
    public final static String PEDIDO_MODIFICAR = "1";
    public static final String BROACAST_DATA="broacast_data";

    private ResultEdoPedido resultEdoPedido;
    private boolean enable = true;

    private PedidosPagerAdapter pedidosPagerAdapter;
//    private BaseViewPagerAdapter pagerAdapter;

    //FILTRO CONTROL
    RealmResults<Catalogo> querycat;
    private List<Catalogo> listFilter;//, listSelected;
    private CatalogoListAdapter adapter_catalogo;
    private Realm realm;
    //FILTRO CONTROL

    private Profile perfil;
    public void loadData(Profile perfil){
        this.perfil=perfil;
    }

    private boolean productsEditable=false;
    private boolean offersEditable=false;

    private BannerController bannerController;
    String campanaActual;
    String nume_iden;

    public HacerPedidoFragment() {
        // Required empty public constructor
    }

    //MARK: TabManagerFragment
    @Override
    protected ViewPager setViewPage() {
        return binding.pagerView;
    }

    //MARK: TabManagerFragment
    @Override
    protected TabLayout setTabs() {
        Log.e(TAG, "setTabs()");
        return binding.tabs;
    }

    //MARK: TabManagerFragment
    @Override
    protected FragmentStatePagerAdapter setAdapter() {
        return pedidosPagerAdapter;
    }

    //MARK: TabManagerFragment
    @Override
    protected List<ModelList> setItems() {
        List<ModelList> items = new ArrayList<>();
        items.add(new ModelList(R.drawable.ic_shopping_cart_white_24dp, getString(R.string.carrito)));
        items.add(new ModelList(R.drawable.ic_list_white_24dp, getString(R.string.ofertas)));
        items.add(new ModelList(R.drawable.baseline_assignment_white_24, getString(R.string.historical)));
        return items;
    }

    //MARK: BaseFragment
    @Override
    protected int getMainLayout() {
        return R.layout.fragment_pedidos_hacer;
    }

    //MARK: TabManagerFragment
    @Override
    protected void initViews(ViewDataBinding view) {

        binding = (FragmentPedidosHacerBinding) view;
        Log.e(TAG, "initViews()");

        pedidosPagerAdapter = new PedidosPagerAdapter(getChildFragmentManager());
        binding.pagerView.addOnPageChangeListener(mOnPageChangeListener);
        binding.pagerView.setOffscreenPageLimit(3);

        binding.fabSendPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEnable()) {
                    msgToast(getString(R.string.pedido_no_puede_modificarse));
                    return;
                }

                if(pedidosPagerAdapter.getCarritoFragment().validate()){
                    testRefreshServer();
                } else {
                    msgToast(getString(R.string.no_se_detectaron_cambios));
                }
            }
        });

        //CONTROL DE FILTROS Y CATALOGO
        realm = Realm.getDefaultInstance();

        binding.ctcRcvFilter.setVisibility(View.GONE);
        binding.rcvFilterPedido.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        binding.rcvFilterPedido.setHasFixedSize(true);

        //listPremios = new ArrayList<>();
        listFilter = new ArrayList<>();
        adapter_catalogo = new CatalogoListAdapter(listFilter, this);
        binding.rcvFilterPedido.setAdapter(adapter_catalogo);

        binding.swipePedidos.setOnRefreshListener(mOnRefreshListener);
        binding.swipePedidos.setEnabled(false);


    }

    @Override
    protected void onLoadedView() {
        Log.e(TAG, "onLoadedView()");
        pedidosController = new PedidosController(getContext());
        //API rest

        String campana =  getActivity().getIntent().getStringExtra("campanaActual");
        if (campana != null){
            campanaActual = campana;
            msgToast(campanaActual);
        }
        bannerController =  new BannerController(getContext());
        cargarpreferencias();

        controlVisible(false, "");
        filterCatalogoDB("");//mostrar toodo el catalogo
        checkEdoPedido();

    }

    private MenuItem searchItem;
    private SearchView searchView;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pedidos, menu);
        final MenuItem searchAsesora = menu.findItem(R.id.menu_asesora);
        searchItem = menu.findItem(R.id.menu_action_search);

        searchView = (SearchView) searchItem.getActionView();

        ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_black_search_24));
        searchView.setQueryHint(Html.fromHtml("<font color = #000000>" + getString(R.string.ingresar_codigo) + "</font>"));

        EditText txtSearch = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        //txtSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
        txtSearch.setInputType(InputType.TYPE_CLASS_TEXT);
        txtSearch.setTextColor(Color.BLACK);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e(TAG, "onCreateOptionsMenu() -> onQueryTextSubmit() -> " + query);
                searchMyQuery(query);
                searchView.clearFocus();
                return false;//habilita el serach del teclado
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e(TAG, "onCreateOptionsMenu() -> onQueryTextChange() -> " + newText);
                filterCatalogo(newText);
                return true;
            }
        });

        searchView.setIconified(true);//inicialmente oculto


        if(dataStore.getTipoPerfil()!=null) {
            boolean isAsesora = dataStore.getTipoPerfil().getPerfil().equals(Profile.ADESORA);
            searchAsesora.setVisible(!isAsesora);
        } else {
            msgToast(getString(R.string.handled_501_se_cerro_sesion));
            gotoMain();
        }

        searchAsesora.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                obtainIdentyAsesora();
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchMyQuery(String query){
        Log.e(TAG, "searchQuery() -> query: " + query);

    }

    //MARK CatalogoHolder.Events
    @Override
    public void onAddCartClick(Catalogo dataRow, int row) {
        Log.e(TAG,"onAddCartClick: "+String.valueOf(row));
        addCart(row);//agregar sin validar
    }

    //MARK CatalogoHolder.Events
    @Override
    public void onIncreaseClick(Catalogo dataRow, int row) {
        Log.e(TAG,"onIncreaseClick, pos:  "+row);
        increaseCart(row);
    }

    //MARK CatalogoHolder.Events
    @Override
    public void onDecreaseClick(Catalogo dataRow, int row) {
        Log.e(TAG,"onDecreaseClick, pos:  "+row);
        int cantidad = dataRow.getCantidad();
        if(cantidad>0) {
            if(cantidad==1){
                testRemoveCart(row);//pregunta si quiere eliminar
            } else {
                decreaseCart(row, cantidad);
            }
        }
    }

    //MARK CatalogoHolder.Events
    @Override
    public void onAddEditableClick(boolean isEditable) {

    }

    public void testRefreshServer(){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.guardar_cambios_server), getString(R.string.desea_guardar_cambios_server));
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    sendToServer();
            }
        });
        simpleDialog.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    private void sendToServer(){
        showProgress();

        LiquidarSend send = obtainProductsLiquidate();
        Log.e(TAG, "sendToServer() -> send: "+new Gson().toJson(send));
        pedidosController.liquidarPedido(send, new TTResultListener<LiquidarDTO>() {
            @Override
            public void success(LiquidarDTO result) {
                dismissProgress();
                if(result != null && result.getCodigo() != null){
                    if(result.getCodigo().equals(EnumLiquidar.OK.getKey())){
                        NumberFormat formatter = NumberFormat.getInstance(Locale.US);

                        String  msg = "Total: "
                                .concat("S/.".concat(formatter.format(Float.parseFloat(result.getTotal_pedido()))))
                                .concat(". ")
                                .concat(result.getMensaje());

                        showSnackBarDuration(msg,15000);

                        pedidosPagerAdapter.getCarritoFragment().pedidoEndviadoexitosamente();
                        pedidosPagerAdapter.getOffersFragment().ofertaEndviadoexitosamente();

                        initFAB();

                        clearAllData();
                    } else if(result.getCodigo().equals(EnumLiquidar.DEBAJO_MONTO.getKey())){
                        NumberFormat formatter = NumberFormat.getInstance(Locale.US);

                        String  msg = "Total: "
                                .concat("S/.".concat(formatter.format(Float.parseFloat(result.getTotal_pedido()))))
                                .concat(". ")
                                .concat(result.getMensaje());

                        showSnackBarDuration(msg,15000);
                    }
                }
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
//        new Http(getActivity()).liquidarPedido(obtainProductsLiquidate(), TAG, BROACAST_LIQUIDATE_PRODUCTS);

    }

    private double total_pedido = 0;
    public LiquidarSend obtainProductsLiquidate(){
        String id_pedido = resultEdoPedido!=null ? resultEdoPedido.getId_pedido() : "";
        List<String> paquetones= new ArrayList<>();
        List<LiquidarProducto> ofertas = new ArrayList<>();
        List<LiquidarProducto> productos = new ArrayList<>();
        total_pedido = 0;

        List<ItemCarrito> itemCarritoList = pedidosPagerAdapter.getCarritoFragment().getListFilterCart();
        for(ItemCarrito item : itemCarritoList) {
            switch (item.getType()){
                case ItemCarrito.TYPE_CATALOGO:
                    productos.add(new LiquidarProducto(String.valueOf(item.getId()), item.getCantidad()));
                    total_pedido = total_pedido + item.getCantidad() * Double.parseDouble(item.getValor());
                    break;
                case ItemCarrito.TYPE_OFFERTS:
                    ofertas.add(new LiquidarProducto(String.valueOf(item.getId()), item.getCantidad()));
                    total_pedido = total_pedido + item.getCantidad() * Double.parseDouble(item.getValor_descuento());
                    break;
            }

        }
        return new LiquidarSend(id_pedido, paquetones, productos, ofertas);

    }

    private void checkEdoPedido(){
        Log.e(TAG,"checkEdoPedido(A)");
        if(perfil != null){
            Log.e(TAG,"checkEdoPedido(B)");
            if(perfil.getPerfil().equals(Profile.ADESORA)){
                Log.e(TAG,"checkEdoPedido(C)");

                searchIdenty("");
            } else {
                enableEdit(false);
                obtainIdentyAsesora();
            }
        }
    }

    private void controlVisible(boolean isVisible, String text){
        //habilita tabs

        setTabIcons(PedidosPagerAdapter.PAGE_CARRITO, isVisible);
        setTabIcons(PedidosPagerAdapter.PAGE_OFFERS, isVisible);

        binding.tvNombreAsesora.setText(text);
        binding.ctxNameAsesora.setVisibility(isVisible && !TextUtils.isEmpty(text) ? View.VISIBLE : View.GONE);
        enableSearch(isVisible);

        fabShow(isVisible);

    }

    private void fabTitle(String title){
        binding.fabSendPedido.setTitle(title);
    }

    private void fabShow(boolean isVisible){
        binding.fabSendPedido.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    private void setBackGroungColor(int color){
        binding.fabSendPedido.setBackgroundColor(color);
    }

    private void updateView(){
        Log.e(TAG, "updateView()");
        obtainCatalogo();

        if (!resultEdoPedido.getMensaje().equalsIgnoreCase("")){
            new dialogoPedido(getContext(), HacerPedidoFragment.this, resultEdoPedido.getMensaje().toUpperCase());
        } else {
            String asesora = resultEdoPedido.getAsesora();
            String[] arraySaldo = asesora.split("S/.");
            Double saldo = Double.parseDouble(arraySaldo[1]);
            String mensaje = "Usted tiene un saldo de S/. "+saldo;
            if (saldo>25){
                new dialogoPedido(getContext(), HacerPedidoFragment.this, mensaje.toUpperCase());
            }
        }

        controlVisible(true, resultEdoPedido.getAsesora());
        //muestra o no ofertas
        pedidosPagerAdapter.getOffersFragment().setShowOffers(resultEdoPedido.getOfertas().getActivo().equals(EstadoPedidoDTO.SHOW_PRDUCTS));

        //VERIFICA SI CAMBIO LA CAMPAÑA
        String campanaActualServer = resultEdoPedido.getCampana();
        String campanaActualLocal = dataStore.getCampanaActual();

        if(campanaActualLocal==null){
            dataStore.setCampanaActual(campanaActualServer);// se respalda la campañ actual
        } else if(!campanaActualLocal.equals(campanaActualServer)){//si cambio la campaña
            // se borrar e pedido actual (prductos y ofertas) de la DB
            dataStore.setCampanaActual(campanaActualServer);// se respalda la campañ actual
            dataStore.setChangeCampana(true);

           /* startActivity(new Intent(getActivity(), FullscreenActivity.class));

            getActivity().finish();*/
            showProgress();
            return;
        }

        selectAction();
    }

    private void selectAction(){
        Log.e(TAG, "selectAction()");
        switch (resultEdoPedido.getEstado_pedido()) {
            case PEDIDO_NUEVO:
                // Todoel proceso desde cero, puede existir algo loca
                //borra los productos de la DB que esten pedidos con anterioridad
                Log.e(TAG, "PEDIDO NUEVO");//no llega nada de productos nuevos, se mantienen los de la db local
                enableEdit(true);
                pedidosPagerAdapter.getOffersFragment().clearEditable();
                pedidosPagerAdapter.getOffersFragment().setEnable(true);
                pedidosPagerAdapter.getOffersFragment().sincOfertasDB(resultEdoPedido.getOfertas().getProductos(), false);//prevalece la oferta local;


                pedidosPagerAdapter.getCarritoFragment().sincCatalogoDB(resultEdoPedido.getProductos().getProductos(), false);//Se da prioridad al pedido local
                pedidosPagerAdapter.getCarritoFragment().clearEditable();
                pedidosPagerAdapter.getCarritoFragment().setEnable(true);

                pedidosPagerAdapter.getCarritoFragment().updateCarrito();//actualiza el carrito

                break;
            case PEDIDO_MODIFICAR:
                //Si, es editar se da prioridad a lo que esta en el server
                Log.e(TAG, "PEDIDO A MODIFICAR");
                enableEdit(true);
                pedidosPagerAdapter.getOffersFragment().clearEditable();
                pedidosPagerAdapter.getOffersFragment().setEnable(true);
                Log.e(TAG, "Ofertas: "+new Gson().toJson(resultEdoPedido.getOfertas().getProductos()));
                pedidosPagerAdapter.getOffersFragment().sincOfertasDB(resultEdoPedido.getOfertas().getProductos(), true);//prevalece la oferta del server
                //enableEdit(true);

                pedidosPagerAdapter.getCarritoFragment().sincCatalogoDB(resultEdoPedido.getProductos().getProductos(), true);//Se borra el pedido local
                pedidosPagerAdapter.getCarritoFragment().clearEditable();
                pedidosPagerAdapter.getCarritoFragment().setEnable(true);

                pedidosPagerAdapter.getCarritoFragment().updateCarrito();//actualiza el carrito
                break;
            case PEDIDO_FACTURADO:
                //ya el pedido fue facturado, no se puede hacer otro o editar, no debe haber nada local
                //se muestra los que llegan, y no se pueden modificar
                Log.e(TAG, "PEDIDO YA FACTURADO");
                enableEdit(false);
                updateTotal();
                break;
        }

    }

    private void enableEdit(boolean isEnable){
        setEnable(isEnable);
    }

    /**
     * Eventos SwipeRefreshLayout
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener
            = new SwipeRefreshLayout.OnRefreshListener(){
        @Override
        public void onRefresh() {

        }
    };

    private void enableSearch(boolean isVisible){
        if(searchItem!=null) {
            searchItem.setVisible(isVisible);
            searchView.setIconified(!isVisible);
        }
    }

    private void hideSearchView(){
        if(searchView!=null) {
            searchView.setQuery("", false);
            searchView.setIconified(true);
        }
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener
            = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.i(TAG,"PAGE_MAIN Page: "+position);
            switch (position){
                case PedidosPagerAdapter.PAGE_CARRITO:
                    enableSearch(true);
                    fabShow(true);
                    pedidosPagerAdapter.getCarritoFragment().setEnable(isEnable());
                    pedidosPagerAdapter.getOffersFragment().setEnable(isEnable());

                    pedidosPagerAdapter.getCarritoFragment().updateCarrito();
                    break;
                case PedidosPagerAdapter.PAGE_OFFERS:
                    enableSearch(true);
                    fabShow(false);
                    pedidosPagerAdapter.getCarritoFragment().setEnable(isEnable());
                    pedidosPagerAdapter.getOffersFragment().setEnable(isEnable());

                    pedidosPagerAdapter.getOffersFragment().filterOffersDB("");
                    break;
                case PedidosPagerAdapter.PAGE_HISTORICAL:
                    enableSearch(false);
                    fabShow(false);
                    pedidosPagerAdapter.getHistorialFragment().setIdentyFacturas(cedula);
                    break;
            }

            hideSearchView();
            KeyBoard.hide(getActivity());

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


//    private String textFilter="";
    public void filterCatalogo(String textFilter){
//        this.textFilter=textFilter;
        Log.e(TAG, "textFilter: "+textFilter+", isEnable(): "+isEnable());
        if(isEnable()) {
            if (getPageCurrent() == PedidosPagerAdapter.PAGE_CARRITO) {
                if (textFilter.length() > 2) {
                    binding.ctcRcvFilter.setVisibility(View.VISIBLE);
                    filterCatalogoDB(textFilter);
                } else {
                    binding.ctcRcvFilter.setVisibility(View.GONE);
                }
            } else if (getPageCurrent() == PedidosPagerAdapter.PAGE_OFFERS) {
                pedidosPagerAdapter.getOffersFragment().filterOffersDB(textFilter);
            }
        }

    }

    private void initFAB(){
        setBackGroungColor(getResources().getColor(R.color.green_check));
        fabTitle("");
    }

    private void clearAllData(){
        setEnable(false);

        controlVisible(false, "");

        //EN ESTE CASO SE ESTA MODO GERENTE, SE LIMPIA TODITO EL PEDIDO AL CAMBIAR DE ASESORA
        pedidosPagerAdapter.getOffersFragment().deleteOferta();
        //pedidosPagerAdapter.getOffersFragment().filterOffersDB("");
        pedidosPagerAdapter.getCarritoFragment().clearCartDB();
        pedidosPagerAdapter.getCarritoFragment().updateCarrito();

        pedidosPagerAdapter.getHistorialFragment().updateView(null);
    }

    String cedula = "";
    public void searchIdenty(String cedula){
        binding.tvNombreAsesora.setText("");
        initFAB();
        this.cedula=cedula;

        if(perfil != null) {
            if (!perfil.getPerfil().equals(Profile.ADESORA))
                setEnable(false);
        }

        showProgress();
        pedidosController.getEstadoPedido(new Identy(cedula), new TTResultListener<EstadoPedidoDTO>() {
            @Override
            public void success(EstadoPedidoDTO result) {
                dismissProgress();
                resultEdoPedido = result.getResult();

                updateView();

                dataFacturas();
            }

            @Override
            public void error(TTError error) {
                setPageCurrent(PedidosPagerAdapter.PAGE_HISTORICAL);

                dismissProgress();
                checkSession(error);

                if(error.getStatusCode() != 404 || error.getStatusCode() != 501) {
                    dataFacturas();
                }
            }
        });

    }

    private void dataFacturas(){
        if (getPageCurrent() == PedidosPagerAdapter.PAGE_HISTORICAL) {
            pedidosPagerAdapter.getHistorialFragment().searchIdenty(cedula);
        }
    }
    ///////////CONTROL DEL FILTRO DE PEDIDOS////////////

    public void testRemoveCart(int row){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.remover), getString(R.string.desea_remover_pedido));
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    removeCart(row);
            }
        });
        simpleDialog.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    public void obtainIdentyAsesora(){
        hideSearchView();
        InputDialog d = new InputDialog();
        d.loadData(getString(R.string.cedula_asesora), getString(R.string.cedula_asesora), new InputDialog.ResponseListener() {
            @Override
            public void result(String inputText) {
                clearAllData();
                searchIdenty(inputText);
            }
        });
        d.show(getActivity().getSupportFragmentManager(),"mDialog");
    }


    private void increaseCart(int position){
        Log.i(TAG, "increaseCart(), pos: "+position);
        if(position>-1 && position<listFilter.size()) {
            realm.beginTransaction();
            try {
                listFilter.get(position).setCantidad(listFilter.get(position).getCantidad()+1);
                adapter_catalogo.notifyItemChanged(position);
                pedidosPagerAdapter.getCarritoFragment().updateCarrito();
                realm.commitTransaction();
            } catch (Throwable e) {
                Log.v(TAG, "increaseCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    private void decreaseCart(int position, int cantidad){
        Log.i(TAG, "decreaseCart(), pos: "+position);
        if(position>-1 && position<listFilter.size()) {
            realm.beginTransaction();
            try {
                listFilter.get(position).setCantidad(cantidad - 1);
                adapter_catalogo.notifyItemChanged(position);
                pedidosPagerAdapter.getCarritoFragment().updateCarrito();
                realm.commitTransaction();
            } catch (Throwable e) {
                Log.v(TAG, "decreaseCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    private void addCart(int position){
        Log.i(TAG, "addCart(), pos: "+position);
        if(position>-1 && position<listFilter.size()) {
            realm.beginTransaction();
            try {
                listFilter.get(position).setCantidad(1);// se predetermina cantidad en 1
                listFilter.get(position).setTime(Calendar.getInstance().getTimeInMillis());//controla el ultimo item agregado a la lista (cambiar a un int y controlar incremento)
                adapter_catalogo.notifyItemChanged(position);
                realm.commitTransaction();

                hideSearchView();
                pedidosPagerAdapter.getCarritoFragment().updateCarrito();

            } catch (Throwable e) {
                Log.v(TAG, "addCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    private void removeCart(int position){
        Log.i(TAG, "removeCart(), pos: "+position);
        if(position>-1 && position<listFilter.size()) {
            realm.beginTransaction();
            try {
                listFilter.get(position).setCantidad(0);// se predetermina cantidad en 1
                adapter_catalogo.notifyItemChanged(position);
                pedidosPagerAdapter.getCarritoFragment().updateCarrito();
                realm.commitTransaction();
            } catch (Throwable e) {
                Log.v(TAG, "removeCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    private void filterCatalogoDB(final String textFilter){
        msgToast("-->"+textFilter);
        Log.v(TAG,"filterCatalogoDB... ---------------filterCatalogoDB--------------");
        realm.beginTransaction();
        try {
            listFilter.clear();
            querycat = realm.where(Catalogo.class)
                    .beginGroup()
                    .equalTo("cantidad",0)
                    .equalTo("cantidadServer",0)
                    .endGroup()
                    .contains("id",textFilter).findAll();
            listFilter.addAll(querycat);
            adapter_catalogo.notifyDataSetChanged();
            realm.commitTransaction();

            Log.e(TAG,"filterCatalogoDB... listFilter.SIZE(): "+listFilter.size());
        } catch(Throwable e) {
            Log.v(TAG,"filterCatalogoDB... ---------------error--------------");
            if(realm.isInTransaction()) {
                realm.cancelTransaction();
            }
            throw e;
        }
    }

    ///////////CONTROL DEL FILTRO DE PEDIDOS////////////

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }



    private void controlEditable(){
        setBackGroungColor((productsEditable | offersEditable) ? getResources().getColor(R.color.colorAccent) : getResources().getColor(R.color.green_check));
    }

    @Override
    public void offersEditable(boolean offersEditable) {
        this.offersEditable = offersEditable;
        controlEditable();
    }

    @Override
    public void productsEditable(boolean productsEditable) {
        this.productsEditable = productsEditable;
        controlEditable();
    }

    @Override
    public void updateTotal(){
        obtainProductsLiquidate();
        Log.e(TAG, "Update Total: "+total_pedido);

        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        fabTitle("S/. ".concat(formatter.format(total_pedido)));
    }

    @Override
    public void updateCarrito() {
        pedidosPagerAdapter.getCarritoFragment().updateCarrito();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach()");
        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach()");
        setHasOptionsMenu(false);
    }


    @Override
    public void onResume(){
        super.onResume();
        cargarpreferencias();
    }

    @Override
    public void ResultadoDialogo() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        if(this.perfil.getPerfil().equalsIgnoreCase("L") || this.perfil.getPerfil().equalsIgnoreCase("Z") ){
            Fragment fragmentoGenerico = new PanelGerenteFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment, fragmentoGenerico).commit();
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Panel resultados");
        } else{
            Fragment fragmentoGenerico = new PanelAsesoraFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment, fragmentoGenerico).commit();
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Panel asesora");
        }

    }


    private void obtainCatalogo(){

        if (!cedula.equalsIgnoreCase("")){
            nume_iden = cedula;
        }

        bannerController.getProductos(campanaActual, nume_iden, new TTResultListener<ProductCatalogoDTO>() {
            @Override
            public void success(ProductCatalogoDTO result) {
                Log.e(TAG, "obtainCatalogo() -> success():" + new Gson().toJson(result));
                //un detalle envia 200 con error 404
                if (result.getCode() == 404) {
                    errorLoadInitData();
                } else {
                    responseCatalogo(result.getResult());
                }
            }

            @Override
            public void error(TTError error) {
                Log.e(TAG, "obtainCatalogo() -> error():" + new Gson().toJson(error));

                //error de Backend
                if (!error.getCodigo().equalsIgnoreCase("")){
                    if(error.getStatusCode() == 404) {
                        try {
                            ProductCatalogoDTO resp = new Gson().fromJson(error.getErrorBody(), ProductCatalogoDTO.class);
                            responseCatalogo(resp.getResult());
                        } catch (JsonSyntaxException e) {
                            //terminatedProcess();
                            //Se comenta para que pueda ingresar al menu esto pasa cuando no carga los productos de catalogo
                            errorLoadInitData();
                        }
                    } else {
                        //terminatedProcess();
                        errorLoadInitData();
                    }
                }
            }
        });

    }

    ListProductCatalogoDTO listaProd_catalogo;
    public void responseCatalogo(ListProductCatalogoDTO listaProd_catalogo){
        Log.e(TAG, "listaProd_catalogo.getOfertas(): "+new Gson().toJson(listaProd_catalogo.getOfertas()));
        Log.e(TAG, "listaProd_catalogo.getPaquetones(): "+new Gson().toJson(listaProd_catalogo.getPaquetones()));
        Log.e(TAG, "listaProd_catalogo.getProductos(): "+new Gson().toJson(listaProd_catalogo.getProductos()));
        if(listaProd_catalogo.getProductos()!=null && listaProd_catalogo.getProductos().size()>0) {
            this.listaProd_catalogo = listaProd_catalogo;

        } else{
            errorLoadInitData();
        }
    }

    public void errorLoadInitData(){
        msgToast("No se pueden cargar datos iniciales");
    }

    private void cargarpreferencias() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String nume_iden = preferences.getString("nume_iden","");
        this.nume_iden = nume_iden;
    }

}
