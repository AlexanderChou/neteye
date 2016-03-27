package com.config.action;

import java.util.List;

import com.base.model.DicContent;
import com.base.model.DicType;
import com.base.service.DicContentService;
import com.base.service.DicTypeService;
import com.base.util.BaseAction;
import com.base.util.JsonUtil;
import com.base.util.Page;

@SuppressWarnings("all")
public class DicContentAction extends BaseAction {
	private DicContent dicContent;
	private Long[] ids;
	private DicContentService dicContentService = new DicContentService();
	private DicTypeService dicTypeService = new DicTypeService();

	/**
	 * 获得数据字典内容集合
	 * 
	 * @return
	 */
	public String queryDicContentList() {
		try {
			Page page = getPage();
			List<DicContent> list = dicContentService.getAll(page, dicContent);
			StringBuffer data = new StringBuffer("[");
			String result = "";
			if (list != null && list.size() > 0) {
				for (DicContent dicContent : list) {
					data.append("{dicContenId:'" + dicContent.getDicContenId()
							+ "'");
					DicType dicType = dicTypeService.getByDicTypeId(dicContent
							.getDicTypeId());

					data.append(",dicTypeId:'" + dicType.getDicTypeName() + "'");
					data.append(",dicContentName:'"
							+ dicContent.getDicContentName() + "'");
					data.append(",dicContentDesc:'"
							+ dicContent.getDicContentDesc() + "'");
					data.append(",dicContentOrder:'"
							+ dicContent.getDicContentOrder() + "'},");
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
	 * 新增数据字典内容<br>
	 * 
	 * @return
	 */
	public String addDicContent() {
		String content = "";
		try {
			boolean flag = dicContentService.addDicContent(dicContent);
			if (!flag) {
				content = JsonUtil.handlerJsonData(false, "新增数据字典内容失败!");
			} else {
				content = JsonUtil.handlerJsonData(true, "新增数据字典内容成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			content = JsonUtil.handlerJsonData(false, "新增数据字典内容失败!");
		}
		try {
			JsonUtil.printMsgToClient(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改数据字典内容<br>
	 * 
	 * @return
	 */
	public String updateDicContent() {
		String content = "";
		try {
			dicContentService.update(dicContent);
			content = JsonUtil.handlerJsonData(true, "修改数据字典内容成功!");
		} catch (Exception e) {
			e.printStackTrace();
			content = JsonUtil.handlerJsonData(false, "修改数据字典内容失败!");
		}
		try {
			JsonUtil.printMsgToClient(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除数据字典内容<br>
	 * 
	 * @return
	 */
	public String deleteDicContent() {
		String content = "";
		try {
			dicContentService.deleteDicContent(ids);
			content = JsonUtil.handlerJsonData(true, "删除数据字典内容成功!");
		} catch (Exception e) {
			e.printStackTrace();
			content = JsonUtil.handlerJsonData(false, "删除数据字典内容失败!");
		}
		try {
			JsonUtil.printMsgToClient(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public DicContent getDicContent() {
		return dicContent;
	}

	public void setDicContent(DicContent dicContent) {
		this.dicContent = dicContent;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public DicContentService getDicContentService() {
		return dicContentService;
	}

	public void setDicContentService(DicContentService dicContentService) {
		this.dicContentService = dicContentService;
	}

}
