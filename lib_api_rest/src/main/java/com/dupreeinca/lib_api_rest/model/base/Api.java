package com.dupreeinca.lib_api_rest.model.base;

import android.content.res.Resources;
import android.provider.Settings;

import com.dupreeinca.lib_api_rest.R;

/**
 * Created by steveparrish on 2/23/18.
 */

public interface Api {
    String API_URL = "https://alcor.dupree.pe/dupreeWS/";
    //String API_URL = "https://alcor2per.azzorti.co/dupreeWS/";

    /**
     * Manufacturer API
     */
    interface Banner {
        String GET_BANNER = "ObtenerEventosDisponibles";
    }

    interface Serial {
        String SERIAL = "SerialTarjeta";
    }

    interface Exchange {
        String BUTAQUISTA = "CanjeButaquista";
        String PALQUISTA = "CanjePalquista";
        String CATALDI = "CanjeCataldi";
        String DAMIANI = "CanjeDamiani";
        String GUELFI = "CanjeGuelfi";
    }

    interface Annulment {
        String ANULACION = "Anulacion";
    }
}
