package com.jnu.playtask_hwj.MainActivity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.playtask_hwj.R;
import com.jnu.playtask_hwj.adapter.PageViewFragmentAdapter;


public class mainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 【1】为MainActivity布局文件中的ViewPager2控件绑定FragmentStateAdapter适配器
        ViewPager2 ViewPager_main = findViewById(R.id.viewpager2_main);
        ViewPager_main.setAdapter(new PageViewFragmentAdapter(getSupportFragmentManager(),getLifecycle()));

        // 【2】将MainActivity布局文件中的导航栏TableLayout & 屏幕滑块ViewPager2控件绑定
        TabLayout tableLayout_header = findViewById(R.id.tableLayout_header);
        new TabLayoutMediator(tableLayout_header,ViewPager_main,new TabLayoutMediator.TabConfigurationStrategy(){
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: // 第一个滑块的标题栏
                        tab.setText(R.string.tab_caption_0_task);
                        break;
                    case 1: // 第二个滑块的标题栏
                        tab.setText(R.string.tab_caption_1_reward);
                        break;
                    case 2: // 第二个滑块的标题栏
                        tab.setText(R.string.tab_caption_2_user);
                        break;
                }

            }
        }).attach();
    }


}