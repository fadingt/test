package dao.impl;

import dao.UserDao;
import dao.WorkflowDao;
import domain.paasaom.Workflow;
import utils.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowDaoImpl implements WorkflowDao {
    public static void main(String[] args) {
        WorkflowDao workflowDao = new WorkflowDaoImpl();
//        Map<String,Object> map = new HashMap<>();
//        map.put("S_TARGET1","3@617158");
//        map.put("S_TYPE","01");
//        map.put("I_TECHID",112631);
        Map<String,Object> flowpop = new HashMap<>();
        flowpop.put("S_TARGET1","3@617158");
        flowpop.put("S_TYPE","01");
        List<Workflow> list = workflowDao.getWorkflowByProperty(flowpop);
        System.out.println(list.size());
        System.out.println(UserDao.getUsername(617158));
    }
    @Override
    public void update(Workflow workflow) {
        HibernateUtil.updateObject(workflow);
    }

    @Override
    public void delete(Workflow workflow) {
        HibernateUtil.deleteObject(workflow);
    }

    @Override
    public void add(Workflow workflow) {
        HibernateUtil.saveObject(workflow);
    }

    @Override
    public List<Workflow> getWorkflowByProperty(Map<String, Object> properties) {
        return HibernateUtil.listObjectByProperty(properties,Workflow.class);
    }

    @Override
    public Workflow getWorkflowById(int id) {
        return HibernateUtil.getObjectById(Workflow.class,id);
    }

    @Override
    public int countWorkflow() {
        return HibernateUtil.countTable(Workflow.class);
    }
}
