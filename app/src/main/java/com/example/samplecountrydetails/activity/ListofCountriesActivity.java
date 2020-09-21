package com.example.samplecountrydetails.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.samplecountrydetails.R;
import com.example.samplecountrydetails.adaptor.ShowCountryDetailsRecyclerViewAdapter;
import com.example.samplecountrydetails.listener.CallBack;
import com.example.samplecountrydetails.model.AllCountryDetailsListModel;
import com.example.samplecountrydetails.model.CountryDetailsModel;
import com.example.samplecountrydetails.model.CountryNameFlagModel;
import com.example.samplecountrydetails.network.ApiClient;
import com.example.samplecountrydetails.network.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListofCountriesActivity extends AppCompatActivity implements CallBack {

    CallBack callBack;
    private ApiInterface apiInterface;
    public static final String TAG = "ListofCountriesActivity";
    private List<CountryNameFlagModel> countryNameFlagModelList;
    private ShowCountryDetailsRecyclerViewAdapter showCountryDetailsRecyclerViewAdapter;
    private HashMap<String, CountryDetailsModel> countryDetailsHashMap;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_countries);
        callBack = this;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        countryNameFlagModelList = new ArrayList<>();
        countryDetailsHashMap = new HashMap<>();
        fetchAllCountiesDetails();
        activity = this;
        setUpRecyclerView(this,countryNameFlagModelList,activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                showCountryDetailsRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    /*create REST API call for get all country details*/
    private void fetchAllCountiesDetails(){
        Call<List<AllCountryDetailsListModel>> allCountryDetailsListCall = apiInterface.getAllCountryDetails();
        allCountryDetailsListCall.enqueue(new Callback<List<AllCountryDetailsListModel>>() {
            @Override
            public void onResponse(Call<List<AllCountryDetailsListModel>> call, Response<List<AllCountryDetailsListModel>> response) {
                try {
                    if (!response.isSuccessful()) {
                        int code = response.code();
                        Log.d(TAG,"RESPONSE CODE:: "+code);
                        return;
                    }
                    List<AllCountryDetailsListModel> allCountryDetailsList = response.body();
                    if (allCountryDetailsList != null) {
                        for (int i = 0; i < allCountryDetailsList.size(); i++) {
                            CountryNameFlagModel countryNameFlagModel = new CountryNameFlagModel();
                            countryNameFlagModel.setCountryName(allCountryDetailsList.get(i).getName());
                            countryNameFlagModel.setCountryFlag(allCountryDetailsList.get(i).getFlag());
                            countryNameFlagModelList.add(countryNameFlagModel);

                            CountryDetailsModel countryDetailsModel = new CountryDetailsModel();
                            countryDetailsModel.setCountryName(allCountryDetailsList.get(i).getName());
                            countryDetailsModel.setFlagImage(allCountryDetailsList.get(i).getFlag());
                            countryDetailsModel.setCapital(allCountryDetailsList.get(i).getCapital());
                            countryDetailsModel.setRegion(allCountryDetailsList.get(i).getRegion());
                            countryDetailsModel.setSubRegion(allCountryDetailsList.get(i).getSubregion());
                            countryDetailsModel.setArea(allCountryDetailsList.get(i).getArea());
                            countryDetailsModel.setPopulation(allCountryDetailsList.get(i).getPopulation());
                            countryDetailsHashMap.put(allCountryDetailsList.get(i).getName(),countryDetailsModel);
                        }
                        setUpRecyclerView(ListofCountriesActivity.this,countryNameFlagModelList,activity);
                    }
                }catch (Exception ex){
                    Log.d(TAG,"RESPONSE EXCEPTION:: "+ex.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<AllCountryDetailsListModel>> call, Throwable t) {
                Log.d(TAG,"FAILURE RESPONSE:: "+t.getMessage());
            }
        });
    }

    /*load data inside recyclerview*/
    private void setUpRecyclerView(Context context, List<CountryNameFlagModel> countryNameFlagModelList, Activity activity){
        RecyclerView recyclerView = findViewById(R.id.country_name_flag_recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        showCountryDetailsRecyclerViewAdapter = new ShowCountryDetailsRecyclerViewAdapter(context, countryNameFlagModelList,activity, callBack);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(showCountryDetailsRecyclerViewAdapter);
    }

    @Override
    public void countryNameCallBack(String countryName) {
        CountryDetailsModel countryDetailsModel = countryDetailsHashMap.get(countryName);
        Intent intent = new Intent(this,ViewSingleCountryDetailsActivity.class);
        intent.putExtra("MY_CLASS_OBJ",countryDetailsModel);
        startActivity(intent);
    }
}
