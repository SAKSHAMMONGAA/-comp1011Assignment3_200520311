package com.car.manager.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.car.manager.R;
import com.car.manager.models.Vehicle;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {
    private final ArrayList<Vehicle> vehicles;
    Context context;
    private final OnVehicleItemClickListener clickListener;


    public VehicleAdapter(Context context,List<Vehicle> vehicles, OnVehicleItemClickListener clickListener) {
        this.context = context;
        this.vehicles = (ArrayList<Vehicle>) vehicles;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_vehicle, parent, false);
        return new ViewHolder(v, clickListener);
    }



    @Override
    public void onBindViewHolder(@NonNull VehicleAdapter.ViewHolder holder, int position) {
        Vehicle vehicle = vehicles.get(position);
        holder.bind(context, vehicle);
        holder.vehicle = vehicle;
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDescr;

        ImageView imageView;
        Vehicle vehicle;
        Context context;

        public ViewHolder(@NonNull View itemView, OnVehicleItemClickListener clickListener) {
            super(itemView);
            txtName = itemView.findViewById(R.id.text_name);
            txtDescr = itemView.findViewById(R.id.text_status);
            imageView = itemView.findViewById(R.id.imageview_thumbnail);

            itemView.setOnClickListener(v -> clickListener.onClick(vehicle));
        }
        void bind(Context context,Vehicle vehicle) {
            txtName.setText(vehicle.getMake());
            txtDescr.setText(vehicle.getCondition());
            Picasso.get().load(Uri.fromFile(new File(vehicle.getFullSizeImage())))
                    .error(R.drawable.baseline_car_crash_24)
                    .into(imageView);
        }
    }

}
