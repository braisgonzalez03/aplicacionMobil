package com.example.appclubtenis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appclubtenis.Model.Players;
import com.example.appclubtenis.R;

import java.util.List;

public class PlayersAdapter extends BaseAdapter {


    private final List<Players> playersList;
    private final LayoutInflater inflater;

    public PlayersAdapter(Context context, List<Players> playersList) {
        this.playersList = playersList;
        this.inflater = LayoutInflater.from(context);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_player, parent, false);
            holder = new ViewHolder();
            holder.textName = convertView.findViewById(R.id.textPlayerName);
            holder.textUser = convertView.findViewById(R.id.textPlayerUser);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Players player = playersList.get(position);
        holder.textName.setText(player.getName());
        holder.textUser.setText(player.getUserName());

        return convertView;
    }

    static class ViewHolder {
        TextView textName;
        TextView textUser;
    }
}
