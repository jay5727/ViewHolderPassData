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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

        HashMap<String, String> hMapHeaderAndCount = new HashMap<>();
        LinkedHashMap<String, List<CustomModel>> objHashmap = new LinkedHashMap<>();
        JSONObject objRoot = null;
        try {
            objRoot = new JSONObject(getAssetJsonData(getApplicationContext()));
            List<CustomModel> lstCustomModel = null;
            String key = null;
            JSONObject jsonObjHeader = objRoot.getJSONObject("data");
//            for (int i = 0; i < jsonArrayHeader.length(); i++)
//            {
            Iterator<String> iter = jsonObjHeader.keys();
            while (iter.hasNext()) {
                key = iter.next();
                hMapHeaderAndCount.put(key, "0");
                try {
                    JSONArray jsonArray = jsonObjHeader.getJSONArray(key);
                    lstCustomModel = new ArrayList<>();
                    //loop through every JSONArray
                    for (int j = 0; j < jsonArray.length(); j++) {
                        //loop through every JSONObject
                        JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                        String name = (String) jsonObject.get("Name");
                        boolean flag = (boolean) jsonObject.get("isSelected");
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
        parentAdapter = new MyCustomParentAdapter(ctx, hMapHeaderAndCount, objHashmap, this);
        recycl1.setAdapter(parentAdapter);


//        String str = "Jay,Abhi,Aravind";
//        List<String> lst = Arrays.asList(str.split(","));
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

    @Override
    public void changeItems(List<CustomModel> items/*, ChildAdapter.ListenerCountCallback myCustomParentAdapter*/) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
        recycle2.setLayoutManager(mLayoutManager);
        recycle2.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL));
        recycle2.setItemAnimator(new DefaultItemAnimator());
        recycle2.setNestedScrollingEnabled(false);
        recycle2.setAdapter(new ChildAdapter(this, items, parentAdapter));
    }

    void populateDummyData() {
        LinkedHashMap<String, List<CustomModel>> hList = new LinkedHashMap<>();
        HashMap<String, String> hMapHeaderAndCount = new HashMap<>();
        hMapHeaderAndCount.put("Skills", "0");
        hMapHeaderAndCount.put("Location", "0");

        hList.put("Skills", Arrays.asList(
                new CustomModel("Biker", false),
                new CustomModel("DeliveryBoy", false),
                new CustomModel("SalesBoy", false),
                new CustomModel("Banker", false)
        ));


        hList.put("Location", Arrays.asList(
                new CustomModel("Mumbai", false),
                new CustomModel("Pune", false),
                new CustomModel("Thane", false),
                new CustomModel("Vikhroli", false)
        ));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycl1.setLayoutManager(mLayoutManager);
        recycl1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recycl1.setItemAnimator(new DefaultItemAnimator());
        recycl1.setNestedScrollingEnabled(false);
        parentAdapter = new MyCustomParentAdapter(ctx, hMapHeaderAndCount, hList, this);
        recycl1.setAdapter(parentAdapter);
    }

    //region Ignore

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

    //...
    /* HashMap<HashMap<String, String>, List<CustomModel>> lstHashMap = new HashMap<>();

        HashMap<String, String> headerAndCount = new HashMap<>();
        //headerAndCount.put("Skills", "1");
        //headerAndCount.put("Location", "2");
        for (int i = 0; i < 2; i++) {
            headerAndCount = new HashMap<>();
            headerAndCount.put(i == 0 ? ("Skills") : ("Location"), i + "");
            lstHashMap.put(headerAndCount, Arrays.asList(
                    (new CustomModel(i == 0 ? "Biker" : "Thane", false)),
                    (new CustomModel(i == 0 ? "DeliveryBoy" : "NaviMumbai", false)),
                    (new CustomModel(i == 0 ? "SalesBoy" : "Mumbai", false)),
                    (new CustomModel(i == 0 ? "Banker" : "Pune", false))
            ));
            int x = 5;
        }*/

    //List< Map<String, List<CustomModel>> > list = new ArrayList<Map<String, List<CustomModel>>>();//This is the final list you need

        /*Map<String, List<CustomModel>> map1 = new HashMap<>();
        //This is one instance of the  map you want to store in the above list.
        //List<String> arraylist1 = new ArrayList<String>();
        //arraylist1.add("Text1");//And so on..
        map1.put("Skills", Arrays.asList(
                new CustomModel("Biker", false),
                new CustomModel("DeliveryBoy", false),
                new CustomModel("SalesBoy", false),
                new CustomModel("Banker", false)
                ));
        //And so on...
        list.add(map1);//In this way you can add.

        Map<String, List<CustomModel>> map2 = new HashMap<>();
        map2.put("Location", Arrays.asList(
                (new CustomModel("Mumbai", false)),
                (new CustomModel("Pune", false)),
                (new CustomModel("Thane", false)),
                (new CustomModel("Vikhroli", false))));


        list.add(map2);//In this way you can add.*/
    //endregion
}