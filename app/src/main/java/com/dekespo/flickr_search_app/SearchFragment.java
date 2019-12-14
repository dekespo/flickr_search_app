package com.dekespo.flickr_search_app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.dekespo.flickr_search_app.helper.FlickrPhotoAdapter;
import com.dekespo.flickr_search_app.models.FlickrPhoto;
import com.dekespo.flickr_search_app.models.FlickrResult;
import com.dekespo.flickr_search_app.retro.FlickerApi;
import com.dekespo.flickr_search_app.retro.FlickrClient;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment
{
    private static final String TAG = "SearchFragment";

    private Context mContext;
    private FlickerApi mFlickerApi;
    private Button mFlickrSearchButton;
    private SearchView mFlickrSearchView;
    private ListView mFlickrPhotoListView;

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

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
        mFlickrSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                getPhotosViaOnline();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });

        mFlickrSearchButton = root.findViewById(R.id.search_button);

        mFlickrPhotoListView = root.findViewById(R.id.list);

        mFlickerApi = FlickrClient.getApiService();

        return root;
    }

    @Override
    public void onDestroy()
    {
        // DO NOT CALL .dispose()
        mCompositeDisposable.clear();
        super.onDestroy();
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
                getPhotosViaOnline();
            }
        });
    }

    private void getPhotosViaOnline()
    {
        String searchText = mFlickrSearchView.getQuery().toString();
        mFlickrSearchView.clearFocus();
        if (searchText.isEmpty())
            return;

//        callAndLoadPhotos(searchText);
        observeAndLoadPhotos(searchText);

    }

    private void callAndLoadPhotos(String searchText)
    {
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
            public void onResponse(@NonNull Call<FlickrResult> call, @NonNull Response<FlickrResult> response)
            {
                if (response.isSuccessful())
                {
                    loadImageWithGlide(response.body().getPhotos().getPhoto());
                }
                else
                {
                    Log.e(TAG, "Failed on response");
                }
            }

            @Override
            public void onFailure(@NonNull Call<FlickrResult> call, Throwable t)
            {
                Log.e(TAG, "Failed with " + t.toString());
            }
        });
    }

    private void observeAndLoadPhotos(String searchText)
    {
        Single<FlickrResult> resultObservable = mFlickerApi.getPhotosSearchResultRxJava(
                FlickrClient.METHOD,
                FlickrClient.API_KEY,
                FlickrClient.FORMAT,
                FlickrClient.NO_JSON_CALLBACK,
                searchText);
        resultObservable.subscribeOn(Schedulers.io()) // “work” on io thread
                .observeOn(AndroidSchedulers.mainThread()) // “listen” on UIThread
                .cache()
                .subscribe(new SingleObserver<FlickrResult>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        Log.d(TAG, "Subscribing now");
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(FlickrResult flickrResult)
                    {
                        Log.d(TAG, "On success");
                        loadImageWithGlide(flickrResult.getPhotos().getPhoto());
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.e(TAG, "Error in observer");
                    }
                });
    }

    private void loadImageWithGlide(ArrayList<FlickrPhoto> flickrPhotoList)
    {
        final FlickrPhotoAdapter photoAdapter = new FlickrPhotoAdapter(mContext, flickrPhotoList);
        mFlickrPhotoListView.setAdapter(photoAdapter);
    }
}