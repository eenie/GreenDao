package com.ssp.greendao.dao;

import java.util.List;

/**
 * Project: GreenDao
 * Desc:
 * Author:Eenie
 * Email:472279981@qq.com
 * Data:2016/6/1
 * Backup:
 */
public class QuetionHelper {

    QuestionDao questionDao;
    AnswerDao answerDao;
    OptionDao optionDao;


    public QuetionHelper(QuestionDao questionDao, AnswerDao answerDao, OptionDao optionDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.optionDao = optionDao;
    }

    /**
     * 根据考试记录的ID获取该次记录的问题
     *
     * @param recordId 记录ID
     * @return 问题列表
     */
    public List<Question> loadQuestion(String recordId) {
        List<Question> questionList = questionDao.loadAll();
        for (Question q : questionList) {
            q.setAnswerList(answerDao._queryAnswer_Answers(q.getId()));
            q.setOptionList(optionDao._queryOption_Options(q.getId()));
        }
        return questionList;
    }


    public void addQuestion(Question q) {




    }


}
