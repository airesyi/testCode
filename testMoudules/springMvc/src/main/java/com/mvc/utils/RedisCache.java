package com.mvc.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisCache {
    @Autowired
    RedisCacheUtil redisCacheUtil;

    public static final int SMS_TIMEOUT = 10 * 60;
    public static final int TICKET_TIMEOUT = 7 * 24 * 60 * 60;
    public static final int MEM_BLOCK_TIMEOUT = 1 * 60;
    public static final int SMS_TIME_LIMIT = 1 * 60;
    public static final int DEFAULT_TIMEOUT = 15 * 24 * 60 * 60;

    public static int Incorrect_Access = 0;
    public static final String INCORRECT_ACCESS_KEY = "Authen_Incorrect_Access_Times";

    private String getKey(String key) {
        return redisCacheUtil.getStringValue(key);
    }

    private String setKey(String key, String value) {
        return setKey(key, value, DEFAULT_TIMEOUT);
    }

    private String setKey(String key, String value, int timeout) {
        return redisCacheUtil.setStringValue(key, value, timeout);
    }

    private boolean expire(String key, int timeout) {
        return redisCacheUtil.expireStringValue(key, timeout);
    }

    private boolean delKey(String key) {
        return redisCacheUtil.delStringValue(key);
    }

    private boolean exists(String key) {
        return redisCacheUtil.exists(key);
    }

    // 储存短信验证码
    public String setSMSCode(String mobile, String checkNum) {
        String mobileKey = "SMS_CODE" + mobile;
        return setKey(mobileKey, checkNum, SMS_TIMEOUT);
    }

    // 检查短信验证码
    public boolean checkSMSCode(String mobile, String checkNum) {
        String key = "SMS_CODE" + mobile;
        String smsCode = getKey(key);
        if (StringUtil.matchWithOutNull(smsCode, checkNum)) {
            delKey(key);
            return true;
        }
        return false;
    }

    /**
     * 删除认证凭证
     *
     * @param gTicket
     * @return
     */
    public void setAuthTicket(String gTicket, Object object) {
        String jsonStr = JsonUtil.convertToString(object);
        setKey(gTicket, jsonStr, TICKET_TIMEOUT);
    }

    /**
     * 删除认证凭证
     *
     * @param gTicket
     * @return
     */
    public void logoutAuth(String gTicket) {
        delKey(gTicket);
    }

    /**
     * 通过用户凭证获取用户信息
     *
     * @param ticketKey:用户信息
     * @return
     */
    public Object getUserInfo(String ticketKey) {
        String jsonStr = getKey(ticketKey);
        Object object = JSON.parse(jsonStr);
        return object;
    }

    /**
     * 检查ticket是否存在
     *
     * @param gTicket
     * @return 刷新是否成功
     */
    public boolean isTicketExist(String gTicket) {
        String str = getKey(gTicket);
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        return true;
    }

    /**
     * 检查认证系统确认是否设置阻塞
     *
     * @return:是否被临时阻塞
     */
    public boolean isCacheBlock() {
        if (exists(INCORRECT_ACCESS_KEY)) {
            return true;
        } else {
            setKey(INCORRECT_ACCESS_KEY, String.valueOf(Incorrect_Access++), MEM_BLOCK_TIMEOUT);
            return false;
        }
    }

    /**
     * 删除阻塞的系统认证记录
     *
     * @return:是否删除成功
     */
    public void cancelCacheBlock() {
        Incorrect_Access = 0;
        delKey(INCORRECT_ACCESS_KEY);
    }

    /**
     * 同一手机发送短信是否超过时间限制
     *
     * @param mobile:发送验证码的手机号
     * @return 是否被限制
     */
    public boolean isSMSTimeLimit(String mobile) {
        String str = "SMSTIME" + mobile;
        if (StringUtil.isEmpty(getKey(str))) {
            setKey(str, mobile, SMS_TIME_LIMIT);
            return false;
        } else {
            return true;
        }
    }


}
