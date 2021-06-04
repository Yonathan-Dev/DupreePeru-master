package com.dupreincaperu.dupree.mh_fragments_menu;


import android.content.Intent;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.dupreeinca.lib_api_rest.controller.PedidosController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.RedimirDTO;
import com.dupreincaperu.dupree.ImageZoomActivity;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentIncentRedimirBinding;
import com.dupreincaperu.dupree.mh_adapters.RedimirListAdapter;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog;
import com.dupreincaperu.dupree.mh_holders.RedimirHolder;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPremios;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemPremios;
import com.dupreeinca.lib_api_rest.model.dto.request.RedimirPremios;
import com.dupreeinca.lib_api_rest.model.dto.request.SendPremios;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncentivosRedimirFragment extends BaseFragment implements RedimirHolder.Events{

    private final String TAG = IncentivosRedimirFragment.class.getName();
    private FragmentIncentRedimirBinding binding;
    private PedidosController pedidosController;

    private final int ADD=0;
    private final int REMOVE=1;
    private final int INCREASE=2;
    private final int DECREASE=3;

    private List<ItemPremios> list, listFilter;
    private RedimirListAdapter adapter_redimir;

    private int oldPuntos=0;
    public IncentivosRedimirFragment() {
        // Required empty public constructor
    }

    ListPremios resultPremios;
    List<SendPremios> sendPremiosList;

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_incent_redimir;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentIncentRedimirBinding) view;

        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.recycler.setHasFixedSize(true);

        sendPremiosList = new ArrayList<>();
        listFilter = new ArrayList<>();
        list = new ArrayList<>();
        adapter_redimir = new RedimirListAdapter(list, listFilter, this);
        binding.recycler.setAdapter(adapter_redimir);

        binding.tvTotalPuntos.setText("");
        binding.btnRedimir.setOnClickListener(mListenerClick);

        enableButton(false);
    }

    @Override
    protected void onLoadedView() {
        pedidosController = new PedidosController(getContext());
        checkPuntos();
    }

    @Override
    public void onAddCartClick(ItemPremios dataRow, int row) {
        //verifica si quiere agregar
        if(isAvailablePts(row)) {
            testAddCart(row);
        }
    }

    @Override
    public void onIncreaseClick(ItemPremios dataRow, int row) {
        Log.e(TAG,"onIncreaseClick, pos:  "+row);
        if(isAvailablePts(row)) {
            increaseCart(row);
        }
    }

    @Override
    public void onDecreaseClick(ItemPremios dataRow, int row) {
        Log.e(TAG,"onDecreaseClick, pos:  "+row);
        decreaseCart(row);
    }

    @Override
    public void onClickImage(ItemPremios dataRow, int row) {
        Log.e(TAG,"adapter_redimir.setRVOnItemClickListener");
        gotoZoomImage(dataRow.getImagen());
    }


    private void checkPuntos(){
        showProgress();
        pedidosController.getRedimirIncentivos(new TTResultListener<RedimirDTO>() {
            @Override
            public void success(RedimirDTO result) {
                dismissProgress();
                updateView(result.getResult());
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
    }
    private void gotoZoomImage(String urlImage){
        Intent intent = new Intent(getActivity(), ImageZoomActivity.class);
        intent.putExtra(ImageZoomActivity.URL_IMAGE, urlImage);
        startActivity(intent);
    }

    View.OnClickListener mListenerClick =
            new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    switch (view.getId()){
                        case R.id.btnRedimir:
                            sendPremios();
                            break;
                    }
                }
            };


    public void testAddCart(int row){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.agregar), getString(R.string.desea_agregar_premio));
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    addCart(row);
            }
        });
        simpleDialog.show(getActivity().getSupportFragmentManager(),"mDialog");
    }


    private void sendPremios(){
        showProgress();
        pedidosController.redimirPremios(new RedimirPremios(sendPremiosList), new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                dismissProgress();

                msgToast(result.getResult());
                checkPuntos();//actualiza la data para verificar cambios
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
    }

    public void testRemoveCart(int row){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.remover), getString(R.string.desea_remover_premio));
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    removeCart(row);
            }
        });
        simpleDialog.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    boolean quitar=false;
    private void updateView(ListPremios result){
        resultPremios = result;

        list.clear();
        listFilter.clear();
        list.addAll(result.getPremios());
        listFilter.addAll(list);

        binding.tvTotalPuntos.setText(String.valueOf(result.getPuntos_premio()).concat(" pts."));

        if(!quitar){
            quitar=true;
            binding.tvTotalPuntos.setText(String.valueOf(result.getPuntos_premio()).concat(" pts."));
        }

        oldPuntos = result.getPuntos_premio();
        //tvTotalPuntos.setText("hhh");
        enableButton(false);
        adapter_redimir.notifyDataSetChanged();
    }

    private void enableButton(boolean isEnable){
        binding.btnRedimir.setEnabled(isEnable);
        binding.btnRedimir.setBackground(isEnable ?
                getResources().getDrawable(R.drawable.rounded_background_blue) :
                getResources().getDrawable(R.drawable.rounded_background_gray)
        );
    }

    private void decreaseCart(int position){
        if(position>-1) {
            int cantidad = Integer.parseInt(listFilter.get(position).getCantidad());
            if (cantidad > 0) {
                if (cantidad == 1) {
                    testRemoveCart(position);//pregunta si quiere eliminar
                } else {
                    listFilter.get(position).setCantidad(String.valueOf(cantidad - 1));
                    updatePuntos(position, DECREASE);
                    adapter_redimir.notifyItemChanged(position);
                }
            }
        }
    }

    private void increaseCart(int position) {
        if(position>-1) {
            listFilter.get(position).setCantidad(String.valueOf(Integer.parseInt(listFilter.get(position).getCantidad()) + 1));
            updatePuntos(position, INCREASE);
            adapter_redimir.notifyItemChanged(position);
        }
    }

    public void addCart(int position){
        Log.i(TAG, "addCart(), pos: "+position);
        if(position>-1) {

            listFilter.get(position).setInTheCart(true);// se habilita controles mas incremento y decremento
            listFilter.get(position).setCantidad("1");// se predetermina cantidad en 1
            updatePuntos(position, ADD);
            adapter_redimir.notifyItemChanged(position);
        }
    }

    private void updatePuntos(int position, int action){
        if(position>-1) {
            int restante = resultPremios.getPuntos_premio();
            int puntosPremio = Integer.parseInt(listFilter.get(position).getPuntos());

            if (action == ADD || action == INCREASE) {
                restante = restante - puntosPremio;
            } else if (action == REMOVE || action == DECREASE) {
                restante = restante + puntosPremio;
            }


            resultPremios.setPuntos_premio(restante);
            binding.tvTotalPuntos.setText("Dispone de: ".concat(String.valueOf(restante)).concat(" pts."));

            enableButton(oldPuntos!=restante);

            updateListToSend(action, new SendPremios(listFilter.get(position).getCodigo(),listFilter.get(position).getCantidad()));
        }
    }

    private void updateListToSend(int action, SendPremios sendPremios){
        int index = indexByCode(sendPremios.getCodigo());

        int cantidad=-1;
        if(index >- 1) {
            cantidad = Integer.parseInt(sendPremiosList.get(index).getCantidad());
        }

        switch (action) {
            case ADD:
                sendPremiosList.add(sendPremios);
                break;
            case REMOVE:
                if(index > -1 && index < sendPremiosList.size())
                    sendPremiosList.remove(index);
                break;
            case INCREASE:
                if( cantidad != -1 ) {
                    cantidad++;
                    sendPremiosList.get(index).setCantidad(String.valueOf(cantidad));
                }
                break;
            case DECREASE:
                if( cantidad != -1 ) {
                    cantidad--;
                    sendPremiosList.get(index).setCantidad(String.valueOf(cantidad));
                }
                break;
        }
    }

    private int indexByCode(String code){
        for(int i=0; i< sendPremiosList.size(); i++){
            if(code.equals(sendPremiosList.get(i).getCodigo())){
                return i;
            }
        }

        return -1;
    }

    public void removeCart(int position){
        Log.i(TAG, "removeCart(), pos: "+position);
        if(position>-1) {
            listFilter.get(position).setInTheCart(false);// se habilita controles mas incremento y decremento
            listFilter.get(position).setCantidad("0");// se predetermina cantidad en 1
            updatePuntos(position, REMOVE);
            adapter_redimir.notifyItemChanged(position);
        }
    }

    private boolean isAvailablePts(int position){
        //.p    `1int position=adapter_redimir.getPosSelected();
        Log.i(TAG, "addCart(), pos: "+position);
        if(position>-1) {
            int puntos = Integer.parseInt(listFilter.get(position).getPuntos());
            int restante = resultPremios.getPuntos_premio();
            if(puntos>restante){
                msgToast("No tiene puntos disponibles");
                return false;
            }
            return true;
        }
        return false;
    }
    
}
