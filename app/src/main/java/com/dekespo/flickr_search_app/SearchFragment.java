package com.dekespo.flickr_search_app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.dekespo.flickr_search_app.models.FlickrPhoto;
import com.dekespo.flickr_search_app.models.FlickrResult;
import com.dekespo.flickr_search_app.retro.FlickerApi;
import com.dekespo.flickr_search_app.retro.FlickrClient;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment
{
    private Context mContext;
    private FlickerApi mFlickerApi;
    private Button mFlickrSearchButton;
    private SearchView mFlickrSearchView;
    private ListView mFlickrPhotoListView;

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        SearchViewModel searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_search, container, false);

        mFlickrSearchView = root.findViewById(R.id.search_view);
        mFlickrSearchView.setActivated(true);
        mFlickrSearchView.setQueryHint("Keyword for flickr");
        mFlickrSearchView.onActionViewExpanded();
        mFlickrSearchView.setIconified(false);

        mFlickrSearchButton = root.findViewById(R.id.search_button);

        mFlickrPhotoListView = root.findViewById(R.id.list);

        mFlickerApi = FlickrClient.getApiService();

        return root;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mFlickrSearchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String searchText = mFlickrSearchView.getQuery().toString();
                if (searchText.isEmpty())
                    return;

                Call<FlickrResult> call = mFlickerApi.getPhotosSearchResult(
                        FlickrClient.METHOD,
                        FlickrClient.API_KEY,
                        FlickrClient.FORMAT,
                        FlickrClient.NO_JSON_CALLBACK,
                        searchText
                );
                call.enqueue(new Callback<FlickrResult>()
                {
                    @Override
                    public void onResponse(Call<FlickrResult> call, Response<FlickrResult> response)
                    {
                        if (response.isSuccessful())
                        {
                            ArrayList<FlickrPhoto> flickrPhotoList = response.body().getPhotos().getPhoto();
                            final ArrayAdapter arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, flickrPhotoList);
                            mFlickrPhotoListView.setAdapter(arrayAdapter);
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
            }
        });
    }
}