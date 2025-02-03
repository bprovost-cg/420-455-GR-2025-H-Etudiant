package com.example.fruits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

// Adapter personnalisé
public class FruitAdapter extends BaseAdapter {
    private Context context;
    private List<Fruit> fruits;

    public FruitAdapter(Context context, List<Fruit> fruits) {
        this.context = context;
        this.fruits = fruits;
    }

    @Override
    public int getCount() { return fruits.size(); }

    @Override
    public Object getItem(int position) { return fruits.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fruit, parent, false);
        }

        // Obtenir les données pour l'élément actuel
        Fruit fruit = fruits.get(position);

        // Mettre à jour les vues
        TextView nameView = convertView.findViewById(R.id.fruitName);
        TextView colorView = convertView.findViewById(R.id.fruitColor);

        nameView.setText(fruit.name);
        colorView.setText(fruit.color);

        return convertView;
    }
}
