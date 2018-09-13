package com.jay.viewholderpassdata.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jay.viewholderpassdata.R;
import com.jay.viewholderpassdata.model.CustomModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jay on 13-09-2018.
 */

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {

    HashMap<String, List<CustomModel>> lstData = null;
    List<CustomModel> lstChild = null;
    List<String> lstKey = null;

    Context ctx;

   /* private ListenerCountCallback mListener;

    public interface ListenerCountCallback {
        void notifyParentAdapter(int position, List<CustomModel> items);
    }*/

    public ChildAdapter(Context ctx, /*HashMap<String, List<CustomModel>> lstData,*/ List<CustomModel> lstChild)
    //,ListenerCountCallback listener)
    {
        //this.mListener = listener;
        //this.lstData = lstData;
        this.lstKey = getKeys();
        this.lstChild = lstChild;
        this.ctx = ctx;
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_column_skill, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.childheader_name.setText(lstChild.get(position).getName());
     /*   holder.tickmark.setBackgroundResource(lst.get(position).getIsSelected() ?
                R.drawable.correctgreen : R.drawable.correctgray);*/

        holder.tickmark.setBackgroundDrawable(ContextCompat.getDrawable(ctx, lstChild.get(position).getIsSelected() ?
                R.drawable.correctgreen : R.drawable.correctgray));

        holder.linLayParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TOGGLE IT
                lstChild.get(position).setIsSelected(!lstChild.get(position).getIsSelected());
                holder.tickmark.setBackgroundDrawable(ContextCompat.getDrawable(ctx, lstChild.get(position).getIsSelected() ?
                        R.drawable.correctgreen : R.drawable.correctgray));
                /*if (mListener != null) {
                    mListener.notifyParentAdapter(position, lstChild);
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstChild != null ? lstChild.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView childheader_name;
        ImageView tickmark;
        LinearLayout linLayParent;

        public ViewHolder(View itemView) {
            super(itemView);
            childheader_name = (TextView) itemView.findViewById(R.id.header_name);
            tickmark = (ImageView) itemView.findViewById(R.id.tickmark);
            linLayParent = (LinearLayout) itemView.findViewById(R.id.linLayParent);
        }
    }
}
