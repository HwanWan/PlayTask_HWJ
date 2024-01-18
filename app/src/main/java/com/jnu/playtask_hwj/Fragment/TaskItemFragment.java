package com.jnu.playtask_hwj.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.jnu.playtask_hwj.MainActivity.TaskItemDetailActivity;
import com.jnu.playtask_hwj.R;
import com.jnu.playtask_hwj.adapter.TaskRecycleViewAdpater;
import com.jnu.playtask_hwj.data.DataBankTask;
import com.jnu.playtask_hwj.data.Reward;
import com.jnu.playtask_hwj.data.UserScore;
import com.jnu.playtask_hwj.data.TaskWork;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskItemFragment extends Fragment {

   ArrayList<TaskWork> taskWorks;
    TextView taskScore;
    public static TextView SHOW_USER_SCORE;
    public String SHOW_SCORE="当前分数为:";


    ActivityResultLauncher<Intent> addItemlauncher;
    ActivityResultLauncher<Intent> updateItemlauncher;
    private static TaskRecycleViewAdpater taskRecycleViewAdpater;

    public ArrayList<TaskWork> getLocalDataSet() {
        return localDataSet;
    }

    private ArrayList<TaskWork> localDataSet;

    public TaskItemFragment() {


    }

    public void CreateTaskFragment(){
        // 创建一个Intent对象，指定要启动的Activity
        Intent intentSave = new Intent(this.getContext(), TaskItemDetailActivity.class);
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
                CreateTaskFragment();
                break;
            case 1:
                /**
                 *  启动子Activity显示修改数据项页面
                 * */
                // 创建一个Intent对象，指定要启动的Activity
                Intent intentUpdate = new Intent(TaskItemFragment.this.getContext(), TaskItemDetailActivity.class);
                // 在Intent对象中传入当前数据项信息，由该Intent对象传给子Activity ==》 子Activity类就能展示原本数据项信息
                int order = item.getOrder();
                intentUpdate.putExtra("taskTitle",taskWorks.get(order).getTitle());
                intentUpdate.putExtra("taskScore",taskWorks.get(order).getScore());
                intentUpdate.putExtra("position",order);
                // 当前Activity启动另一个Activity
                updateItemlauncher.launch(intentUpdate);
                break;
            case 2:
               /**
                * 删除功能
                * */
                   //设置暂停式提示消息：AlertDialog.Builder.setXxx().create().show()
                   new AlertDialog.Builder(TaskItemFragment.this.getContext())
                           .setTitle("提示")
                           .setMessage("确定已完成吗？")
                           .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   // 在这里编写点击确定按钮后的逻辑
                                   // 获取当前数据的位置
                                   int order = item.getOrder();
   
                                   // 加分
                                   Double newScore = Double.parseDouble(taskWorks.get(order).getScore()) + UserScore.getScore();
                                   UserScore.setScore(newScore);
                                   if(TaskItemFragment.SHOW_USER_SCORE!=null)
                                       TaskItemFragment.SHOW_USER_SCORE.setText(SHOW_SCORE+UserScore.getScore());
                                   if(RewardItemFragment.SHOW_USER_SCORE!=null)
                                       RewardItemFragment.SHOW_USER_SCORE.setText(SHOW_SCORE+UserScore.getScore());
                                   // 点击CheckBox时删除当前行
                                   taskWorks.remove(order);
                                   taskRecycleViewAdpater.notifyItemRemoved(order);
                                   new DataBankTask().saveShopItms(TaskItemFragment.this.getContext(),taskWorks);
                               }
                           })
                           .setNegativeButton("取消", null)
                           .create().show();
                   break;
               case 3:
                   /**
                    * 删除功能
                    * */
                   //设置暂停式提示消息：AlertDialog.Builder.setXxx().create().show()
                   new AlertDialog.Builder(TaskItemFragment.this.getContext())
                           .setTitle("提示")
                           .setMessage("确定删除吗？")
                           .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   // 在这里编写点击确定按钮后的逻辑
                                   // 获取当前数据的位置
                                   int order = item.getOrder();
                                   // 点击CheckBox时删除当前行
                                   taskWorks.remove(order);
                                   taskRecycleViewAdpater.notifyItemRemoved(order);
                                   new DataBankTask().saveShopItms(TaskItemFragment.this.getContext(),taskWorks);
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
    public static TaskItemFragment newInstance() {
        TaskItemFragment fragment = new TaskItemFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_task_item, container, false);

        TaskItemFragment.SHOW_USER_SCORE=rootView.findViewById(R.id.id_Show_User_Score);
        TaskItemFragment.SHOW_USER_SCORE.setText(SHOW_SCORE+UserScore.getScore());

        //TextView Show_User_Score_TextView = rootView.findViewById(R.id.id_Show_User_Score);
        /**
         * 【6】创建RecyclerView
         * */
        //【6.1】加载RecyclerView控件
        RecyclerView taskItemsRecyclerView = rootView.findViewById(R.id.recycle_view_tasks);
        taskItemsRecyclerView.setLongClickable(true);

        //【6.2】生成布局管理器
        taskItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        /**
         * 使用序列化读取文件内容
         * */
        taskWorks=new DataBankTask().loadShopItms(this.getContext());
        if(taskWorks==null || 0==taskWorks.size()){
            //【6.3】设置适配器
            taskWorks.add(new TaskWork("11点睡觉", "20"));
            taskWorks.add(new TaskWork("看计网第四章", "40"));
        }

        // 点击该按钮时显示新建任务布局
        Button createTaskButton = rootView.findViewById(R.id.button_CreateTask);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateTaskFragment();
            }
        });

        // Required empty public constructor
        /**
         * 实现TaskRecycleViewAdpater.SignalListener接口
         * 点击CheckBox时更新总分数
         * */
        TaskRecycleViewAdpater.SignalListener signalListener = new TaskRecycleViewAdpater.SignalListener() {
            @Override
            public void SignalFragment(String taskScore, int position) {
                // 加分
                Double newScore = Double.parseDouble(taskScore) + UserScore.getScore();
                UserScore.setScore(newScore);
                if(TaskItemFragment.SHOW_USER_SCORE!=null)
                    TaskItemFragment.SHOW_USER_SCORE.setText(SHOW_SCORE+UserScore.getScore());
                if(RewardItemFragment.SHOW_USER_SCORE!=null)
                    RewardItemFragment.SHOW_USER_SCORE.setText(SHOW_SCORE+UserScore.getScore());
                // 点击CheckBox时删除当前行
                taskWorks.remove(position);
                taskRecycleViewAdpater.notifyItemRemoved(position);
                new DataBankTask().saveShopItms(TaskItemFragment.this.getContext(),taskWorks);

            }
        };

        taskRecycleViewAdpater = new TaskRecycleViewAdpater(taskWorks);
        taskRecycleViewAdpater.setSignalListener(signalListener);
        taskItemsRecyclerView.setAdapter(taskRecycleViewAdpater);
        /**
         * 【7.创建菜单】
         *  1.在MainActivity类的OnCreate()中
         *   给RecycleView注册菜单 ==》 点击右键的时候就会出现上下文菜单
         */
        //1.注册菜单
        registerForContextMenu(taskItemsRecyclerView);
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
                        String bookTitle = data.getStringExtra("taskTitle");
                        String bookScore = data.getStringExtra("taskScore");

                        if(bookScore!=null && !bookScore.equals("") && bookTitle!=null && !bookTitle.equals("")){
                            // 将添加的内容显示到主Activity的RecycleView中
                            taskWorks.add(new TaskWork(bookTitle, bookScore));
                            // 通知RecycleView的适配器数据内容有新增，让适配器重新刷新数据
                            taskRecycleViewAdpater.notifyItemInserted(taskWorks.size());
                            // 将新增行的checkBox勾选值设置为false

                            // 将新数据写入到文件中
                            new DataBankTask().saveShopItms(this.getContext(),taskWorks);

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
                        String taskTitle = data.getStringExtra("taskTitle");
                        String taskScore = data.getStringExtra("taskScore");
                        int position = data.getIntExtra("position", -1);
                        TaskWork taskWork = taskWorks.get(position);
                        taskWork.setTitle(taskTitle);
                        taskWork.setScore(taskScore);
                        // 刷新页面
                        taskRecycleViewAdpater.notifyItemChanged(position);
                        // 将修改的数据写入到文件中
                        new DataBankTask().saveShopItms(TaskItemFragment.this.getContext(),taskWorks);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // 处理取消操作
                        //关闭activity的显示：要关闭的Activity类.this.finish()

                    }
                }
        );
        return rootView;
    }

}
