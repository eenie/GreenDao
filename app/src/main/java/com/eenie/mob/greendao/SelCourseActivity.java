package com.eenie.mob.greendao;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ssp.greendao.dao.Course;
import com.ssp.greendao.dao.CourseDao;
import com.ssp.greendao.dao.Student;
import com.ssp.greendao.dao.StudentCourse;
import com.ssp.greendao.dao.StudentCourseDao;
import com.ssp.greendao.dao.StudentDao;

import java.util.ArrayList;
import java.util.List;

public class SelCourseActivity extends AppCompatActivity {

    TextView txtName;
    Student student;
    StudentDao studentDao;
    CourseDao courseDao;
    StudentCourseDao studentCourseDao;
    GreenDaoApplication mApplication;
    Context mContext;
    ListView noSelList;
    ListView selList;
    List<Course> selCourse = new ArrayList<>();
    List<Course> noSelCourse = new ArrayList<>();
    CourseAdapter selAdapter;
    CourseAdapter noSelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_course);

        mContext = this;
        mApplication = (GreenDaoApplication) getApplication();
        studentDao = mApplication.getDaoSession().getStudentDao();
        courseDao = mApplication.getDaoSession().getCourseDao();
        studentCourseDao = mApplication.getDaoSession().getStudentCourseDao();

        initView();
        getStu(getIntent().getLongExtra("stu", 0));
    }

    private void initView() {
        txtName = (TextView) findViewById(R.id.txtUser);
        noSelList = (ListView) findViewById(R.id.noSelList);
        noSelAdapter = new CourseAdapter();
        noSelList.setAdapter(noSelAdapter);
        noSelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StudentCourse sc = new StudentCourse();
                sc.setCourse((Course) noSelAdapter.getItem(position));
                sc.setStudent(student);
                studentCourseDao.insert(sc);
                upListView();
            }
        });


        selList = (ListView) findViewById(R.id.selList);
        selAdapter = new CourseAdapter();
        selList.setAdapter(selAdapter);

    }


    public void getStu(long id) {
        student = studentDao.load(id);
        txtName.setText(student.getName());
    }


    @Override
    protected void onResume() {
        super.onResume();
        upListView();

    }


    private void upListView() {
        selCourse.clear();
        noSelCourse.clear();



        List<Course> courses = courseDao.loadAll();
        student.refresh();


        for (StudentCourse sc : student.getStudentCourseList()) {
            selCourse.add(sc.getCourse());
        }
        noSelCourse = courses;


        System.out.println("size " + selCourse.size());
        selAdapter.replace(selCourse);
        noSelAdapter.replace(courses);

    }


    private class CourseAdapter extends BaseAdapter {
        List<Course> courses = new ArrayList<>();
        public CourseAdapter() {

        }


        public void replace(List<Course> list) {
            this.courses = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return courses.size();
        }

        @Override
        public Object getItem(int position) {
            return courses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView txtName;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_sel_course, null);
                txtName = (TextView) convertView.findViewById(R.id.txtName);
                convertView.setTag(txtName);
            } else {
                txtName = (TextView) convertView.getTag();
            }
            txtName.setText(courses.get(position).getCourseName());
            return convertView;
        }
    }


}
