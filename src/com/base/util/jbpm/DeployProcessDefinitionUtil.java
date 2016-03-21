package com.base.util.jbpm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import junit.framework.TestCase;



public class DeployProcessDefinitionUtil extends TestCase{
	static JbpmConfiguration jbpmConfiguration = null;

	static {

	jbpmConfiguration = JbpmConfiguration.getInstance();

	}

	public void setUp() {

       // jbpmConfiguration.createSchema();

	}

	public void tearDown() {

	//删除数据库表

	//jbpmConfiguration.dropSchema();

	}

	/**

	* 测试方法
	 * @throws FileNotFoundException 

	*

	*/

	public void testDeploy() throws FileNotFoundException {

	// Between the 3 method calls below, all data is passed via the

	// database. Here, in this unit test, these 3 methods are executed

	// right after each other because we want to test a complete process

	// scenario情节. But in reality, these methods represent different

	// requests to a server.

	// Since we start with a clean, empty in-memory database, we have to

	// deploy the process first. In reality, this is done once by the

	// process developer.

	/**
	 * 
	* 
	* 这个方法把业务处理定义通过Hibernate保存到数据库中。
	*/

		  JbpmContext context=jbpmConfiguration.createJbpmContext();
		  FileInputStream fis = new FileInputStream("src/com/base/util/jbpm/processdefinition.xml");
		  ProcessDefinition processDefinition = ProcessDefinition.parseXmlInputStream(fis);;
		  context.deployProcessDefinition(processDefinition);
		  context.close();

	}



}
