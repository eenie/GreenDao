package com.eenie.mob.greendao;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ssp.greendao.dao.Course;
import com.ssp.greendao.dao.CourseDao;
import com.ssp.greendao.dao.Student;
import com.ssp.greendao.dao.StudentDao;

public class AddCouseActivity extends AppCompatActivity {


    Context mContext;
    GreenDaoApplication mApplication;
    CourseDao courseDao;
    EditText editName;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_couse);

        mContext = this;
        mApplication = (GreenDaoApplication) getApplication();
        courseDao = mApplication.getDaoSession().getCourseDao();
        initView();
    }

    private void initView() {

        editName = (EditText) findViewById(R.id.editName);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course c = new Course();
                c.setCourseName(editName.getText().toString());
                addStu(c);
                finish();
            }
        });

    }


    private void addStu(Course c) {
        courseDao.insert(c);
    }
}
