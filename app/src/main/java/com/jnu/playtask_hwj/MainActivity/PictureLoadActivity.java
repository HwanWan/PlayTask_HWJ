package com.jnu.playtask_hwj.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.jnu.playtask_hwj.R;

import java.io.File;
import java.io.IOException;

public class PictureLoadActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_load);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            File file = new File(uri.getPath());
        }
    }

    public void uploadImage(File file) throws IOException {
        FileProvider fileProvider = new FileProvider();
        Uri uri = fileProvider.getUriForFile(this, "${applicationId}.fileprovider", file);

        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put("image", file.getAbsolutePath());
        Uri resultUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
}