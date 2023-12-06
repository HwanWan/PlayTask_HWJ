package com.jnu.playtask_hwj.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jnu.playtask_hwj.R;

import java.io.FileNotFoundException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {


    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
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

    ImageView imageView;
    Button button_loadPicture;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_user, container, false);
        imageView = (ImageView)inflate.findViewById(R.id.iv_avatar);
        button_loadPicture = (Button) inflate.findViewById(R.id.button_loadPicture);
        button_loadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUploadClick(view);
            }
        });
        return inflate;

    }
    public void onUploadClick(View view) {
        // 启动系统相册应用，选择图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 判断用户是否选择了图片
        if (resultCode == RESULT_OK && data != null) {
            // 获取用户选择的图片文件的 Uri
            Uri uri = data.getData();
            // 将图片文件转换成 Bitmap 对象
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            // 将图片显示到 ImageView 控件中
            imageView.setImageBitmap(bitmap);
        }
    }
}