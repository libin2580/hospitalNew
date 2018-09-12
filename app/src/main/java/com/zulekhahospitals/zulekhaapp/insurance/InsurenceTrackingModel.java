package com.zulekhahospitals.zulekhaapp.insurance;

/**
 * Created by Anvin on 12/6/2017.
 */

public class InsurenceTrackingModel {
    String tracking_no,service_name,approval_status,remarks,validity,service_cost,approved_limit;

    public String getTracking_no() {
        return tracking_no;
    }

    public void setTracking_no(String tracking_no) {
        this.tracking_no = tracking_no;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(String approval_status) {
        this.approval_status = approval_status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getService_cost() {
        return service_cost;
    }

    public void setService_cost(String service_cost) {
        this.service_cost = service_cost;
    }

    public String getApproved_limit() {
        return approved_limit;
    }

    public void setApproved_limit(String approved_limit) {
        this.approved_limit = approved_limit;
    }
}
