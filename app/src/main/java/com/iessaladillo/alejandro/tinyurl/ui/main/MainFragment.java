package com.iessaladillo.alejandro.tinyurl.ui.main;

import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iessaladillo.alejandro.tinyurl.R;

public class MainFragment extends Fragment {

    private MainViewModel vm;
    private EditText txtUrl;
    private Button btnTiny;
    private TextView lblSmallUrl;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = ViewModelProviders.of(this).get(MainViewModel.class);
        setupViews(requireView());
        observeShowResult();
    }

    private void observeShowResult() {
        vm.getTinyUrl().observe(this, this::showUrl);
    }

    private void showUrl(String url) {
        InputMethodManager imm =
                (InputMethodManager) requireActivity().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
        if (imm != null && requireActivity().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), 0);
        }
        lblSmallUrl.setText(url);
    }

    private void setupViews(View view) {
        txtUrl = ViewCompat.requireViewById(view, R.id.txtUrl);
        lblSmallUrl = ViewCompat.requireViewById(view, R.id.lblSmallUrl);
        btnTiny = ViewCompat.requireViewById(view, R.id.btnTiny);

        btnTiny.setOnClickListener(v -> makeSmall());
    }

    private void makeSmall() {
        vm.makeSmall(txtUrl.getText().toString());
    }

}
