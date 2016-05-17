package com.eenie.mob.greendao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ssp.greendao.dao.CourseDao;
import com.ssp.greendao.dao.Student;
import com.ssp.greendao.dao.StudentDao;

public class MainActivity extends AppCompatActivity {
    GreenDaoApplication mApplication;
    Button btnStu;
    Button btnCourse;
    Context mContext;
    StudentDao studentDao;
    CourseDao courseDao;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mApplication = (GreenDaoApplication) getApplication();
        studentDao = mApplication.getDaoSession().getStudentDao();
        courseDao = mApplication.getDaoSession().getCourseDao();


        btnStu = (Button) findViewById(R.id.btnStu);
        btnStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, StudentActivity.class));
            }
        });
        btnCourse = (Button) findViewById(R.id.btnCourse);
        btnCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CourseActivity.class));
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        btnCourse.setText("课程（" + courseDao.count() + "）");
        btnStu.setText("学生（" + studentDao.count() + "）");


    }
}
