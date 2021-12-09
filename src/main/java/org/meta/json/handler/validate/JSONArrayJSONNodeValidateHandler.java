package org.meta.json.handler.validate;

import com.alibaba.fastjson.JSONArray;
import org.meta.json.constant.NodeConstant;
import org.meta.json.exception.VerificationException;
import org.meta.json.handler.JSONNodeValidateHandler;
import org.meta.json.model.TraceContainer;
import org.meta.json.model.XValidate;

import java.util.Objects;

public class JSONArrayJSONNodeValidateHandler implements JSONNodeValidateHandler {

    @Override
    public boolean validate(String jsonModel, Object value, XValidate xVerification, TraceContainer traceContainer) {
        if (null == value) {
            traceContainer.addErrorLog(String.format("路径:{%s}, 值:{%s}, 不符合{%s}中的规定:type>>>%s", traceContainer.getCurrentValidatePath(), Objects.toString(value), NodeConstant.X_VERIFICATION, xVerification.getType()));
        }
        if (xVerification.isEmpty()) {
            JSONArray val = (JSONArray) value;
            if (xVerification.isEmpty()) {
                if (!val.isEmpty()) {
                    traceContainer.addErrorLog(String.format("路径:{%s}, 值:{%s}, 不符合{%s}中的规定:isEmpty>>>%s", traceContainer.getCurrentValidatePath(), Objects.toString(value), NodeConstant.X_VERIFICATION, xVerification.isEmpty()));
                }
            }
        }
        return true;
    }

}
