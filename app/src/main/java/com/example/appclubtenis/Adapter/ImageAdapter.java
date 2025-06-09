package com.example.appclubtenis.Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appclubtenis.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> { //Lo utilizamos porque con RecyclerView tenemos la opción de poner la lista de imágenes de manera horizontal

    private final Context context;
    private final int[] imageResIds;
    private int selectedPosition = RecyclerView.NO_POSITION;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ImageAdapter(Context context, int[] imageResIds) {
        this.context = context;
        this.imageResIds = imageResIds;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        int resId = imageResIds[position];
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        holder.imageView.setImageBitmap(bitmap);

        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.color.tennis_court_black);
        } else {
            holder.itemView.setBackgroundResource(0);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentPosition = holder.getAdapterPosition();
                if (currentPosition == RecyclerView.NO_POSITION) return;

                int previousSelected = selectedPosition;
                selectedPosition = currentPosition;

                notifyItemChanged(previousSelected);
                notifyItemChanged(selectedPosition);

                if (listener != null) {
                    listener.onItemClick(currentPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageResIds.length;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewItem);
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int position) {
        int previousSelected = selectedPosition;
        selectedPosition = position;
        if (previousSelected != RecyclerView.NO_POSITION) {
            notifyItemChanged(previousSelected);
        }
        if (selectedPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(selectedPosition);
        }
    }


    public String getResourceName(int position) {
        if (position >= 0 && position < imageResIds.length) {
            return context.getResources().getResourceEntryName(imageResIds[position]);
        }
        return null;
    }

}
