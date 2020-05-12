package com.dupreincaperu.dupree.files;

import android.content.Context;

import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marwuinh@gmail.com on 7/4/18.
 */

public class LoadJsonFile {
    private Context context;
    private Gson gson;

    public LoadJsonFile(Context context) {
        this.context = context;
    }

    /**
     * Get the JSON from asset and converted to a String
     * @return
     */
    private String loadJSONFromAsset(String name_file) {
        String json;
        try {
            InputStream is = context.getAssets().open(name_file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public List<ModelList> getParentezcos(String name_file){
        List<ModelList> result = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(name_file.concat(".json"));
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(name_file);

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jo_inside = jsonArray.getJSONObject(i);
                int id = jo_inside.getInt("id");
                String name = jo_inside.getString("name");

                result.add(new ModelList(id, name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

}
