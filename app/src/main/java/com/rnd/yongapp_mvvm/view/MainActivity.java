package com.rnd.yongapp_mvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.rnd.yongapp_mvvm.R;
import com.rnd.yongapp_mvvm.databinding.ActivityMainBinding;
import com.rnd.yongapp_mvvm.enums.Enums;
import com.rnd.yongapp_mvvm.eventbus.OnClickBus;
import com.rnd.yongapp_mvvm.viewmodel.MainViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private ActivityMainBinding binding;
    private MainAdapter mainAdapter;
    private int page = 1;
    private String keyword = "카카오";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        EventBus.getDefault().register(this);

        initData();
    }

    private void initData(){
        mainAdapter = new MainAdapter();

        viewModel = new MainViewModel(mainAdapter,keyword,page);
        binding.setViewModel(viewModel);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.recyclerView.setAdapter(mainAdapter);
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // recyclerView의 스크롤이 최 하단에 도착했을 경우
                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_more_data), Toast.LENGTH_LONG).show();
                    viewModel.getBooks(keyword,++page);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onClikEventReceived(OnClickBus bus) {
        if (bus.getEvent() == Enums.ONCLICK) {
            Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
        }
    }
}
