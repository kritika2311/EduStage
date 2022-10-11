package com.example.edustage1;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView mExpert_Name, mExperience, mMembers;
    LinearLayout desc1, desc2;
    ImageView mExpert_Image;
    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        mExpert_Image = itemView.findViewById(R.id.expert_image);
        mExpert_Name = itemView.findViewById(R.id.expert_name);
        mExperience = itemView.findViewById(R.id.experience);
        mMembers = itemView.findViewById(R.id.members);
        desc1 = itemView.findViewById(R.id.desc1);
        desc2 = itemView.findViewById(R.id.desc2);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

    }
    private ViewHolder.ClickListener mClickListener;
    public interface  ClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){
       mClickListener = clickListener;
    }
}
