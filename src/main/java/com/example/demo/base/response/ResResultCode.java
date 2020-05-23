package com.example.demo.base.response;


import com.example.demo.util.StringUtils;

/**
 * @author Administrator
 */
public enum ResResultCode {
    SUCCESS("200","成功"),
    SYSTEM_ERR("10001", "系统错误"),
    DATABASE_ERR("10002", "数据库操作出错，请重试"),
    INVALID_PARAM("10003", "{0}非法的请求参数"),
    INVALID_CLIENT("10004", "用户认证失败"),
    INVALID_GRANT("10005", "非法的授权信息"),
    UNAUTHORIZED_CLIENT("10006", "缺少授权信息"),
    INVALID_MAX_LENGTH("10007","{0}长度不合理，最大长度为 {1}"),
    INVALID_MIN_LENGTH("10008","{0}长度不合理，最小长度为 {1}"),
    EXPIRED_ACCESS_TOKEN("10009", "提供的访问令牌已过期"),
    MANY_PARAMS("10010", "请求参数过多"),
    DATA_ENCRPYT_ERR("10011", "数据加密失败"),
    DATA_DNCRPYT_ERR("10012", "数据解密失败"),
    COMPLICATED_ERROR("10013", "该记录信息已经被别人更新，请重新查询后更新"),
    DESERIALIZE_ERR("10014", "JSON反序列化错误"),
    REQUIRED_ERR("20001", "{0}为必填参数"),
    FORMAT_ERR("20012", "输入文字{0}格式不正确"),
    IS_EXIST_ERR("20013", "该资源已存在"),
    NOT_FOUND("40401","{0}不存在");

    private String code;
    private String label;

    public String getEnumName() {
        return "AUTH_DETAIL_INFO";
    }

    private ResResultCode(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static ResResultCode getByCode(String code) {
        ResResultCode[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ResResultCode c = var1[var3];
            if (StringUtils.isEquals(c.getCode(), code)) {
                return c;
            }
        }

        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String toCodeString() {
        return String.valueOf(this.code);
    }
}

