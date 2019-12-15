package com.dekespo.flickr_search_app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.SearchView;

import com.dekespo.flickr_search_app.helper.FlickrPhotoAdapter;
import com.dekespo.flickr_search_app.models.FlickrResult;
import com.dekespo.flickr_search_app.retro.FlickerApi;
import com.dekespo.flickr_search_app.retro.FlickrClient;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

    private static final String METHOD = "flickr.photos.search";
    /*
    1 public photos
    2 private photos visible to friends
    3 private photos visible to family
    4 private photos visible to friends & family
    5 completely private photos
     */
    private static final int PRIVACY_FILTER = 1;
    /*
    1 for safe.
    2 for moderate.
    3 for restricted.
     */
    private static final int SAFE_SEARCH = 1;
    // Possible values are all (default), photos or videos
    private static final String MEDIA = "photos";

    private int mContentType;
    private boolean mIsGallery;

    // Other
    //    per_page (Optional)
    //    Number of photos to return per page. If this argument is omitted, it defaults to 100. The maximum allowed value is 500.
    //    page (Optional)
    //    The page of results to return. If this argument is omitted, it defaults to 1

    private FlickerApi mFlickerApi;
    private Button mFlickrSearchButton;
    private SearchView mFlickrSearchView;
    private GridView mFlickrPhotoGridView;
    private MainActivity mMainAcitivity;

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        if (context instanceof MainActivity)
        {
            mMainAcitivity = (MainActivity) context;
        }
        else
        {
            Log.e(TAG, "Cannot set the activity!");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.fragment_search, container, false);

        mFlickrPhotoGridView = root.findViewById(R.id.gridView);


        SwitchDrawerItem layoutToggleDrawerItem = new SwitchDrawerItem()
                .withName("Layout: Grid or List")
                .withOnCheckedChangeListener(new OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked)
                    {
                        int gridColumnNo = 1;
                        if (isChecked)
                            gridColumnNo = 2;
                        mFlickrPhotoGridView.setNumColumns(gridColumnNo);
                    }
                });
        SwitchDrawerItem contentTypeSwitch = new SwitchDrawerItem()
                .withName("Content Type: Photos or Screenshots")
                .withOnCheckedChangeListener(new OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked)
                    {
                        mContentType = 1;
                        if (isChecked)
                            mContentType = 2;
                    }
                });
        SwitchDrawerItem isGallerySwitch = new SwitchDrawerItem()
                .withName("Gallery")
                .withOnCheckedChangeListener(new OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked)
                    {
                        mIsGallery = isChecked;
                    }
                });


        DrawerBuilder drawerBuilder = new DrawerBuilder();
        drawerBuilder
                .withActivity(mMainAcitivity)
                .addDrawerItems(
                        layoutToggleDrawerItem,
                        contentTypeSwitch,
                        isGallerySwitch
                )
                .build();


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
                METHOD,
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
                    if (response.body() != null)
                    {
                        loadImageWithGlide(response.body());
                    }
                    else
                    {
                        Log.e(TAG, "Response body is empty");
                    }
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
                METHOD,
                FlickrClient.API_KEY,
                searchText,
                PRIVACY_FILTER,
                SAFE_SEARCH,
                mContentType,
                MEDIA,
                mIsGallery,
                FlickrClient.FORMAT,
                FlickrClient.NO_JSON_CALLBACK);
        resultObservable.subscribeOn(Schedulers.io()) // “work” on io thread
                .observeOn(AndroidSchedulers.mainThread()) // “listen” on UIThread
                .cache()
                .subscribe(new SingleObserver<FlickrResult>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(FlickrResult flickrResult)
                    {
                        loadImageWithGlide(flickrResult);
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.e(TAG, "Error in observer");
                    }
                });
    }

    private void loadImageWithGlide(FlickrResult flickrResult)
    {
        final FlickrPhotoAdapter photoAdapter = new FlickrPhotoAdapter(mMainAcitivity, flickrResult.getPhotos().getPhoto(), getFragmentManager().beginTransaction());
        mFlickrPhotoGridView.setAdapter(photoAdapter);
    }

}