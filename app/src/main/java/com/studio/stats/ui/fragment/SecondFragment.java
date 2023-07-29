package com.studio.stats.ui.fragment;

import static android.provider.Settings.System.putString;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.studio.stats.R;
import com.studio.stats.databinding.FragmentSecondBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        binding.buttonFirst.setOnClickListener(view1 -> {
            Map<String, Object> user = new HashMap<>();
            user.put("name", Objects.requireNonNull(binding.firstNameEditText.getText()).toString());
            user.put("second_name", Objects.requireNonNull(binding.secondNameEditText.getText()).toString());
            user.put("device_id", id);
            user.put("is_for_raf", true);

            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(documentReference -> Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w("TAG", "Error adding document", e));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}