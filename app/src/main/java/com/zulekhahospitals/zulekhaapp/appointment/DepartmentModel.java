package com.zulekhahospitals.zulekhaapp.appointment;

/**
 * Created by Libin_Cybraum on 7/16/2016.
 */
public class DepartmentModel {
    public String getDep_id() {
        return dept_id;
    }

    public void setDep_id(String dep_id) {
        this.dept_id = dep_id;
    }

    public String getDep_nam() {
        return dept_nam;
    }

    public void setDep_nam(String dep_nam) {
        this.dept_nam = dep_nam;
    }

    String dept_id,dept_nam;
}
