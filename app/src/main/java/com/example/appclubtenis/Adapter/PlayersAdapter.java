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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PlayersAdapter extends BaseAdapter {


    private List<Players> playersList;
    private Context context;
    private List<String> assetImages;


    public PlayersAdapter(Context context, List<Players> playersList,List<String> assetImages) {
        this.playersList = playersList;
        this.context = context;
        this.assetImages = assetImages;
        copyImagesToInternalStorage();
    }

    private void copyImagesToInternalStorage() {
        File imageDir = new File(context.getFilesDir(), "");
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }

        for (String imageName : assetImages) {
            File destFile = new File(imageDir, imageName);
            if (!destFile.exists()) {
                try (InputStream is = context.getAssets().open(imageName);
                     FileOutputStream fos = new FileOutputStream(destFile)) {

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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

        if (assetImages != null && !assetImages.isEmpty()) {
            String imageName = assetImages.get(position % assetImages.size());
            File imageFile = new File(context.getFilesDir(), imageName);
            if (imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.ic_launcher_foreground);
            }
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }

        return elemento;
    }
}
