package com.mohit.covidtrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView  active,confirm,death,test,recover;
    TextView todayrecover,todayconfirm,todaydeath,todaytest,date,countrydata;
    private List<countrydata> list;
    String Country ="India";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        list= new ArrayList<>();
        countrydata.setText(Country);
        if(getIntent().getStringExtra("country") !=null)
        {
            Country = getIntent().getStringExtra("country");
        }

        countrydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Country.class);
                intent.putExtra("India",countrydata.getText().toString());
                startActivity(intent);
            }
        });
        apicontroller.getApiset().getCountryData().enqueue(new Callback<List<countrydata>>() {
            @Override
            public void onResponse(Call<List<countrydata>> call, Response<List<countrydata>> response) {
                list.addAll(response.body());
                for (int i=0 ;i<list.size();i++)
                {
                    if(list.get(i).getCountry().equals(Country))
                    {
                        int totalconfirm = Integer.parseInt(list.get(i).getCases());
                        int totaldeath = Integer.parseInt(list.get(i).getDeaths());
                        int totalrecover = Integer.parseInt(list.get(i).getRecovered());
                        int totalactive = Integer.parseInt(list.get(i).getActive());
                        int totaltest = Integer.parseInt(list.get(i).getTests());
                        int totaltodayconfirm = Integer.parseInt(list.get(i).getTodayCases());
                        int totaltodaydeath = Integer.parseInt(list.get(i).getTodayDeaths());
                        int totaltodayrecover = Integer.parseInt(list.get(i).getTodayRecovered());
                        settext (list.get(i).getUpdated());

                        active.setText(NumberFormat.getInstance().format(totalactive));
                        confirm.setText(NumberFormat.getInstance().format(totalconfirm));
                        death.setText(NumberFormat.getInstance().format(totaldeath));
                        recover.setText(NumberFormat.getInstance().format(totalrecover));
                        test.setText(NumberFormat.getInstance().format(totaltest));
                        todayconfirm.setText(NumberFormat.getInstance().format(totaltodayconfirm));
                        todaydeath.setText(NumberFormat.getInstance().format(totaltodaydeath));
                        todayrecover.setText(NumberFormat.getInstance().format(totaltodayrecover));

                    }
                }

                
            }

            @Override
            public void onFailure(Call<List<countrydata>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void settext(String updated) {
        DateFormat format = new SimpleDateFormat("MMM dd,yyyy");
        long millisecond = Long.parseLong(updated);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);
        date.setText("Updated at "+format.format(calendar.getTime()));


    }

    public void init()
    {
        active = findViewById(R.id.active);
        confirm = findViewById(R.id.confirm);
        death = findViewById(R.id.death);
        recover = findViewById(R.id.recover);
        test = findViewById(R.id.test);
        todayconfirm = findViewById(R.id.dailyconfirm);
        todaydeath = findViewById(R.id.dailydeath);
        todayrecover = findViewById(R.id.dailyrecover);
        todaytest = findViewById(R.id.dailytest);
        date = findViewById(R.id.date);
        countrydata = findViewById(R.id.country);


    }
}