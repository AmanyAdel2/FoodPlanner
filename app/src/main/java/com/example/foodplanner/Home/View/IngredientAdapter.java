package com.example.foodplanner.Home.View;

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
import com.example.foodplanner.Models.Ingredient;
import com.example.foodplanner.R;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private Context context;
    ArrayList<Ingredient> ingredientsArrayList = new ArrayList<>();

    public IngredientAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_item, parent, false);
        IngredientViewHolder holder = new IngredientViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        if(ingredientsArrayList !=null) {
            Ingredient ingredient = ingredientsArrayList.get(position);
            holder.categoryName.setText(ingredient.getStrIngredient());
            Glide.with(context).load("https://www.themealdb.com/images/ingredients/"+ingredient.getStrIngredient().replaceAll(" ", "%20") + ".png")
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.categoryImage);
            holder.cardView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MealListActivity.class);
                intent.putExtra("model", ingredient);
                intent.putExtra("type","Ingredient");
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return ingredientsArrayList == null ? 0 : ingredientsArrayList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryImage;
        TextView categoryName;
        CardView cardView;
        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
    public void setDataSource(ArrayList<Ingredient> ingredientsArrayList) {
        this.ingredientsArrayList = ingredientsArrayList;
        notifyDataSetChanged();
    }
}
