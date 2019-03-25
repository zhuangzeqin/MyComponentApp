package com.eeepay.zzq.demo.lib_common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author :xqf
 * @date :2018/4/17 11:13
 * @desc :
 * @update :
 */

public class LoginInfo implements Serializable {
    /**
     * status : 200
     * msg : 登录成功
     * data : {"myInfo":{"id":21,"merchant_no":"50725","oem_no":"OEM000011","wx_openid":"oOXUI0sUKvTj_rNyd1YY4t2tVmao","unionid":"oP6FRwuCibC-d9W-176Z4MObIQtw","one_agent_no":"347433","agent_no":"347433","agent_node":"0-347433-","create_time":"2018-05-03 23:25:30","user_name":"我是呵呵","mobile_username":"13424230742","par_mer_no":"46498","mer_node":"0-46498-50725-","mer_capa":"1","mer_act_status":"1","mer_account":0,"headimgurl":"http://thirdwx.qlogo.cn/mmopen/9IZPn9Jy5yeJ1QffMbuIKuqZ0kaxcYxEIY0A23m9oibCncCCYj0SLR09rQibtlajicuHTLerqcQIdtmmHJhMianic5G4UQaQ32pE2/132","status":"1","freeze_amount":0,"last_update_time":"2018-06-12 16:36:59","bind_wechat":23663,"openid":"oOXUI0sUKvTj_rNyd1YY4t2tVmao","nickname":"我是呵呵","mer_capa_zh":"用户","top_mer_capa":"5","has_password":true,"merchant_no_sign":"c19670dc14a851d9133b56ee3b96a38e","oem_no_sign":"5350d2ff71cdf8672d09ebde60a07f8b"},"oemInfo":{"id":1,"oem_no":"OEM000011","oem_name":"超级兑","create_time":"2018-05-03 21:20:04","last_update_time":"2018-07-12 20:13:03","company_no":"yiliankejifuwu","company_name":"深圳市移联天下科技服务有限公司","service_phone":"4009003066","public_account":"gh_87302f5698eb","public_account_name":"积分超级兑","mailbox":"szzml@xhtpay.com","agreement":"666"},"merCapas":[{"oem_config_code":"mer_capa_1","mer_capa":"1","mer_capa_zh":"用户"},{"oem_config_code":"mer_capa_2","mer_capa":"2","mer_capa_zh":"超级用户"},{"oem_config_code":"mer_capa_3","mer_capa":"3","mer_capa_zh":"会员"},{"oem_config_code":"mer_capa_4","mer_capa":"4","mer_capa_zh":"黄金会员"},{"oem_config_code":"mer_capa_5","mer_capa":"5","mer_capa_zh":"钻石会员"}],"parMerInfo":{"mer_no":"46498","nickname":"喜悦之龙","headimgurl":"http://thirdwx.qlogo.cn/mmopen/UHGGVp2oIia2jIawqlH1FSwTQicthcicO32rzLpRjF6kG0RaFwFsW24gKato3k4ibYYmP1Wia29Jp924JXAiatwO8RhKoFv1O6ogqU/132","mobile_username":"13823711222"}}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "myInfo=" + myInfo +
                    ", oemInfo=" + oemInfo +
                    ", parMerInfo=" + parMerInfo +
                    ", merCapas=" + merCapas +
                    '}';
        }

        /**
         * myInfo : {"id":21,"merchant_no":"50725","oem_no":"OEM000011","wx_openid":"oOXUI0sUKvTj_rNyd1YY4t2tVmao","unionid":"oP6FRwuCibC-d9W-176Z4MObIQtw","one_agent_no":"347433","agent_no":"347433","agent_node":"0-347433-","create_time":"2018-05-03 23:25:30","user_name":"我是呵呵","mobile_username":"13424230742","par_mer_no":"46498","mer_node":"0-46498-50725-","mer_capa":"1","mer_act_status":"1","mer_account":0,"headimgurl":"http://thirdwx.qlogo.cn/mmopen/9IZPn9Jy5yeJ1QffMbuIKuqZ0kaxcYxEIY0A23m9oibCncCCYj0SLR09rQibtlajicuHTLerqcQIdtmmHJhMianic5G4UQaQ32pE2/132","status":"1","freeze_amount":0,"last_update_time":"2018-06-12 16:36:59","bind_wechat":23663,"openid":"oOXUI0sUKvTj_rNyd1YY4t2tVmao","nickname":"我是呵呵","mer_capa_zh":"用户","top_mer_capa":"5","has_password":true,"merchant_no_sign":"c19670dc14a851d9133b56ee3b96a38e","oem_no_sign":"5350d2ff71cdf8672d09ebde60a07f8b"}
         * oemInfo : {"id":1,"oem_no":"OEM000011","oem_name":"超级兑","create_time":"2018-05-03 21:20:04","last_update_time":"2018-07-12 20:13:03","company_no":"yiliankejifuwu","company_name":"深圳市移联天下科技服务有限公司","service_phone":"4009003066","public_account":"gh_87302f5698eb","public_account_name":"积分超级兑","mailbox":"szzml@xhtpay.com","agreement":"666"}
         * merCapas : [{"oem_config_code":"mer_capa_1","mer_capa":"1","mer_capa_zh":"用户"},{"oem_config_code":"mer_capa_2","mer_capa":"2","mer_capa_zh":"超级用户"},{"oem_config_code":"mer_capa_3","mer_capa":"3","mer_capa_zh":"会员"},{"oem_config_code":"mer_capa_4","mer_capa":"4","mer_capa_zh":"黄金会员"},{"oem_config_code":"mer_capa_5","mer_capa":"5","mer_capa_zh":"钻石会员"}]
         * parMerInfo : {"mer_no":"46498","nickname":"喜悦之龙","headimgurl":"http://thirdwx.qlogo.cn/mmopen/UHGGVp2oIia2jIawqlH1FSwTQicthcicO32rzLpRjF6kG0RaFwFsW24gKato3k4ibYYmP1Wia29Jp924JXAiatwO8RhKoFv1O6ogqU/132","mobile_username":"13823711222"}
         */



        private MyInfoBean myInfo;
        private OemInfoBean oemInfo;
        private ParMerInfoBean parMerInfo;
        private List<MerCapasBean> merCapas;

        public MyInfoBean getMyInfo() {
            return myInfo;
        }

        public void setMyInfo(MyInfoBean myInfo) {
            this.myInfo = myInfo;
        }

        public OemInfoBean getOemInfo() {
            return oemInfo;
        }

        public void setOemInfo(OemInfoBean oemInfo) {
            this.oemInfo = oemInfo;
        }

        public ParMerInfoBean getParMerInfo() {
            return parMerInfo;
        }

        public void setParMerInfo(ParMerInfoBean parMerInfo) {
            this.parMerInfo = parMerInfo;
        }

        public List<MerCapasBean> getMerCapas() {
            return merCapas;
        }

        public void setMerCapas(List<MerCapasBean> merCapas) {
            this.merCapas = merCapas;
        }

        public static class MyInfoBean {

            @Override
            public String toString() {
                return "MyInfoBean{" +
                        "id=" + id +
                        ", merchant_no='" + merchant_no + '\'' +
                        ", oem_no='" + oem_no + '\'' +
                        ", wx_openid='" + wx_openid + '\'' +
                        ", unionid='" + unionid + '\'' +
                        ", one_agent_no='" + one_agent_no + '\'' +
                        ", agent_no='" + agent_no + '\'' +
                        ", agent_node='" + agent_node + '\'' +
                        ", create_time='" + create_time + '\'' +
                        ", user_name='" + user_name + '\'' +
                        ", mobile_username='" + mobile_username + '\'' +
                        ", par_mer_no='" + par_mer_no + '\'' +
                        ", mer_node='" + mer_node + '\'' +
                        ", mer_capa='" + mer_capa + '\'' +
                        ", mer_act_status='" + mer_act_status + '\'' +
                        ", mer_account=" + mer_account +
                        ", headimgurl='" + headimgurl + '\'' +
                        ", status='" + status + '\'' +
                        ", freeze_amount=" + freeze_amount +
                        ", last_update_time='" + last_update_time + '\'' +
                        ", bind_wechat=" + bind_wechat +
                        ", openid='" + openid + '\'' +
                        ", nickname='" + nickname + '\'' +
                        ", mer_capa_zh='" + mer_capa_zh + '\'' +
                        ", top_mer_capa='" + top_mer_capa + '\'' +
                        ", has_password=" + has_password +
                        ", merchant_no_sign='" + merchant_no_sign + '\'' +
                        ", oem_no_sign='" + oem_no_sign + '\'' +
                        '}';
            }

            /**
             * id : 21
             * merchant_no : 50725
             * oem_no : OEM000011
             * wx_openid : oOXUI0sUKvTj_rNyd1YY4t2tVmao
             * unionid : oP6FRwuCibC-d9W-176Z4MObIQtw
             * one_agent_no : 347433
             * agent_no : 347433
             * agent_node : 0-347433-
             * create_time : 2018-05-03 23:25:30
             * user_name : 我是呵呵
             * mobile_username : 13424230742
             * par_mer_no : 46498
             * mer_node : 0-46498-50725-
             * mer_capa : 1
             * mer_act_status : 1
             * mer_account : 0
             * headimgurl : http://thirdwx.qlogo.cn/mmopen/9IZPn9Jy5yeJ1QffMbuIKuqZ0kaxcYxEIY0A23m9oibCncCCYj0SLR09rQibtlajicuHTLerqcQIdtmmHJhMianic5G4UQaQ32pE2/132
             * status : 1
             * freeze_amount : 0
             * last_update_time : 2018-06-12 16:36:59
             * bind_wechat : 23663
             * openid : oOXUI0sUKvTj_rNyd1YY4t2tVmao
             * nickname : 我是呵呵
             * mer_capa_zh : 用户
             * top_mer_capa : 5
             * has_password : true
             * merchant_no_sign : c19670dc14a851d9133b56ee3b96a38e
             * oem_no_sign : 5350d2ff71cdf8672d09ebde60a07f8b
             */

            private int id;
            private String merchant_no;
            private String oem_no;
            private String wx_openid;
            private String unionid;
            private String one_agent_no;
            private String agent_no;
            private String agent_node;
            private String create_time;
            private String user_name;
            private String mobile_username;
            private String par_mer_no;
            private String mer_node;
            private String mer_capa;
            private String mer_act_status;
            private int mer_account;
            private String headimgurl;
            private String status;
            private int freeze_amount;
            private String last_update_time;
            private int bind_wechat;
            private String openid;
            private String nickname;
            private String mer_capa_zh;
            private String top_mer_capa;
            private boolean has_password;
            private String merchant_no_sign;
            private String oem_no_sign;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMerchant_no() {
                return merchant_no;
            }

            public void setMerchant_no(String merchant_no) {
                this.merchant_no = merchant_no;
            }

            public String getOem_no() {
                return oem_no;
            }

            public void setOem_no(String oem_no) {
                this.oem_no = oem_no;
            }

            public String getWx_openid() {
                return wx_openid;
            }

            public void setWx_openid(String wx_openid) {
                this.wx_openid = wx_openid;
            }

            public String getUnionid() {
                return unionid;
            }

            public void setUnionid(String unionid) {
                this.unionid = unionid;
            }

            public String getOne_agent_no() {
                return one_agent_no;
            }

            public void setOne_agent_no(String one_agent_no) {
                this.one_agent_no = one_agent_no;
            }

            public String getAgent_no() {
                return agent_no;
            }

            public void setAgent_no(String agent_no) {
                this.agent_no = agent_no;
            }

            public String getAgent_node() {
                return agent_node;
            }

            public void setAgent_node(String agent_node) {
                this.agent_node = agent_node;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getMobile_username() {
                return mobile_username;
            }

            public void setMobile_username(String mobile_username) {
                this.mobile_username = mobile_username;
            }

            public String getPar_mer_no() {
                return par_mer_no;
            }

            public void setPar_mer_no(String par_mer_no) {
                this.par_mer_no = par_mer_no;
            }

            public String getMer_node() {
                return mer_node;
            }

            public void setMer_node(String mer_node) {
                this.mer_node = mer_node;
            }

            public String getMer_capa() {
                return mer_capa;
            }

            public void setMer_capa(String mer_capa) {
                this.mer_capa = mer_capa;
            }

            public String getMer_act_status() {
                return mer_act_status;
            }

            public void setMer_act_status(String mer_act_status) {
                this.mer_act_status = mer_act_status;
            }

            public int getMer_account() {
                return mer_account;
            }

            public void setMer_account(int mer_account) {
                this.mer_account = mer_account;
            }

            public String getHeadimgurl() {
                return headimgurl;
            }

            public void setHeadimgurl(String headimgurl) {
                this.headimgurl = headimgurl;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getFreeze_amount() {
                return freeze_amount;
            }

            public void setFreeze_amount(int freeze_amount) {
                this.freeze_amount = freeze_amount;
            }

            public String getLast_update_time() {
                return last_update_time;
            }

            public void setLast_update_time(String last_update_time) {
                this.last_update_time = last_update_time;
            }

            public int getBind_wechat() {
                return bind_wechat;
            }

            public void setBind_wechat(int bind_wechat) {
                this.bind_wechat = bind_wechat;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getMer_capa_zh() {
                return mer_capa_zh;
            }

            public void setMer_capa_zh(String mer_capa_zh) {
                this.mer_capa_zh = mer_capa_zh;
            }

            public String getTop_mer_capa() {
                return top_mer_capa;
            }

            public void setTop_mer_capa(String top_mer_capa) {
                this.top_mer_capa = top_mer_capa;
            }

            public boolean isHas_password() {
                return has_password;
            }

            public void setHas_password(boolean has_password) {
                this.has_password = has_password;
            }

            public String getMerchant_no_sign() {
                return merchant_no_sign;
            }

            public void setMerchant_no_sign(String merchant_no_sign) {
                this.merchant_no_sign = merchant_no_sign;
            }

            public String getOem_no_sign() {
                return oem_no_sign;
            }

            public void setOem_no_sign(String oem_no_sign) {
                this.oem_no_sign = oem_no_sign;
            }
        }

        public static class OemInfoBean {
            @Override
            public String toString() {
                return "OemInfoBean{" +
                        "id=" + id +
                        ", oem_no='" + oem_no + '\'' +
                        ", oem_name='" + oem_name + '\'' +
                        ", create_time='" + create_time + '\'' +
                        ", last_update_time='" + last_update_time + '\'' +
                        ", company_no='" + company_no + '\'' +
                        ", company_name='" + company_name + '\'' +
                        ", service_phone='" + service_phone + '\'' +
                        ", public_account='" + public_account + '\'' +
                        ", public_account_name='" + public_account_name + '\'' +
                        ", mailbox='" + mailbox + '\'' +
                        ", agreement='" + agreement + '\'' +
                        '}';
            }

            /**
             * id : 1
             * oem_no : OEM000011
             * oem_name : 超级兑
             * create_time : 2018-05-03 21:20:04
             * last_update_time : 2018-07-12 20:13:03
             * company_no : yiliankejifuwu
             * company_name : 深圳市移联天下科技服务有限公司
             * service_phone : 4009003066
             * public_account : gh_87302f5698eb
             * public_account_name : 积分超级兑
             * mailbox : szzml@xhtpay.com
             * agreement : 666
             */

            private int id;
            private String oem_no;
            private String oem_name;
            private String create_time;
            private String last_update_time;
            private String company_no;
            private String company_name;
            private String service_phone;
            private String public_account;
            private String public_account_name;
            private String mailbox;
            private String agreement;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOem_no() {
                return oem_no;
            }

            public void setOem_no(String oem_no) {
                this.oem_no = oem_no;
            }

            public String getOem_name() {
                return oem_name;
            }

            public void setOem_name(String oem_name) {
                this.oem_name = oem_name;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getLast_update_time() {
                return last_update_time;
            }

            public void setLast_update_time(String last_update_time) {
                this.last_update_time = last_update_time;
            }

            public String getCompany_no() {
                return company_no;
            }

            public void setCompany_no(String company_no) {
                this.company_no = company_no;
            }

            public String getCompany_name() {
                return company_name;
            }

            public void setCompany_name(String company_name) {
                this.company_name = company_name;
            }

            public String getService_phone() {
                return service_phone;
            }

            public void setService_phone(String service_phone) {
                this.service_phone = service_phone;
            }

            public String getPublic_account() {
                return public_account;
            }

            public void setPublic_account(String public_account) {
                this.public_account = public_account;
            }

            public String getPublic_account_name() {
                return public_account_name;
            }

            public void setPublic_account_name(String public_account_name) {
                this.public_account_name = public_account_name;
            }

            public String getMailbox() {
                return mailbox;
            }

            public void setMailbox(String mailbox) {
                this.mailbox = mailbox;
            }

            public String getAgreement() {
                return agreement;
            }

            public void setAgreement(String agreement) {
                this.agreement = agreement;
            }
        }

        public static class ParMerInfoBean {
            @Override
            public String toString() {
                return "ParMerInfoBean{" +
                        "mer_no='" + mer_no + '\'' +
                        ", nickname='" + nickname + '\'' +
                        ", headimgurl='" + headimgurl + '\'' +
                        ", mobile_username='" + mobile_username + '\'' +
                        '}';
            }

            /**
             * mer_no : 46498
             * nickname : 喜悦之龙
             * headimgurl : http://thirdwx.qlogo.cn/mmopen/UHGGVp2oIia2jIawqlH1FSwTQicthcicO32rzLpRjF6kG0RaFwFsW24gKato3k4ibYYmP1Wia29Jp924JXAiatwO8RhKoFv1O6ogqU/132
             * mobile_username : 13823711222
             */

            private String mer_no;
            private String nickname;
            private String headimgurl;
            private String mobile_username;

            public String getMer_no() {
                return mer_no;
            }

            public void setMer_no(String mer_no) {
                this.mer_no = mer_no;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getHeadimgurl() {
                return headimgurl;
            }

            public void setHeadimgurl(String headimgurl) {
                this.headimgurl = headimgurl;
            }

            public String getMobile_username() {
                return mobile_username;
            }

            public void setMobile_username(String mobile_username) {
                this.mobile_username = mobile_username;
            }
        }

        public static class MerCapasBean {
            @Override
            public String toString() {
                return "MerCapasBean{" +
                        "oem_config_code='" + oem_config_code + '\'' +
                        ", mer_capa='" + mer_capa + '\'' +
                        ", mer_capa_zh='" + mer_capa_zh + '\'' +
                        '}';
            }

            /**
             * oem_config_code : mer_capa_1
             * mer_capa : 1
             * mer_capa_zh : 用户
             */

            private String oem_config_code;
            private String mer_capa;
            private String mer_capa_zh;

            public String getOem_config_code() {
                return oem_config_code;
            }

            public void setOem_config_code(String oem_config_code) {
                this.oem_config_code = oem_config_code;
            }

            public String getMer_capa() {
                return mer_capa;
            }

            public void setMer_capa(String mer_capa) {
                this.mer_capa = mer_capa;
            }

            public String getMer_capa_zh() {
                return mer_capa_zh;
            }

            public void setMer_capa_zh(String mer_capa_zh) {
                this.mer_capa_zh = mer_capa_zh;
            }
        }
    }
}
