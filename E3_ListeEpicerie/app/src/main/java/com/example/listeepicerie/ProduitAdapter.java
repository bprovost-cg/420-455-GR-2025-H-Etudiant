package com.example.listeepicerie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ProduitAdapter extends ArrayAdapter<Produit>
{
    private final int layoutId;
    private final int textId1;
    private final int textId2;

    public ProduitAdapter(@NonNull Context context, int layoutId, List<Produit> list, int textId1, int textId2) {
        super(context,layoutId, list);
        this.layoutId = layoutId;
        this.textId1 = textId1;
        this.textId2 = textId2;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(layoutId, parent, false);
        }
        Produit item = (Produit)getItem(position);
        TextView text1 = convertView.findViewById(textId1);
        TextView text2 = convertView.findViewById(textId2);
        text1.setText(item.getNom());
        text2.setText(item.getDescription());
        return convertView;
    }
}
