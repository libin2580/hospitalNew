package com.zulekhahospitals.zulekhaapp.departments;

/**
 * Created by libin on 9/29/2016.
 */
public class Department_Model {
    String deptId,divId,deptName,department_image,brach_id;

    public String getBrach_id() {
        return brach_id;
    }

    public void setBrach_id(String brach_id) {
        this.brach_id = brach_id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDivId() {
        return divId;
    }

    public void setDivId(String divId) {
        this.divId = divId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDepartment_image() {
        return department_image;
    }

    public void setDepartment_image(String department_image) {
        this.department_image = department_image;
    }
}
