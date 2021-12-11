package org.meta.json;


import org.meta.json.model.Node;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AppTest {

    private static final Path testModelFilePath = Paths.get(new File("").getAbsolutePath()).resolve("src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "resources").resolve("model").resolve("test.json");
    private static final Path testDataFilePath  = Paths.get(new File("").getAbsolutePath()).resolve("src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator") + "resources").resolve("data").resolve("test.json");

    public static void main(String[] args) {
        Node testNode = new Node(testModelFilePath, testDataFilePath);
        Node node     = new Node((Path) null, null);

        List<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            nodeList.add(node);
            nodeList.add(testNode);
        }

        nodeList.parallelStream().forEach(it -> printFormat(it));
        //nodeList.stream().forEach(it -> System.out.println("检验结果: " + (it.validate() ? "通过, 暂不支持JSONArray类型节点。" : "不通过。")));
    }

    public static void printFormat(Node it) {
        System.out.println("----------------------------- Thread-ID: " + Thread.currentThread().getId() + " 检验结果: " + (it.validate() ? "通过, 暂不支持JSONArray类型节点。" : "不通过。日志如下: "));
        System.out.println("----------------------------- 错误日志: " + it.getTraceContainer().getErrorLog().size() + "条 -----------------------------");
        it.getTraceContainer().getErrorLog().stream().forEach(log -> System.out.println(log + "`path is err "));
        System.out.println("----------------------------- 正常日志: " + it.getTraceContainer().getPassLog().size() + "条 -----------------------------");
        it.getTraceContainer().getPassLog().stream().forEach(log -> System.out.println(log + "`path is ok "));
    }

}
