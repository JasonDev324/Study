package io.tanjundang.study.test.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import io.tanjundang.study.R;

public class IntentActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        btnIntent = (Button) findViewById(R.id.btnIntent);
        btnIntent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btnIntent)) {
            Intent intent = new Intent();
            Uri uri = Uri.parse("tel://");
            intent.setAction(Intent.ACTION_SEND);
//            intent.addCategory(Intent.CATEGORY_DEFAULT);
//            intent.setData(uri);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jon@example.com"}); // recipients
//            intent.putExtra(Intent.EXTRA_PHONE_NUMBER,"12312");
//            intent.setType("text/*");
            startActivity(intent);
        }
    }
}
