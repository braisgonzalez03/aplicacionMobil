package com.example.appclubtenis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appclubtenis.R;
import com.example.appclubtenis.Utils.SpinnerItem;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<SpinnerItem> {

    private LayoutInflater inflater;

    public CustomSpinnerAdapter(Context context, List<SpinnerItem> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_item_image_text, parent, false);
            holder = new ViewHolder();
            holder.iconImageView = convertView.findViewById(R.id.iconImageView);
            holder.nameTextView = convertView.findViewById(R.id.nameTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SpinnerItem currentItem = getItem(position);

        if (currentItem != null) {
            holder.iconImageView.setImageResource(currentItem.getIconResId());
            holder.nameTextView.setText(currentItem.getName());
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView nameTextView;
    }
}
