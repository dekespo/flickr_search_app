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
import com.dekespo.flickr_search_app.helper.ApiService;
import com.dekespo.flickr_search_app.helper.Employee;
import com.dekespo.flickr_search_app.helper.EmployeeList;
import com.dekespo.flickr_search_app.helper.RetroClient;

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

        final ArrayAdapter arrayAdapter = generateArrayAdapter();
        addListView(root, arrayAdapter);
        addSearchView(root, arrayAdapter);

        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        Call<EmployeeList> call = api.getMyJSON();

        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<EmployeeList>()
        {
            @Override
            public void onResponse(Call<EmployeeList> call, Response<EmployeeList> response)
            {

                if (response.isSuccessful())
                {
                    /**
                     * Got Successfully
                     */
                    ArrayList<Employee> employeeList = response.body().getEmployee();
                    for (Employee employee : employeeList)
                    {
                        Log.e("DEKE", "Employee name " + employee.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<EmployeeList> call, Throwable t)
            {
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
                String clickedItem = (String) list.getItemAtPosition(position);
                Toast.makeText(mContext, clickedItem, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayAdapter<String> generateArrayAdapter()
    {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("JAVA");
        arrayList.add("ANDROID");
        arrayList.add("C Language");
        arrayList.add("CPP Language");
        arrayList.add("Go Language");
        arrayList.add("AVN SYSTEMS");
        return new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, arrayList);
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