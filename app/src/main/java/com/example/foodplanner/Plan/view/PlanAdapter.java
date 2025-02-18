package com.example.foodplanner.Plan.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Favorite.view.OnItemClickListener;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.R;

import java.util.ArrayList;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {
    private Context context;
    ArrayList<Meal> mealArrayList = new ArrayList<>();
    OnPlanCardClicked listener;

    public PlanAdapter(Context context, OnPlanCardClicked listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.day_item, parent, false);
        PlanViewHolder holder = new PlanViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        if (mealArrayList != null) {
            Meal meal = mealArrayList.get(position);
            holder.favMealName.setText(meal.getStrMeal());
            Glide.with(context).load(meal.getStrMealThumb()).placeholder(R.drawable.loading).error(R.drawable.ic_launcher_foreground).into(holder.favMealImage);
            holder.deleteImage.setOnClickListener(v -> {listener.onDeleteDayCard(meal);});
            holder.cardView.setOnClickListener(v -> {listener.onClickDayCard(meal);});
        }
    }

    @Override
    public int getItemCount() {
        return mealArrayList.size();
    }


    class PlanViewHolder extends RecyclerView.ViewHolder {
        ImageView favMealImage, deleteImage;
        TextView favMealName;
        CardView cardView;
        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            favMealImage = itemView.findViewById(R.id.dayImage);
            deleteImage = itemView.findViewById(R.id.deleteDay);
            favMealName = itemView.findViewById(R.id.dayName);
            cardView = itemView.findViewById(R.id.dayCard);
        }
    }

    public void setDataSource(ArrayList<Meal> mealArrayList) {
        this.mealArrayList = mealArrayList;
        notifyDataSetChanged();
    }
}
