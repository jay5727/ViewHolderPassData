package com.jay.viewholderpassdata;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.jay.viewholderpassdata.adapter.ChildAdapter;
import com.jay.viewholderpassdata.adapter.MyCustomParentAdapter;
import com.jay.viewholderpassdata.model.CustomModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MyCustomParentAdapter.ChildCallback {

    @BindView(R.id.recycl1)
    RecyclerView recycl1;


    @BindView(R.id.recycle2)
    RecyclerView recycle2;

    @BindView(R.id.close)
    Button close;

    @BindView(R.id.apply)
    Button apply;
    Context ctx;

    MyCustomParentAdapter parentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        ButterKnife.bind(this);

 /*       HashMap<String, Object> retMap = new Gson().fromJson(
                getAssetJsonData(getApplicationContext()), new TypeToken<HashMap<String, Object>>() {
                }.getType()
        );
        String data = getAssetJsonData(getApplicationContext());

        Type type = new TypeToken<Data>() {
        }.getType();
        Data modelObject = new Gson().fromJson(data, type);

        try {
            Map<String, Object> myModel = jsonToMap(new JSONObject(data));

            HashMap<String, List<CustomModel>> obj = (HashMap<String, List<CustomModel>>)
                    jsonToMap(new JSONObject(data)).get("data");

            HashMap<String, List<CustomModel>>  map = new Gson().fromJson(data, new TypeToken<HashMap<String, String>>(){}.getType());
            String str = null;
            HashMap<String, List<CustomModel>> obj = new HashMap<>();
            obj.put("Skills", Arrays.asList(
                    (new CustomModel("Biker", false)),
                    (new CustomModel("DeliveryBoy", false)),
                    (new CustomModel("SalesBoy", false)),
                    (new CustomModel("Banker", false))

            ));
            obj.put("Location", Arrays.asList(
                    (new CustomModel("Mumbai", false)),
                    (new CustomModel("Pune", false)),
                    (new CustomModel("Thane", false)),
                    (new CustomModel("Vikhroli", false))

            ));

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recycl1.setLayoutManager(mLayoutManager);
            recycl1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recycl1.setItemAnimator(new DefaultItemAnimator());
            recycl1.setNestedScrollingEnabled(false);
            parentAdapter = new MyCustomParentAdapter(ctx, obj, this);
            recycl1.setAdapter(parentAdapter);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
 */
        HashMap<String, List<CustomModel>> objHashmap = new HashMap<>();
        JSONObject obj = null;
        try {
            obj = new JSONObject(getAssetJsonData(getApplicationContext()));
            List<CustomModel> lstCustomModel = null;
            String key = null;
            JSONObject jsonArrayHeader = obj.getJSONObject("data");
//            for (int i = 0; i < jsonArrayHeader.length(); i++)
//            {
            Iterator<String> iter = jsonArrayHeader.keys();
            while (iter.hasNext()) {
                key = iter.next();
                try {
                    JSONArray jo_inside = jsonArrayHeader.getJSONArray(key);
                    lstCustomModel = new ArrayList<>();
                    for (int j = 0; j < jo_inside.length(); j++) {
                        JSONObject innerArray = (JSONObject) jo_inside.get(j);
                        String name = (String) innerArray.get("Name");
                        boolean flag = (boolean) innerArray.get("isSelected");
                        lstCustomModel.add(new CustomModel(name, flag));
                    }
                    objHashmap.put(key, lstCustomModel);
                } catch (JSONException e) {
                    // Something went wrong!
                }
            }
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycl1.setLayoutManager(mLayoutManager);
        recycl1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recycl1.setItemAnimator(new DefaultItemAnimator());
        recycl1.setNestedScrollingEnabled(false);
        parentAdapter = new MyCustomParentAdapter(ctx, objHashmap, this);
        recycl1.setAdapter(parentAdapter);


//        String str = "Jay,Abhi,Aravind";
//        Lpareist<String> lst = Arrays.asList(str.split(","));
//        List<CustomModel> lstCustom = null;
//        lstCustom = new ArrayList<>();
//        for (int i = 0; i < lst.size(); i++) {
//            lstCustom.add(new CustomModel(lst.get(i), false));
//        }
//        HashMap<String, List<CustomModel>> obj1 = new HashMap<>();
//        obj1.put("Skills", lstCustom);

    }

    public String getAssetJsonData(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        Log.e("data", json);
        return json;

    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    @Override
    public void changeItems(List<CustomModel> items) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
        recycle2.setLayoutManager(mLayoutManager);
        recycle2.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL));
        recycle2.setItemAnimator(new DefaultItemAnimator());
        recycle2.setNestedScrollingEnabled(false);
        //mAdapter  =new ChildAdapter()
        recycle2.setAdapter(new ChildAdapter(this, items));
        //recycle2.setAdapter(new ChildAdapter(this, parentAdapter.lstData, items, null));
    }

    @OnClick(R.id.apply)
    public void onClick() {
        if (parentAdapter != null) {
            if (parentAdapter.lstData != null && parentAdapter.lstData.size() > 0) {
                String selectedName = "";
                String Filters = "\"Filters\": [{";//"Filters": [{
                for (int i = 0; i < parentAdapter.lstData.size(); i++) {
                    String key = parentAdapter.lstKey.get(i);//SKILLS,LOCATION
                    boolean iskeyAdded = false;
                    for (int j = 0; j < parentAdapter.lstData.get(key).size(); j++) {

                        if (parentAdapter.lstData.get(key).get(j).getIsSelected()) {
                            if (!iskeyAdded) {
                                iskeyAdded = true;
                                selectedName += key + ":" + parentAdapter.lstData.get(key).get(j).getName() + ",";
                            } else {
                                selectedName += parentAdapter.lstData.get(key).get(j).getName() + ",";
                            }
                            //[{"RecruiterID": "4000023","RecentlyAdded": "true","IsFilterApplied": "true","Filters": [{"Skill": "","Location": "Mumbai,Pune","Gender": "M"}]}
                        }
                    }
                }
                Toast.makeText(ctx, Filters + selectedName + "}]", Toast.LENGTH_SHORT).show();
            }
        }
    }
}