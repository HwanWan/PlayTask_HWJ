package com.jnu.playtask_hwj.adapter;


import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jnu.playtask_hwj.R;
import com.jnu.playtask_hwj.data.Reward;

import java.util.ArrayList;

/**
 * 内部类：RecyclerView
 */
public class RewardRecycleViewAdpater extends RecyclerView.Adapter<RewardRecycleViewAdpater.ViewHolder>  {

    private ArrayList<Reward> localDataSet;
    TextView rewardItemScore;
    CheckBox checkBox;
    public interface SignalListener{
        public void SignalFragment(String rewardScore, int position);
    }

    private static SignalListener signalListener;
    public void setSignalListener(SignalListener signalListener) {
        RewardRecycleViewAdpater.signalListener = signalListener;
    }

    /**
     * 构造适配器：传入数据集
     */
    public RewardRecycleViewAdpater(ArrayList<Reward> dataSet) {
        localDataSet = dataSet;
    }
    /**
     * 1.加载每一项的布局文件
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // 创建一个布局文件来定义RecycleView的每个项的外观，并在onCreateViewHolder()函数绑定布局文件
        // Create a new view, which defines the UI of the list item
        View reward_item_row_view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reward_item_row, viewGroup, false);

        checkBox = reward_item_row_view.findViewById(R.id.checkBox_FinishReward);
        checkBox.setChecked(false);




        return new ViewHolder(reward_item_row_view);
    }



    /**
     * 加载布局文件里面的控件: 将布局文件的控件与控件对象绑定
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener  {

        // 布局文件的控件
        private final TextView title;
        private final TextView score;


        /**
         * 【7.创建菜单】
         * 2.在RecyclerView的适配器Adapter类的内部类ViewHolder实现View.OnCreateContextMenuListener接口
         *   并重写onCreateContextMenu()函数设置右键菜单的显示内容
         */
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("具体操作");
            int position = this.getAdapterPosition();
            contextMenu.add(ContextMenu.NONE, 0, position, "添加");
            contextMenu.add(ContextMenu.NONE, 1, position, "修改");
            contextMenu.add(ContextMenu.NONE, 2, position, "已完成");
            contextMenu.add(ContextMenu.NONE, 3, position, "删除");
        }
        public ViewHolder(View rewardItemview) {
            // 将每个项的布局文件里的控件与控件对象绑定
            super(rewardItemview);
            title = (TextView) rewardItemview.findViewById(R.id.id_rewardItem_title);
            score = (TextView) rewardItemview.findViewById(R.id.id_rewardItem_score);



            /**
             * 【7.创建菜单】
             * 3.事件的发生者是RecycleView控件的每一栏数据
             * 所以要在ViewHolder构造器中为RecycleView控件的每一栏数据设置监听器
             * 当在数据栏点击鼠标右键时就会响应去调用onCreateContextMenu()函数展示右键菜单
             */
            rewardItemview.setOnCreateContextMenuListener(this);
        }



        public TextView getTitle() {
            return title;
        }


        public TextView getScore() {
            return score;
        }


    }





    /**
     * 将控件与数据绑定
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTitle().setText(localDataSet.get(position).getTitle());
        viewHolder.getScore().setText(localDataSet.get(position).getScore());

        // 实现点击checkBox的响应函数
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    if(signalListener!=null){
                        int layoutPosition = viewHolder.getLayoutPosition();
                        signalListener.SignalFragment(localDataSet.get(layoutPosition).getScore(), layoutPosition);
                    }
                    checkBox.setChecked(false);
                }
            }
        });
    }

    /**
     * 返回控件数量
     */
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }



}
