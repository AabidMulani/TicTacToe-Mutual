package com.aabidmulani.tictacmutual.app.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.aabidmulani.tictacmutual.BaseActivity;
import com.aabidmulani.tictacmutual.BaseFragment;
import com.aabidmulani.tictacmutual.R;
import com.aabidmulani.tictacmutual.app.fragment.DashboardFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        showThisFragment(new DashboardFragment());
    }


    private void showThisFragment(BaseFragment fragment) {
        getSupportActionBar().setTitle(fragment.getFragmentTitle());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }


}
