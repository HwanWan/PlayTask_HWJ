package com.jnu.playtask_hwj.data;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 负责用来读入数据到文件、将文件读取出来
 * */
public class DataBankReward {

    final String FILENAME="TaskItems.data";
    /**
     * 读取文件内容
     */
    public ArrayList<Reward> loadRewardItms(Context context) {
        ArrayList<Reward> rewards = new ArrayList<>();
        try {
            // 从文件中读取数据
            FileInputStream fileIn = context.openFileInput(FILENAME);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            rewards =(ArrayList<Reward>) objIn.readObject();
            objIn.close();
            fileIn.close();
            Log.d("Serializable","数据读取成功，共读取"+rewards.size()+"条数据");
        }catch (Exception e){
            e.printStackTrace();
        }
        return rewards;
    }
    /**
     * 将数据写入文件
     * */
    public void saveRewardItms(Context context, ArrayList<Reward> books) {
        try {
            // 将数据写入到文件
            FileOutputStream fileOut = context.openFileOutput(FILENAME,context.MODE_PRIVATE);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(books);
            objOut.close();
            fileOut.close();
            Log.d("Serializable","数据写入成功");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
