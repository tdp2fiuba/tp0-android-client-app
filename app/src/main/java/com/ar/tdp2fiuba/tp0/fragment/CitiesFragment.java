package com.ar.tdp2fiuba.tp0.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ar.tdp2fiuba.tp0.adapter.CitiesRecyclerViewAdapter;
import com.ar.tdp2fiuba.tp0.R;
import com.ar.tdp2fiuba.tp0.model.City;
/**
 * A fragment representing a list of Cities.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnCitiesFragmentTapListener}
 * interface.
 */
public class CitiesFragment extends Fragment {

    private OnCitiesFragmentTapListener mListener;

    public CitiesFragment() {}

    public static CitiesFragment newInstance() {
        return new CitiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new CitiesRecyclerViewAdapter(mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCitiesFragmentTapListener) {
            mListener = (OnCitiesFragmentTapListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCitiesFragmentTapListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCitiesFragmentTapListener {
        void onCitySelected(City city);
    }
}
