package com.uca.constants;

public class UcaConstants {
    /**
     * 审核初始化状态：0
     */
    public static final int ARCHIVES_CHECK_STATUS_INIT = 0;

    /**
     * 送审状态：1
     */
    public static final int ARCHIVES_CHECK_STATUS_SENDING = 1;

    /**
     * 未通过审核状态：2
     */
    public static final int ARCHIVES_CHECK_STATUS_FAILED = 2;

    /**
     * 通过审核状态：3
     */
    public static final int ARCHIVES_CHECK_STATUS_PASS = 3;

    /**
     * 重新上传状态：4
     */
    public static final int ARCHIVES_CHECK_STATUS_AGAIN = 4;

    /**
     * 经办人：ROLE_RF_HANDING
     */
    public static final String UM_ROLE_CODE_HANDING = "ROLE_RF_HANDING";

    /**
     * 普通用户：ROLE_RF_USER
     */
    public static final String UM_ROLE_CODE_USER = "ROLE_RF_USER";

    /**
     * 工程档案：1
     */
    public static final int ARCHIVES_TYPE_PROJECT = 1;

    /**
     * 声像档案：2
     */
    public static final int ARCHIVES_TYPE_SOUND = 2;

    /**
     * 已预约：1
     */
    public static final int APPLY_QUERY_RECORD_STATUS_ONE = 1;

    /**
     * 可预约：2
     */
    public static final int APPLY_QUERY_RECORD_STATUS_TWO = 2;

    /**
     * 可来馆取件：1
     */
    public static final int ARCHIVES_COPY_STATUS_ONE = 1;

    /**
     * 尚未办理完成：0
     */
    public static final int ARCHIVES_COPY_STATUS_ZERO = 0;

    /**
     * 机构根节点id：0
     */
    public static final int UM_ORG_ROOT_ID = 0;

    /**
     * 入档状态：1
     */
    public static final int ARCHIVES_INTO_FILE_STATUS_ONE = 1;

    /**
     * 入档状态：0
     */
    public static final int ARCHIVES_INTO_FILE_STATUS_ZERO = 0;

    /**
     * 未发布：1
     */
    public static final int EXAM_PAPER_STATUS_NO_PUBLISH = 1;

    /**
     * 发布：2
     */
    public static final int EXAM_PAPER_STATUS_PUBLISH = 2;

    /**
     * 多选：1
     */
    public static final int EXAM_QUESTION_TYPE_MULTIPLE = 1;

    /**
     * 单选：2
     */
    public static final int EXAM_QUESTION_TYPE_SINGLE = 2;

    /**
     * 问答题：4
     */
    public static final int EXAM_QUESTION_TYPE_WENDATI = 4;

    /**
     * 通知通告已发布：1
     */
    public static final int NOTICE_RELEASE_STATUS_ONE = 1;

    /**
     * 通知通告未发布：2
     */
    public static final int NOTICE_RELEASE_STATUS_TWO = 2;

    /**
     * 会议室可用：1
     */
    public static final int MEETING_ROOM_STATUS_ONE = 1;

    /**
     * 会议室不可用：2
     */
    public static final int MEETING_ROOM_STATUS_TWO = 2;

    /**
     * 按周会议室预约统计
     */
    public static final int MEETING_ROOM_APPLY_STATISTICS_WEEK = 1;

    /**
     * 按月会议室预约统计
     */
    public static final int MEETING_ROOM_APPLY_STATISTICS_MONTH = 2;

    /**
     * 按年会议室预约统计
     */
    public static final int MEETING_ROOM_APPLY_STATISTICS_YEAR = 3;

    /**
     * 未阅卷：1
     */
    public static final int ANSWER_STATUS_NO_READOVER = 1;

    /**
     * 已阅卷：2
     */
    public static final int ANSWER_STATUS_READOVER = 2;

    /**
     * 市档案馆机构：3
     */
    public static final int ORG_TYPE_CITY = 3;

    /**
     * 区县机构：2
     */
    public static final int ORG_TYPE_AREA = 2;

    /**
     * 机构用户：9（用于判别机构用户树下的节点是用户）
     */
    public static final int ORG_TYPE_USER = 9;

    /**
     * 1：普通用户
     */
    public static final int UM_USER_EXPAND_TYPE_USER = 1;

    /**
     * 3：市档案馆用户
     */
    public static final int UM_USER_EXPAND_TYPE_CITYUSER = 3;

    /**
     * 2：区县用户
     */
    public static final int UM_USER_EXPAND_TYPE_AREAUSER = 2;

    /**
     * 1：档案未重命名
     */
    public static final int RENAME_STATUS_N = 1;
    /**
     * 2：档案已重命名
     */
    public static final int RENAME_STATUS_Y = 2;
}
