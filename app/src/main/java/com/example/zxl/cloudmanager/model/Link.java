package com.example.zxl.cloudmanager.model;

/**
 * Created by ZXL on 2016/8/3.
 */
public class Link {
    public static final String localhost = "http://192.168.1.101/yunmgr_v1.0/api/uc.php?app=";
    public static final String API = "http://localhost/yunmgr_v1.0/api/uc.php?app=";

    public static final String manage_Leave = "manage_leave&act=";

    public static final String get_List = "get_list";

    //manage_leave
    public static final String mem_name = "mem_name";//员工姓名
    public static final String leave_type = "leave_type";//请假类型
    public static final String status = "status";//状态
    public static final String start_time = "start_time";//开始时间
    public static final String end_time = "end_time";//结束时间

    //manage_trip
    public static final String start_time_s = "start_time_s";//开始时间(小)
    public static final String start_time_e = "start_time_e";//开始时间(大)
    public static final String over_time_s = "over_time_s";//回归时间（小）
    public static final String over_time_e = "over_time_e";//回归时间（大）

    //manage_work
    public static final String mem_id = "";//加班员工ID
    public static final String work_pm = "";//加班项目ID

    //pm_bug
    public static final String pm_id = "";
}
