package com.uca.statistic.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.statistic.dao.OrgArchivesStatisticDao;
import com.uca.statistic.pojos.UcaOrgArchivesStatistic;
import com.uca.statistic.service.OrgArchivesStatisticService;
import com.uca.statistic.vo.UcaOrgArchivesStatisticVo;

@Service
public class OrgArchivesStatisticServiceImpl implements OrgArchivesStatisticService {
    private SecurityIdentifyUtils secUtils = new SecurityUtils();

    @Autowired
    private OrgArchivesStatisticDao orgArchivesStatisticDao;

    // @Override
    // public void find(JSONObject object, Page<UcaOrgArchivesStatistic> page) {
    // // TODO Auto-generated method stub
    // Criteria crit =
    // orgArchivesStatisticDao.getSession().createCriteria(UcaOrgArchivesStatistic.class);
    // crit.add(Restrictions.eq("createUserId",
    // secUtils.getUser().getUserId()));
    //
    // int total = crit.list().size();
    //
    // crit.addOrder(Order.desc("createTime"));
    // crit.setFirstResult(page.getPageNo());
    // crit.setMaxResults(page.getPageSize());
    // List<UcaOrgArchivesStatistic> lists = crit.list();
    //
    // if(lists != null && lists.size() > 0) {
    // for(UcaOrgArchivesStatistic po : lists) {
    // po.setCreateUserName(secUtils.getUser().getUserName());
    // }
    // object.put("total", total);
    // object.put("rows", lists);
    // } else {
    // object.put("total", 0);
    // object.put("rows", new ArrayList<UcaOrgArchivesStatistic>());
    // }
    // }

    @Override
    public void find(JSONObject object, Page<UcaOrgArchivesStatisticVo> page) {
        StringBuilder sql = new StringBuilder(
                "select us.id as id,us.year_value as yv,"
                        + "us.org_name as ona,us.user_name as un,us.create_user_id as uid,us.create_user_phone as up,"
                        + "us.create_time as ct,us.code_1 as c1,us.code_2 as c2,us.code_3 as c3,us.code_4 as c4,us.code_5 as c5,"
                        + "us.code_6 as c6,us.code_7 as c7,us.code_8 as c8,us.code_9 as c9,us.code_10 as c10,us.code_11 as c11,"
                        + "us.code_12 as c12,us.code_13 as c13,us.code_14 as c14,us.code_15 as c15,us.code_16 as c16,"
                        + "us.code_17 as c17,us.code_18 as c18,us.code_19 as c19,us.code_20 as c20,us.code_21 as c21,"
                        + "us.code_22 as c22,us.code_23 as c23,us.code_24 as c24,us.code_25 as c25,us.code_26 as c26,"
                        + "us.code_27 as c27,us.code_28 as c28,us.code_29 as c29,us.code_30 as c30,us.code_31 as c31,"
                        + "us.code_32 as c32,us.code_33 as c33,us.code_34 as c34,us.code_35 as c35,us.code_36 as c36,"
                        + "us.code_37 as c37,us.code_38_1 as c381,us.code_38_2 as c382,us.code_38_3 as c383,us.code_39 as c39,"
                        + "us.code_40 as c40,us.code_41 as c41,us.code_42 as c42,us.code_43 as c43,us.code_44 as c44,"
                        + "us.code_45 as c45,us.code_46 as c46,us.code_47 as c47,us.code_48 as c48,us.code_49 as c49,"
                        + "us.code_50 as c50,us.code_51 as c51,us.code_52 as c52,us.code_53 as c53,us.code_54 as c54,"
                        + "us.code_55 as c55,us.code_56 as c56,us.code_57 as c57,us.code_58 as c58,us.code_59 as c59,"
                        + "us.code_60 as c60,us.code_61 as c61,us.code_62 as c62,us.code_63 as c63,us.code_64 as c64,"
                        + "us.code_65 as c65,us.code_66 as c66,us.code_67 as c67,us.code_68 as c68,us.code_69 as c69,"
                        + "us.code_70 as c70,us.code_71 as c71,us.code_72 as c72,us.code_73 as c73,us.code_74 as c74,"
                        + "us.code_75 as c75,us.code_76 as c76,us.code_77 as c77,us.code_78 as c78,us.code_79 as c79,"
                        + "us.code_80 as c80,us.code_81 as c81,us.code_82 as c82,us.code_83 as c83,us.code_84 as c84,"
                        + "us.code_85 as c85,us.code_86 as c86,us.code_87 as c87,us.code_88 as c88,us.code_89 as c89,"
                        + "us.code_90 as c90,us.code_91 as c91,us.code_92 as c92,us.code_93 as c93,us.code_94 as c94,"
                        + "us.code_95 as c95,us.code_96 as c96,us.code_97 as c97,us.code_98 as c98,us.code_99 as c99, "
                        + "us.code_100 as c100,us.code_101 as c101,us.code_102 as c102,us.code_103 as c103,"
                        + "us.code_104 as c104,us.code_105 as c105,us.code_106 as c106,us.code_107 as c107,"
                        + "us.code_108 as c108,us.code_109 as c109,us.code_110 as c110,us.code_111 as c111,"
                        + "us.code_112 as c112,us.code_113 as c113,us.code_114 as c114,us.code_115 as c115,"
                        + "us.code_116 as c116,us.code_117 as c117,us.code_118 as c118,us.code_119 as c119,"
                        + "us.code_120 as c120,us.code_121 as c121,us.code_122 as c122,us.code_123 as c123 "
                        + "from uca_org_archives_statistic us where us.create_user_id=:createUserId order by us.create_time desc");
        String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
        SQLQuery query = orgArchivesStatisticDao.getSession().createSQLQuery(sql.append(pagingSql).toString());
        query.setParameter("createUserId", secUtils.getUser().getUserId());

        List<Object[]> list = query.list();
        List<UcaOrgArchivesStatisticVo> listResult = new ArrayList<UcaOrgArchivesStatisticVo>(list.size());
        UcaOrgArchivesStatisticVo vo = null;

        if (list != null && list.size() > 0) {
            for (Object[] obj : list) {
                vo = new UcaOrgArchivesStatisticVo();
                vo.setId(Integer.valueOf(obj[0].toString()));
                vo.setYv(obj[1] != null ? Integer.valueOf(obj[1].toString()) : 0);
                vo.setOna(obj[2] != null ? obj[2].toString() : "");
                vo.setUn(obj[3] != null ? obj[3].toString() : "");
                vo.setUid(obj[4] != null ? Integer.valueOf(obj[4].toString()) : 0);
                vo.setUp(obj[5] != null ? obj[5].toString() : "");
                vo.setCt(obj[6] != null ? DateUtil.DateToStr((Date) obj[6]) : "");
                vo.setC1(obj[7] != null ? Integer.valueOf(obj[7].toString()) : 0);
                vo.setC2(obj[8] != null ? Integer.valueOf(obj[8].toString()) : 0);
                vo.setC3(obj[9] != null ? Integer.valueOf(obj[9].toString()) : 0);
                vo.setC4(obj[10] != null ? Integer.valueOf(obj[10].toString()) : 0);
                vo.setC5(obj[11] != null ? Integer.valueOf(obj[11].toString()) : 0);
                vo.setC6(obj[12] != null ? Integer.valueOf(obj[12].toString()) : 0);
                vo.setC7(obj[13] != null ? Integer.valueOf(obj[13].toString()) : 0);
                vo.setC8(obj[14] != null ? Integer.valueOf(obj[14].toString()) : 0);
                vo.setC9(obj[15] != null ? Integer.valueOf(obj[15].toString()) : 0);
                vo.setC10(obj[16] != null ? Integer.valueOf(obj[16].toString()) : 0);
                vo.setC11(obj[17] != null ? Integer.valueOf(obj[17].toString()) : 0);
                vo.setC12(obj[18] != null ? Integer.valueOf(obj[18].toString()) : 0);
                vo.setC13(obj[19] != null ? Integer.valueOf(obj[19].toString()) : 0);
                vo.setC14(obj[20] != null ? Integer.valueOf(obj[20].toString()) : 0);
                vo.setC15(obj[21] != null ? Integer.valueOf(obj[21].toString()) : 0);
                vo.setC16(obj[22] != null ? Integer.valueOf(obj[22].toString()) : 0);
                vo.setC17(obj[23] != null ? Integer.valueOf(obj[23].toString()) : 0);
                vo.setC18(obj[24] != null ? Integer.valueOf(obj[24].toString()) : 0);
                vo.setC19(obj[25] != null ? Integer.valueOf(obj[25].toString()) : 0);
                vo.setC20(obj[26] != null ? Integer.valueOf(obj[26].toString()) : 0);
                vo.setC21(obj[27] != null ? Integer.valueOf(obj[27].toString()) : 0);
                vo.setC22(obj[28] != null ? Integer.valueOf(obj[28].toString()) : 0);
                vo.setC23(obj[29] != null ? Integer.valueOf(obj[29].toString()) : 0);
                vo.setC24(obj[30] != null ? Integer.valueOf(obj[30].toString()) : 0);
                vo.setC25(obj[31] != null ? Integer.valueOf(obj[31].toString()) : 0);
                vo.setC26(obj[32] != null ? Integer.valueOf(obj[32].toString()) : 0);
                vo.setC27(obj[33] != null ? Integer.valueOf(obj[33].toString()) : 0);
                vo.setC28(obj[34] != null ? Integer.valueOf(obj[34].toString()) : 0);
                vo.setC29(obj[35] != null ? Integer.valueOf(obj[35].toString()) : 0);
                vo.setC30(obj[36] != null ? Integer.valueOf(obj[36].toString()) : 0);
                vo.setC31(obj[37] != null ? Integer.valueOf(obj[37].toString()) : 0);
                vo.setC32(obj[38] != null ? Integer.valueOf(obj[38].toString()) : 0);
                vo.setC33(obj[39] != null ? Integer.valueOf(obj[39].toString()) : 0);
                vo.setC34(obj[40] != null ? Integer.valueOf(obj[40].toString()) : 0);
                vo.setC35(obj[41] != null ? Integer.valueOf(obj[41].toString()) : 0);
                vo.setC36(obj[42] != null ? Integer.valueOf(obj[42].toString()) : 0);
                vo.setC37(obj[43] != null ? Integer.valueOf(obj[43].toString()) : 0);
                vo.setC381(obj[44] != null ? Integer.valueOf(obj[44].toString()) : 0);
                vo.setC382(obj[45] != null ? Integer.valueOf(obj[45].toString()) : 0);
                vo.setC383(obj[46] != null ? Integer.valueOf(obj[46].toString()) : 0);
                vo.setC39(obj[47] != null ? Integer.valueOf(obj[47].toString()) : 0);
                vo.setC40(obj[48] != null ? Integer.valueOf(obj[48].toString()) : 0);
                vo.setC41(obj[49] != null ? Integer.valueOf(obj[49].toString()) : 0);
                vo.setC42(obj[50] != null ? Integer.valueOf(obj[50].toString()) : 0);
                vo.setC43(obj[51] != null ? Integer.valueOf(obj[51].toString()) : 0);
                vo.setC44(obj[52] != null ? Integer.valueOf(obj[52].toString()) : 0);
                vo.setC45(obj[53] != null ? Integer.valueOf(obj[53].toString()) : 0);
                vo.setC46(obj[54] != null ? Integer.valueOf(obj[54].toString()) : 0);
                vo.setC47(obj[55] != null ? Integer.valueOf(obj[55].toString()) : 0);
                vo.setC48(obj[56] != null ? Integer.valueOf(obj[56].toString()) : 0);
                vo.setC49(obj[57] != null ? Integer.valueOf(obj[57].toString()) : 0);
                vo.setC50(obj[58] != null ? Integer.valueOf(obj[58].toString()) : 0);
                vo.setC51(obj[59] != null ? Integer.valueOf(obj[59].toString()) : 0);
                vo.setC52(obj[60] != null ? Integer.valueOf(obj[60].toString()) : 0);
                vo.setC53(obj[61] != null ? Integer.valueOf(obj[61].toString()) : 0);
                vo.setC54(obj[62] != null ? Integer.valueOf(obj[62].toString()) : 0);
                vo.setC55(obj[63] != null ? Integer.valueOf(obj[63].toString()) : 0);
                vo.setC56(obj[64] != null ? Integer.valueOf(obj[64].toString()) : 0);
                vo.setC57(obj[65] != null ? Integer.valueOf(obj[65].toString()) : 0);
                vo.setC58(obj[66] != null ? Integer.valueOf(obj[66].toString()) : 0);
                vo.setC59(obj[67] != null ? Integer.valueOf(obj[67].toString()) : 0);
                vo.setC60(obj[68] != null ? Integer.valueOf(obj[68].toString()) : 0);
                vo.setC61(obj[69] != null ? Integer.valueOf(obj[69].toString()) : 0);
                vo.setC62(obj[70] != null ? Integer.valueOf(obj[70].toString()) : 0);
                vo.setC63(obj[71] != null ? Integer.valueOf(obj[71].toString()) : 0);
                vo.setC64(obj[72] != null ? Integer.valueOf(obj[72].toString()) : 0);
                vo.setC65(obj[73] != null ? Integer.valueOf(obj[73].toString()) : 0);
                vo.setC66(obj[74] != null ? Integer.valueOf(obj[74].toString()) : 0);
                vo.setC67(obj[75] != null ? Integer.valueOf(obj[75].toString()) : 0);
                vo.setC68(obj[76] != null ? Integer.valueOf(obj[76].toString()) : 0);
                vo.setC69(obj[77] != null ? Integer.valueOf(obj[77].toString()) : 0);
                vo.setC70(obj[78] != null ? Integer.valueOf(obj[78].toString()) : 0);
                vo.setC71(obj[79] != null ? Integer.valueOf(obj[79].toString()) : 0);
                vo.setC72(obj[80] != null ? Integer.valueOf(obj[80].toString()) : 0);
                vo.setC73(obj[81] != null ? Integer.valueOf(obj[81].toString()) : 0);
                vo.setC74(obj[82] != null ? Integer.valueOf(obj[82].toString()) : 0);
                vo.setC75(obj[83] != null ? Integer.valueOf(obj[83].toString()) : 0);
                vo.setC76(obj[84] != null ? Integer.valueOf(obj[84].toString()) : 0);
                vo.setC77(obj[85] != null ? Integer.valueOf(obj[85].toString()) : 0);
                vo.setC78(obj[86] != null ? Integer.valueOf(obj[86].toString()) : 0);
                vo.setC79(obj[87] != null ? Integer.valueOf(obj[87].toString()) : 0);
                vo.setC80(obj[88] != null ? Integer.valueOf(obj[88].toString()) : 0);
                vo.setC81(obj[89] != null ? Integer.valueOf(obj[89].toString()) : 0);
                vo.setC82(obj[90] != null ? Integer.valueOf(obj[90].toString()) : 0);
                vo.setC83(obj[91] != null ? Integer.valueOf(obj[91].toString()) : 0);
                vo.setC84(obj[92] != null ? Integer.valueOf(obj[92].toString()) : 0);
                vo.setC85(obj[93] != null ? Integer.valueOf(obj[93].toString()) : 0);
                vo.setC86(obj[94] != null ? Integer.valueOf(obj[94].toString()) : 0);
                vo.setC87(obj[95] != null ? Integer.valueOf(obj[95].toString()) : 0);
                vo.setC88(obj[96] != null ? Integer.valueOf(obj[96].toString()) : 0);
                vo.setC89(obj[97] != null ? Integer.valueOf(obj[97].toString()) : 0);
                vo.setC90(obj[98] != null ? Integer.valueOf(obj[98].toString()) : 0);
                vo.setC91(obj[99] != null ? Integer.valueOf(obj[99].toString()) : 0);
                vo.setC92(obj[100] != null ? Integer.valueOf(obj[100].toString()) : 0);
                vo.setC93(obj[101] != null ? Integer.valueOf(obj[101].toString()) : 0);
                vo.setC94(obj[102] != null ? Integer.valueOf(obj[102].toString()) : 0);
                vo.setC95(obj[103] != null ? Integer.valueOf(obj[103].toString()) : 0);
                vo.setC96(obj[104] != null ? Integer.valueOf(obj[104].toString()) : 0);
                vo.setC97(obj[105] != null ? Integer.valueOf(obj[105].toString()) : 0);
                vo.setC98(obj[106] != null ? Integer.valueOf(obj[106].toString()) : 0);
                vo.setC99(obj[107] != null ? Integer.valueOf(obj[107].toString()) : 0);
                vo.setC100(obj[108] != null ? Integer.valueOf(obj[108].toString()) : 0);
                vo.setC101(obj[109] != null ? Integer.valueOf(obj[109].toString()) : 0);
                vo.setC102(obj[110] != null ? Integer.valueOf(obj[110].toString()) : 0);
                vo.setC103(obj[111] != null ? Integer.valueOf(obj[111].toString()) : 0);
                vo.setC104(obj[112] != null ? Integer.valueOf(obj[112].toString()) : 0);
                vo.setC105(obj[113] != null ? Integer.valueOf(obj[113].toString()) : 0);
                vo.setC106(obj[114] != null ? Integer.valueOf(obj[114].toString()) : 0);
                vo.setC107(obj[115] != null ? Integer.valueOf(obj[115].toString()) : 0);
                vo.setC108(obj[116] != null ? Integer.valueOf(obj[116].toString()) : 0);
                vo.setC109(obj[117] != null ? Integer.valueOf(obj[117].toString()) : 0);
                vo.setC110(obj[118] != null ? Integer.valueOf(obj[118].toString()) : 0);
                vo.setC111(obj[119] != null ? Integer.valueOf(obj[119].toString()) : 0);
                vo.setC112(obj[120] != null ? Integer.valueOf(obj[120].toString()) : 0);
                vo.setC113(obj[121] != null ? Integer.valueOf(obj[121].toString()) : 0);
                vo.setC114(obj[122] != null ? Integer.valueOf(obj[122].toString()) : 0);
                vo.setC115(obj[123] != null ? Integer.valueOf(obj[123].toString()) : 0);
                vo.setC116(obj[124] != null ? Integer.valueOf(obj[124].toString()) : 0);
                vo.setC117(obj[125] != null ? Integer.valueOf(obj[125].toString()) : 0);
                vo.setC118(obj[126] != null ? Integer.valueOf(obj[126].toString()) : 0);
                vo.setC119(obj[127] != null ? Integer.valueOf(obj[127].toString()) : 0);
                vo.setC120(obj[128] != null ? Integer.valueOf(obj[128].toString()) : 0);
                vo.setC121(obj[129] != null ? Integer.valueOf(obj[129].toString()) : 0);
                vo.setC122(obj[130] != null ? Integer.valueOf(obj[130].toString()) : 0);
                vo.setC123(obj[131] != null ? Integer.valueOf(obj[131].toString()) : 0);
                vo.setCun(secUtils.getUser().getUserName());
                listResult.add(vo);
            }
        }

        page.setListResult(listResult);

        pagingSql = "select count(1) from uca_org_archives_statistic us where us.create_user_id=:createUserId";
        query = orgArchivesStatisticDao.getSession().createSQLQuery(pagingSql);
        query.setParameter("createUserId", secUtils.getUser().getUserId());

        List countList = query.list();
        object.put("rows", page.getListResult());
        object.put("total", (BigInteger) countList.get(0));
    }

    @Override
    public UcaOrgArchivesStatistic find(String sd, String ed) {
        // TODO Auto-generated method stub
        Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }

        Criteria crit = orgArchivesStatisticDao.getSession().createCriteria(UcaOrgArchivesStatistic.class);
        if (null != start && null != end) {
            crit.add(Restrictions.between("createTime", start, end));
        } else {
            if (null != start) {
                crit.add(Restrictions.ge("createTime", start));
            } else if (null != end) {
                crit.add(Restrictions.le("createTime", end));
            }
        }

        List<UcaOrgArchivesStatistic> lists = crit.list();

        if (lists != null && lists.size() > 0) {
            UcaOrgArchivesStatistic retObj = new UcaOrgArchivesStatistic();
            for (UcaOrgArchivesStatistic obj : lists) {
                retObj.setCode_1(retObj.getCode_1() + obj.getCode_1());
                retObj.setCode_2(retObj.getCode_2() + obj.getCode_2());
                retObj.setCode_3(retObj.getCode_3() + obj.getCode_3());
                retObj.setCode_4(retObj.getCode_4() + obj.getCode_4());
                retObj.setCode_5(retObj.getCode_5() + obj.getCode_5());
                retObj.setCode_6(retObj.getCode_6() + obj.getCode_6());
                retObj.setCode_7(retObj.getCode_7() + obj.getCode_7());
                retObj.setCode_8(retObj.getCode_8() + obj.getCode_8());
                retObj.setCode_9(retObj.getCode_9() + obj.getCode_9());
                retObj.setCode_10(retObj.getCode_10() + obj.getCode_10());
                retObj.setCode_11(retObj.getCode_11() + obj.getCode_11());
                retObj.setCode_12(retObj.getCode_12() + obj.getCode_12());
                retObj.setCode_13(retObj.getCode_13() + obj.getCode_13());
                retObj.setCode_14(retObj.getCode_14() + obj.getCode_14());
                retObj.setCode_15(retObj.getCode_15() + obj.getCode_15());
                retObj.setCode_16(retObj.getCode_16() + obj.getCode_16());
                retObj.setCode_17(retObj.getCode_17() + obj.getCode_17());
                retObj.setCode_18(retObj.getCode_18() + obj.getCode_18());
                retObj.setCode_19(retObj.getCode_19() + obj.getCode_19());
                retObj.setCode_20(retObj.getCode_20() + obj.getCode_20());
                retObj.setCode_21(retObj.getCode_21() + obj.getCode_21());
                retObj.setCode_22(retObj.getCode_22() + obj.getCode_22());
                retObj.setCode_23(retObj.getCode_23() + obj.getCode_23());
                retObj.setCode_24(retObj.getCode_24() + obj.getCode_24());
                retObj.setCode_25(retObj.getCode_25() + obj.getCode_25());
                retObj.setCode_26(retObj.getCode_26() + obj.getCode_26());
                retObj.setCode_27(retObj.getCode_27() + obj.getCode_27());
                retObj.setCode_28(retObj.getCode_28() + obj.getCode_28());
                retObj.setCode_29(retObj.getCode_29() + obj.getCode_29());
                retObj.setCode_30(retObj.getCode_30() + obj.getCode_30());
                retObj.setCode_31(retObj.getCode_31() + obj.getCode_31());
                retObj.setCode_32(retObj.getCode_32() + obj.getCode_32());
                retObj.setCode_33(retObj.getCode_33() + obj.getCode_33());
                retObj.setCode_34(retObj.getCode_34() + obj.getCode_34());
                retObj.setCode_35(retObj.getCode_35() + obj.getCode_35());
                retObj.setCode_36(retObj.getCode_36() + obj.getCode_36());
                retObj.setCode_37(retObj.getCode_37() + obj.getCode_37());
                retObj.setCode_38_1(retObj.getCode_38_1() + obj.getCode_38_1());
                retObj.setCode_38_2(retObj.getCode_38_2() + obj.getCode_38_2());
                retObj.setCode_38_3(retObj.getCode_38_3() + obj.getCode_38_3());
                retObj.setCode_39(retObj.getCode_39() + obj.getCode_39());
                retObj.setCode_40(retObj.getCode_40() + obj.getCode_40());
                retObj.setCode_41(retObj.getCode_41() + obj.getCode_41());
                retObj.setCode_42(retObj.getCode_42() + obj.getCode_42());
                retObj.setCode_43(retObj.getCode_43() + obj.getCode_43());
                retObj.setCode_44(retObj.getCode_44() + obj.getCode_44());
                retObj.setCode_45(retObj.getCode_45() + obj.getCode_45());
                retObj.setCode_46(retObj.getCode_46() + obj.getCode_46());
                retObj.setCode_47(retObj.getCode_47() + obj.getCode_47());
                retObj.setCode_48(retObj.getCode_48() + obj.getCode_48());
                retObj.setCode_49(retObj.getCode_49() + obj.getCode_49());
                retObj.setCode_50(retObj.getCode_50() + obj.getCode_50());
                retObj.setCode_51(retObj.getCode_51() + obj.getCode_51());
                retObj.setCode_52(retObj.getCode_52() + obj.getCode_52());
                retObj.setCode_53(retObj.getCode_53() + obj.getCode_53());
                retObj.setCode_54(retObj.getCode_54() + obj.getCode_54());
                retObj.setCode_55(retObj.getCode_55() + obj.getCode_55());
                retObj.setCode_56(retObj.getCode_56() + obj.getCode_56());
                retObj.setCode_57(retObj.getCode_57() + obj.getCode_57());
                retObj.setCode_58(retObj.getCode_58() + obj.getCode_58());
                retObj.setCode_59(retObj.getCode_59() + obj.getCode_59());
                retObj.setCode_60(retObj.getCode_60() + obj.getCode_60());
                retObj.setCode_61(retObj.getCode_61() + obj.getCode_61());
                retObj.setCode_62(retObj.getCode_62() + obj.getCode_62());
                retObj.setCode_63(retObj.getCode_63() + obj.getCode_63());
                retObj.setCode_64(retObj.getCode_64() + obj.getCode_64());
                retObj.setCode_65(retObj.getCode_65() + obj.getCode_65());
                retObj.setCode_66(retObj.getCode_66() + obj.getCode_66());
                retObj.setCode_67(retObj.getCode_67() + obj.getCode_67());
                retObj.setCode_68(retObj.getCode_68() + obj.getCode_68());
                retObj.setCode_69(retObj.getCode_69() + obj.getCode_69());
                retObj.setCode_70(retObj.getCode_70() + obj.getCode_70());
                retObj.setCode_71(retObj.getCode_71() + obj.getCode_71());
                retObj.setCode_72(retObj.getCode_72() + obj.getCode_72());
                retObj.setCode_73(retObj.getCode_73() + obj.getCode_73());
                retObj.setCode_74(retObj.getCode_74() + obj.getCode_74());
                retObj.setCode_75(retObj.getCode_75() + obj.getCode_75());
                retObj.setCode_76(retObj.getCode_76() + obj.getCode_76());
                retObj.setCode_77(retObj.getCode_77() + obj.getCode_77());
                retObj.setCode_78(retObj.getCode_78() + obj.getCode_78());
                retObj.setCode_79(retObj.getCode_79() + obj.getCode_79());
                retObj.setCode_80(retObj.getCode_80() + obj.getCode_80());
                retObj.setCode_81(retObj.getCode_81() + obj.getCode_81());
                retObj.setCode_82(retObj.getCode_82() + obj.getCode_82());
                retObj.setCode_83(retObj.getCode_83() + obj.getCode_83());
                retObj.setCode_84(retObj.getCode_84() + obj.getCode_84());
                retObj.setCode_85(retObj.getCode_85() + obj.getCode_85());
                retObj.setCode_86(retObj.getCode_86() + obj.getCode_86());
                retObj.setCode_87(retObj.getCode_87() + obj.getCode_87());
                retObj.setCode_88(retObj.getCode_88() + obj.getCode_88());
                retObj.setCode_89(retObj.getCode_89() + obj.getCode_89());
                retObj.setCode_90(retObj.getCode_90() + obj.getCode_90());
                retObj.setCode_91(retObj.getCode_91() + obj.getCode_91());
                retObj.setCode_92(retObj.getCode_92() + obj.getCode_92());
                retObj.setCode_93(retObj.getCode_93() + obj.getCode_93());
                retObj.setCode_94(retObj.getCode_94() + obj.getCode_94());
                retObj.setCode_95(retObj.getCode_95() + obj.getCode_95());
                retObj.setCode_96(retObj.getCode_96() + obj.getCode_96());
                retObj.setCode_97(retObj.getCode_97() + obj.getCode_97());
                retObj.setCode_98(retObj.getCode_98() + obj.getCode_98());
                retObj.setCode_99(retObj.getCode_99() + obj.getCode_99());
                retObj.setCode_100(retObj.getCode_100() + obj.getCode_100());
                retObj.setCode_101(retObj.getCode_101() + obj.getCode_101());
                retObj.setCode_102(retObj.getCode_102() + obj.getCode_102());
                retObj.setCode_103(retObj.getCode_103() + obj.getCode_103());
                retObj.setCode_104(retObj.getCode_104() + obj.getCode_104());
                retObj.setCode_105(retObj.getCode_105() + obj.getCode_105());
                retObj.setCode_106(retObj.getCode_106() + obj.getCode_106());
                retObj.setCode_107(retObj.getCode_107() + obj.getCode_107());
                retObj.setCode_108(retObj.getCode_108() + obj.getCode_108());
                retObj.setCode_109(retObj.getCode_109() + obj.getCode_109());
                retObj.setCode_110(retObj.getCode_110() + obj.getCode_110());
                retObj.setCode_111(retObj.getCode_111() + obj.getCode_111());
                retObj.setCode_112(retObj.getCode_112() + obj.getCode_112());
                retObj.setCode_113(retObj.getCode_113() + obj.getCode_113());
                retObj.setCode_114(retObj.getCode_114() + obj.getCode_114());
                retObj.setCode_115(retObj.getCode_115() + obj.getCode_115());
                retObj.setCode_116(retObj.getCode_116() + obj.getCode_116());
                retObj.setCode_117(retObj.getCode_117() + obj.getCode_117());
                retObj.setCode_118(retObj.getCode_118() + obj.getCode_118());
                retObj.setCode_119(retObj.getCode_119() + obj.getCode_119());
                retObj.setCode_120(retObj.getCode_120() + obj.getCode_120());
                retObj.setCode_121(retObj.getCode_121() + obj.getCode_121());
                retObj.setCode_122(retObj.getCode_122() + obj.getCode_122());
                retObj.setCode_123(retObj.getCode_123() + obj.getCode_123());
            }
            return retObj;
        }
        return null;
    }

    @Override
    public void saveOrUpdate(JSONObject object, UcaOrgArchivesStatistic po) {
        // TODO Auto-generated method stub
        po.setCreateUserId(secUtils.getUser().getUserId());
        po.setCreateTime(new Date());

        // 第70行=72+74=76+78
        int code72 = po.getCode_72();
        int code74 = po.getCode_74();
        int code76 = po.getCode_76();
        int code78 = po.getCode_78();
        if ((code72 + code74) != (code76 + code78)) {
            object.put("success", false);
            object.put("msg", "本年利用档案所属时期与利用者类别(卷次)数量不一致！");
            return;
        } else {
            po.setCode_70(code72 + code74);
        }
        // 第71行=73+75=77+79
        int code73 = po.getCode_73();
        int code75 = po.getCode_75();
        int code77 = po.getCode_77();
        int code79 = po.getCode_79();
        if ((code73 + code75) != (code77 + code79)) {
            object.put("success", false);
            object.put("msg", "本年利用档案所属时期与利用者类别(件次)数量不一致！");
            return;
        } else {
            po.setCode_71(code73 + code75);
        }
        // 82≥83
        int code82 = po.getCode_82();
        int code83 = po.getCode_83();
        if (code82 < code83) {
            object.put("success", false);
            object.put("msg", "本年举办档案展览个数不应小于基本陈列个数！");
            return;
        }
        // 89≥90＋91＋92＋93
        int code89 = po.getCode_89();
        int code90 = po.getCode_90();
        int code91 = po.getCode_91();
        int code92 = po.getCode_92();
        int code93 = po.getCode_93();
        if (code89 < (code90 + code91 + code92 + code93)) {
            object.put("success", false);
            object.put("msg", "档案馆总建筑面积不应小于其他建筑面积总和！");
            return;
        }
        // 第29行=46+55
        int code46 = po.getCode_46();
        int code55 = po.getCode_55();
        po.setCode_29(code46 + code55);
        // 第31行=47+56
        int code47 = po.getCode_47();
        int code56 = po.getCode_56();
        po.setCode_31(code47 + code56);
        // 第46行=49+51+53
        int code49 = po.getCode_49();
        int code51 = po.getCode_51();
        int code53 = po.getCode_53();
        po.setCode_46(code49 + code51 + code53);
        // 第47行=48+50+52+54
        int code48 = po.getCode_48();
        int code50 = po.getCode_50();
        int code52 = po.getCode_52();
        int code54 = po.getCode_54();
        po.setCode_47(code48 + code50 + code52 + code54);

        orgArchivesStatisticDao.saveOrUpdate(po);

        if (po.getId() > 0) {
            object.put("success", true);
            object.put("msg", "添加成功！");
        } else {
            object.put("success", false);
            object.put("msg", "添加失败！");
        }
    }

}
