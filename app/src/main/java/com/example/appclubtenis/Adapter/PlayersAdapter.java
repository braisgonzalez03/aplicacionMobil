package com.example.appclubtenis.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appclubtenis.Model.Players;
import com.example.appclubtenis.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PlayersAdapter extends BaseAdapter {


    private List<Players> playersList;
    private Context context;

    public PlayersAdapter(Context context, List<Players> playersList) {
        this.playersList = playersList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return playersList.size();
    }

    @Override
    public Object getItem(int position) {
        return playersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull  ViewGroup parent) {
        LayoutInflater mostrado = LayoutInflater.from(context);
        View elemento = mostrado.inflate(R.layout.item_player,parent,false);

        TextView nameText = elemento.findViewById(R.id.textPlayerName);
        TextView userText = elemento.findViewById(R.id.textPlayerUser);
        ImageView imageView = elemento.findViewById(R.id.imagePlayer);

        Players player = ((Players)getItem(position));
        nameText.setText(player.getName());
        userText.setText(player.getUserName());


        if (player.getImageName() != null) {
            try (InputStream is = context.getAssets().open("images/" + player.getImageName())) {
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                imageView.setImageResource(R.drawable.ic_launcher_foreground);
            }
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }


        return elemento;
    }
}
