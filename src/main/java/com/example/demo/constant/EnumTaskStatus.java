package com.example.demo.constant;

import com.example.demo.util.StringUtils;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EnumTaskStatus {
    CREATE_FINISH(1, "CREATE", "创建完成"),
    UPLOAD_DATA(2, "UPLOAD_DATA", "上传数据完成"),
    WAIT(3, "WAIT", "待执行"),
    RUNNING(4, "RUNNING", "执行中"),
    FINISH(5, "FINISH", "执行结束"),
    START(6,"START","开始执行"),
    UPLOAD_DATA_ERROR(7,"UPLOAD_DATA_ERROR","任务数据上传失败"),
    DATA_OUT(8,"DATA_OUT","数据分发完毕"),
    TIME_OUT(9,"TIME_OUT","任务超时");

    private int codeInt;
    private String codeStr;
    private String msg;


    EnumTaskStatus(int codeInt, String codeStr, String msg) {
        this.codeInt = codeInt;
        this.codeStr = codeStr;
        this.msg = msg;
    }

    public int getCodeInt() {
        return codeInt;
    }

    public void setCodeInt(int codeInt) {
        this.codeInt = codeInt;
    }

    public String getCodeStr() {
        return codeStr;
    }

    public void setCodeStr(String codeStr) {
        this.codeStr = codeStr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 讲枚举转换成list格式，这样前台遍历的时候比较容易，列如 下拉框 后台调用toList方法，就可以得到codeInt,  codeStr和msg
     * @return
     */
    static List getList() {
        // Lists.newArrayList()其实和new ArrayList()几乎一模一样, 唯一它帮你做的(其实是javac帮你做的), 就是自动推导尖括号里的数据类型.
        List list = Lists.newArrayList();
        for (EnumTaskStatus enumTaskStatus : EnumTaskStatus.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("codeInt", enumTaskStatus.getCodeInt());
            map.put("codeStr", enumTaskStatus.getCodeStr());
            map.put("msg", enumTaskStatus.getMsg());
            list.add(map);
        }
        return list;
    }

    /**
     * 根据codeInt获取msg信息
     * @param codeInt
     * @return
     */
    public static String getMsgByCodeInt(int codeInt){
        String msgInfo = "";
        for (EnumTaskStatus enumTaskStatus : EnumTaskStatus.values()) {
            if(enumTaskStatus.getCodeInt() == codeInt){
                msgInfo = enumTaskStatus.getMsg();
                continue;
            }
        }
        if(StringUtils.isNotEmpty(msgInfo)){
            return msgInfo;
        } else {
            return String.valueOf(codeInt);
        }
    }

    /**
     * 根据codeStr获取msg信息
     * @param codeStr
     * @return
     */
    public static String getMsgByCodeStr(String codeStr){
        String msgInfo = "";
        for (EnumTaskStatus enumTaskStatus : EnumTaskStatus.values()) {
            if(enumTaskStatus.getCodeStr().equals(codeStr)){
                msgInfo = enumTaskStatus.getMsg();
            }
        }
        if(StringUtils.isNotEmpty(msgInfo)){
            return msgInfo;
        } else {
            return codeStr;
        }
    }



}
