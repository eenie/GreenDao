package com.eenie.mob.greendao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ssp.greendao.dao.Course;
import com.ssp.greendao.dao.CourseDao;
import com.ssp.greendao.dao.Student;
import com.ssp.greendao.dao.StudentDao;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {


    ListView courseList;
    List<Course> courses = new ArrayList<>();
    CourseAdapter courseAdapter;
    GreenDaoApplication mApplication;
    Context mContext;
    CourseDao courseDao;
    TextView empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couse);

        mContext = this;
        mApplication = (GreenDaoApplication) getApplication();
        courseDao = mApplication.getDaoSession().getCourseDao();
        initView();
    }

    private void initView() {
        courseList = (ListView) findViewById(R.id.courseList);
        courseAdapter = new CourseAdapter();
        courseList.setAdapter(courseAdapter);
        empty = (TextView) findViewById(R.id.txtEmpty);
        courseList.setEmptyView(empty);
    }


    private class CourseAdapter extends BaseAdapter {
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


        private class Holder {

            TextView txtId, txtName, txtState;
            LinearLayout rootBg;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_student, null);
                holder = new Holder();
                holder.txtId = (TextView) convertView.findViewById(R.id.txtID);
                holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
                holder.txtState = (TextView) convertView.findViewById(R.id.txtState);
                holder.rootBg = (LinearLayout) convertView.findViewById(R.id.rootBg);

                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();
            }

            Course course = courses.get(position);

            holder.txtId.setText(String.valueOf(course.getId()));
            holder.txtName.setText(String.valueOf(course.getCourseName()));


            int size = course.getStudentCourseList().size();

            if (size > 0) {
                holder.txtState.setText("已经有" + String.valueOf(size) + "人选了这门课");

            } else {
                holder.txtState.setText("还没有人选哟~");
            }

            return convertView;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        courses = courseDao.loadAll();
        courseAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mian_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(mContext, AddCouseActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
