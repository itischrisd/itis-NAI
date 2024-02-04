package decision;

import java.util.HashMap;
import java.util.Map;

public class DecisionTreeNode {

    private final Map<String, DecisionTreeNode> children;
    private final String attribute;
    private String classLabel;

    public DecisionTreeNode(String attribute) {
        this.attribute = attribute;
        this.children = new HashMap<>();
    }

    public void addChild(String attributeValue, DecisionTreeNode child) {
        children.put(attributeValue, child);
    }

    public void setClassLabel(String classLabel) {
        this.classLabel = classLabel;
    }

    public String getClassLabel() {
        return classLabel;
    }

    public String getAttribute() {
        return attribute;
    }

    public Map<String, DecisionTreeNode> getChildren() {
        return children;
    }
}
