package cn.com.agree.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class orgNodeTest {
    private orgNode node;
    private final orgNode root = new orgNode("0001","赞同");
    public orgNodeTest(){
        this.node = new orgNode("0001001027014003", "赞同-赞同科技-应用开发事业部-西南应用开发部-西南开发三部");
    }

    @Test
    void initOrgTree() {
    }

    @Test
    void getLevel() {
        System.out.println(this.node.getLevel());
    }

    @Test
    void parsePath() {
    }

    @Test
    void getParent() {
        orgNode parent=this.node;
        System.out.println(parent=parent.getParent());
        System.out.println(parent=parent.getParent());
        System.out.println(parent=parent.getParent());
        System.out.println(parent=parent.getParent());
        System.out.println(parent=parent.getParent());

    }
    @Test void addChild(){
        List list = new ArrayList();
        list.add("a");
        list.add("a");
        System.out.println(list.size());
    }
}