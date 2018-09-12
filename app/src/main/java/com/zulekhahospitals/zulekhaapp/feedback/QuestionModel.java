package com.zulekhahospitals.zulekhaapp.feedback;

/**
 * Created by user 1 on 11-10-2016.
 */

public class QuestionModel {
    private String questid;
    private String questname;
    private String department;
    private String user_id;
    private String agentid;
    private String person_name;

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getQuestid() {
        return questid;
    }

    public void setQuestid(String questid) {
        this.questid = questid;
    }

    public String getQuestname() {
        return questname;
    }

    public void setQuestname(String questname) {
        this.questname = questname;
    }

}
