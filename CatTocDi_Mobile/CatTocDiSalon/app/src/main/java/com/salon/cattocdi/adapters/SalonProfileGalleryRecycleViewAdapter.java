package com.salon.cattocdi.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.salon.cattocdi.R;
import com.salon.cattocdi.utils.MyContants;

public class SalonProfileGalleryRecycleViewAdapter extends RecyclerView.Adapter<SalonProfileGalleryRecycleViewAdapter.ImageHolder>{

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.salon_profile_image_gallery, viewGroup, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder imageHolder, int i) {
//        imageHolder.image.setBackgroundResource(MyContants.SALON_MODELS_IDS[i]);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ImageHolder extends RecyclerView.ViewHolder{
        public View item;
        public ImageView image;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            this.item = itemView;
            image = itemView.findViewById(R.id.salon_profile_image);
        }
    }
}
