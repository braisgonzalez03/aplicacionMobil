package com.example.appclubtenis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appclubtenis.Model.Players;
import com.example.appclubtenis.R;

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
        TextView texto1 = elemento.findViewById(R.id.textPlayerName);
        texto1.setText(((Players)getItem(position)).getName());
        TextView texto2 = elemento.findViewById(R.id.textPlayerUser);
        texto2.setText(((Players)getItem(position)).getUserName());

        return elemento;
    }
}
