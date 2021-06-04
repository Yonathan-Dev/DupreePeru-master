package com.dupreincaperu.dupree.mh_fragments_menu.pedidos.historial.detalle_factura;

import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.View;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemDetailFacturaDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemFacturaDTO;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentDetalleFacturaBinding;
import com.dupreincaperu.dupree.mh_adapters.DetailFacturasListAdapter;
import com.dupreincaperu.dupree.mh_holders.DetailFacturasHolder;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleFacturaFragment extends BaseFragment implements DetailFacturasHolder.Events{

    public static final String TAG = DetalleFacturaFragment.class.getName();

    private ItemFacturaDTO data;
    private DetailFacturasListAdapter adapterList;

    public DetalleFacturaFragment() {
        // Required empty public constructor
    }

    public static DetalleFacturaFragment newInstance() {
        Bundle args = new Bundle();

        DetalleFacturaFragment fragment = new DetalleFacturaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle==null || (data = bundle.getParcelable(TAG))==null){
            onBack();
        }
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_detalle_factura;
    }

    private FragmentDetalleFacturaBinding binding;
    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentDetalleFacturaBinding) view;

        binding.refresh.setEnabled(false);
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.recycler.setHasFixedSize(true);

        adapterList = new DetailFacturasListAdapter(data.getProductos(), this);
        binding.recycler.setAdapter(adapterList);
    }

    @Override
    protected void onLoadedView() {
        binding.header.arrow.setVisibility(View.GONE);
        binding.header.tvIdFactura.setText(getString(R.string.concat_id_factura, data.getIdFactura()));

        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        binding.header.tvPrecioTotal.setText(getString(R.string.concat_precio_total, formatter.format(Float.parseFloat(data.getPrecioTotal()))));
        binding.header.tvCampana.setText(getString(R.string.concat_campana, data.getCampana()));
    }

    //MARK: HistorialHolder.Events
    @Override
    public void onClickRoot(ItemDetailFacturaDTO dataRow, int row) {

    }
}
