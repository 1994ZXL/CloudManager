package com.example.zxl.cloudmanager.model;

/**
 * Created by ZXL on 2016/8/3.
 */
public class Link {
//    public static final String localhost = "http://192.168.3.119:8099/yunmgr_v1.0/api/uc.php?app=";
    public static final String localhost = "http://192.168.3.10/yunmgr_v1.0/api/uc.php?app=";
//    public static final String localhost = "http://192.168.0.102/yunmgr_v1.0/api/uc.php?app=";
    public static final String API = "http://localhost/yunmgr_v1.0/api/uc.php?app=";

    public static final String get_list = "get_list";
    public static final String edit = "edit";
    public static final String add = "add";
    public static final String options_pmmember = "options_pmmember";

    public static final String is_pmmaster = "is_pmmaster"; //是否是项目主管
    public static final String is_puncher = "is_puncher"; //是否是考勤主管
    public static final String is_pmleader = "is_pmleader"; //是否是项目领导
    public static final String mem_job = "mem_job"; //职务
    public static final String user_id = "user_id"; //当前登录人员id

    //manage_leave
    public static final String manage_leave = "manage_leave&act=";
    public static final String mem_name = "mem_name";//员工姓名
    public static final String leave_type = "leave_type";//请假类型
    public static final String status = "status";//状态
    public static final String start_time = "start_time";//开始时间
    public static final String end_time = "end_time";//结束时间
    public static final String leave_reson = "leave_reason"; //请假原因
    public static final String handle_opinion = "handle_opinion"; //处理意见
    public static final String leave_id = "leave_id"; //请假id

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
    public static final String trip_id = "trip_id"; //出差id
    public static final String address = "address"; //出差地址
    public static final String detail_addr = "detail_addr"; //详细地址
    public static final String trip_reason = "trip_reason"; //出差原因
    public static final String over_time_trip_add = "over_time"; //回程时间

    //manage_work
    public static final String manage_work = "manage_work&act=";
    public static final String mem_id = "mem_id";//加班员工ID
    public static final String work_pm = "work_pm";//加班项目名
    public static final String work_id = "work_id"; //加班id
    public static final String work_reason = "work_reason"; //加班原因

    //my_work
    public static final String my_work = "my_work&act=";

    //work_list
    public static final String work_list = "work_list&act=";

    //ps_bug
    public static final String ps_bug = "ps_bug&act=";

    //pm_bug
    public static final String pm_bug = "pm_bug&act=";
    public static final String pmbug_id = "pmbug_id";
    public static final String modify_time = "modify_time";

    //my_bug
    public static final String my_bug = "my_bug&act=";
    public static final String submit_time_from = "submit_time_from"; //发现时间 开始
    public static final String submit_time_to = "submit_time_to"; //发现时间 结束
    public static final String mofify_time_from = "mofify_time_from"; //修改时间 开始
    public static final String modify_time_to = "modify_time_to"; //修改时间 结束
    public static final String submitter = "submitter"; //发现人
    public static final String modifier = "modifier"; //修改人
    public static final String level = "level"; //bug等级
    public static final String into_way = "into_way"; //进入方式
    public static final String case_mode = "case_mode"; //用例模型
    public static final String modular = "modular"; //模板
    public static final String remark = "remark"; //备注

    //find_daily
    public static final String find_daily = "find_daily&act=";
    public static final String daily_time_from = "daily_date_from"; //日报日期 开始
    public static final String daily_time_to = "daily_date_to"; //日报日期 结束
    public static final String content = "content"; //日报内容
    public static final String sort = "sort"; //排序
    public static final String page_count = "page_count"; //每页显示数量
    public static final String curl_page = "curl_page"; //当前页

    //manage_daily
    public static final String manage_daily = "manage_daily&act=";
    public static final String opinion = "opinion";

    //my_daily
    public static final String my_daily = "my_daily&act=";
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
    public static final String pm_task = "pm_task&act=";
    public static final String pmtask_id = "pmtask_id"; //项目任务ID
    public static final String over_time = "end_time"; //结束时间
    public static final String title = "title";
    public static final String evaluate = "evaluate";
    public static final String pmsch_id = "pmsch_id";
    public static final String start_time_from = "start_time_from"; //开始时间 开始
    public static final String start_time_to = "start_time_to"; //开始时间 结束
    public static final String end_time_from = "end_time_from"; //结束时间 开始
    public static final String end_time_to = "end_time_to"; //结束时间 结束

    //pm_task_list
    public static final String pm_task_list = "pm_task_list&act=";

    //pm_schedule
    public static final String pm_schedule = "pm_schedule&act=";
    public static final String report_time_from = "report_time_from"; //提交时间开始
    public static final String report_time_to = "report_time_to"; //提交时间结束
    public static final String percent_from = "percent_from"; //进度 开始
    public static final String percent_to = "percent_to"; //进度 结束
    public static final String percent = "percent"; //进度
    public static final String report_time = "report_time"; //提交时间

    //manage_pm
    public static final String manage_pm = "manage_pm&act=";
    public static final String pm_id = "pm_id"; //项目id
    public static final String pm_master_name = "pm_master"; //项目主管
    public static final String project_name = "project_name"; //项目名称
    public static final String goon_technical = "goon_technical"; //技术负责人
    public static final String goon_business = "goon_business"; //客服
    public static final String project_summary = "project_summary"; //项目内容
    public static final String belong_unit = "belong_unit"; //客户单位
    public static final String custom_name = "custom_name"; //客户联系人
    public static final String custom_phone = "custom_phone"; //客户手机
    public static final String ready_time = "ready_time"; //准备开始时间
    public static final String goon_time = "goon_time"; //运维开始时间
    public static final String todo_time = "todo_time"; //开发开始时间
    public static final String cancel_time = "cancel_time"; //取消时间
    public static final String finshed_time = "finshed_time"; //结束时间
    public static final String goon_actual_start_time = "goon_actual_start_time"; //运维实际开始时间
    public static final String goon_actual_end_time = "goon_actual_end_time"; //运维实际结束时间
    public static final String project_state = "project_state"; //项目状态(0:取消，1:准备，2:开发，3:维护，4:结束)

    //pm_list
    public static final String pm_list = "pm_list&act=";

    //my_operation
    public static final String my_operation = "my_operation&act=";

    //my_note
    public static final String my_note = "my_note&act=";
    public static final String my_note_over_time = "over_time";
    public static final String note_id = "note_id";

    //pm_contact
    public static final String pm_contact = "pm_contact&act=";
    public static final String pmcon_id = "pmcon_id"; //项目通讯录ID
    public static final String contact_name = "contact_name";
    public static final String contact_phone = "contact_phone";
    public static final String contact_position = "contact_position";
    public static final String contact_company = "contact_company";

    //pmcontact 公共查询
    public static final String pmcontact = "pmcontact&act=";

    //pm_manage_member
    public static final String pm_manage_member = "pm_manage_member&act=";
    public static final String pmmem_id = "pmmem_id"; //项目成员ID
    public static final String role = "role"; //身份（1领导；2项目负责人；3一般成员）
    public static final String member_res = "member_res"; //职责说明

    //pm_member 公共查询
    public static final String pm_member = "pm_member&act=";
}
