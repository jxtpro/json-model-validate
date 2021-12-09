package org.meta.json.handler.validate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.meta.json.constant.NodeConstant;
import org.meta.json.exception.EmptyException;
import org.meta.json.handler.JSONNodeValidateHandler;
import org.meta.json.model.TraceContainer;
import org.meta.json.model.XValidate;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class JSONObjectJSONNodeValidateHandler  implements JSONNodeValidateHandler {

    @Override
    public boolean validate(String jsonModel, Object value, XValidate xVerification, TraceContainer traceContainer) {
        NodeVerificationHandler nodeVerificationHandler = new NodeVerificationHandler(jsonModel, traceContainer);
        if (null == value) {
            throw new EmptyException(String.format("路径:{%s}, 值:{%s}, 不符合{%s}中的规定:type>>>%s", traceContainer.getCurrentValidatePath(), Objects.toString(value), NodeConstant.X_VERIFICATION, xVerification.getType()));
        }
        JSONObject                     val     = (JSONObject) value;
        Set<Map.Entry<String, Object>> entries = val.entrySet();
        if (xVerification.isEmpty()) {
            if (entries.size() > 0) {
                traceContainer.addErrorLog(String.format("路径:{%s}, 值:{%s}, 不符合{%s}中的规定:isEmpty>>>%s", traceContainer.getCurrentValidatePath(), Objects.toString(value), NodeConstant.X_VERIFICATION, xVerification.isEmpty()));
            }
        } else {
            for (Map.Entry<String, Object> child : entries) {
                Object childValue = child.getValue();
                if (childValue instanceof JSONArray) {
                    continue;
                }
                if (childValue instanceof Boolean) {
                    continue;
                }
                if (childValue instanceof String) {
                    continue;
                }
                if (childValue instanceof Integer) {
                    continue;
                }
                if (childValue instanceof Long) {
                    continue;
                }
                if (childValue instanceof Float) {
                    continue;
                }
                if (childValue instanceof Double) {
                    continue;
                }
                nodeVerificationHandler.handlerNextValueValidate(child);
            }
        }
        return true;
    }

}
