package org.meta.json.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.meta.json.constant.NodeConstant;
import org.meta.json.exception.NoVerificationException;
import org.meta.json.exception.VerificationException;
import org.meta.json.handler.XVerificationValidateHandler;

import java.lang.reflect.Field;
import java.util.Objects;

public class XValidate implements XVerificationValidateHandler {

    private XValidate() {
    }

    /**
     * 必填
     * 七种取值:
     * 1. String
     * 2. Integer
     * 3. Long
     * 4. JSONObject
     * 5. Double
     * 6. Float
     * 7. JSONArray
     */
    private String  type;
    /**
     * 三种取值:
     * 1. >=0数字
     * 2. 0-n范围
     * 3. 不填，不进行校验
     */
    private String  length;
    /**
     * 三种取值:
     * 1. false
     * 2. true
     * 3. 不填，不进行校验
     */
    private boolean isEmpty;
    /**
     * 二种取值:
     * 1. 正则
     * 2. 不填，不进行校验
     */
    private String  regex;
    /**
     * 取值范围:
     * Integer.MIN_VALUE-Integer.MAX_VALUE
     * Long.MIN_VALUE-Long.MAX_VALUE
     * Float.MIN_VALUE-Float.MAX_VALUE
     * Double.MIN_VALUE-Double.MAX_VALUE
     */
    private String  range;


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    public boolean isEmpty() {
        return isEmpty;
    }
    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
    public String getRegex() {
        return regex;
    }
    public void setRegex(String regex) {
        this.regex = regex;
    }
    public String getRange() {
        return range;
    }
    public void setRange(String range) {
        this.range = range;
    }

    @Override
    public void validateSelf(TraceContainer traceContainer) {
        if (Objects.isNull(this)) {
            throw new VerificationException(String.format("路径:{%s}, 验证信息出现异常:{%s}", traceContainer.getCurrentValidatePath(), JSON.toJSONString(this)));
        }
        if (Objects.isNull(this.getType())) {
            throw new VerificationException(String.format("路径:{%s}, {%s}中:type不能为空。", traceContainer.getCurrentValidatePath(), NodeConstant.X_VERIFICATION));
        }
        if (Objects.isNull(this.getRegex())) {
            throw new VerificationException(String.format("路径:{%s}, {%s}中:regex不能为空。", traceContainer.getCurrentValidatePath(), NodeConstant.X_VERIFICATION));
        }
        if (Objects.isNull(this.getLength())) {
            throw new VerificationException(String.format("路径:{%s}, {%s}中:length不能为空。", traceContainer.getCurrentValidatePath(), NodeConstant.X_VERIFICATION));
        }
    }

    public static XValidate getCurrentXVerification(String jsonModel, TraceContainer traceContainer) {
        Object verificationNode = JSONPath.extract(jsonModel, traceContainer.getCurrentValidatePath());
        if (Objects.isNull(verificationNode)) {
            throw new VerificationException(String.format("路径:{%s}, 不属于model内规定的节点", traceContainer.getCurrentValidatePath()));
        }
        JSONObject modelNode            = JSON.parseObject(JSON.toJSONString(verificationNode));
        String     xVerificationNodeStr = modelNode.getString(NodeConstant.X_VERIFICATION);
        if (Objects.isNull(xVerificationNodeStr)) {
            throw new NoVerificationException(String.format("路径:{%s}, 没有验证信息", traceContainer.getCurrentValidatePath()));
        }
        Field[] verificationFields = XValidate.class.getDeclaredFields();
        for (Field field : verificationFields) {
            String verificationName = field.getName();
            if (!xVerificationNodeStr.contains(verificationName)) {
                throw new VerificationException(String.format("路径:{%s}, 验证信息中缺少:{%s}", traceContainer.getCurrentValidatePath(), verificationName));
            }
        }
        return JSON.parseObject(xVerificationNodeStr, XValidate.class);
    }

}
