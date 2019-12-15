package com.dekespo.flickr_search_app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.dekespo.flickr_search_app.helper.FlickrCommentAdapter;
import com.dekespo.flickr_search_app.models.FlickrCommentResult;
import com.dekespo.flickr_search_app.models.FlickrPhoto;
import com.dekespo.flickr_search_app.retro.FlickerApi;
import com.dekespo.flickr_search_app.retro.FlickrClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.dekespo.flickr_search_app.models.FlickrPhoto.FLICKR_PHOTO_KEY;

public class DetailFragment extends Fragment
{
    private static final String TAG = "DetailFragment";
    private static final String METHOD = "flickr.photos.comments.getList";

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


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
        final View root = inflater.inflate(R.layout.fragment_detail, container, false);
        FlickrPhoto photo = (FlickrPhoto) getArguments().getSerializable(FLICKR_PHOTO_KEY);
        ImageView imageView = root.findViewById(R.id.detail_image);
        Glide.with(getContext())
                .load(photo.getUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_menu_camera)
                .into(imageView);

        final ListView flickrCommentListView = root.findViewById(R.id.detail_listView);
        FlickerApi client = FlickrClient.getApiService();

        Single<FlickrCommentResult> resultObservable = client.getPhotoComments(
                METHOD,
                FlickrClient.API_KEY,
                photo.getId(),
                FlickrClient.FORMAT,
                FlickrClient.NO_JSON_CALLBACK
        );

        resultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FlickrCommentResult>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(FlickrCommentResult flickrCommentResult)
                    {
                        Log.d(TAG, "Success in getting comment list");
                        if (flickrCommentResult != null && flickrCommentResult.getComments() != null && flickrCommentResult.getComments().getComment() != null)
                        {
                            final FlickrCommentAdapter commentAdapter = new FlickrCommentAdapter(mContext, flickrCommentResult.getComments().getComment());
                            flickrCommentListView.setAdapter(commentAdapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.e(TAG, "Error in observer " + e);
                    }
                });

        Log.d(TAG, "Uploaded comments");

        return root;
    }

    @Override
    public void onDestroy()
    {
        // DO NOT CALL .dispose()
        mCompositeDisposable.clear();
        super.onDestroy();
    }

}