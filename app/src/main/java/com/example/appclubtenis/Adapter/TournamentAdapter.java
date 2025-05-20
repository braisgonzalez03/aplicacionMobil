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
import com.example.appclubtenis.Model.Tournaments;
import com.example.appclubtenis.R;

import java.util.List;

public class TournamentAdapter extends BaseAdapter {

    private List<Tournaments> tournamentsList;
    private Context context;

    public TournamentAdapter(Context context, List<Tournaments> tournamentsList) {
        this.tournamentsList = tournamentsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return tournamentsList.size();
    }

    @Override
    public Object getItem(int position) {
        return tournamentsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater mostrado = LayoutInflater.from(context);
        View elemento = mostrado.inflate(R.layout.item_tournament,parent,false);
        TextView texto1 = elemento.findViewById(R.id.textTournamentName);
        texto1.setText(((Tournaments)getItem(position)).getName());
        TextView texto2 = elemento.findViewById(R.id.textDescription);
        texto2.setText(((Tournaments)getItem(position)).getDescription());

        return elemento;
    }

}
