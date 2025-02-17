package com.example.foodplanner.MealActivity.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Models.IngredientMeasure;
import com.example.foodplanner.R;

import java.util.ArrayList;

public class IngredientItemAdapter extends RecyclerView.Adapter<IngredientItemAdapter.IngredientItemViewHolder> {
    private Context context;
    ArrayList<IngredientMeasure> ingredientList = new ArrayList<>();

    public IngredientItemAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public IngredientItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_item, parent, false);
        IngredientItemViewHolder holder = new IngredientItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientItemViewHolder holder, int position) {
        if(ingredientList != null) {
            IngredientMeasure ingredient = ingredientList.get(position);
            holder.ingredientName.setText(ingredient.getStrIngredient());
            holder.ingredientMeasure.setText(ingredient.getStrMeasure());
            Glide.with(context).load("https://www.themealdb.com/images/ingredients/"+ingredient.getStrIngredient().replaceAll(" ", "%20") + ".png")
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.ingredientImage);
        }
    }

    @Override
    public int getItemCount() {
        return ingredientList == null ? 0 : ingredientList.size();
    }

    class IngredientItemViewHolder extends RecyclerView.ViewHolder{
        ImageView ingredientImage;
        TextView ingredientName, ingredientMeasure;
        public IngredientItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientImage = itemView.findViewById(R.id.ingredient_image);
            ingredientName = itemView.findViewById(R.id.strIngredient);
            ingredientMeasure = itemView.findViewById(R.id.strMeasure);
        }
    }
    public void setDataSource(ArrayList<IngredientMeasure> ingredientList) {
        this.ingredientList = ingredientList;
        notifyDataSetChanged();
    }
}
