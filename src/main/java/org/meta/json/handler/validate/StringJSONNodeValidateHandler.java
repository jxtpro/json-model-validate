package org.meta.json.handler.validate;

import org.meta.json.constant.NodeConstant;
import org.meta.json.exception.VerificationException;
import org.meta.json.handler.JSONNodeValidateHandler;
import org.meta.json.model.TraceContainer;
import org.meta.json.model.XValidate;

import java.util.Objects;
import java.util.regex.Pattern;

public class StringJSONNodeValidateHandler implements JSONNodeValidateHandler {

    @Override
    public boolean validate(String jsonModel, Object value, XValidate xVerification, TraceContainer traceContainer) {
        if (null != value && value instanceof String) {
            if (xVerification.isEmpty()) {
                if (null != value && !"".equals(value)) {
                    traceContainer.addErrorLog(String.format("路径:{%s}, 值:{%s}, 不符合{%s}中的规定:isNull", traceContainer.getCurrentValidatePath(), Objects.toString(value), NodeConstant.X_VERIFICATION));
                }
            }
            if (!"".equals(xVerification.getRegex()) && !Pattern.matches(xVerification.getRegex(), (CharSequence) value)) {
                traceContainer.addErrorLog(String.format("路径:{%s}, 值:{%s}, 不符合{%s}中:regex>>>%s", traceContainer.getCurrentValidatePath(), value, NodeConstant.X_VERIFICATION, xVerification.getRegex()));
            }
            String  length     = xVerification.getLength();
            boolean lengthFlag = false;
            if (!"".equals(length) && length.contains(NodeConstant.RANGE_SPLIT_SYMBOL)) {
                String[] split = length.split(NodeConstant.RANGE_SPLIT_SYMBOL);
                String   str   = (String) value;
                if (Integer.valueOf(split[0]) > str.length() || str.length() > Integer.valueOf(split[1])) {
                    lengthFlag = true;
                }
            } else if (!"".equals(length) && !"".equals(value)) {
                if (((String) value).length() == Integer.valueOf(xVerification.getLength())) {
                    lengthFlag = false;
                }
            }
            if (lengthFlag) {
                traceContainer.addErrorLog(String.format("路径:{%s}, 值:{%s}, 不符合{%s}中的规定:length>>>%s", traceContainer.getCurrentValidatePath(), Objects.toString(value), NodeConstant.X_VERIFICATION, length));
            }
        } else {
            traceContainer.addErrorLog(String.format("路径:{%s}, 值:{%s}, 不符合{%s}中的规定:type>>>%s", traceContainer.getCurrentValidatePath(), Objects.toString(value), NodeConstant.X_VERIFICATION, xVerification.getType()));
        }
        return true;
    }

}
