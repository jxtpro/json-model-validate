package org.meta.json.model;

import java.util.*;
import java.util.stream.Collectors;

public class TraceContainer {

    private Queue<String> traceKeyPaths  = new LinkedList<>();
    private Stack<String> traceKeyClears = new Stack<>();
    private Set<String>   passLog        = new LinkedHashSet<>();
    private Set<String>   errorLog       = new LinkedHashSet<>();

    public void trace(String key) {
        traceKeyPaths.add(key);
        traceKeyClears.addElement(key);
    }

    public void clear() {
        traceKeyClears.clear();
        traceKeyPaths.clear();
    }

    public void addErrorLog(String log) {
        errorLog.add(log);
    }
    public void addPassLog(String log) {
        passLog.add(log);
    }
    public Set<String> getErrorLog() {
        return errorLog;
    }
    public Set<String> getPassLog() {
        return passLog;
    }
    public void passCheck() {
        addPassLog(getClearPath());
        Queue<String> temp = new LinkedList<>();
        traceKeyClears.stream().forEach(it -> temp.add(it));
        traceKeyPaths.clear();
        traceKeyPaths.addAll(temp);
        String clearPath           = getClearPath();
        String currentValidatePath = getCurrentValidatePath();
        if (clearPath.equals(currentValidatePath)) {
            traceKeyPaths.remove(traceKeyClears.pop());
        }
    }

    public String getCurrentValidatePath() {
        if (Objects.isNull(traceKeyPaths)) {
            return "";
        }
        return traceKeyPaths.stream().collect(Collectors.joining("."));
    }

    public String getClearPath() {
        if (Objects.isNull(traceKeyClears)) {
            return "";
        }
        return traceKeyClears.stream().collect(Collectors.joining("."));
    }

}
