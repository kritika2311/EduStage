package com.example.edustage1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomerAdapter_consultExperts extends RecyclerView.Adapter<ViewHolder> {

    consult_experts ConsultExperts;
    List<Model> modelList;
    Context context;

    public CustomerAdapter_consultExperts(consult_experts consultExperts, List<Model> modelList) {
        this.ConsultExperts = consultExperts;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.experts_itemlist, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ConsultExperts.selectExpert(position);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mExpert_Name.setText(modelList.get(position).getName());
        holder.mExperience.setText(modelList.get(position).getExperience());
        holder.mMembers.setText(modelList.get(position).getMembers());
        Picasso.get().load(modelList.get(position).getImage()).into(holder.mExpert_Image);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
