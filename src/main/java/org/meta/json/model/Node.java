package org.meta.json.model;

import com.alibaba.fastjson.JSON;
import org.meta.json.constant.NodeConstant;
import org.meta.json.handler.JSONNodeValidateHandler;
import org.meta.json.handler.validate.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Node {

    private       Map<String, Object>                  model            = new HashMap<>();
    private       Map<String, Object>                  data             = new HashMap<>();
    private       Path                                 baseModelPath    = Paths.get(new File("").getAbsolutePath()).resolve("src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "resources").resolve("model");
    private       Path                                 baseDataPath     = Paths.get(new File("").getAbsolutePath()).resolve("src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "resources").resolve("data");
    private       String                               defaultModelFile = "default.json";
    private       String                               defaultDataFile  = "default.json";
    private       String                               jsonModel;
    public static Map<String, JSONNodeValidateHandler> handlerMap       = new HashMap<>(8);
    private       TraceContainer                       traceContainer   = new TraceContainer();
    private       Object                               lock             = new Object();

    public void init() {
        jsonModel = JSON.toJSONString(model);
    }

    static {
        handlerMap.put(NodeConstant.X_VERIFICATION_TYPE_BOOLEAN, new BooleanJSONNodeValidateHandler());
        handlerMap.put(NodeConstant.X_VERIFICATION_TYPE_STRING, new StringJSONNodeValidateHandler());
        handlerMap.put(NodeConstant.X_VERIFICATION_TYPE_INTEGER, new IntegerJSONNodeValidateHandler());
        handlerMap.put(NodeConstant.X_VERIFICATION_TYPE_LONG, new LongJSONNodeValidateHandler());
        handlerMap.put(NodeConstant.X_VERIFICATION_TYPE_FLOAT, new FloatJSONNodeValidateHandler());
        handlerMap.put(NodeConstant.X_VERIFICATION_TYPE_DOUBLE, new DoubleJSONNodeValidateHandler());
        handlerMap.put(NodeConstant.X_VERIFICATION_TYPE_JSONOBJECT, new JSONObjectJSONNodeValidateHandler());
        handlerMap.put(NodeConstant.X_VERIFICATION_TYPE_JSONARRAY, new JSONArrayJSONNodeValidateHandler());
    }

    public Node() {
    }

    public Node(Map<String, Object> model) {
        this.model = model;
        init();
    }

    public Node(Map<String, Object> model, Map<String, Object> data) {
        this.model = model;
        this.data = data;
        init();
    }

    public Node(Path modelFile) {
        load(modelFile, null);
        init();
    }

    public Node(Path modelFile, Path dataFile) {
        load(modelFile, dataFile);
        init();
    }

    private void load(Path modelFile, Path dataFile) {
        if (Objects.isNull(modelFile)) {
            modelFile = baseModelPath.resolve(defaultModelFile);
        }
        if (Objects.isNull(dataFile)) {
            dataFile = baseDataPath.resolve(defaultDataFile);
        }
        try {
            model.putAll(JSON.parseObject(Files.readAllBytes(modelFile), Map.class));
            data.putAll(JSON.parseObject(Files.readAllBytes(dataFile), Map.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validate() {
        NodeVerificationHandler handlerVerification = new NodeVerificationHandler(jsonModel, traceContainer);
        synchronized (lock) {
            return handlerVerification.checkValue(data);
        }
    }

    public boolean validate(Map<String, Object> data) {
        NodeVerificationHandler handlerVerification = new NodeVerificationHandler(jsonModel, traceContainer);
        synchronized (lock) {
            return handlerVerification.checkValue(data);
        }
    }

    public TraceContainer getTraceContainer() {
        return traceContainer;
    }

}
