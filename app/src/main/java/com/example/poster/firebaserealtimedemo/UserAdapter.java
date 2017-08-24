package com.example.poster.firebaserealtimedemo;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by POSTER on 25.05.2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserModel> list;

    public UserAdapter(List<UserModel> list){
        this.list = list;
    }
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        UserModel user = list.get(position);
        holder.textName.setText(user.firstName + " " + user.lastName);
        holder.textAge.setText(user.age + "");
        holder.textJob.setText(user.job);

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(holder.getAdapterPosition(), 0, 0, "delete");
                menu.add(holder.getAdapterPosition(), 1, 0, "change");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView textName, textAge, textJob;

        public UserViewHolder(View itemView) {
            super(itemView);

            textName = (TextView) itemView.findViewById(R.id.text_age);
            textAge = (TextView) itemView.findViewById(R.id.text_age);
            textJob = (TextView) itemView.findViewById(R.id.text_age);
            System.out.println("123123");
        }
    }
}
