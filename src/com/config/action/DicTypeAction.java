package com.config.action;

import java.util.List;

import com.base.model.DicType;
import com.base.service.DicTypeService;
import com.base.util.BaseAction;
import com.base.util.JsonUtil;
import com.base.util.Page;

@SuppressWarnings("all")
public class DicTypeAction extends BaseAction {
	private DicType dicType;
	private String[] ids;
	private DicTypeService dicTypeService = new DicTypeService();

	/**
	 * 获得数据字典
	 * 
	 * @return
	 */
	public String queryDicTypeList() {
		try {
			Page page = getPage();
			List<DicType> list = dicTypeService.getAll(page, dicType);
			StringBuffer data = new StringBuffer("[");
			String result = "";
			if (list != null && list.size() > 0) {
				for (DicType dicType : list) {
					data.append("{dicTypeId:'" + dicType.getDicTypeId() + "'");
					data.append(",dicTypeName:'" + dicType.getDicTypeName()
							+ "'");
					data.append(",dicTypeDesc:'" + dicType.getDicTypeDesc()
							+ "'},");
				}
				result = data.substring(0, data.length() - 1) + "]";
			} else {
				result = "[]";
			}
			String content = JsonUtil.pageToJosnData(page, result);
			// 输出到客户端
			JsonUtil.printMsgToClient(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得所有的数据字典
	 * 
	 * @return
	 */
	public String queryAllDicType() {
		try {
			Page page = getPage();
			List<DicType> list = dicTypeService.getAll();
			StringBuffer data = new StringBuffer("[");
			String result = "";
			if (list != null && list.size() > 0) {
				for (DicType dicType : list) {
					data.append("{dicTypeId:'" + dicType.getDicTypeId() + "'");
					data.append(",dicTypeName:'" + dicType.getDicTypeName()
							+ "'},");
				}
				result = data.substring(0, data.length() - 1) + "]";
			} else {
				result = "[]";
			}
			String content = JsonUtil.pageToJosnData(page, result);
			// 输出到客户端
			JsonUtil.printMsgToClient(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 新增数据字典<br>
	 * 
	 * @return
	 */
	public String addDicType() {
		String content = "";
		try {
			int result = dicTypeService.addDicType(dicType);
			if (result == 2) {
				content = JsonUtil
						.handlerJsonData(false, "新增基础数据失败,已存在相同的字典类型");
			} else {
				content = JsonUtil.handlerJsonData(true, "新增基础数据成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			content = JsonUtil.handlerJsonData(false, "新增基础数据失败!");
		}
		try {
			JsonUtil.printMsgToClient(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改数据字典<br>
	 * 
	 * @return
	 */
	public String updateDicType() {
		String content = "";
		try {
			dicTypeService.update(dicType);
			content = JsonUtil.handlerJsonData(true, "修改基础数据成功!");
		} catch (Exception e) {
			e.printStackTrace();
			content = JsonUtil.handlerJsonData(false, "修改基础数据失败!");
		}
		try {
			JsonUtil.printMsgToClient(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除数据字典(需要级联的把字典详情同时也给删除)<br>
	 * 
	 * @return
	 */
	public String deleteDictype() {
		String content = "";
		try {
			dicTypeService.deleteDicType(ids);
			content = JsonUtil.handlerJsonData(true, "删除基础数据成功!");
		} catch (Exception e) {
			e.printStackTrace();
			content = JsonUtil.handlerJsonData(false, "删除基础数据失败!");
		}
		try {
			JsonUtil.printMsgToClient(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public DicType getDicType() {
		return dicType;
	}

	public void setDicType(DicType dicType) {
		this.dicType = dicType;
	}

}
