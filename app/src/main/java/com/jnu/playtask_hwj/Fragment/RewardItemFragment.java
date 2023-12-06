package com.jnu.playtask_hwj.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.playtask_hwj.MainActivity.RewardItemDetailActivity;
import com.jnu.playtask_hwj.R;
import com.jnu.playtask_hwj.adapter.RewardRecycleViewAdpater;
import com.jnu.playtask_hwj.data.DataBankReward;
import com.jnu.playtask_hwj.data.DataBankTask;
import com.jnu.playtask_hwj.data.Reward;
import com.jnu.playtask_hwj.data.TaskWork;
import com.jnu.playtask_hwj.data.UserScore;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RewardItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewardItemFragment extends Fragment {

   ArrayList<Reward> rewards;
    TextView rewardScore;
    public static TextView SHOW_USER_SCORE;
    public String SHOW_SCORE="当前分数为:";


    ActivityResultLauncher<Intent> addItemlauncher;
    ActivityResultLauncher<Intent> updateItemlauncher;
    private static RewardRecycleViewAdpater rewardRecycleViewAdpater;

    public ArrayList<Reward> getLocalDataSet() {
        return localDataSet;
    }

    private ArrayList<Reward> localDataSet;

    public RewardItemFragment() {


    }

    public void CreateRewardFragment(){
        // 创建一个Intent对象，指定要启动的Activity
        Intent intentSave = new Intent(this.getContext(), RewardItemDetailActivity.class);
        // 当前Activity启动另一个Activity
        addItemlauncher.launch(intentSave);
    }


    /***
     * 【7.创建菜单】
     *  3.在MainActivity类重写onContextItemSelected()，响应点击菜单项事件
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                /**
                 *  启动子Activity显示添加数据项页面
                 * */
                CreateRewardFragment();
                break;
            case 1:
                /**
                 *  启动子Activity显示修改数据项页面
                 * */
                // 创建一个Intent对象，指定要启动的Activity
                Intent intentUpdate = new Intent(RewardItemFragment.this.getContext(), RewardItemDetailActivity.class);
                // 在Intent对象中传入当前数据项信息，由该Intent对象传给子Activity ==》 子Activity类就能展示原本数据项信息
                int order = item.getOrder();
                intentUpdate.putExtra("rewardTitle",rewards.get(order).getTitle());
                intentUpdate.putExtra("rewardScore",rewards.get(order).getScore());
                intentUpdate.putExtra("position",order);
                // 当前Activity启动另一个Activity
                updateItemlauncher.launch(intentUpdate);
                break;
            case 2:
            /**
             * 删除功能
             * */
                //设置暂停式提示消息：AlertDialog.Builder.setXxx().create().show()
                new AlertDialog.Builder(RewardItemFragment.this.getContext())
                        .setTitle("提示")
                        .setMessage("确定已完成吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 在这里编写点击确定按钮后的逻辑
                                // 获取当前数据的位置
                                int order = item.getOrder();

                                // UserScore.setScore(UserScore.getScore()+Double.parseDouble(rewards.get(order).getScore()));
                                rewards.remove(order);
                                rewardRecycleViewAdpater.notifyItemRemoved(order);
                                new DataBankReward().saveRewardItms(RewardItemFragment.this.getContext(),rewards);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create().show();
                break;

            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RewardItemFragment newInstance() {
        RewardItemFragment fragment = new RewardItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reward_item, container, false);
        RewardItemFragment.SHOW_USER_SCORE=rootView.findViewById(R.id.id_Show_User_Score);
        RewardItemFragment.SHOW_USER_SCORE.setText(SHOW_SCORE+UserScore.getScore());
        // 点击该按钮时显示新建任务布局
        Button createRewardButton = rootView.findViewById(R.id.button_CreateReward);
        createRewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateRewardFragment();
            }
        });
        //TextView Show_User_Score_TextView = rootView.findViewById(R.id.id_Show_User_Score);
        /**
         * 【6】创建RecyclerView
         * */
        //【6.1】加载RecyclerView控件
        RecyclerView rewardItemsRecyclerView = rootView.findViewById(R.id.recycle_view_rewards);
        rewardItemsRecyclerView.setLongClickable(true);

        //【6.2】生成布局管理器
        rewardItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        /**
         * 使用序列化读取文件内容
         * */
        rewards=new DataBankReward().loadRewardItms(this.getContext());
        if(rewards==null || 0==rewards.size()){
            //【6.3】设置适配器
            rewards.add(new Reward("11点睡觉", "20"));
            rewards.add(new Reward("看计网第四章", "40"));
        }

        // Required empty public constructor
        /**
         * 实现RewardRecycleViewAdpater.SignalListener接口
         * 点击CheckBox时更新总分数
         * */
        RewardRecycleViewAdpater.SignalListener signalListener = new RewardRecycleViewAdpater.SignalListener() {
            @Override
            public void SignalFragment(String rewardScore, int position) {
                // 扣分
                Double newScore = UserScore.getScore()-Double.parseDouble(rewardScore);
                UserScore.setScore(newScore);
                if(TaskItemFragment.SHOW_USER_SCORE!=null)
                    TaskItemFragment.SHOW_USER_SCORE.setText(SHOW_SCORE+UserScore.getScore());
                if(RewardItemFragment.SHOW_USER_SCORE!=null)
                    RewardItemFragment.SHOW_USER_SCORE.setText(SHOW_SCORE+UserScore.getScore());
                // 点击CheckBox时删除当前行
                rewards.remove(position);
                rewardRecycleViewAdpater.notifyItemRemoved(position);
            }
        };

        rewardRecycleViewAdpater = new RewardRecycleViewAdpater(rewards);
        rewardRecycleViewAdpater.setSignalListener(signalListener);
        rewardItemsRecyclerView.setAdapter(rewardRecycleViewAdpater);
        /**
         * 【7.创建菜单】
         *  1.在MainActivity类的OnCreate()中
         *   给RecycleView注册菜单 ==》 点击右键的时候就会出现上下文菜单
         */
        //1.注册菜单
        registerForContextMenu(rewardItemsRecyclerView);
        /**
         * 【7.创建菜单】
         *  2.在RecyclerView的适配器Adapter类的内部类ViewHolder实现View.OnCreateContextMenuListener接口
         *   并重写onCreateContextMenu()函数设置右键菜单的显示内容
         */

        /**
         * 获取子Activity返回数据
         * */
        addItemlauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.d("Serializable","调用了addItemlauncher的registerForActivityResult()方法");
                        Intent data = result.getData();
                        String bookTitle = data.getStringExtra("rewardTitle");
                        String bookScore = data.getStringExtra("rewardScore");
                        if(bookScore!=null && !bookScore.equals("") && bookTitle!=null && !bookTitle.equals("")){
                            // 将添加的内容显示到主Activity的RecycleView中
                            rewards.add(new Reward(bookTitle, bookScore));
                            // 通知RecycleView的适配器数据内容有新增，让适配器重新刷新数据
                            rewardRecycleViewAdpater.notifyItemInserted(rewards.size());
                            // 将新增行的checkBox勾选值设置为false
                            // 将新数据写入到文件中
                            new DataBankReward().saveRewardItms(this.getContext(),rewards);
                        }

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // 处理取消操作
                    }
                }
        );

        /**
         * 修改数据项：
         *  Step1.数据项的长按菜单中，修改菜单项的响应数据启动子Activity页面，并把当前菜单项数据传递给子Activity显示
         *  Step2.子Activity再将修改好的数据项内容传递过来
         *  Step3.ActivityResultLauncher对象再将新数据项信息写入到文件里
         * */
        updateItemlauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.d("Serializable","调用了updateItemlauncher的registerForActivityResult()方法");
                        Intent data = result.getData();
                        String rewardTitle = data.getStringExtra("rewardTitle");
                        String rewardScore = data.getStringExtra("rewardScore");
                        int position = data.getIntExtra("position", -1);
                        Reward rewardWork = rewards.get(position);
                        rewardWork.setTitle(rewardTitle);
                        rewardWork.setScore(rewardScore);
                        // 刷新页面
                        rewardRecycleViewAdpater.notifyItemChanged(position);
                        // 将修改的数据写入到文件中
                        new DataBankReward().saveRewardItms(RewardItemFragment.this.getContext(),rewards);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // 处理取消操作
                        //关闭activity的显示：要关闭的Activity类.this.finish()

                    }
                }
        );
        return rootView;
    }

}