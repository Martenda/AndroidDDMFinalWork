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
import androidx.viewpager.widget.ViewPager;

import com.example.taskmasters.R;
import com.example.taskmasters.databinding.FragmentCreateUserBinding;
import com.example.taskmasters.model.user.User;
import com.example.taskmasters.ui.createUser.selectCredentials.SelectCredentialsFragment;
import com.example.taskmasters.ui.createUser.selectData.SelectDataFragment;
import com.example.taskmasters.ui.createUser.selectType.SelectTypeFragment;
import com.google.android.material.snackbar.Snackbar;
//import com.example.taskmasters.ui.map.MapsFragment;

public class PlaceholderFragment extends Fragment {

    public final User user;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private ViewPager viewPager;

    private PageViewModel pageViewModel;
    private FragmentCreateUserBinding binding;

    private SelectTypeFragment selectTypeFragment;
    private SelectDataFragment selectDataFragment;
    //    private MapsFragment mapsFragment;

    private SelectCredentialsFragment selectCredentialsFragment;

    public PlaceholderFragment(User user) {
        this.user = user;
    }

    public static PlaceholderFragment newInstance(int index, User user) {
        PlaceholderFragment fragment = new PlaceholderFragment(user);
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public User getUser() {
        return this.user;
    }

    public void Callback(String option, int to) {
        Snackbar.make(this.requireView(), option, 2000).show();
        viewPager.setCurrentItem(to - 1);
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

        viewPager = requireActivity().findViewById(R.id.view_pager);
        binding = FragmentCreateUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null && getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
            showSelectTypeFragment();
        }
        if (getArguments() != null && getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
            showSelectDataFragment();
        }
//        if (getArguments() != null && getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
//        }
        if (getArguments() != null && getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
            showSelectCredentialsFragment();
        }

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

    private void showSelectTypeFragment() {
        if (selectTypeFragment == null) {
            selectTypeFragment = new SelectTypeFragment(this);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.constraintLayout, selectTypeFragment, "Fragment1")
                    .commit();
        } else {
            getChildFragmentManager().beginTransaction()
                    .show(selectTypeFragment)
                    .commit();
        }
    }

    private void showSelectDataFragment() {
        if (selectDataFragment == null) {
            selectDataFragment = new SelectDataFragment(this);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.constraintLayout, selectDataFragment, "Fragment2")
                    .commit();
        } else {
            getChildFragmentManager().beginTransaction()
                    .show(selectDataFragment)
                    .commit();
        }
    }

    private void showSelectCredentialsFragment() {
        if (selectCredentialsFragment == null) {
            selectCredentialsFragment = new SelectCredentialsFragment(this);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.constraintLayout, selectCredentialsFragment, "Fragment4")
                    .commit();
        } else {
            getChildFragmentManager().beginTransaction()
                    .show(selectCredentialsFragment)
                    .commit();
        }
    }

}
