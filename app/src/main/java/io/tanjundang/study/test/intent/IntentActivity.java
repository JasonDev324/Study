package io.tanjundang.study.test.intent;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;

import io.tanjundang.study.R;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.LogTool;

public class IntentActivity extends AppCompatActivity {

    private int IMAGE_CODE = 666;
    private int CONTACT_CODE = 667;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
    }

    public void sendToQQ(View v) {
        Intent intent = new Intent();
//设置发送Action
        intent.setAction(Intent.ACTION_SEND);
//利用Intent的KEY EXTRA_TEXT来传递数据
        intent.putExtra(Intent.EXTRA_TEXT, "This is my Share text.");
        intent.setType("text/*");
        startActivity(intent);
    }

    public void sendToEmail(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"TanJunDang@126.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
        intent.putExtra(Intent.EXTRA_TEXT, "Email message text");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
        startActivity(intent);
    }

    public void dialPhone(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        Uri uri = Uri.parse("tel:10000000");
        intent.setData(uri);
        startActivity(intent);
    }

    public void webView(View v) {
        Uri uri = Uri.parse("http://www.baidu.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void sendImage(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "选择"), IMAGE_CODE);

    }

    public void selectContact(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(intent, CONTACT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CODE) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                /* 将Bitmap设定到ImageView */
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                LogTool.e(getClass().getName(), e.getMessage().toString());
            }
        } else if (requestCode == CONTACT_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();
                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int name = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHONETIC_NAME);
                String number = cursor.getString(column);
                String selectName = "";
                Functions.toast(selectName + ":" + number);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
