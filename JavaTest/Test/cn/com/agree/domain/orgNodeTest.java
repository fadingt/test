package cn.com.agree.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class orgNodeTest {
    private orgNode node;
    private final orgNode root = new orgNode("0001","��ͬ");
    public orgNodeTest(){
        this.node = new orgNode("0001001027014003", "��ͬ-��ͬ�Ƽ�-Ӧ�ÿ�����ҵ��-����Ӧ�ÿ�����-���Ͽ�������");
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