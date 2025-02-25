package com.example.androidversions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.List;

public class AndroidVersionAdapter extends ArrayAdapter<AndroidVersion> {

    public AndroidVersionAdapter(@NonNull Context context, int resource, List<AndroidVersion> list) {
        super(context, resource, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.android_version, parent, false);
        }
        AndroidVersion item = getItem(position);
        TextView textName = convertView.findViewById(R.id.text_name);
        TextView textCodeName = convertView.findViewById(R.id.text_code_name);
        TextView textApiVersion = convertView.findViewById(R.id.text_api_version);

        textName.setText(item.Name);
        textCodeName.setText(item.CodeName);
        textApiVersion.setText(item.ApiVersion);

        return convertView;
    }
}
