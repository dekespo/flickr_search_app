package com.dekespo.flickr_search_app.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.dekespo.flickr_search_app.R;
import com.dekespo.flickr_search_app.models.FlickrPhoto;
import com.dekespo.flickr_search_app.models.FlickrResult;
import com.dekespo.flickr_search_app.retro.ApiService;
import com.dekespo.flickr_search_app.retro.RetroClient;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment
{

    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);


        ApiService api = RetroClient.getApiService();
        Call<FlickrResult> call = api.getFlickrResult();

        call.enqueue(new Callback<FlickrResult>()
        {
            @Override
            public void onResponse(Call<FlickrResult> call, Response<FlickrResult> response)
            {
                if (response.isSuccessful())
                {
                    ArrayList<FlickrPhoto> flickrPhotoList = response.body().getPhotos().getPhoto();
                    final ArrayAdapter arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, flickrPhotoList);
                    addListView(root, arrayAdapter);
                    addSearchView(root, arrayAdapter);
                }
                else
                {
                    Log.e("DEKE", "Failed on response");
                }
            }

            @Override
            public void onFailure(Call<FlickrResult> call, Throwable t)
            {
                Log.e("DEKE", "Failed with " + t.toString());
            }
        });


        return root;
    }


    private void addListView(final View root, final ArrayAdapter arrayAdapter)
    {
        final ListView list = root.findViewById(R.id.list);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // TODO: Make this generic or something else
                FlickrPhoto flickrPhoto = (FlickrPhoto) list.getItemAtPosition(position);
                String clickedItem = flickrPhoto.toString();
                Toast.makeText(mContext, clickedItem, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addSearchView(final View root, final ArrayAdapter arrayAdapter)
    {
        SearchView search = root.findViewById(R.id.search);
        search.setActivated(true);
        search.setQueryHint("Type your keyword here");
        search.onActionViewExpanded();
        search.setIconified(false);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}