package com.ar.tdp2fiuba.tp0.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ar.tdp2fiuba.tp0.adapter.CitiesRecyclerViewAdapter;
import com.ar.tdp2fiuba.tp0.R;
import com.ar.tdp2fiuba.tp0.adapter.PaginationScrollListener;
import com.ar.tdp2fiuba.tp0.model.City;
import com.ar.tdp2fiuba.tp0.service.CitiesService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * A fragment representing a list of Cities.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnCitiesFragmentTapListener}
 * interface.
 */
public class CitiesFragment extends Fragment {

    public interface OnCitiesFragmentTapListener {
        void onCitySelected(City city);
    }

    private OnCitiesFragmentTapListener mListener;

    private CitiesRecyclerViewAdapter mAdapter;

    private static int currentPage = 0;
    private boolean isLoading = true;

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

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.city_list_recycler_view);
        Context context = view.getContext();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CitiesRecyclerViewAdapter(mListener);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                retrieveMoreCities();
            }

            @Override
            public int getTotalPageCount() {
                return 740;
            }

            @Override
            public boolean isLastPage() {
                return currentPage == getTotalPageCount();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            protected int getLoadingOffset() {
                return 20;
            }
        });
        retrieveMoreCities();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        currentPage = 0;
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

    private void stopLoading() {
        isLoading = false;
        View view = getView();
        if (view != null) {
            view.findViewById(R.id.city_list_progress_bar).setVisibility(View.GONE);
        }
    }

    private void startLoading() {
        isLoading = true;
        View view = getView();
        if (view != null) {
            view.findViewById(R.id.city_list_progress_bar).setVisibility(View.VISIBLE);
        }
    }

    private void retrieveMoreCities() {
        final int count = 100;
        Response.Listener<JSONArray> successListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        mAdapter.add(new Gson().fromJson(response.getJSONObject(i).toString(), City.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                currentPage++;
                stopLoading();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                stopLoading();
            }
        };
        startLoading();
        CitiesService.getCities(currentPage + 1, count, successListener, errorListener);
    }

}
