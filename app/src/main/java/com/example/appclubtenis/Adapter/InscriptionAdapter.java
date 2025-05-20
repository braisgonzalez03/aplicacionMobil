package com.example.appclubtenis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appclubtenis.Model.Inscriptions;
import com.example.appclubtenis.Model.Players;
import com.example.appclubtenis.Model.Tournaments;
import com.example.appclubtenis.R;

import java.util.List;

public class InscriptionAdapter extends BaseAdapter {

    private List<Inscriptions> inscriptionsList;
    private Context context;

    public InscriptionAdapter(Context context, List<Inscriptions> inscriptionsList) {
        this.inscriptionsList = inscriptionsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return inscriptionsList.size();
    }

    @Override
    public Object getItem(int position) {
        return inscriptionsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater mostrado = LayoutInflater.from(context);
        View elemento = mostrado.inflate(R.layout.item_inscription,parent,false);
        TextView texto1 = elemento.findViewById(R.id.textInscriptionId);
        texto1.setText("ID Inscription: " + (((Inscriptions)getItem(position)).getInscriptionId()));
        TextView texto2 = elemento.findViewById(R.id.textNamePlayer);
        texto2.setText("Player Name: " + (((Inscriptions)getItem(position)).getPlayer().getName()));
        TextView texto3 = elemento.findViewById(R.id.textNameTournament);
        texto3.setText("Tournament Name: " + (((Inscriptions)getItem(position)).getTournament().getName()));
        TextView texto4 = elemento.findViewById(R.id.textStartDate);
        texto4.setText("Date start: " + (((Inscriptions)getItem(position)).getStartDate()));

        TextView texto5 = elemento.findViewById(R.id.textEndDate);

        if(((Inscriptions)getItem(position)).getEndDate() != null){
            texto5.setVisibility(View.VISIBLE);
            texto5.setText("Date end: " + (((Inscriptions)getItem(position)).getEndDate()));
        }else{
            texto5.setVisibility(View.GONE);
        }


        return elemento;
    }
}
