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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ssp.greendao.dao.Student;
import com.ssp.greendao.dao.StudentDao;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {


    ListView stuList;
    List<Student> students = new ArrayList<>();
    StudentAdapter studentAdapter;
    GreenDaoApplication mApplication;
    Context mContext;
    StudentDao studentDao;
    TextView empty;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        mContext = this;
        mApplication = (GreenDaoApplication) getApplication();
        studentDao = mApplication.getDaoSession().getStudentDao();
        initView();
    }

    private void initView() {
        stuList = (ListView) findViewById(R.id.stuList);
        studentAdapter = new StudentAdapter();
        stuList.setAdapter(studentAdapter);
        empty = (TextView) findViewById(R.id.txtEmpty);
        stuList.setEmptyView(empty);
        stuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(mContext, SelCourseActivity.class);
                i.putExtra("stu", students.get(position).getId());
                startActivity(i);
            }
        });

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
                startActivity(new Intent(mContext, AddStuActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class StudentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return students.size();
        }

        @Override
        public Object getItem(int position) {
            return students.get(position);
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

            Student stu = students.get(position);

            holder.txtId.setText(String.valueOf(stu.getId()));
            holder.txtName.setText(String.valueOf(stu.getName()));


            int size = stu.getStudentCourseList().size();

            if (size > 0) {
                holder.txtState.setText("已经选了" + String.valueOf(size) + "门课");

            } else {
                holder.txtState.setText("还没有选课哟~");
            }

            if (stu.getSex().equals("男")) {
                holder.rootBg.setBackgroundColor(Color.parseColor("#05BB77"));

            } else {
                holder.rootBg.setBackgroundColor(Color.parseColor("#FF655D"));
            }


            return convertView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        students = studentDao.loadAll();
        studentAdapter.notifyDataSetChanged();
    }
}
