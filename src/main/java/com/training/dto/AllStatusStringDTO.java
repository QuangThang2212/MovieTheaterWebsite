package com.training.dto;

public class AllStatusStringDTO {

    //classifiled seat when selec
    public final static String normalStatus = "normal_available";
    public final static String vipStatus = "vip_available";
    public final static String soldStatus = "sold";

    //for seat type
    public final static String vipSeatStatus = "Vip";
    public final static double vipSeatPrice = 100000;
    public final static String normalSeatStatus = "normal";
    public final static double normalSeatPrice = 50000;

    //ticket status
    public final static String bookingInvoice = "booking";
    public final static String sellingInvoice = "selling";

    //Role name
    public final static String employeeRole = "ROLE_EMPLOYEE";
    public final static String memberRole = "ROLE_USER";

    //convert choice
    public final static String agreeConvert = "Agree";
    public final static String disagreeConvert = "Disagree";

    //booking status
    public final static String waitingForTicket = "WAITING FOR TICKET";
    public final static String getTicket = "GET TICKET";
    public final static String cancelTicket = "CANCEL TICKET";

    public final static String scheduleSeatStatus = "booked";
}
