package com.example.zxl.cloudmanager.model;

/**
 * Created by ZXL on 2016/8/3.
 */
public class Link {
//    public static final String localhost = "http://192.168.3.119:8099/yunmgr_v1.0/api/uc.php?app=";
    public static final String localhost = "http://192.168.1.105/yunmgr_v1.0/api/uc.php?app=";
    public static final String API = "http://localhost/yunmgr_v1.0/api/uc.php?app=";

    public static final String get_list = "get_list";
    public static final String edit = "edit";

    //manage_leave
    public static final String manage_leave = "manage_leave&act=";
    public static final String mem_name = "mem_name";//员工姓名
    public static final String leave_type = "leave_type";//请假类型
    public static final String status = "status";//状态
    public static final String start_time = "start_time";//开始时间
    public static final String end_time = "end_time";//结束时间
    public static final String leave_reson = "leave_reason"; //请假原因
    //leave_list
    public static final String leave_list = "leave_list&act=";

    //my_leave
    public static final String my_leave = "my_leave&act=";

    //trip_list
    public static final String trip_list = "trip_list&act=";

    //my_trip
    public static final String my_trip = "my_trip&act=";

    //manage_trip
    public static final String manage_trip = "manage_trip&act=";
    public static final String start_time_s = "start_time_s";//开始时间(小)
    public static final String start_time_e = "start_time_e";//开始时间(大)
    public static final String over_time_s = "over_time_s";//回归时间（小）
    public static final String over_time_e = "over_time_e";//回归时间（大）

    //manage_work
    public static final String manage_work = "manage_work&act=";
    public static final String mem_id = "mem_id";//加班员工ID
    public static final String work_pm = "work_pm";//加班项目名

    //my_work
    public static final String my_work = "my_work&act=";

    //work_list
    public static final String work_list = "work_list&act=";

    //pm_bug
    public static final String pm_id = "pm_id";

    //my_bug
    public static final String submit_time_t = "submit_time_t"; //发现时间 开始
    public static final String submit_time_f = "submit_time_f"; //发现时间 结束
    public static final String modify_time_t = "modify_time_t"; //修改时间 开始
    public static final String modify_time_f = "modify_time_f"; //修改时间 结束
    public static final String submitter = "submitter"; //发现人
    public static final String modifier = "modifier"; //修改人
    public static final String level = "level"; //bug等级

    //find_daily
    public static final String report_time_t = "report_time_t"; //日报的提交时间 开始
    public static final String report_time_f = "report_time_f"; //日报的提交时间 结束
    public static final String content = "content"; //日报内容
    public static final String sort = "sort"; //排序
    public static final String page_count = "page_count"; //每页显示数量
    public static final String curl_page = "curl_page"; //当前页

    //my_daily
    public static final String create_time_t = "create_time_t"; //日报的创建时间 开始
    public static final String create_time_f = "create_time_f"; //日报的创建时间 结束
    public static final String state = "state"; //日报状态
    public static final String daily_id = "daily_id"; //日报的提交时间 开始

    //member
    public static final String user_name = "user_name"; //用户名
    public static final String password = "password"; //密码
    public static final String user_type = "user_type"; //用户类型
    public static final String phone = "phone"; //手机号码
    public static final String qq = "qq"; //QQ号
    public static final String wchat = "wchat"; //微信号
    public static final String email = "email"; //邮箱
    public static final String mem_region = "mem_region"; //地址
    public static final String mem_addr = "mem_addr"; //详细地址

    //my_punch
    public static final String my_punch = "my_punch&act=get_list";
    public static final String att_date_start = "att_date_start"; //考勤日期(早)
    public static final String att_date_end = "att_date_end"; //考勤日期（晚）
    public static final String att_id = "att_id"; //考勤id
    public static final String s_att_time = "s_att_time"; //上班签到时间
    public static final String e_att_time = "e_att_time"; //下班签到时间

    //manage_punch
    public static final String manage_punch = "manage_punch&act=get_list";
    public static final String S_att_time = "S_att_time"; //上班签到时间
    public static final String S_time = "S_time"; //规定上班时间
    public static final String E_att_time = "E_att_time"; //下班签到时间
    public static final String E_time = "E_time"; //规定下班时间
    public static final String comp_id = "comp_id"; //公司id

    //punch_list
    public static final String punch_list = "punch_list&act=get_list";
    public static final String att_date_from = "att_date_from"; //考勤日期（早）
    public static final String att_date_to = "att_date_to"; //考勤日期（晚）

    //pm_task
    public static final String pmtask_id = "pmtask_id"; //项目任务ID
    public static final String over_time = "over_time"; //结束时间
    public static final String title = "title";
    public static final String evaluate = "evaluate";
    public static final String pmsch_id = "pmsch_id";
    public static final String percent = "percent";

    //manage_pm
    public static final String project_name = "project_name"; //项目名称
    public static final String ready_time = "ready_time"; //准备开始时间
    public static final String finished_time = "finished_time"; //结束时间
    public static final String project_state = "project_state"; //项目状态
    public static final String header = "header"; //项目负责人

}
