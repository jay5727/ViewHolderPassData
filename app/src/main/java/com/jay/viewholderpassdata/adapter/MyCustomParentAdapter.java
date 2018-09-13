package com.jay.viewholderpassdata.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
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
import java.util.List;

/**
 * Created by Jay on 13-09-2018.
 */

public class MyCustomParentAdapter extends RecyclerView.Adapter<MyCustomParentAdapter.ViewHolder>
        //implements ChildAdapter.ListenerCountCallback
{


    public HashMap<String, List<CustomModel>> lstData = null;
    public List<String> lstKey = null;
    Context ctx;

    private ChildCallback mListener;

    /*@Override
    public void notifyParentAdapter(int parentPosition,List<CustomModel> items) {
        //lstData.get(parentPosition)
    }*/

    public interface ChildCallback {
        void changeItems(List<CustomModel> items);
    }

    public MyCustomParentAdapter(Context ctx, HashMap<String, List<CustomModel>> lstData, ChildCallback childCallback) {
        this.mListener = childCallback;
        this.ctx = ctx;
        this.lstData = lstData;
        this.lstKey = getKeys();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_column, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (lstKey == null) return;
        String key = lstKey.get(position);

        final List<CustomModel> lstChild = lstData.get(key);

        holder.header_name.setText(lstKey.get(position));

        /*RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
        holder.recycle2.setLayoutManager(mLayoutManager);
        holder.recycle2.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL));
        holder.recycle2.setItemAnimator(new DefaultItemAnimator());
        holder.recycle2.setAdapter(new SkillFilterAdapter(lst));*/


        holder.header_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.changeItems(lstChild);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstData == null ? 0 : lstData.size();
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