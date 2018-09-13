package com.jay.viewholderpassdata.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.jay.viewholderpassdata.R;
import com.jay.viewholderpassdata.model.CustomModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhisheksharma on 12-Sep-2018.
 */

public class SkillFilterAdapter extends RecyclerView.Adapter<SkillFilterAdapter.MyViewHolder> {
    List<CustomModel> Skillfilterlist = null;

    public SkillFilterAdapter(List<CustomModel> listfilter) {
        this.Skillfilterlist = listfilter;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_column_skill, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String val = "";
        try {
            val = Skillfilterlist.get(position).getName();
        } catch (Exception ex) {
            String s = ex.getMessage();
        }
        //holder.listitem.setText(Skillfilterlist.get(position));
        holder.listitem.setText(val);
    }

    @Override
    public int getItemCount() {
        return Skillfilterlist == null ? 0 : Skillfilterlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView listitem;
        ImageView tickmark;

        public MyViewHolder(View itemView) {
            super(itemView);
            listitem = (TextView) itemView.findViewById(R.id.header_name);
            tickmark = (ImageView) itemView.findViewById(R.id.tickmark);
        }
    }
}
