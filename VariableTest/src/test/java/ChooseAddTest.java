
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.StAppInfo;

public class ChooseAddTest {

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg-mem-fullhistory.xml");

	@Test
	// deploys process with form properties
	@Deployment(resources = { "ChooseAdd.bpmn20.xml" })
	public void testChooseAdd() {

		
		Map<String, Object> variables = new HashMap<String, Object>();
		StAppInfo stInfo = new StAppInfo();
		variables.put("stAppInfo", stInfo);
	
	    RuntimeService runtimeService = activitiRule.getRuntimeService();
	    runtimeService.startProcessInstanceByKey("part1ChooseAdd", variables);
	    
	    Map<String, String> formProperties = new HashMap<String, String>();
		formProperties.put("addName", "Bernd Hauch");
		formProperties.put("addEmail", "advisor@email.de");	
		
		TaskService taskService = activitiRule.getTaskService();
		Task task = taskService.createTaskQuery().singleResult();
		FormService formService = activitiRule.getFormService();
		
		formService.submitTaskFormData(task.getId(), formProperties);
		
	
		List<HistoricDetail> historyVariables = activitiRule.getHistoryService()
			    .createHistoricDetailQuery()
			    .variableUpdates()
			    .orderByVariableName()
			    .asc()
			    .list();

	
		assertNotNull(historyVariables);

		assertEquals(1, historyVariables.size());
		HistoricVariableUpdate stAppUpdate = ((HistoricVariableUpdate) historyVariables.get(0));

	
		assertEquals("stAppInfo", stAppUpdate.getVariableName());
		StAppInfo  saii = (StAppInfo) stAppUpdate.getValue();
		
		//saii.getAdvisorName() is null
		assertEquals("Bernd Hauch", saii.getAddName());
		
		
	
//		System.out.println("sould be Bernd Hauch: " + studentInfo.getaddName());
		

		System.out.println("Test with input == false, is successfull");
	}
}
