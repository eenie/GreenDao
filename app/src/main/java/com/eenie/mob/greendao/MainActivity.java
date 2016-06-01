package com.eenie.mob.greendao;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.ssp.greendao.dao.AnswerDao;
import com.ssp.greendao.dao.OptionDao;
import com.ssp.greendao.dao.QuestionDao;

public class MainActivity extends AppCompatActivity {
    GreenDaoApplication mApplication;
    Button btnStu;
    Button btnCourse;
    Context mContext;
    QuestionDao questionDao;
    OptionDao optionDao;
    AnswerDao answerDao;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mApplication = (GreenDaoApplication) getApplication();
        questionDao = mApplication.getDaoSession().getQuestionDao();
        optionDao = mApplication.getDaoSession().getOptionDao();
        answerDao = mApplication.getDaoSession().getAnswerDao();





    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
