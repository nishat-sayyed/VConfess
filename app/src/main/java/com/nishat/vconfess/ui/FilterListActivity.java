package com.nishat.vconfess.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nishat.vconfess.R;

import java.util.ArrayList;

public class FilterListActivity extends AppCompatActivity {

    public static final String KEY_TAG = "KEY_TAG";

    private ArrayList<String> tags;

    private Button addAnotherTagButton;
    private Button finishButton;
    private EditText tagEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_list);

        addAnotherTagButton = (Button) findViewById(R.id.addAnotherTagButton);
        finishButton = (Button) findViewById(R.id.finishButton);
        tagEditText = (EditText) findViewById(R.id.tagEditText);

        tags = new ArrayList<String>();

        tags.add("TE_INFT_C_boy");
        tags.add("SE_CS_A_girl");

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra(KEY_TAG, "This is from Filter");
                setResult(RESULT_OK, intent);
                finish();
//                String tag = tagEditText.getText().toString();
//                if(!TextUtils.isEmpty(tag)){
//                    tags.add(tag);
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Enter the tag", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}
