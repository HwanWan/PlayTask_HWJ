package com.jnu.playtask_hwj.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jnu.playtask_hwj.Fragment.RewardItemFragment;
import com.jnu.playtask_hwj.Fragment.TaskItemFragment;
import com.jnu.playtask_hwj.Fragment.UserFragment;

/**
 * 将Fragment 与 FragmentStateAdapter适配器绑定
 * */
public class PageViewFragmentAdapter extends FragmentStateAdapter {

    public PageViewFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: // 第一个滑块绑定BookItemFragment
                return TaskItemFragment.newInstance();
            case 1:
                return RewardItemFragment.newInstance();
            case 2:
                return UserFragment.newInstance();
        }
        return TaskItemFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
