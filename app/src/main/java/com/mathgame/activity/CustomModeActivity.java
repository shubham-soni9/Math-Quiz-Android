package com.mathgame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mathgame.R;
import com.mathgame.adapter.CustomModeAdapter;
import com.mathgame.database.ObjectBox;
import com.mathgame.model.CustomMode;
import com.mathgame.structure.BaseActivity;
import com.mathgame.appdata.Codes;
import com.mathgame.util.Transition;
import com.mathgame.util.Utils;

import java.util.List;

import io.objectbox.Box;

public class CustomModeActivity extends BaseActivity implements View.OnClickListener {
    private AppCompatImageView ivBack;
    private RecyclerView       rvCustomModeList;
    private CustomModeAdapter  adapter;
    private List<CustomMode>   customModes;
    private AppCompatImageView ivAddOption;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_mode);
        init();
        setList();
    }

    private void setList() {
        Box<CustomMode> customModeBox = ObjectBox.get().boxFor(CustomMode.class);
        customModes=customModeBox.getAll();
        adapter = new CustomModeAdapter(customModes);
        rvCustomModeList.setAdapter(adapter);
    }

    private void init() {
        rvCustomModeList = findViewById(R.id.rvCustomModeList);
        ivBack = findViewById(R.id.ivBack);
        ivAddOption = findViewById(R.id.ivAddOption);
        rvCustomModeList.setLayoutManager(new LinearLayoutManager(this));
        Utils.setOnClickListener(this, ivBack, ivAddOption);
        ivAddOption.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Codes.RequestCode.OPEN_ADD_CUSTOM_MODE_ACTIVITY:
                    setList();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivAddOption:
                Transition.transitForResult(this, AddCustomModeActivity.class, Codes.RequestCode.OPEN_ADD_CUSTOM_MODE_ACTIVITY);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Transition.exit(this);
    }
}
