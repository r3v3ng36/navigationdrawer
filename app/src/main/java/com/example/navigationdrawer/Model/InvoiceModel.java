
package com.example.navigationdrawer.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceModel {

    @SerializedName("s_id")
    @Expose
    private String sId;
    @SerializedName("s_entry_no")
    @Expose
    private String sEntryNo;
    @SerializedName("s_idno")
    @Expose
    private String sIdno;
    @SerializedName("s_fname")
    @Expose
    private String sFname;
    @SerializedName("s_lname")
    @Expose
    private String sLname;
    @SerializedName("s_cnic")
    @Expose
    private String sCnic;
    @SerializedName("s_mobile_no")
    @Expose
    private String sMobileNo;
    @SerializedName("s_alt_mobileno")
    @Expose
    private String sAltMobileno;
    @SerializedName("s_email")
    @Expose
    private String sEmail;
    @SerializedName("s_guardian_name")
    @Expose
    private String sGuardianName;
    @SerializedName("s_guardian_relation")
    @Expose
    private String sGuardianRelation;
    @SerializedName("s_guardian_mobile")
    @Expose
    private String sGuardianMobile;
    @SerializedName("s_address")
    @Expose
    private String sAddress;
    @SerializedName("s_gender")
    @Expose
    private String sGender;
    @SerializedName("s_course_id")
    @Expose
    private String sCourseId;
    @SerializedName("s_teacher_id")
    @Expose
    private String sTeacherId;
    @SerializedName("s_last_qualification")
    @Expose
    private String sLastQualification;
    @SerializedName("s_current_enrolled_inst")
    @Expose
    private String sCurrentEnrolledInst;
    @SerializedName("s_no_of_attempts")
    @Expose
    private String sNoOfAttempts;
    @SerializedName("s_last_attempt_marks")
    @Expose
    private String sLastAttemptMarks;
    @SerializedName("s_paper1_marks")
    @Expose
    private String sPaper1Marks;
    @SerializedName("s_paper2_marks")
    @Expose
    private String sPaper2Marks;
    @SerializedName("s_feesamount")
    @Expose
    private String sFeesamount;
    @SerializedName("s_feespaid")
    @Expose
    private String sFeespaid;
    @SerializedName("s_status")
    @Expose
    private String sStatus;
    @SerializedName("s_photo")
    @Expose
    private String sPhoto;
    @SerializedName("s_checkin")
    @Expose
    private String sCheckin;
    @SerializedName("s_checkout")
    @Expose
    private String sCheckout;
    @SerializedName("s_callstatus")
    @Expose
    private String sCallstatus;
    @SerializedName("s_callstatus_date")
    @Expose
    private String sCallstatusDate;
    @SerializedName("s_reservedtext")
    @Expose
    private String sReservedtext;
    @SerializedName("s_createby")
    @Expose
    private String sCreateby;
    @SerializedName("s_editby")
    @Expose
    private String sEditby;
    @SerializedName("s_converted")
    @Expose
    private String sConverted;
    @SerializedName("s_password")
    @Expose
    private String sPassword;
    @SerializedName("s_type")
    @Expose
    private String sType;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("c_id")
    @Expose
    private String cId;
    @SerializedName("c_entry_no")
    @Expose
    private String cEntryNo;
    @SerializedName("c_idno")
    @Expose
    private String cIdno;
    @SerializedName("c_title")
    @Expose
    private String cTitle;
    @SerializedName("c_teacher_id")
    @Expose
    private String cTeacherId;
    @SerializedName("c_duration")
    @Expose
    private String cDuration;
    @SerializedName("c_fees")
    @Expose
    private String cFees;
    @SerializedName("c_startdate")
    @Expose
    private String cStartdate;
    @SerializedName("c_enddate")
    @Expose
    private String cEnddate;
    @SerializedName("c_status")
    @Expose
    private String cStatus;
    @SerializedName("c_current")
    @Expose
    private String cCurrent;
    @SerializedName("c_upcoming")
    @Expose
    private String cUpcoming;

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public String getSEntryNo() {
        return sEntryNo;
    }

    public void setSEntryNo(String sEntryNo) {
        this.sEntryNo = sEntryNo;
    }

    public String getSIdno() {
        return sIdno;
    }

    public void setSIdno(String sIdno) {
        this.sIdno = sIdno;
    }

    public String getSFname() {
        return sFname;
    }

    public void setSFname(String sFname) {
        this.sFname = sFname;
    }

    public String getSLname() {
        return sLname;
    }

    public void setSLname(String sLname) {
        this.sLname = sLname;
    }

    public String getSCnic() {
        return sCnic;
    }

    public void setSCnic(String sCnic) {
        this.sCnic = sCnic;
    }

    public String getSMobileNo() {
        return sMobileNo;
    }

    public void setSMobileNo(String sMobileNo) {
        this.sMobileNo = sMobileNo;
    }

    public String getSAltMobileno() {
        return sAltMobileno;
    }

    public void setSAltMobileno(String sAltMobileno) {
        this.sAltMobileno = sAltMobileno;
    }

    public String getSEmail() {
        return sEmail;
    }

    public void setSEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getSGuardianName() {
        return sGuardianName;
    }

    public void setSGuardianName(String sGuardianName) {
        this.sGuardianName = sGuardianName;
    }

    public String getSGuardianRelation() {
        return sGuardianRelation;
    }

    public void setSGuardianRelation(String sGuardianRelation) {
        this.sGuardianRelation = sGuardianRelation;
    }

    public String getSGuardianMobile() {
        return sGuardianMobile;
    }

    public void setSGuardianMobile(String sGuardianMobile) {
        this.sGuardianMobile = sGuardianMobile;
    }

    public String getSAddress() {
        return sAddress;
    }

    public void setSAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getSGender() {
        return sGender;
    }

    public void setSGender(String sGender) {
        this.sGender = sGender;
    }

    public String getSCourseId() {
        return sCourseId;
    }

    public void setSCourseId(String sCourseId) {
        this.sCourseId = sCourseId;
    }

    public String getSTeacherId() {
        return sTeacherId;
    }

    public void setSTeacherId(String sTeacherId) {
        this.sTeacherId = sTeacherId;
    }

    public String getSLastQualification() {
        return sLastQualification;
    }

    public void setSLastQualification(String sLastQualification) {
        this.sLastQualification = sLastQualification;
    }

    public String getSCurrentEnrolledInst() {
        return sCurrentEnrolledInst;
    }

    public void setSCurrentEnrolledInst(String sCurrentEnrolledInst) {
        this.sCurrentEnrolledInst = sCurrentEnrolledInst;
    }

    public String getSNoOfAttempts() {
        return sNoOfAttempts;
    }

    public void setSNoOfAttempts(String sNoOfAttempts) {
        this.sNoOfAttempts = sNoOfAttempts;
    }

    public String getSLastAttemptMarks() {
        return sLastAttemptMarks;
    }

    public void setSLastAttemptMarks(String sLastAttemptMarks) {
        this.sLastAttemptMarks = sLastAttemptMarks;
    }

    public String getSPaper1Marks() {
        return sPaper1Marks;
    }

    public void setSPaper1Marks(String sPaper1Marks) {
        this.sPaper1Marks = sPaper1Marks;
    }

    public String getSPaper2Marks() {
        return sPaper2Marks;
    }

    public void setSPaper2Marks(String sPaper2Marks) {
        this.sPaper2Marks = sPaper2Marks;
    }

    public String getSFeesamount() {
        return sFeesamount;
    }

    public void setSFeesamount(String sFeesamount) {
        this.sFeesamount = sFeesamount;
    }

    public String getSFeespaid() {
        return sFeespaid;
    }

    public void setSFeespaid(String sFeespaid) {
        this.sFeespaid = sFeespaid;
    }

    public String getSStatus() {
        return sStatus;
    }

    public void setSStatus(String sStatus) {
        this.sStatus = sStatus;
    }

    public String getSPhoto() {
        return sPhoto;
    }

    public void setSPhoto(String sPhoto) {
        this.sPhoto = sPhoto;
    }

    public String getSCheckin() {
        return sCheckin;
    }

    public void setSCheckin(String sCheckin) {
        this.sCheckin = sCheckin;
    }

    public String getSCheckout() {
        return sCheckout;
    }

    public void setSCheckout(String sCheckout) {
        this.sCheckout = sCheckout;
    }

    public String getSCallstatus() {
        return sCallstatus;
    }

    public void setSCallstatus(String sCallstatus) {
        this.sCallstatus = sCallstatus;
    }

    public String getSCallstatusDate() {
        return sCallstatusDate;
    }

    public void setSCallstatusDate(String sCallstatusDate) {
        this.sCallstatusDate = sCallstatusDate;
    }

    public String getSReservedtext() {
        return sReservedtext;
    }

    public void setSReservedtext(String sReservedtext) {
        this.sReservedtext = sReservedtext;
    }

    public String getSCreateby() {
        return sCreateby;
    }

    public void setSCreateby(String sCreateby) {
        this.sCreateby = sCreateby;
    }

    public String getSEditby() {
        return sEditby;
    }

    public void setSEditby(String sEditby) {
        this.sEditby = sEditby;
    }

    public String getSConverted() {
        return sConverted;
    }

    public void setSConverted(String sConverted) {
        this.sConverted = sConverted;
    }

    public String getSPassword() {
        return sPassword;
    }

    public void setSPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    public String getSType() {
        return sType;
    }

    public void setSType(String sType) {
        this.sType = sType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getCEntryNo() {
        return cEntryNo;
    }

    public void setCEntryNo(String cEntryNo) {
        this.cEntryNo = cEntryNo;
    }

    public String getCIdno() {
        return cIdno;
    }

    public void setCIdno(String cIdno) {
        this.cIdno = cIdno;
    }

    public String getCTitle() {
        return cTitle;
    }

    public void setCTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public String getCTeacherId() {
        return cTeacherId;
    }

    public void setCTeacherId(String cTeacherId) {
        this.cTeacherId = cTeacherId;
    }

    public String getCDuration() {
        return cDuration;
    }

    public void setCDuration(String cDuration) {
        this.cDuration = cDuration;
    }

    public String getCFees() {
        return cFees;
    }

    public void setCFees(String cFees) {
        this.cFees = cFees;
    }

    public String getCStartdate() {
        return cStartdate;
    }

    public void setCStartdate(String cStartdate) {
        this.cStartdate = cStartdate;
    }

    public String getCEnddate() {
        return cEnddate;
    }

    public void setCEnddate(String cEnddate) {
        this.cEnddate = cEnddate;
    }

    public String getCStatus() {
        return cStatus;
    }

    public void setCStatus(String cStatus) {
        this.cStatus = cStatus;
    }

    public String getCCurrent() {
        return cCurrent;
    }

    public void setCCurrent(String cCurrent) {
        this.cCurrent = cCurrent;
    }

    public String getCUpcoming() {
        return cUpcoming;
    }

    public void setCUpcoming(String cUpcoming) {
        this.cUpcoming = cUpcoming;
    }

}
