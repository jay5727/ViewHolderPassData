package com.jay.viewholderpassdata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jay.viewholderpassdata.R;
import com.jay.viewholderpassdata.model.CustomModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Jay on 13-09-2018.
 */

public class MyCustomParentAdapter extends RecyclerView.Adapter<MyCustomParentAdapter.ViewHolder>
        implements ChildAdapter.ListenerCountCallback {

    private HashMap<String, String> hMapHeaderAndCount = null;
    public LinkedHashMap<String, List<CustomModel>> lstData = null;
    //public List<Map<String, List<CustomModel>>> lstData=null;
    //HashMap<List<String>, List<CustomModel>> lstData = new HashMap<>();
    private String lastKeyClicked = null;
    public List<String> lstKey = null;
    private Context ctx;


    private ChildCallback mListener;

    public interface ChildCallback {
        void changeItems(List<CustomModel> items);
        //void changeItems(List<CustomModel> items, ChildAdapter.ListenerCountCallback adapter);
        //void changeItems(List<CustomModel> items, MyCustomParentAdapter adapter);
    }

    public MyCustomParentAdapter(Context ctx
                               /*  HashMap<List<String>, List<CustomModel>> lstData*/
                                 /*List<Map<String, List<CustomModel>>> lstData*/,
                                 HashMap<String, String> hMapHeaderAndCount,
                                 LinkedHashMap<String, List<CustomModel>> lstData,
                                 ChildCallback childCallback) {
        this.mListener = childCallback;
        this.ctx = ctx;
        this.lstData = lstData;
        this.hMapHeaderAndCount = hMapHeaderAndCount;
        this.lstKey = getKeys();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_column, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final List<CustomModel> lstChild = lstData.get(lstKey.get(position));
        String count = hMapHeaderAndCount.get(lstKey.get(position)) != null
                && !hMapHeaderAndCount.get(lstKey.get(position)).equals("0") ?
                hMapHeaderAndCount.get(lstKey.get(position)) : "";

        holder.tvCount.setText(count);
        holder.header_name.setText(lstKey.get(position));

        holder.header_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastKeyClicked = lstKey.get(position);
                mListener.changeItems(lstChild/*, MyCustomParentAdapter.this*/);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstData == null ? 0 : lstData.size();
    }

    @Override
    public void notifyParentAdapter(int count/*, List<CustomModel> items*/) {
        if (lastKeyClicked != null) {
            hMapHeaderAndCount.put(lastKeyClicked, String.valueOf(count));
            notifyDataSetChanged();
        }
    }

    private List<String> getKeys() {
        if (lstData == null)
            return null;
        List<String> keys = new ArrayList<>();
        for (String key : lstData.keySet()) {
            keys.add(key);
        }
        return keys;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView header_name;
        TextView tvCount;
        ImageView tickmark;
        //RecyclerView recycle2;


        public ViewHolder(View itemView) {
            super(itemView);
            header_name = (TextView) itemView.findViewById(R.id.header_name);
            tickmark = (ImageView) itemView.findViewById(R.id.tickmark);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            //recycle2 = (RecyclerView) itemView.findViewById(R.id.recycle2);
        }
    }
}