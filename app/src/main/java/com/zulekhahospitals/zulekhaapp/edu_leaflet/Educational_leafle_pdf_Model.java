package com.zulekhahospitals.zulekhaapp.edu_leaflet;

/**
 * Created by libin on 10/3/2016.
 */
public class Educational_leafle_pdf_Model {
    String imageid;
    String dept_name;

    public String getLeaflets() {
        return leaflets;
    }

    public void setLeaflets(String leaflets) {
        this.leaflets = leaflets;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getLeaflets_cover() {
        return leaflets_cover;
    }

    public void setLeaflets_cover(String leaflets_cover) {
        this.leaflets_cover = leaflets_cover;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeaflets_pdf() {
        return leaflets_pdf;
    }

    public void setLeaflets_pdf(String leaflets_pdf) {
        this.leaflets_pdf = leaflets_pdf;
    }

    public String getAdd_date() {
        return add_date;
    }

    public void setAdd_date(String add_date) {
        this.add_date = add_date;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    String leaflets;
    String leaflets_cover;
    String status;
    String leaflets_pdf;
    String orders;
    String add_date;
}
