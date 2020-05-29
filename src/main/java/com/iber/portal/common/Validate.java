package com.iber.portal.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.common.utils.HttpUtil;
import com.iber.portal.util.PropertyUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharSet;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * 校验类
 * lf
 * 2017年11月15日
 */
public class Validate {

    private final static Logger LOGGER = LoggerFactory.getLogger(Validate.class);

    /**
     * 手机号码:
     * 13[0-9], 14[5-8], 15[0, 1, 2, 3, 5, 6, 7, 8, 9],166,17[0-8], 18[0-9]
     * 移动号段: 134,135,136,137,138,139,147,148,150,151,152,157,158,159,170,172,178,182,183,184,187,188,198
     * 联通号段: 130,131,132,145,146,155,156,166,170,171,175,176,185,186
     * 电信号段: 133,149,153,170,173,174,177,180,181,189,199
     */
    private static final String PHONE_REG = "^1(3[0-9]|4[5-7]|5[0-35-9]|66|7[0-8]|8[0-9]|9[89])\\d{8}$";

    /**
     * 电话号码pattern
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REG);

    /**
     * 手机非虚拟号段
     */
    private static final String PHONE_NOT_VIRTUAL_REG = "^1(3[0-9]|4[5-7]|5[0-35-9]|66|7[2-8]|8[0-9]|9[89])\\d{8}$";


    /**
     * 电话号码非虚拟号码pattern
     */
    private static final Pattern PHONE_NOT_VIRTUAL_PATTERN = Pattern.compile(PHONE_NOT_VIRTUAL_REG);

    /**
     * 手机号验证url
     */
    private static final String CHECK_PHONE_URL = PropertyUtil.getString("check.phone.url");

    /**
     * 身份证验证url
     */
    private static final String CHECK_IDCARD_URL = PropertyUtil.getString("check.idCard.url");

    /**
     * 驾驶证验证url
     */
    private static final String CHECK_DRIVER_IDCARD_URL = "http://api.chinadatapay.com/communication/personal/2003?key=3c343cb9fdf3f6f31dac737b8c4e1927&name={name}&idcard={idCard}";

    // 电话接口访问成功
    private static final String CHECK_PHONE_CODE_SUCCESS = "10000";

    // 驾驶证接口成功
    private static final String CHECK_DRIVER_IDCARD_CODE_SUCCESS = "10000";

    // 驾驶证接口成功
    private static final String CHECK_DRIVER_IDCARD_RESULT_SUCCESS = "一致";

    /**
     * 校验合法手机号码且不是虚拟号段号码
     * @param phone
     * @return
     */
    public static boolean isPhoneNotVirtual(String phone){
        if (StringUtils.isBlank(phone)) return false;
        return PHONE_NOT_VIRTUAL_PATTERN.matcher(phone).matches();
    }

    /**
     * 判断存在空字符串
     * @return
     */
    public static boolean hasEmpty(String... strings){
        if (ArrayUtils.isEmpty(strings)) return true;
        for (String string : strings) {
            if (StringUtils.isBlank(string)) return true;
        }
        return false;
    }

    /**
     * 校验电话号码
     * @param phone 电话
     * @param name 姓名
     * @param idCard 身份证
     * @return
     */
    public static boolean checkPhone(String phone,String name,String idCard){
        try {
            if (hasEmpty(phone,name,idCard)) return false;
            final String url = CHECK_PHONE_URL.replace("{name}", URLEncoder.encode(name, SysConstant.DEFAULT_CHARSET))
                    .replace("{idCard}", idCard)
                    .replace("{phone}",phone);
            // 发送请求
            final String json = HttpTool.sendGet(url);
            final JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) return false;
            final String code = jsonObject.getString("code");
            if (CHECK_PHONE_CODE_SUCCESS.equals(code)){
                // 成功
                final JSONObject data = jsonObject.getJSONObject("data");
                if (data != null && "1".equals(data.getString("state"))){
                    return true;
                }
            }
            LOGGER.warn("result:{}",jsonObject);
            return false;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("字符集不正确！");
        }
        return false;
    }

    /**
     * 实名认证身份证
     * @param name 姓名
     * @param idCard 身份证
     * @return
     */
    public static boolean checkIdCard(String name,String idCard){
        try {
            if (hasEmpty(name,idCard)) return false;
            final String url = CHECK_IDCARD_URL.replace("{name}", URLEncoder.encode(name, SysConstant.DEFAULT_CHARSET))
                    .replace("{idCard}", idCard);
            final String json = HttpTool.sendGet(url);
            if(StringUtils.isBlank(json)){
                return false;
            }
            JSONObject jsonObject = JSONObject.parseObject(json);
            if(jsonObject != null && "0".equals(jsonObject.getString("error_code"))){
                JSONObject result = jsonObject.getJSONObject("result");
                if (result != null && "1".equals(result.getString("res"))){
                    return true;
                }
            }
            LOGGER.warn("result:{}",jsonObject);
            return false;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("字符集不正确！");
        }
        return false;
    }

    /**
     * 实名认证驾驶证
     * @param name 姓名
     * @param driverIdCard 驾驶证
     * @return
     */
    public static boolean checkDriverIdCard(String name,String driverIdCard){
        try {
            if (hasEmpty(name,driverIdCard)) return false;
            final String url = CHECK_DRIVER_IDCARD_URL.replace("{name}", URLEncoder.encode(name, SysConstant.DEFAULT_CHARSET))
                    .replace("{idCard}", driverIdCard);
            String json = HttpTool.sendGet(url);
            final JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) return false;
            final String code = jsonObject.getString("code");
            if (CHECK_DRIVER_IDCARD_CODE_SUCCESS.equals(code)){
                // 成功
                final JSONObject data = jsonObject.getJSONObject("data");
                if (data != null && CHECK_DRIVER_IDCARD_RESULT_SUCCESS.equals(data.getString("cardNoCheckResult"))
                        && CHECK_DRIVER_IDCARD_RESULT_SUCCESS.equals(data.getString("nameCheckResult"))){
                    return true;
                }
            }
            LOGGER.warn("result:{}",jsonObject);
            return false;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("字符集不正确！");
        }
        return false;
    }

    public static void main(String[] args) {
//        checkPhone("13580769940","林晓怡","441701199402260082");
        checkDriverIdCard("王鸿伟","05187145");
    }

}
