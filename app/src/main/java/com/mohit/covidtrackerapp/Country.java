package com.mohit.covidtrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Country extends AppCompatActivity {
    private RecyclerView recyclerView;
    TextView defalutcountry,defaultcountrycases;
    EditText search;
    adapter countryadapter;
    ArrayList<countrydata> data;
    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        recyclerView = findViewById(R.id.recycker);
        search = findViewById(R.id.search);
        defalutcountry= findViewById(R.id.defaultcountry);
        defaultcountrycases = findViewById(R.id.defaultcountrycases);
        data = new ArrayList<>();
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading");
        dialog.show();
        String countryname = getIntent().getStringExtra("India");

        defalutcountry.setText(countryname);
         countryadapter = new adapter(data,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(countryadapter);
        apicontroller.getApiset().getCountryData().enqueue(new Callback<List<countrydata>>() {
            @Override
            public void onResponse(Call<List<countrydata>> call, Response<List<countrydata>> response) {
                data.addAll(response.body());
                countryadapter.notifyDataSetChanged();
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<countrydata>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(Country.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterdata(s.toString());

            }
        });
    }

    private void filterdata(String filterdata) {
        ArrayList<countrydata> filterlist = new ArrayList<>();
        for (countrydata item :data)
        {
            if(item.getCountry().toLowerCase().contains(filterdata.toLowerCase()))
            {
                filterlist.add(item);

            }
        }

        countryadapter.Filterlist(filterlist);

    }
}