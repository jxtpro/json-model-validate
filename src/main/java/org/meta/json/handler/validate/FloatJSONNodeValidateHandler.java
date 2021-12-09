package org.meta.json.handler.validate;

import org.meta.json.constant.NodeConstant;
import org.meta.json.exception.VerificationException;
import org.meta.json.handler.JSONNodeValidateHandler;
import org.meta.json.model.TraceContainer;
import org.meta.json.model.XValidate;

import java.util.Objects;

import static org.meta.json.constant.NodeConstant.RANGE_SPLIT_SYMBOL;

public class FloatJSONNodeValidateHandler implements JSONNodeValidateHandler {

    @Override
    public boolean validate(String jsonModel, Object value, XValidate xVerification, TraceContainer traceContainer) {
        if (null != value && value instanceof Float) {
            String range = xVerification.getRange();
            if (range.contains(RANGE_SPLIT_SYMBOL)) {
                String[] split = range.split(NodeConstant.RANGE_SPLIT_SYMBOL);
                Float    data  = (Float) value;
                if (Float.valueOf(split[0]) > data || data > Float.valueOf(split[1])) {
                    traceContainer.addErrorLog(String.format("路径:{%s}, 值:{%s}, 不符合{%s}中的规定:range>>>%s", traceContainer.getCurrentValidatePath(), Objects.toString(value), NodeConstant.X_VERIFICATION, range));
                }
            }
        } else {
            traceContainer.addErrorLog(String.format("路径:{%s}, 值:{%s}, 不符合{%s}中的规定:type>>>%s", traceContainer.getCurrentValidatePath(), Objects.toString(value), NodeConstant.X_VERIFICATION, xVerification.getType()));
        }
        return true;
    }

}
