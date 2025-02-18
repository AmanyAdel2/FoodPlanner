package com.example.foodplanner.ChooseMeal.view;

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

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ChooseViewHolder>{
    private Context context;
    ArrayList<Meal> mealArrayList = new ArrayList<>();

    OnDayCardClick listener;

    public ChooseAdapter(Context context, OnDayCardClick listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChooseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.plan_item, parent, false);
        ChooseViewHolder holder = new ChooseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseAdapter.ChooseViewHolder holder, int position) {
        if (mealArrayList != null) {
            Meal meal = mealArrayList.get(position);
            holder.planName.setText(meal.getStrMeal());
            Glide.with(context).load(meal.getStrMealThumb()).placeholder(R.drawable.loading).error(R.drawable.ic_launcher_foreground).into(holder.planImage);
            holder.addImage.setOnClickListener(v -> {listener.onAddIconClick(meal);});
            holder.cardView.setOnClickListener(v -> {listener.onCardChoose(meal);});
        }
    }

    @Override
    public int getItemCount() {
        return mealArrayList.size();
    }

    class ChooseViewHolder extends RecyclerView.ViewHolder {
        ImageView planImage, addImage;
        TextView planName;
        CardView cardView;
        public ChooseViewHolder(@NonNull View itemView) {
            super(itemView);
            planImage = itemView.findViewById(R.id.planImage);
            addImage = itemView.findViewById(R.id.addPlan);
            planName = itemView.findViewById(R.id.planName);
            cardView = itemView.findViewById(R.id.planCard);
        }
    }

    public void setDataSource(ArrayList<Meal> mealArrayList) {
        this.mealArrayList = mealArrayList;
        notifyDataSetChanged();
    }
}
