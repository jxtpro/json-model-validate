package org.meta.json.handler.validate;

import org.meta.json.constant.NodeConstant;
import org.meta.json.handler.JSONNodeValidateHandler;
import org.meta.json.handler.VerificationHandler;
import org.meta.json.model.Node;
import org.meta.json.model.TraceContainer;
import org.meta.json.model.XValidate;

import java.util.Map;
import java.util.Objects;

public class NodeVerificationHandler implements VerificationHandler {

    private String                               jsonModel;
    private TraceContainer                       traceContainer;

    public NodeVerificationHandler() {
    }
    public NodeVerificationHandler(String jsonModel,  TraceContainer traceContainer) {
        this.jsonModel = jsonModel;
        this.traceContainer = traceContainer;
    }

    public boolean checkValue(Map<String, Object> root) {
        if (Objects.isNull(root)) {
            return false;
        }
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            handlerNextValueValidate(entry);
            traceContainer.clear();
        }
        return true;
    }

    /**
     * @param entry
     * @see org.meta.json.model.XValidate
     */
    @Override
    public void handlerNextValueValidate(Map.Entry<String, Object> entry) {
        String key = entry.getKey();
        traceContainer.trace(key);
        Object    value         = entry.getValue();
        XValidate xVerification = XValidate.getCurrentXVerification(jsonModel, traceContainer);
        xVerification.validateSelf(traceContainer);

        if (!Objects.isNull(value)) {
            String[] xVerificationTypes = NodeConstant.X_VERIFICATION_TYPES;
            for (String type : xVerificationTypes) {
                if (type.equals(xVerification.getType())) {
                    JSONNodeValidateHandler jsonNodeValidateHandler = Node.handlerMap.get(type);
                    jsonNodeValidateHandler.validate(jsonModel, value, xVerification, traceContainer);
                    break;
                }
            }
            traceContainer.passCheck();
        }
    }

}
