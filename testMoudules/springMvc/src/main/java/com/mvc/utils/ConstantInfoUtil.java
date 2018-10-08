package com.mvc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConstantInfoUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConstantInfoUtil.class);

    public static final String AUTH_SYS_CACHE_PREFIX = "test_sys_cache_prefix";
    public static final String SYS_CHAR_ENCODE = "utf-8";
    public static final String AUTH_TICKET = "gTicket";
    public static final String ACCOUNT_INFO = "accountInfo";
    public static final String SUB_SYS_KEY = "sysId";
    public static final String SUB_SYS_VALUE = "sysTicket";

    public static final String ERROR_PREFIX = "INT_RE_MSG_";

    //短信验证码模版使用
    public static final String SMS_BLOCK_TIME = "SMSBlockTime";
    public static final String CHECK_NUM = "checkNum";
    public static final String CHECK_CODE = "checkCode";

    //短信配置字段

    public static final String SMS_SERVER_NUM = "SMSServerNum";
    public static String CLY_PASSWD = "";

    public static final String SMS_TYPE = "smsType_";
    public static final String SMS_URL = "smsUrl_";
    public static final String SMS_ACCOUNT_NAME = "accountName_";
    public static final String SMS_ACCOUNT_VALUE = "accountValue_";
    public static final String SMS_CREDENTIAL_NAME = "credentialName_";
    public static final String SMS_CREDENTIAL_VALUE = "credentialValue_";
    public static final String SMS_MOBILE = "mobileDesc_";
    public static final String SMS_CONTENT = "contentDesc_";
    public static final String SMS_CODE_NAME = "codeName_";
    public static final String SMS_CODE_SUCCESS = "successCode_";

    // MQ的消息类型
    public static final String MQ_TYPE_UPDATE_PHONE = "updateUserMobile";
    public static final String MQ_TYPE_CREATE_CONSUMER = "createConsumer";
    public static final String MQ_TYPE_CREATE_EMPLOYEE = "createEmployee";
    public static final String MQ_TYPE_CREATE_SUPPLIER = "createSupplier";
    public static final String MQ_TYPE_UPDATE_SUPPLIER = "updateSupplier";
    public static final String MQ_TYPE_AUTHEN_LOGOUT = "logout";

    // MQ消息体的属性
    public final static String MQ_MSG_TYPE = "messageType";
    public final static String MQ_MSG_DATA = "data";

    // 注册用户类型
    public static final String REG_TYPE_SMS = "sms";
    public static final String REG_TYPE_CUSTOMER = "customer";
    public static final String REG_TYPE_SUPPLIER = "supplier";

    // 批量查询分割符
    public static final String BATCH_QUEUE_SPLIT_SYMBOL = ",";

    // ldap connection
    public static final String LDAP_FACTORY = "ldapFactory";
    public static final String LDAP_URL = "ldapUrl";
    public static final String LDAP_SECURITY = "security_authentication";
    public static final String ATTR_ARRAY = "attr_person_array";
    public static final String DOMAIN_PREFIX = "domain_prefix";
    public static final String COMMON_NAME = "common_name";
    // ldap alias
    public static final String LDAP_EMAIL_ALIAS = "emailAlias";
    public static final String LDAP_REAL_NAME_ALIAS = "realNameAlias";
    public static final String LDAP_DEPARTMENT_ALIAS = "departmentAlias";
    public static final String LDAP_POSITION_ALIAS = "positionAlias";
    public static final String LDAP_DESC_ALIAS = "descriptionAlias";
    public static final String LDAP_ACCOUNT_ALIAS = "accountAlias";
    public static final String LDAP_OFFICE_PLACE_ALIAS = "officePlaceAlias";
    public static final String LDAP_PHONE_NUMBER_ALIAS = "phoneNumber";
    // ldap scan
    public static final String LDAP_AUTO_SCAN = "autoScan";
    public static final String LDAP_SCAN_USER_ACCOUNT = "userAccount";
    public static final String LDAP_SCAN_USER_PWD = "userPwd";
    public static final String LDAP_SCAN_FILTER = "filter";
    // ldap convert
    public static final String LDAP_DEP_NUM = "departmentNum";
    public static final String LDAP_DEP_ALIAS_PREFIX = "operateDep_";
    public static final String LDAP_DEP_CONVERT_ALIAS = "operateDep_value_";
    // ldap search
    public static final String LDAP_SEARCH_NUM = "search_num";
    public static final String LDAP_SEARCH_NAME = "search_name_";

    //错误码
    public static final int SUCCESS = 0;
    public static final int FAILED = 1;

    public static final int SMS_FAILED = 11;
    public static final int MESSAGE_NOT_FOUND = 12;
    public static final int MESSAGE_TIME_LIMIT = 13;

    //认证
    public static final int AUTH_FAILED = 21;
    public static final int NOT_AUTH_SYS = 22;

    public static final int REFRESH_TICKET_FAILED = 31;
    public static final int TICKET_NOT_EXIST = 41;

    // ldap
    public static final int LDAP_CONFIG_ERROR = 51;
    public static final int AD_LOGIN_FAILED = 53;
    public static final int AD_LOGIN_NO_SCAN = 54;
    public static final int LDAP_EMP_NO_EMAIL = 55;
    public static final int LDAP_EMP_NO_DEPT_JOB = 56;

    // 商家管理
    public static final int CREATE_SUPPLIER_DUPLICATE_NAME = 61;
    public static final int UPDATE_SUPPLIER_NO_USERNAME = 62;
    public static final int UPDATE_SUPPLIER_VALIDATION_ERROR = 63;
    public static final int SUPPLIER_LOGIN_FAILED = 64;

    private static Properties smsProp = new Properties();
    private static Properties empProp = new Properties();

    static {
        try {
            InputStream smsIn = ConstantInfoUtil.class.getResourceAsStream("/configValue/smsConfig.properties");
            InputStream empIn = ConstantInfoUtil.class.getResourceAsStream("/configValue/ldap.properties");
            smsProp.load(new InputStreamReader(smsIn, SYS_CHAR_ENCODE));
            empProp.load(new InputStreamReader(empIn, SYS_CHAR_ENCODE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * */
    public static int SERVNUM = Integer.parseInt(getSMSMsg(SMS_SERVER_NUM));

    /*
     *
     * */
    public static String getSMSMsg(String str) {
        return smsProp.getProperty(str);
    }

    public static String getEMPMsg(String str) {
        return empProp.getProperty(str);
    }

    public static String getEMPMsg(String str, String defaultStr) {
        return StringUtil.isEmpty(empProp.getProperty(str)) ? defaultStr : empProp.getProperty(str);
    }

    public static void reloadSMSConfig() {
        smsProp.clear();
        try {
            InputStream in = ConstantInfoUtil.class.getResourceAsStream("/configValue/smsConfig.properties");
            smsProp.load(new InputStreamReader(in, SYS_CHAR_ENCODE));
            SERVNUM = Integer.parseInt(getSMSMsg(SMS_SERVER_NUM));
            CLY_PASSWD = "";
            logger.debug("reload sms config, file number:" + SERVNUM);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadEMPConfig() {
        empProp.clear();
        try {
            InputStream in = ConstantInfoUtil.class.getResourceAsStream("/configValue/ldap.properties");
            empProp.load(new InputStreamReader(in, SYS_CHAR_ENCODE));
            logger.debug("reload emp config!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
