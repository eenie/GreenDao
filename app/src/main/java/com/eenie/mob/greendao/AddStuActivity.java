package com.eenie.mob.greendao;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ssp.greendao.dao.Student;
import com.ssp.greendao.dao.StudentDao;

public class AddStuActivity extends AppCompatActivity {

    Context mContext;
    GreenDaoApplication mApplication;
    StudentDao studentDao;
    EditText editName;
    RadioGroup sexGroup;
    Button btnAdd;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stu);
        mContext = this;
        mApplication = (GreenDaoApplication) getApplication();
        studentDao = mApplication.getDaoSession().getStudentDao();
        initView();
    }

    private void initView() {

        editName = (EditText) findViewById(R.id.editName);
        sexGroup = (RadioGroup) findViewById(R.id.sexGroup);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student s = new Student();
                s.setName(editName.getText().toString());
                s.setSex(((RadioButton) findViewById(sexGroup.getCheckedRadioButtonId())).getText().toString());
                addStu(s);
                finish();
            }
        });

    }


    private void addStu(Student stu) {
        studentDao.insert(stu);
    }

}
