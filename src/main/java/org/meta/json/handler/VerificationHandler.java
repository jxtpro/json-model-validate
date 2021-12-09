package org.meta.json.handler;



import org.meta.json.model.TraceContainer;

import java.util.Map;

public interface VerificationHandler {

    boolean checkValue(Map<String, Object> root);

    void handlerNextValueValidate(Map.Entry<String, Object> entry);

}
