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

public class ListAdapter<T> extends ArrayAdapter {

    private LayoutInflater layoutInflater;
    private final List<T> list;
    private int layoutId;
    private int textId1;
    private int textId2;

    public ListAdapter(@NonNull Context context, List<T> list, int layoutId, int textId1, int textId2) {
        super(context, layoutId);
        this.list = list;
        this.layoutId = layoutId;
        this.textId1 = textId1;
        this.textId2 = textId2;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(layoutId, parent, false);
        }
        T item = this.list.get(position);
        TextView text1 = convertView.findViewById(textId1);
        TextView text2 = convertView.findViewById(textId2);
        return convertView;
    }
}
