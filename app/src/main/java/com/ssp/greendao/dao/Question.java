package com.ssp.greendao.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity mapped to table "Question".
 */
public class Question {

    private Long id;
    private String index;
    private String record_id;
    private String question_id;
    private String question_text;
    private String question_type;
    private String question_score;
    private Boolean isCheck;
    private Boolean isCorrect;
    private String analysis;
    List<Option> optionList = new ArrayList<>();
    List<Answer> answerList = new ArrayList<>();


    public Question() {


    }

    public Question(Long id) {
        this.id = id;
    }

    public Question(Long id, String index, String record_id, String question_id, String question_text, String question_type, String question_score, Boolean isCheck, Boolean isCorrect, String analysis) {
        this.id = id;
        this.index = index;
        this.record_id = record_id;
        this.question_id = question_id;
        this.question_text = question_text;
        this.question_type = question_type;
        this.question_score = question_score;
        this.isCheck = isCheck;
        this.isCorrect = isCorrect;
        this.analysis = analysis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public String getQuestion_score() {
        return question_score;
    }

    public void setQuestion_score(String question_score) {
        this.question_score = question_score;
    }

    public Boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Boolean isCheck) {
        this.isCheck = isCheck;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }


    public List<Option> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Option> optionList) {
        this.optionList = optionList;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }







    public static Question parseQuestionJson(JSONObject object) throws JSONException {
        JSONObject node = object.getJSONObject("node");
        JSONObject data = node.getJSONObject("data");
        JSONObject info = node.getJSONObject("info");
        Question question = new Question();
        question.setQuestion_id(info.getString("questionId"));
        question.setQuestion_type(info.getString("type"));
        question.setQuestion_score(object.getString("points"));
        question.setAnalysis(data.getJSONArray("analytical").getJSONObject(0).getJSONObject("params").getString("value"));
        question.setQuestion_text(data.getJSONArray("stem").getJSONObject(0).getJSONObject("params").getString("value"));



        ArrayList<Option> optionList = new ArrayList<>();
        if (question.getQuestion_type().startsWith("f") || question.getQuestion_type().startsWith("sh")) {

            //如果该问题为填空题或者问答题
            JSONArray catArray = data.getJSONArray("option");

            int optionSize = catArray.length();
            for (int j = 0; j < optionSize; j++) {
                JSONObject cat = catArray.getJSONObject(j);
                Option option = new Option();
                option.setOption_id(cat.getString("questionDataId"));
                option.setQuestion(question);
                optionList.add(option);
            }
            question.setOptionList(optionList);
        } else {
            if (data.optJSONArray("synopsis") != null && data.optJSONArray("option") != null) {
                JSONArray optionArray = data.getJSONArray("synopsis");
                JSONArray catArray = data.getJSONArray("option");
                int optionSize = optionArray.length();
                for (int j = 0; j < optionSize; j++) {
                    JSONObject param = optionArray.getJSONObject(j);
                    JSONObject cat = catArray.getJSONObject(j);
                    Option option = new Option();
//                    option.setId(cat.getString("questionDataId"));
//                    option.setIndex(param.getInt("pPos"));
//                    option.setDesc(param.getJSONObject("params").getString("value"));
//                    option.setCorrect(cat.getJSONObject("params").getBoolean("value"));
//                    option.setQuestionId(question.getId());
                    optionList.add(option);
                }
                question.setOptionList(optionList);
            }
        }

        return question;
    }






}
