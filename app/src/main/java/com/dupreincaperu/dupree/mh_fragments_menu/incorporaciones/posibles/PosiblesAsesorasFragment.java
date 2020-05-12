package com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.posibles;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dupreeinca.lib_api_rest.enums.EnumStatusPreInsc;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentPosiblesAsesorasBinding;
import com.dupreincaperu.dupree.files.LoadJsonFile;
import com.dupreincaperu.dupree.files.ManagerFiles;
import com.dupreincaperu.dupree.mh_CRUD.CRUDPosibles_Nuevas;
import com.dupreeinca.lib_api_rest.model.dto.response.realm.Posibles_Nuevas;
import com.dupreincaperu.dupree.mh_adapters.NuevasPagerAdapter;
import com.dupreincaperu.dupree.mh_dialogs.ListString;
import com.dupreincaperu.dupree.mh_dialogs.SingleListDialog;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.Incorp_Nuevas_Fragment;
import com.dupreincaperu.dupree.mh_holders.ListNuevasHolder;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class PosiblesAsesorasFragment extends BaseFragment  implements ListNuevasHolder.Events{

    private FragmentPosiblesAsesorasBinding binding;
    private String TAG = PosiblesAsesorasFragment.class.getName();
    private Realm realm;
    private Posibles_Nuevas posibles_nuevas;
    private EditText tipo_docu, cedula,nombre,apellido, movil1,movil2,direccion,barrio;
    private List<ModelList> listTipoDoc;
    private LoadJsonFile jsonFile;
    private Profile perfil;

    public void loadData(Profile perfil){
        this.perfil=perfil;
    }

    public static PosiblesAsesorasFragment newInstance() {

        Bundle args = new Bundle();

        PosiblesAsesorasFragment fragment = new PosiblesAsesorasFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public PosiblesAsesorasFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_posibles_asesoras;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentPosiblesAsesorasBinding) view;
        realm = Realm.getDefaultInstance();
        configView(binding);
    }

    private void configView(FragmentPosiblesAsesorasBinding binding){
        posibles_nuevas = new Posibles_Nuevas();

        tipo_docu= binding.txtSpnTypeId;
        cedula= binding.txtIdenty;
        nombre= binding.txtName;
        apellido= binding.txtLastname;
        movil1 = binding.txtCellphone1;
        movil2= binding.txtCellphone2;
        direccion = binding.txtDireccion;
        barrio    = binding.txtBarrio;

        binding.txtSpnTypeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList(ListString.BROACAST_REG_TYPE_DOC, getString(R.string.tipo_de_documento), listTipoDoc, binding.txtSpnTypeId.getText().toString());
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  tipo_docu_key = String.valueOf(Arrays.asList(getResources().getStringArray(R.array.typeIdenty)).indexOf(tipo_docu.getText().toString()));
                posibles_nuevas.setTipo_docu(tipo_docu_key);
                posibles_nuevas.setCedula(cedula.getText().toString());
                posibles_nuevas.setNombre(nombre.getText().toString());
                posibles_nuevas.setApellido(apellido.getText().toString());
                posibles_nuevas.setMovil1(movil1.getText().toString());
                posibles_nuevas.setMovil2(movil2.getText().toString());
                posibles_nuevas.setDireccion(direccion.getText().toString());
                posibles_nuevas.setBarrio(barrio.getText().toString());
                posibles_nuevas.setEstado(EnumStatusPreInsc.AUTORIZADO.getKey());

                boolean hay = CRUDPosibles_Nuevas.existeRegistro(posibles_nuevas.getCedula());
                //   boolean ban = CRUDPosibles_Nuevas.existeRegistroId(1);

                //if(hay)
                //{
                //    msgToast("Cedula:"+posibles_nuevas.getCedula()+" ya existe. Elimine y vuelva a crear el registro.");
                //}else
                if(nombre.getText().length()==0||apellido.getText().length()==0||movil1.getText().length()==0)
                {
                    msgToast("Debe digitar todos los campos del formulario.");
                }
                else//inserte
                {
                    CRUDPosibles_Nuevas.adicionarNueva(posibles_nuevas,perfil);
                    msgToast("Insertada correctamente");

                    Fragment fragmentoGenerico = new Incorp_Nuevas_Fragment();//hay que validar para que caiga en la pagina correcta
                    ((Incorp_Nuevas_Fragment)fragmentoGenerico).loadData(NuevasPagerAdapter.PAGE_LIST_POSI_ASES, perfil);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment, fragmentoGenerico)// se reconoce al fragment por el String
                            .commit();
                }


            }
        });
    }

    @Override
    protected void onLoadedView() {
        jsonFile = new LoadJsonFile(getContext());
        listTipoDoc = jsonFile.getParentezcos(ManagerFiles.TIPO_DOC.getKey());
    }

    public void showList(String tag, String title, List<ModelList> data, String itemSelected){
        SingleListDialog dialog = new SingleListDialog();
        dialog.loadData(title, data, itemSelected, new SingleListDialog.ListenerResponse() {
            @Override
            public void result(ModelList item) {
                Log.i(TAG, tag);
                switch(tag){
                    case ListString.BROACAST_REG_TYPE_DOC:
                        binding.txtSpnTypeId.setError(null);
                        binding.txtSpnTypeId.setText(item.getName());
                        break;
                }
            }
        });
        dialog.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    @Override
    public void onClickRoot(Posibles_Nuevas dataRow, int row) {

    }

    //@Override
    //public void onBorrarLinea(String cedula) {

    //}

    @Override
    public void onDeleteRegistroId(int id) {

    }
}
