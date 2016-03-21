package com.ticket.action;

import java.io.PrintWriter;
import java.util.List;

import com.base.model.Category;
import com.base.util.BaseAction;
import com.ticket.dao.CategoryDAO;

public class CategoryManageAction extends BaseAction {
	private String totalCount;
	private boolean success;
	private boolean failure;
	private List<Category> categories;
	private Category category;
	//private String name;
	private String description;
	private CategoryDAO categoryDAO = new CategoryDAO();
	
	public String listCategories() throws Exception {
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		categories = categoryDAO.getCategories(start, limit);
		totalCount = String.valueOf(categoryDAO.getCategoryCount());
		return SUCCESS;
	}
	public String deleteCategory() throws Exception {
		String[] categoryIds = this.getRequest().getParameter("categoryIds").trim().split(";");
		for (String categoryId : categoryIds) {
			categoryDAO.delete(Long.parseLong(categoryId));
		}
		PrintWriter writer = this.getResponse().getWriter();
		writer.print("ok");
		writer.close();
		return null;
	}
	public String modifyCategory() throws Exception {
		String categoryId = this.getRequest().getParameter("categoryId");
		String name = this.getRequest().getParameter("name");
		Category category = categoryDAO.getCategoryById(Long.parseLong(categoryId));
		boolean categoryNameIsHave = categoryDAO.checkCategoryNameIsExist(name);  //重名判断
		if(!(category.getName().equals(name))){	
			if (categoryNameIsHave) {
				success = false;
				return SUCCESS;
			}
		}
		category.setName(name);
		category.setDescription(description);
		categoryDAO.save(category);
		success = true;
		return SUCCESS;
	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String addCategory() throws Exception {
		boolean categoryNameIsHave = categoryDAO.checkCategoryNameIsExist(category.getName());
		if (categoryNameIsHave) {
			failure = true;
			return SUCCESS;
		}
		categoryDAO.save(category);
		success = true;
		return SUCCESS;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
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
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
