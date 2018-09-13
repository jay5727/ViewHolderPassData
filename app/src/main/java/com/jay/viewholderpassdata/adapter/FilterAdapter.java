package com.jay.viewholderpassdata.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jay.viewholderpassdata.R;

import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by abhisheksharma on 11-Sep-2018.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {
    ArrayList<String> filterlist = new ArrayList<String>();

    public FilterAdapter(ArrayList<String> filter) {
        this.filterlist = filter;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_column, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(filterlist.get(position));
    }

    @Override
    public int getItemCount() {
        return filterlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.header_name);
        }
    }


}
