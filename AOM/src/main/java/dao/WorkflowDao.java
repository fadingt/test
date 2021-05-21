package dao;

import domain.paasaom.Workflow;

import java.util.List;
import java.util.Map;

public interface WorkflowDao {
    void update(Workflow workflow);

    void delete(Workflow workflow);

    void add(Workflow workflow);

    List<Workflow> getWorkflowByProperty(Map<String, Object> properties);

    Workflow getWorkflowById(int id);

    int countWorkflow();
}
