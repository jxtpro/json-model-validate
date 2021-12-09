package org.meta.json.handler;

import org.meta.json.model.TraceContainer;
import org.meta.json.model.XValidate;

public interface JSONNodeValidateHandler {

    boolean validate(String jsonModel, Object value, XValidate xVerification, TraceContainer traceContainer);

}
