package com.car.manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.car.manager.R;
import com.car.manager.databinding.FragmentListBinding;
import com.car.manager.models.Vehicle;
import com.car.manager.ui.adapters.OnVehicleItemClickListener;
import com.car.manager.ui.adapters.VehicleAdapter;
import com.car.manager.ui.viewmodels.AppViewModel;

public class ListFragment extends Fragment implements OnVehicleItemClickListener{

    private static final String ARG_PARAM = "filter";
    private final String TAG = getClass().getName();
    private String filter;

    private FragmentListBinding binding;
    private AppViewModel viewModel;

    public static ListFragment newInstance(String filter) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, filter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filter = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(ListFragment.this).get(AppViewModel.class);
        return binding.getRoot();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvVehicles.setLayoutManager(layoutManager);
        switch (filter) {
            case "all":
                viewModel.getAllVehicles().observe(this, vehicles -> {
                    VehicleAdapter adapter = new VehicleAdapter(requireContext(), vehicles, this);
                    binding.rvVehicles.setAdapter(adapter);
                });

                break;
            case "sold":
                viewModel.getSoldVehicles().observe(this, vehicles -> {
                    VehicleAdapter adapter = new VehicleAdapter(requireContext(), vehicles, this::onClick);
                    binding.rvVehicles.setAdapter(adapter);
                });
                break;
            case "purchase":
                viewModel.getAvailableVehicles().observe(this, vehicles -> {
                    VehicleAdapter adapter = new VehicleAdapter(requireContext(), vehicles, this::onClick);
                    binding.rvVehicles.setAdapter(adapter);
                });
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        viewModel.refresh();
        setUpRecyclerView();
    }

    @Override
    public void onClick(Vehicle vehicle) {

        Intent intent = new Intent(requireActivity(), VehicleActivity.class);
        intent.putExtra("vehicle", vehicle);
        startActivity(intent);

    }



}