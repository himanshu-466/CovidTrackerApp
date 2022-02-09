package com.mohit.covidtrackerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

public class adapter extends RecyclerView.Adapter<adapter.voewholder> {
    ArrayList<countrydata > data;
    Context context;

    public adapter(ArrayList<countrydata> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public voewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.samplerecylcer,parent,false);
        return new voewholder(view);
    }

    // this code is for searching in recyclerview
    public void Filterlist(ArrayList<countrydata> filterlist)
    {
        data = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull adapter.voewholder holder, int position) {
        countrydata countrydata = data.get(position);
        holder.CountryName.setText(countrydata.getCountry().trim());
        holder.CountryTotalCases.setText(NumberFormat.getInstance().format(Integer.parseInt(countrydata.getCases().trim())));
        holder.sno.setText(String.valueOf(position+1));

        Map<String ,String> img = countrydata.getCountryInfo();
        holder.code.setText(img.get("iso3").trim());
        Glide.with(context).load(img.get("flag")).into(holder.flag);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("country",countrydata.getCountry());
                context.startActivity(intent);


            }
        });




    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class voewholder extends RecyclerView.ViewHolder {
        ImageView flag;
        TextView code,CountryName,CountryTotalCases,sno;
        public voewholder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            flag = itemView.findViewById(R.id.flag);
            code=itemView.findViewById(R.id.code);
            CountryName=itemView.findViewById(R.id.countryName);
            CountryTotalCases = itemView.findViewById(R.id.countryTotalCases);
            sno= itemView.findViewById(R.id.sno);
        }
    }
}
