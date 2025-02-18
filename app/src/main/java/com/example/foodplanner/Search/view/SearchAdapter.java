package com.example.foodplanner.Search.view;

import android.content.Context;
import android.content.Intent;
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
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Models.Search;
import com.example.foodplanner.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private Context context;
    ArrayList<Search> searchArrayList = new ArrayList<>();
    OnItemSearchClick listener;

    public SearchAdapter(Context context, OnItemSearchClick listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_item, parent, false);
        SearchViewHolder holder = new SearchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        if (searchArrayList != null) {
            Search search = searchArrayList.get(position);
            holder.searchName.setText(search.getSearchName());
            Glide.with(context).load(search.getSearchImage()).placeholder(R.drawable.loading).error(R.drawable.ic_launcher_foreground).into(holder.searchImage);
            holder.cardView.setOnClickListener(v -> {
                listener.onItemClick(search);
            });
        }
    }

    @Override
    public int getItemCount() {
        return searchArrayList.size();
    }


    class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView searchImage;
        TextView searchName;
        CardView cardView;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            searchImage = itemView.findViewById(R.id.searchImage);
            searchName = itemView.findViewById(R.id.searchName);
            cardView = itemView.findViewById(R.id.searchCard);
        }
    }

    public void setDataSource(ArrayList<Search> searchArrayList) {
        this.searchArrayList = searchArrayList;
        notifyDataSetChanged();
    }
}

