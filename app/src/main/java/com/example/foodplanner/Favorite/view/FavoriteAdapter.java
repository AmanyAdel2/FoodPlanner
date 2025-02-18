package com.example.foodplanner.Favorite.view;

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
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.R;
import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private Context context;
    ArrayList<Meal> mealArrayList = new ArrayList<>();
    OnItemClickListener listener;

    public FavoriteAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.favorite_list, parent, false);
        FavoriteViewHolder holder = new FavoriteViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        if (mealArrayList != null) {
            Meal meal = mealArrayList.get(position);
            holder.favMealName.setText(meal.getStrMeal());
            Glide.with(context).load(meal.getStrMealThumb()).placeholder(R.drawable.loading).error(R.drawable.ic_launcher_foreground).into(holder.favMealImage);
            holder.deleteImage.setOnClickListener(v -> {listener.onDeleteMealClick(meal);});
            holder.cardView.setOnClickListener(v -> {listener.onItemClick(meal);});
        }
    }

    @Override
    public int getItemCount() {
        return mealArrayList.size();
    }


    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView favMealImage, deleteImage;
        TextView favMealName;
        CardView cardView;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            favMealImage = itemView.findViewById(R.id.favoriteImage);
            deleteImage = itemView.findViewById(R.id.favoriteDelete);
            favMealName = itemView.findViewById(R.id.favoriteName);
            cardView = itemView.findViewById(R.id.favoriteCard);
        }
    }

    public void setDataSource(ArrayList<Meal> mealArrayList) {
        this.mealArrayList = mealArrayList;
        notifyDataSetChanged();
    }
}
