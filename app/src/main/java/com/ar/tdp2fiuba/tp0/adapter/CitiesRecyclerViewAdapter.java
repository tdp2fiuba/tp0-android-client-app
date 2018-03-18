package com.ar.tdp2fiuba.tp0.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ar.tdp2fiuba.tp0.R;
import com.ar.tdp2fiuba.tp0.fragment.CitiesFragment;
import com.ar.tdp2fiuba.tp0.model.City;
import com.ar.tdp2fiuba.tp0.service.CitiesService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.ar.tdp2fiuba.tp0.model.City} and makes a call to the
 * specified {@link CitiesFragment.OnCitiesFragmentTapListener}.
 */
public class CitiesRecyclerViewAdapter extends RecyclerView.Adapter<CitiesRecyclerViewAdapter.ViewHolder> {

    private final List<City> mCities;
    private final CitiesFragment.OnCitiesFragmentTapListener mListener;

    public CitiesRecyclerViewAdapter(CitiesFragment.OnCitiesFragmentTapListener listener) {
        mListener = listener;
        mCities = new LinkedList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mCities.get(position);
        holder.mCityNameView.setText(mCities.get(position).name);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onCitySelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mCityNameView;
        public City mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCityNameView = (TextView) view.findViewById(R.id.city_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCityNameView.getText() + "'";
        }
    }

    public void add(City city) {
        mCities.add(city);
        notifyItemInserted(mCities.size() - 1);
    }

    public void addAll(List<City> cities) {
        for (City city : cities) {
            add(city);
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public City getItem(int position) {
        return mCities.get(position);
    }
}
