package com.jnu.playtask_hwj.MainActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.jnu.playtask_hwj.R;


/**
 * 点击新增/修改菜单项时弹出的子页面
 * */
public class RewardItemDetailActivity extends AppCompatActivity {
    private int position =-1;
    private TextInputEditText bookTitle;
    private TextInputEditText bookScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_item_detail);

        /**
         * 如果是要修改该项数据，MainActivity会传递该数据项参数
         * ==》将这个数据项参数显示出来
         */
        Intent intent = getIntent();
        if (intent!=null){
            String title = intent.getStringExtra("rewardTitle");
            if(null != title){
                // 显示数据
                position = intent.getIntExtra("position", -1);
                bookTitle = findViewById(R.id.id_inputText_RewardTitle);
                bookTitle.setText(title);
            }
            String score = intent.getStringExtra("rewardScore");
            if(null != score){
                // 显示数据
                position = intent.getIntExtra("position", -1);
                bookScore = findViewById(R.id.id_inputText_RewardScore);
                bookScore.setText(title);
            }
        }

        // 当点击确认按钮关闭当前Activity
        Button button_ok=findViewById(R.id.id_inputReward_ButtonOk);
        // 给按钮添加点击响应器 ==》 返回新创建 / 新修改好的数据
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 返回数据
                 * */
                // 添加需要传递给另一个Activity的数据
                bookTitle = findViewById(R.id.id_inputText_RewardTitle);
                intent.putExtra("rewardTitle", bookTitle.getText().toString());

                bookScore = findViewById(R.id.id_inputText_RewardScore);
                intent.putExtra("rewardScore", bookScore.getText().toString());
                // 返回携带数据项信息的intent对象必须是上面的intent对象 ==》 否则如果是修改数据项信息就没办法将position传递过去
                setResult(Activity.RESULT_OK,intent);

                /**
                 * 关闭当前BookItemDetailActivity
                 * */
                RewardItemDetailActivity.this.finish();
            }
        });
    }
}
