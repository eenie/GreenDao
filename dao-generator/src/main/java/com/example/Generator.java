package com.example;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class Generator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(2, "com.ssp.greendao.dao");//1是数据库版本号，com.ssp.greendao.dao是自动生成的java类存放的包名，包括核心的DaoMaster,DaoSession等
//        addNote(schema);
//        addCustomerOrder(schema);
//        addStudentCourse(schema);

        addExamTable(schema);

//        addExam(schema);
        new DaoGenerator().generateAll(schema, "./app/src/main/java");//DaoMaster,DaoSession等自动生成java类存放在app Model的src/main/java文件夹下
    }

    /**
     * 添加一张单表
     *
     * @param schema
     */
    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");//创建一张Note表
        note.addIdProperty().primaryKey().autoincrement();//设置主键自增长
//        note.addIdProperty();这样默认就是id是主键并且自增长了，如果要自己设置一个键作为主键可以按上面一行代码这样写
        note.addStringProperty("text").notNull();//增加String类型的text列并且不能是空的，如果插入内容是空的会报异常
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }

    /**
     * 添加两张表顾客表和订单表，一对多关系
     *
     * @param schema
     */
    private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();//设置默认的id主键
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword ，设置表名
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        // 外键添加,外键customerId
        order.addToOne(customer, customerId);
        ToMany customerToOrders = customer.addToMany(order, customerId);//1对多关系，一个Customer顾客可以有多个Order订单，一个Order订单只属于一个Customer顾客
        customerToOrders.setName("orders");//设置Api的名称 getOrders()
        customerToOrders.orderAsc(orderDate);//设置查询结果按时间进行升序排序


    }

    /**
     * 添加三张表学生表，课程表还有一张中间表，多对多关系
     *
     * @param schema
     */
    private static void addStudentCourse(Schema schema) {
        Entity student = schema.addEntity("Student");
        student.addIdProperty();
        student.addStringProperty("name").notNull();
        student.addStringProperty("sex").notNull();

        Entity course = schema.addEntity("Course");
        course.addIdProperty();
        course.addStringProperty("courseName").notNull();

        Entity studentCourse = schema.addEntity("StudentCourse");//中间表用于关联学生表和课程表
        studentCourse.addIdProperty();
        Property studentId = studentCourse.addLongProperty("studentId").getProperty();
        Property courseId = studentCourse.addLongProperty("courseId").getProperty();

        studentCourse.addToOne(student, studentId);
        studentCourse.addToOne(course, courseId);

        student.addToMany(studentCourse, studentId);
        course.addToMany(studentCourse, courseId);
    }


    /**
     * 表 Exam、User、Question、Option、UserExam、ExamQuestion
     * 多对多：
     * Exam对应多个User、User对应多个Exam (中间表为：UserExam)
     * Exam对应多个Question、Question对应多个Exam(中间表为：ExamQuestion)
     * 一对多：
     * Question对应多个Option
     *
     * @param schema 建表工具
     */
    public static void addExam(Schema schema) {
        //Exam表
        Entity exam = schema.addEntity("Exam");
        exam.addIntProperty("id").primaryKey();
        exam.addStringProperty("name");
        exam.addStringProperty("notice");
        exam.addStringProperty("cover");
        exam.addIntProperty("type");
        exam.addIntProperty("state");

        //User表
        Entity user = schema.addEntity("User");
        user.addIntProperty("id").primaryKey();
        user.addStringProperty("name");

        //UserExam表（中间表）
        Entity userExam = schema.addEntity("UserExam");
        userExam.addIdProperty();
        Property userId = userExam.addIntProperty("userId").getProperty();
        Property examId = userExam.addIntProperty("examId").getProperty();

        userExam.addToOne(exam, examId);
        userExam.addToOne(user, userId);

        user.addToMany(userExam, userId);
        exam.addToMany(userExam, examId);

    }


    public static void addExamTable(Schema schema) {

        Entity question = schema.addEntity("Question");
        question.setTableName("Question");//Question表：问题表
        question.addIdProperty().autoincrement().getProperty();  //自增ID
        question.addStringProperty("index");//顺序
        question.addStringProperty("record_id"); //所属record的id
        question.addStringProperty("question_id");//该question的id
        question.addStringProperty("question_text");//question的文本内容
        question.addStringProperty("question_type");//question的类型
        question.addStringProperty("question_score");//question的分数
        question.addBooleanProperty("isCheck");//question是否被标记
        question.addBooleanProperty("isCorrect");//question是否回答正确
        question.addStringProperty("analysis");//question的解析

        Entity option = schema.addEntity("Option");
        option.setTableName("Option");//Option表：选项表
        option.addIdProperty().autoincrement().getProperty(); //自增ID
        option.addStringProperty("index"); //顺序
        option.addStringProperty("option_id");//该option的ID
        option.addStringProperty("option_text");//option文本
        Property queId = option.addLongProperty("question_id").getProperty();//option所属的question的id

        option.addToOne(question, queId);  //给Option添加question外键
        ToMany queOptions = option.addToMany(option, queId);
        queOptions.setName("options"); //设置函数名（ 通过getOptions(queId)函数获得该问题下的所有option）

        Entity answer = schema.addEntity("Answer");
        answer.setTableName("Answer");//回答表（包括用户答案、正确答案）
        answer.addStringProperty("option_id");
        answer.addIdProperty().autoincrement();//自增ID
        answer.addStringProperty("correct_ans");//正确答案
        answer.addStringProperty("user_ans");//用户答案
        Property quId = answer.addLongProperty("question_id").getProperty();

        answer.addToOne(question, quId);  //给Answer添加question外键
        ToMany queAnswers = answer.addToMany(answer, quId);
        queAnswers.setName("answers"); //设置函数名（ 通过getAnswers(queId)函数获得该问题下的所有Answer）


    }


}
