package com.example.foodplanner.Home.View;



import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.MealListActivity.view.MealListActivity;
import com.example.foodplanner.Models.Country;
import com.example.foodplanner.R;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private Context context;
    ArrayList<Country> countryArrayList = new ArrayList<>();

    String[] area;

    public CountryAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_item, parent, false);
        area = view.getResources().getStringArray(R.array.area);
        CountryViewHolder holder = new CountryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        if(countryArrayList !=null) {
            Country country = countryArrayList.get(position);
            holder.categoryName.setText(country.getStrArea());
            Log.i("TAG", "onBindViewHolder: "+area[position]);
            String image = "https://flagsapi.com/"+area[position]+"/shiny/64.png";
            Glide.with(context).load(image)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.categoryImage);
            holder.cardView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MealListActivity.class);
                intent.putExtra("model", country);
                intent.putExtra("type","Country");
                intent.putExtra("image",image);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return countryArrayList.size();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryImage;
        TextView categoryName;
        CardView cardView;
        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
    public void setDataSource(ArrayList<Country> countryArrayList) {
        this.countryArrayList = countryArrayList;
        notifyDataSetChanged();
    }
}
