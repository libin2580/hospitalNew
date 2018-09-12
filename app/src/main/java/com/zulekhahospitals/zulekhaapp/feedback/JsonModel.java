package com.zulekhahospitals.zulekhaapp.feedback;

/**
 * Created by user 1 on 01-06-2016.
 */
public class JsonModel {
    private String questions;
    private String answers;

    public String getPoor_department_name() {
        return poor_department_name;
    }

    public void setPoor_department_name(String poor_department_name) {
        this.poor_department_name = poor_department_name;
    }

    private String poor_department_name;
    public String getRecommend() {
        return recommend;
    }

    public String getOverall_satisfaction() {
        return overall_satisfaction;
    }

    public void setOverall_satisfaction(String overall_satisfaction) {
        this.overall_satisfaction = overall_satisfaction;
    }

    public void setRecommend(String recommend) {

        this.recommend = recommend;
    }
    private String overall_satisfaction;
    private String department;
    private String suggestions;
    private String recommend;

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
}
