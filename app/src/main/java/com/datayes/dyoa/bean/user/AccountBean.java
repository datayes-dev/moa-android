package com.datayes.dyoa.bean.user;

import com.datayes.dyoa.common.network.bean.BaseResponseBean;

import java.util.List;

/**
 * Created by shenen.gao on 2016/8/15.
 */
public class AccountBean extends BaseResponseBean {

    /**
     * name : pengchao.yan
     * password : null
     * domain : datayes.com
     * principalName : pengchao.yan@datayes.com
     * givenName : 彭超
     * surname : 鄢
     * fullEntry : pengchao.yan@datayes.com
     * email : pengchao.yan@datayes.com
     * phone :
     * mobile :
     * mobileVerified : null
     * gender :
     * birthday : null
     * birthdayStr :
     * account :
     * rolesList : [{"id":0,"roleName":"COMMON.AUDITOR","roleDisplayName":"审计人员","serviceName":"租户角色","serviceId":0,"description":"审计人员","permission":null,"readOnly":false,"display":true},{"id":0,"roleName":"COMMON.BD","roleDisplayName":"市场拓展","serviceName":"租户角色","serviceId":0,"description":"市场拓展","permission":null,"readOnly":false,"display":true},{"id":0,"roleName":"COMMON.INVEST","roleDisplayName":"rrp invest","serviceName":"租户角色","serviceId":0,"description":"rrp invest","permission":null,"readOnly":false,"display":true}]
     * organizationList : [{"name":"471","type":"DEPARTMENT","description":"工程技术中心","haveChildNode":false}]
     * status : R
     * validationCode : null
     * nickName :
     * province :
     * city :
     * imageId : null
     * lastLogintime : 1471251770000
     * verifyResult : null
     * hireDateTime : 1458616171000
     * isActive : true
     * hasReportLine : false
     * description :
     * firstChar :  
     * superAdmin : false
     * passportUser : false
     * admin : false
     * accountLock : false
     */

    private UserBean user;
    /**
     * id : 31
     * internalId : 0
     * domain : datayes.com
     * company : 通联数据datayes
     * email : bin.wang@datayes.com
     * phone : 021-60216246
     * externalAccount : false
     * serviceList : null
     * status : ACTIVE
     * contactName : 王斌
     * adminName : null
     * account : mingxuan.zhang@datayes.com
     * website : null
     * accountType : EMAIL
     * comment : null
     * registerId : 31
     * createdTime : 1397822567000
     * submitTime : 1402311346000
     * ready : false
     * accountNum : 1791
     * privateDeployedEmailSent : true
     * login : true
     */

    private TenantBean tenant;
    /**
     * user : {"name":"pengchao.yan","password":null,"domain":"datayes.com","principalName":"pengchao.yan@datayes.com","givenName":"彭超","surname":"鄢","fullEntry":"pengchao.yan@datayes.com","email":"pengchao.yan@datayes.com","phone":"","mobile":"","mobileVerified":null,"gender":"","birthday":null,"birthdayStr":"","account":"","rolesList":[{"id":0,"roleName":"COMMON.AUDITOR","roleDisplayName":"审计人员","serviceName":"租户角色","serviceId":0,"description":"审计人员","permission":null,"readOnly":false,"display":true},{"id":0,"roleName":"COMMON.BD","roleDisplayName":"市场拓展","serviceName":"租户角色","serviceId":0,"description":"市场拓展","permission":null,"readOnly":false,"display":true},{"id":0,"roleName":"COMMON.INVEST","roleDisplayName":"rrp invest","serviceName":"租户角色","serviceId":0,"description":"rrp invest","permission":null,"readOnly":false,"display":true}],"organizationList":[{"name":"471","type":"DEPARTMENT","description":"工程技术中心","haveChildNode":false}],"status":"R","validationCode":null,"nickName":"","province":"","city":"","imageId":null,"lastLogintime":1471251770000,"verifyResult":null,"hireDateTime":1458616171000,"isActive":true,"hasReportLine":false,"description":"","firstChar":"\u0000","superAdmin":false,"passportUser":false,"admin":false,"accountLock":false}
     * tenant : {"id":31,"internalId":0,"domain":"datayes.com","company":"通联数据datayes","email":"bin.wang@datayes.com","phone":"021-60216246","externalAccount":false,"serviceList":null,"status":"ACTIVE","contactName":"王斌","adminName":null,"account":"mingxuan.zhang@datayes.com","website":null,"accountType":"EMAIL","comment":null,"registerId":31,"createdTime":1397822567000,"submitTime":1402311346000,"ready":false,"accountNum":1791,"privateDeployedEmailSent":true,"login":true}
     * accounts : [{"domain":"datayes.com","username":"pengchao.yan","surName":"鄢","givenName":"彭超","nickName":null,"tenantName":"通联数据datayes","isActive":true,"principalName":"pengchao.yan@datayes.com"},{"domain":"datayes.com","username":"pengchao.yan1","surName":"超","givenName":"哥","nickName":null,"tenantName":"通联数据datayes","isActive":false,"principalName":"pengchao.yan1@datayes.com"},{"domain":"wmcloud.com","username":"40095","surName":null,"givenName":null,"nickName":null,"tenantName":"个人账号","isActive":false,"principalName":"40095@wmcloud.com"}]
     * anonymous : false
     * personalUser : {"province":"","city":"","nickName":"","userName":"yixiubaba","surName":"","givenName":"","description":"","gender":"","passportInfo":{"mail":null,"mailPending":false,"weibo":null,"wechat":null,"mobile":null,"username":"yixiubaba"}}
     */

    private boolean anonymous;
    /**
     * province :
     * city :
     * nickName :
     * userName : yixiubaba
     * surName :
     * givenName :
     * description :
     * gender :
     * passportInfo : {"mail":null,"mailPending":false,"weibo":null,"wechat":null,"mobile":null,"username":"yixiubaba"}
     */

    private PersonalUserBean personalUser;
    /**
     * domain : datayes.com
     * username : pengchao.yan
     * surName : 鄢
     * givenName : 彭超
     * nickName : null
     * tenantName : 通联数据datayes
     * isActive : true
     * principalName : pengchao.yan@datayes.com
     */

    private List<AccountsBean> accounts;

    //当前激活的账号
    private AccountsBean activieAccount = null;

    /**
     * 获取激活账号
     * @return
     */
    public AccountsBean getActivieAccount() {

        if (activieAccount == null) {

            if (accounts != null && !accounts.isEmpty()) {

                for (AccountsBean bean : accounts) {

                    if (bean.isIsActive()) {

                        activieAccount = bean;

                        break;
                    }
                }
            }
        }

        return activieAccount;
    }

    /**
     * 获取用户名
     * @return
     */
    public String getUserName() {

        if (personalUser != null) {

            if (personalUser.getNickName() != null && !personalUser.getNickName().isEmpty() && !personalUser.getNickName().equals("null")) {

                return personalUser.getNickName();

            } else if (personalUser.getSurName() != null && personalUser.getGivenName() != null
                    && !personalUser.getSurName().isEmpty() && !personalUser.getGivenName().isEmpty()
                    && !personalUser.getSurName().equals("null") && !personalUser.getGivenName().equals("null")){

                return personalUser.getSurName() + personalUser.getGivenName();

            } else if (personalUser.getUserName() != null && !personalUser.getUserName().isEmpty() && !personalUser.getUserName().equals("null")) {

                return personalUser.getUserName();

            } else {

                AccountsBean bean = getActivieAccount();

                if (bean != null) {

                    if (bean.getNickName() != null && !bean.getNickName().isEmpty() && !bean.getNickName().equals("null")) {

                        return bean.getNickName();

                    } else if (bean.getSurName() != null && bean.getGivenName() != null
                            && !bean.getSurName().isEmpty() && !bean.getGivenName().isEmpty()
                            && !bean.getSurName().equals("null") && !bean.getGivenName().equals("null")){

                        return bean.getSurName() + bean.getGivenName();

                    } else {

                        return bean.getUsername();
                    }
                }
            }
        }

        return "";
    }

    /**
     * 是否是租户账号
     * @return
     */
    public boolean isZuHuAccount() {

        AccountsBean bean = getActivieAccount();

        if (bean != null && !bean.getDomain().equals("wmcloud.com")) {

            return true;
        }

        return false;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public TenantBean getTenant() {
        return tenant;
    }

    public void setTenant(TenantBean tenant) {
        this.tenant = tenant;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public PersonalUserBean getPersonalUser() {
        return personalUser;
    }

    public void setPersonalUser(PersonalUserBean personalUser) {
        this.personalUser = personalUser;
    }

    public List<AccountsBean> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountsBean> accounts) {
        this.accounts = accounts;
    }

    public static class UserBean {
        private String name;
        private Object password;
        private String domain;
        private String principalName;
        private String givenName;
        private String surname;
        private String fullEntry;
        private String email;
        private String phone;
        private String mobile;
        private Object mobileVerified;
        private String gender;
        private Object birthday;
        private String birthdayStr;
        private String account;
        private String status;
        private Object validationCode;
        private String nickName;
        private String province;
        private String city;
        private Object imageId;
        private long lastLogintime;
        private Object verifyResult;
        private long hireDateTime;
        private boolean isActive;
        private boolean hasReportLine;
        private String description;
        private String firstChar;
        private boolean superAdmin;
        private boolean passportUser;
        private boolean admin;
        private boolean accountLock;
        /**
         * id : 0
         * roleName : COMMON.AUDITOR
         * roleDisplayName : 审计人员
         * serviceName : 租户角色
         * serviceId : 0
         * description : 审计人员
         * permission : null
         * readOnly : false
         * display : true
         */

        private List<RolesListBean> rolesList;
        /**
         * name : 471
         * type : DEPARTMENT
         * description : 工程技术中心
         * haveChildNode : false
         */

        private List<OrganizationListBean> organizationList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getPrincipalName() {
            return principalName;
        }

        public void setPrincipalName(String principalName) {
            this.principalName = principalName;
        }

        public String getGivenName() {
            return givenName;
        }

        public void setGivenName(String givenName) {
            this.givenName = givenName;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getFullEntry() {
            return fullEntry;
        }

        public void setFullEntry(String fullEntry) {
            this.fullEntry = fullEntry;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getMobileVerified() {
            return mobileVerified;
        }

        public void setMobileVerified(Object mobileVerified) {
            this.mobileVerified = mobileVerified;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public String getBirthdayStr() {
            return birthdayStr;
        }

        public void setBirthdayStr(String birthdayStr) {
            this.birthdayStr = birthdayStr;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getValidationCode() {
            return validationCode;
        }

        public void setValidationCode(Object validationCode) {
            this.validationCode = validationCode;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Object getImageId() {
            return imageId;
        }

        public void setImageId(Object imageId) {
            this.imageId = imageId;
        }

        public long getLastLogintime() {
            return lastLogintime;
        }

        public void setLastLogintime(long lastLogintime) {
            this.lastLogintime = lastLogintime;
        }

        public Object getVerifyResult() {
            return verifyResult;
        }

        public void setVerifyResult(Object verifyResult) {
            this.verifyResult = verifyResult;
        }

        public long getHireDateTime() {
            return hireDateTime;
        }

        public void setHireDateTime(long hireDateTime) {
            this.hireDateTime = hireDateTime;
        }

        public boolean isIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        public boolean isHasReportLine() {
            return hasReportLine;
        }

        public void setHasReportLine(boolean hasReportLine) {
            this.hasReportLine = hasReportLine;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFirstChar() {
            return firstChar;
        }

        public void setFirstChar(String firstChar) {
            this.firstChar = firstChar;
        }

        public boolean isSuperAdmin() {
            return superAdmin;
        }

        public void setSuperAdmin(boolean superAdmin) {
            this.superAdmin = superAdmin;
        }

        public boolean isPassportUser() {
            return passportUser;
        }

        public void setPassportUser(boolean passportUser) {
            this.passportUser = passportUser;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public boolean isAccountLock() {
            return accountLock;
        }

        public void setAccountLock(boolean accountLock) {
            this.accountLock = accountLock;
        }

        public List<RolesListBean> getRolesList() {
            return rolesList;
        }

        public void setRolesList(List<RolesListBean> rolesList) {
            this.rolesList = rolesList;
        }

        public List<OrganizationListBean> getOrganizationList() {
            return organizationList;
        }

        public void setOrganizationList(List<OrganizationListBean> organizationList) {
            this.organizationList = organizationList;
        }

        public static class RolesListBean {
            private int id;
            private String roleName;
            private String roleDisplayName;
            private String serviceName;
            private int serviceId;
            private String description;
            private Object permission;
            private boolean readOnly;
            private boolean display;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getRoleName() {
                return roleName;
            }

            public void setRoleName(String roleName) {
                this.roleName = roleName;
            }

            public String getRoleDisplayName() {
                return roleDisplayName;
            }

            public void setRoleDisplayName(String roleDisplayName) {
                this.roleDisplayName = roleDisplayName;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public int getServiceId() {
                return serviceId;
            }

            public void setServiceId(int serviceId) {
                this.serviceId = serviceId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Object getPermission() {
                return permission;
            }

            public void setPermission(Object permission) {
                this.permission = permission;
            }

            public boolean isReadOnly() {
                return readOnly;
            }

            public void setReadOnly(boolean readOnly) {
                this.readOnly = readOnly;
            }

            public boolean isDisplay() {
                return display;
            }

            public void setDisplay(boolean display) {
                this.display = display;
            }
        }

        public static class OrganizationListBean {
            private String name;
            private String type;
            private String description;
            private boolean haveChildNode;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public boolean isHaveChildNode() {
                return haveChildNode;
            }

            public void setHaveChildNode(boolean haveChildNode) {
                this.haveChildNode = haveChildNode;
            }
        }
    }

    public static class TenantBean {
        private int id;
        private int internalId;
        private String domain;
        private String company;
        private String email;
        private String phone;
        private boolean externalAccount;
        private Object serviceList;
        private String status;
        private String contactName;
        private Object adminName;
        private String account;
        private Object website;
        private String accountType;
        private Object comment;
        private int registerId;
        private long createdTime;
        private long submitTime;
        private boolean ready;
        private int accountNum;
        private boolean privateDeployedEmailSent;
        private boolean login;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getInternalId() {
            return internalId;
        }

        public void setInternalId(int internalId) {
            this.internalId = internalId;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public boolean isExternalAccount() {
            return externalAccount;
        }

        public void setExternalAccount(boolean externalAccount) {
            this.externalAccount = externalAccount;
        }

        public Object getServiceList() {
            return serviceList;
        }

        public void setServiceList(Object serviceList) {
            this.serviceList = serviceList;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public Object getAdminName() {
            return adminName;
        }

        public void setAdminName(Object adminName) {
            this.adminName = adminName;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public Object getWebsite() {
            return website;
        }

        public void setWebsite(Object website) {
            this.website = website;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public Object getComment() {
            return comment;
        }

        public void setComment(Object comment) {
            this.comment = comment;
        }

        public int getRegisterId() {
            return registerId;
        }

        public void setRegisterId(int registerId) {
            this.registerId = registerId;
        }

        public long getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(long createdTime) {
            this.createdTime = createdTime;
        }

        public long getSubmitTime() {
            return submitTime;
        }

        public void setSubmitTime(long submitTime) {
            this.submitTime = submitTime;
        }

        public boolean isReady() {
            return ready;
        }

        public void setReady(boolean ready) {
            this.ready = ready;
        }

        public int getAccountNum() {
            return accountNum;
        }

        public void setAccountNum(int accountNum) {
            this.accountNum = accountNum;
        }

        public boolean isPrivateDeployedEmailSent() {
            return privateDeployedEmailSent;
        }

        public void setPrivateDeployedEmailSent(boolean privateDeployedEmailSent) {
            this.privateDeployedEmailSent = privateDeployedEmailSent;
        }

        public boolean isLogin() {
            return login;
        }

        public void setLogin(boolean login) {
            this.login = login;
        }
    }

    public static class PersonalUserBean {
        private String province;
        private String city;
        private String nickName;
        private String userName;
        private String surName;
        private String givenName;
        private String description;
        private String gender;
        /**
         * mail : null
         * mailPending : false
         * weibo : null
         * wechat : null
         * mobile : null
         * username : yixiubaba
         */

        private PassportInfoBean passportInfo;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSurName() {
            return surName;
        }

        public void setSurName(String surName) {
            this.surName = surName;
        }

        public String getGivenName() {
            return givenName;
        }

        public void setGivenName(String givenName) {
            this.givenName = givenName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public PassportInfoBean getPassportInfo() {
            return passportInfo;
        }

        public void setPassportInfo(PassportInfoBean passportInfo) {
            this.passportInfo = passportInfo;
        }

        public static class PassportInfoBean {
            private Object mail;
            private boolean mailPending;
            private Object weibo;
            private Object wechat;
            private Object mobile;
            private String username;

            public Object getMail() {
                return mail;
            }

            public void setMail(Object mail) {
                this.mail = mail;
            }

            public boolean isMailPending() {
                return mailPending;
            }

            public void setMailPending(boolean mailPending) {
                this.mailPending = mailPending;
            }

            public Object getWeibo() {
                return weibo;
            }

            public void setWeibo(Object weibo) {
                this.weibo = weibo;
            }

            public Object getWechat() {
                return wechat;
            }

            public void setWechat(Object wechat) {
                this.wechat = wechat;
            }

            public Object getMobile() {
                return mobile;
            }

            public void setMobile(Object mobile) {
                this.mobile = mobile;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }

    public static class AccountsBean {
        private String domain;
        private String username;
        private String surName;
        private String givenName;
        private String nickName;
        private String tenantName;
        private boolean isActive;
        private String principalName;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSurName() {
            return surName;
        }

        public void setSurName(String surName) {
            this.surName = surName;
        }

        public String getGivenName() {
            return givenName;
        }

        public void setGivenName(String givenName) {
            this.givenName = givenName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getTenantName() {
            return tenantName;
        }

        public void setTenantName(String tenantName) {
            this.tenantName = tenantName;
        }

        public boolean isIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        public String getPrincipalName() {
            return principalName;
        }

        public void setPrincipalName(String principalName) {
            this.principalName = principalName;
        }
    }
}
