package com.aptech.duongquocvietexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.aptech.duongquocvietexam.database.AppDatabase;
import com.aptech.duongquocvietexam.entity.FeedBack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edName, edEmail, edDes;
    Spinner spinner;
    CheckBox ckAgree;
    Button btnSend;
    String gender = "Male";
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDatabase.getAppDatabase(this);

        ckAgree = findViewById(R.id.cbAccept);
        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);
        edDes = findViewById(R.id.edDescription);
        spinner = findViewById(R.id.spGender);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        String[] listGender = {"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listGender);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG", "onItemSelected: " + listGender[i]);
                gender = listGender[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void onSend(){
        if (!validate()){
            return;
        }
        FeedBack feedBack = new FeedBack();
        feedBack.name = edName.getText().toString();
        feedBack.email = edEmail.getText().toString();
        feedBack.description = edDes.getText().toString();
        feedBack.gender = gender;
        db.feedBackDao().insertFeedBack(feedBack);
        Toast.makeText(MainActivity.this, "Có " + db.feedBackDao().getAllFeedBack().size() + " feedbacks trong database", Toast.LENGTH_SHORT).show();
    }

    private boolean validate(){
        String mes = null;
        if (edName.getText().toString().trim().isEmpty()){
            mes = "Chưa nhập username";
        }else if (edDes.getText().toString().trim().isEmpty()){
            mes = "Chưa nhập mô tả";
        }else if (edEmail.getText().toString().trim().isEmpty()){
            mes = "Chưa nhập email";
        }else if (!ckAgree.isChecked()){
            mes = "Bạn phải đồng ý phản hồi gmail";
        }
        if (mes != null){
            Toast.makeText(this, mes, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSend:
                onSend();
                break;
            default:
                break;
        }
    }
}