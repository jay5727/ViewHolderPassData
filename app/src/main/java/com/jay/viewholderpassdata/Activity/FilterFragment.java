/*
package com.jay.viewholderpassdata.Activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.innov.recruiter.R;
import com.innov.recruiter.adapters.FilterAdapter;
import com.innov.recruiter.adapters.SkillFilterAdapter;
import com.innov.recruiter.events.ResponceEvent;
import com.innov.recruiter.ui.activities.RecyclerTouchListener;
import com.innov.recruiter.utils.InnovConstant;
import com.innov.recruiter.webservices.InnovWebservices;
import com.innov.recruiter.webservices.request.SkillRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

import static com.innov.recruiter.events.ResponceEvent.EventType.ResponceGetFilterList;
import static com.innov.recruiter.events.ResponceEvent.EventType.ResponceGetSkillList;
import static com.innov.recruiter.utils.InnovUtils.connectedToInternet;
import static com.innov.recruiter.utils.InnovUtils.getSharedPreferences;
import static com.innov.recruiter.utils.InnovUtils.showToastMessage;
import static com.innov.recruiter.utils.InnovUtils.simpleClassName;

*/
/**
 * Created by abhisheksharma on 11-Sep-2018.
 *//*


public class FilterFragment extends InnovBaseFragment {

    private static String TAG = simpleClassName(FilterFragment.class);
    Context appContext;
    @BindView(R.id.recycl1)
    RecyclerView recycl1;
    @BindView(R.id.recycle2)
    RecyclerView recycle2;
    @BindView(R.id.close)
    Button close;
    @BindView(R.id.apply)
    Button apply;
    private FilterAdapter mAdapter;
    private SkillFilterAdapter mAdapterSkill;
    List<String> skillliset = new ArrayList<String>();

    public static FilterFragment newInstance() {

        return new FilterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appContext = this.getActivity();
        Fabric.with(appContext, new Crashlytics());
        View view = inflater.inflate(R.layout.filterscreen, container, false);
        ButterKnife.bind(this, view);
        GetFilterData();
        return view;
    }

    private void GetFilterData() {
        if (!connectedToInternet(appContext)) {
            Timber.e("No internet connection!");
            showToastMessage(appContext, "Please check your internet connectivity", "L");
            return;
        }
        InnovWebservices.getInstance(new Object()).setWebService(getActivity(), InnovWebservices.GETRECRUITERFILTERLIST).execute();
    }

    */
/**
     * For Responce
     *//*

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ResponceEvent event) {
        Bundle bundleArgs = event.getExtras();

        if (bundleArgs.getString("Responce") == null) {
            showToastMessage(appContext, "Server Error Please try again..", "S");
            return;
        }
        if (event.myEventType == ResponceGetFilterList) {
            Log.e(TAG, "event responce : " + bundleArgs.getString("Responce"));
            setFilterList(bundleArgs.getString("Responce").toString());
        } else if (event.myEventType == ResponceGetSkillList) {
            Log.e(TAG, "event responce : " + bundleArgs.getString("Responce"));
            setSkillList(bundleArgs.getString("Responce").toString());
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setFilterList(String responce) {
        try {
            JSONObject jsonObject = new JSONObject(responce);
            String status = jsonObject.getString("Status");
            if (status.equalsIgnoreCase("Suceess")) {
                JSONArray filter = jsonObject.getJSONArray("FilterList");
                final ArrayList<String> lisvalueforfilter = new Gson().fromJson(filter.toString(), new TypeToken<List<String>>() {
                }.getType());
                Log.e("filteredlist", filter + " ");
                mAdapter = new FilterAdapter(lisvalueforfilter);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recycl1.setLayoutManager(mLayoutManager);
                recycl1.setItemAnimator(new DefaultItemAnimator());
                recycl1.addItemDecoration(new DividerItemDecoration(recycl1.getContext(), DividerItemDecoration.VERTICAL));
                recycl1.setAdapter(mAdapter);
                recycl1.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recycl1, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        String listitem = lisvalueforfilter.get(position).toString().trim();


                        if (listitem.equalsIgnoreCase("Skill")) {
                            if (!connectedToInternet(appContext)) {
                                Timber.e("No internet connection!");
                                showToastMessage(appContext, "Please check your internet connectivity", "L");
                                return;
                            } else {
                                SkillRequest request = new SkillRequest();
                                String recruiterID = getSharedPreferences(getActivity(), InnovConstant.Recuter_ID).trim();
                                request.setRecruiterID(recruiterID);
                                request.setType(listitem);
                                InnovWebservices.getInstance(request).setWebService(getActivity(), InnovWebservices.GETRECRUITERFILTERLISTSKILL).execute();
                            }

                        } else if (listitem.equalsIgnoreCase("Recently Added")) {

                        } else if (listitem.equalsIgnoreCase("Location")) {
                            if (!connectedToInternet(appContext)) {
                                Timber.e("No internet connection!");
                                showToastMessage(appContext, "Please check your internet connectivity", "L");
                                return;
                            } else {
                                SkillRequest request = new SkillRequest();
                                String recruiterID = getSharedPreferences(getActivity(), InnovConstant.Recuter_ID);
                                request.setRecruiterID(recruiterID);
                                request.setType(listitem);
                                InnovWebservices.getInstance(request).setWebService(getActivity(), InnovWebservices.GETRECRUITERFILTERLISTSKILL).execute();
                            }
                        } else {

                        }
                        Toast.makeText(getActivity(), listitem + " is selected!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));


            } else {
                showToastMessage(appContext, "Invalid OTP", "S");
            }
        } catch (Exception e) {
            Log.e(TAG, " json Exception " + e.toString());
        }

    }

    private void setSkillList(String responce) {
        try {
            JSONObject jsonObject = new JSONObject(responce);
            String status = jsonObject.getString("Status");
            if (status.equalsIgnoreCase("Success")) {

                String skillfiltered = jsonObject.getString("FilterDataList");
                skillliset = Arrays.asList(skillfiltered.split(","));
                Log.e("filteredlist", skillfiltered + " ");
                mAdapterSkill = new SkillFilterAdapter(skillliset);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recycle2.setLayoutManager(mLayoutManager);
                recycle2.setItemAnimator(new DefaultItemAnimator());
                recycle2.addItemDecoration(new DividerItemDecoration(recycle2.getContext(), DividerItemDecoration.VERTICAL));
                recycle2.setAdapter(mAdapterSkill);
                recycle2.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recycle2, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        String listitem = skillliset.get(position);
                        Toast.makeText(getActivity(), listitem + " is selected!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
            }
        } catch (Exception e) {
            Log.e(TAG, " json Exception " + e.toString());
        }
    }


}
*/
