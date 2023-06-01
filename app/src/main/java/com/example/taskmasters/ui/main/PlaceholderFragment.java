package com.example.taskmasters.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskmasters.R;
import com.example.taskmasters.databinding.FragmentCreateUserBinding;
import com.example.taskmasters.ui.createUser.selectData.SelectDataFragment;
import com.example.taskmasters.ui.createUser.selectType.SelectTypeFragment;
//import com.example.taskmasters.ui.map.MapsFragment;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentCreateUserBinding binding;

    private SelectTypeFragment selectTypeFragment;
    private SelectDataFragment selectDataFragment;
//    private MapsFragment mapsFragment;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentCreateUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null && getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
            if (selectTypeFragment == null) {
                selectTypeFragment = new SelectTypeFragment();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.constraintLayout, selectTypeFragment, "Fragment1")
                        .commit();
            } else {
                getChildFragmentManager().beginTransaction()
                        .show(selectTypeFragment)
                        .commit();
            }
        }
        if (getArguments() != null && getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
            if (selectDataFragment == null) {
                selectDataFragment = new SelectDataFragment();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.constraintLayout, selectDataFragment, "Fragment2")
                        .commit();
            } else {
                getChildFragmentManager().beginTransaction()
                        .show(selectDataFragment)
                        .commit();
            }
        }
//        if (getArguments() != null && getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
//            if (mapsFragment == null) {
//                mapsFragment = new MapsFragment();
//                getChildFragmentManager().beginTransaction()
//                        .add(R.id.constraintLayout, mapsFragment, "Fragment3")
//                        .commit();
//            } else {
//                getChildFragmentManager().beginTransaction()
//                        .show(mapsFragment)
//                        .commit();
//            }
//        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        hideFragments();
    }

    private void hideFragments() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (selectTypeFragment != null) {
            transaction.hide(selectTypeFragment);
        }
        if (selectDataFragment != null) {
            transaction.hide(selectDataFragment);
        }
        transaction.commit();
    }
}
