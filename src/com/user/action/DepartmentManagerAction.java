package com.user.action;

import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.base.model.Department;
import com.base.util.BaseAction;
import com.user.dao.DepartmentDAO;

/*
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
*/
/**
 * 部门的管理action
 * @author 李宪亮
 *
 */
public class DepartmentManagerAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private Department department;
	private DepartmentDAO departmentDAO = new DepartmentDAO();
	private List<Department> departments;
	private String totalCount;
	private String departmentName;
	private boolean isHave;
	private boolean success;
	private boolean failure;
	

	/**
	 * 以表单的形式添加部门
	 * @return
	 * @throws Exception
	 */
	public String addDepartmentbyForm() throws Exception {
		DepartmentDAO departmentDao = new DepartmentDAO();
		Department d = departmentDao.getDepartmentByName(department.getName());
		if (d != null) {
			failure = true;
			return SUCCESS;
		}
		
		departmentDao.save(department);
		success = true;
		return SUCCESS;
	}
	
	
	/**
	 * 部门添加或更新
	 * @return 
	 * @throws Exception
	 */
	public String addDepartment() throws Exception {
		String departmentId = this.getRequest().getParameter("departmentId").trim();
		String departmentName = this.getRequest().getParameter("departmentName").trim();
		String departmentSubtxt = this.getRequest().getParameter("departmentSubtxt").trim();
		if (StringUtils.isNotEmpty(departmentName)) {
			Department d = new Department();
			if (StringUtils.isNotEmpty(departmentId)) {
				d.setId(Long.parseLong(departmentId));
			}
			d.setName(departmentName);
			d.setSubtxt(departmentSubtxt);
			departmentDAO.save(d);
			
			PrintWriter writer = this.getResponse().getWriter();
			writer.print("ok");
			writer.close();
		}
		return null;
	}
	
	/**
	 * 部门列表
	 * @return
	 * @throws Exception
	 */
	public String listDepartment() throws Exception {
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		departments = departmentDAO.getAllDepartment(start, limit);
		totalCount = String.valueOf(departmentDAO.getDepartmentCount());
		return SUCCESS;
	}
	
	/**
	 * 得到所有的部门列表
	 * @return
	 * @throws Exception
	 */
	public String listAllDepartment() throws Exception {
		departments = this.departmentDAO.getAllDepartment();
		return SUCCESS;
	}
	
	/**
	 * 检验部门名字是否存在
	 * @return isHave 为 true 时 表示存在
	 * @throws Exception
	 */
	public String checkDepartmentName() throws Exception {
		
		
		isHave = departmentDAO.checkDepartmentNameIsHave(departmentName);
		return SUCCESS;
	}
	
	/**
	 * 删除部门
	 * @return 
	 * @throws Exception
	 */
	public String deleteDepartment() throws Exception {
		String[] departmentIds = this.getRequest().getParameter("departmentIds").trim().split(";");
		for (String departmentId : departmentIds) {
			if (StringUtils.isNotEmpty(departmentId)) {
				Department d = new Department();
				d.setId(Long.parseLong(departmentId));
				this.departmentDAO.delete(d);
				PrintWriter writer = this.getResponse().getWriter();
				writer.print("ok");
				writer.close();
			}
		}
		return null;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}


	public List<Department> getDepartments() {
		return departments;
	}


	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}


	public String getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isHave() {
		return isHave;
	}

	public void setHave(boolean isHave) {
		this.isHave = isHave;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public boolean isFailure() {
		return failure;
	}


	public void setFailure(boolean failure) {
		this.failure = failure;
	}
	
}
