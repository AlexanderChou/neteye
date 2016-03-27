package com.totalIP.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.totalIP.dto.Name;

public class NodeUtil {
	public static List<Name> getNodeList(){
		List<Name> nodes = new ArrayList<Name>();
		nodes.add(new Name().setChineseName("清华大学 ").setEngName("thu_0").setGroupId("清华大学"));
		nodes.add(new Name().setChineseName("中国农业大学").setEngName("thu_1").setGroupId("清华大学"));
		nodes.add(new Name().setChineseName("北京林业大学").setEngName("thu_2").setGroupId("清华大学"));
		nodes.add(new Name().setChineseName("华北电力大学(北京)").setEngName("thu_3").setGroupId("清华大学"));
		nodes.add(new Name().setChineseName("中国地质大学(北京)").setEngName("thu_4").setGroupId("清华大学"));
		nodes.add(new Name().setChineseName("中国矿业大学(北京)").setEngName("thu_5").setGroupId("清华大学"));
		
		nodes.add(new Name().setChineseName("北京大学").setEngName("pku_0").setGroupId("北京大学"));
		nodes.add(new Name().setChineseName("中国人民大学").setEngName("pku_1").setGroupId("北京大学"));
		nodes.add(new Name().setChineseName("北京理工大学").setEngName("pku_2").setGroupId("北京大学"));
		nodes.add(new Name().setChineseName("北京外国语大学").setEngName("pku_3").setGroupId("北京大学"));
		nodes.add(new Name().setChineseName("中央民族大学").setEngName("pku_4").setGroupId("北京大学"));
		
		nodes.add(new Name().setChineseName("北京邮电大学").setEngName("bupt_0").setGroupId("北京邮电大学"));
		nodes.add(new Name().setChineseName("北京交通大学").setEngName("bupt_1").setGroupId("北京邮电大学"));
		nodes.add(new Name().setChineseName("北京化工大学").setEngName("bupt_2").setGroupId("北京邮电大学"));
		nodes.add(new Name().setChineseName("北京科技大学").setEngName("bupt_3").setGroupId("北京邮电大学"));
		nodes.add(new Name().setChineseName("北京中医药大学").setEngName("bupt_4").setGroupId("北京邮电大学"));
		nodes.add(new Name().setChineseName("北京师范大学").setEngName("bupt_5").setGroupId("北京邮电大学"));
		nodes.add(new Name().setChineseName("对外经济贸易大学").setEngName("bupt_6").setGroupId("北京邮电大学"));
		nodes.add(new Name().setChineseName("中央音乐学院").setEngName("bupt_7").setGroupId("北京邮电大学"));
		nodes.add(new Name().setChineseName("太原理工大学").setEngName("bupt_8").setGroupId("北京邮电大学"));
		nodes.add(new Name().setChineseName("内蒙古大学").setEngName("bupt_9").setGroupId("北京邮电大学"));
		nodes.add(new Name().setChineseName("中国传媒大学").setEngName("bupt_10").setGroupId("北京邮电大学"));
		nodes.add(new Name().setChineseName("中央财经大学").setEngName("bupt_11").setGroupId("北京邮电大学"));
		nodes.add(new Name().setChineseName("中国政法大学").setEngName("bupt_12").setGroupId("北京邮电大学"));
		nodes.add(new Name().setChineseName("北京工业大学").setEngName("bupt_13").setGroupId("北京邮电大学"));
		////////nodes.add(new Name().setChineseName("北京广播学院").setEngName("bupt_").setGroupId("bupt"));

		
		nodes.add(new Name().setChineseName("北京航空航天大学").setEngName("buaa_0").setGroupId("北京航空航天大学"));
		
		nodes.add(new Name().setChineseName("天津大学").setEngName("tju_0").setGroupId("天津大学"));
		nodes.add(new Name().setChineseName("南开大学").setEngName("tju_1").setGroupId("天津大学"));
		nodes.add(new Name().setChineseName("河北工业大学").setEngName("tju_2").setGroupId("天津大学"));
		nodes.add(new Name().setChineseName("天津医科大学").setEngName("tju_3").setGroupId("天津大学"));
		
		nodes.add(new Name().setChineseName("大连理工大学").setEngName("dalian_0").setGroupId("大连理工大学"));
		nodes.add(new Name().setChineseName("大连海事大学").setEngName("dalian_1").setGroupId("大连理工大学"));
		
		nodes.add(new Name().setChineseName("东北大学").setEngName("northeast_0").setGroupId("东北大学"));
		nodes.add(new Name().setChineseName("辽宁大学").setEngName("northeast_1").setGroupId("东北大学"));
		
		nodes.add(new Name().setChineseName("吉林大学").setEngName("jlu_0").setGroupId("吉林大学"));
		nodes.add(new Name().setChineseName("东北师范大学").setEngName("jlu_1").setGroupId("吉林大学"));
		nodes.add(new Name().setChineseName("延边大学").setEngName("jlu_2").setGroupId("吉林大学"));
		
		nodes.add(new Name().setChineseName("哈尔滨工业大学").setEngName("hit_0").setGroupId("哈尔滨工业大学"));
		nodes.add(new Name().setChineseName("哈尔滨工程大学").setEngName("hit_1").setGroupId("哈尔滨工业大学"));
		nodes.add(new Name().setChineseName("东北农业大学").setEngName("hit_2").setGroupId("哈尔滨工业大学"));
		
		nodes.add(new Name().setChineseName("复旦大学").setEngName("fudan_0").setGroupId("复旦大学"));
		
		nodes.add(new Name().setChineseName("同济大学").setEngName("tongji_0").setGroupId("同济大学"));
		
		nodes.add(new Name().setChineseName("上海交通大学").setEngName("sjtu_0").setGroupId("上海交通大学"));
		nodes.add(new Name().setChineseName("华东理工大学").setEngName("sjtu_1").setGroupId("上海交通大学"));
		nodes.add(new Name().setChineseName("东华大学").setEngName("sjtu_2").setGroupId("上海交通大学"));
		nodes.add(new Name().setChineseName("上海华东师范大学").setEngName("sjtu_3").setGroupId("上海交通大学"));
		nodes.add(new Name().setChineseName("上海财经大学").setEngName("sjtu_4").setGroupId("上海交通大学"));
		nodes.add(new Name().setChineseName("上海外国语大学").setEngName("sjtu_5").setGroupId("上海交通大学"));
		nodes.add(new Name().setChineseName("上海大学").setEngName("sjtu_6").setGroupId("上海交通大学"));
		nodes.add(new Name().setChineseName("南昌大学").setEngName("sjtu_7").setGroupId("上海交通大学"));
		nodes.add(new Name().setChineseName("第二军医大学").setEngName("sjtu_8").setGroupId("上海交通大学"));
		
		nodes.add(new Name().setChineseName("东南大学").setEngName("southeast_0").setGroupId("东南大学"));
		nodes.add(new Name().setChineseName("南京师范大学").setEngName("southeast_1").setGroupId("东南大学"));
		nodes.add(new Name().setChineseName("河海大学").setEngName("southeast_2").setGroupId("东南大学"));
		nodes.add(new Name().setChineseName("南京农业大学").setEngName("southeast_3").setGroupId("东南大学"));
		nodes.add(new Name().setChineseName("南京航空航天大学").setEngName("southeast_4").setGroupId("东南大学"));
		nodes.add(new Name().setChineseName("中国矿业大学").setEngName("southeast_5").setGroupId("东南大学"));
		nodes.add(new Name().setChineseName("南京理工大学").setEngName("southeast_6").setGroupId("东南大学"));
		nodes.add(new Name().setChineseName("苏州大学").setEngName("southeast_7").setGroupId("东南大学"));
		nodes.add(new Name().setChineseName("江南大学").setEngName("southeast_8").setGroupId("东南大学"));
		nodes.add(new Name().setChineseName("南京大学").setEngName("southeast_9").setGroupId("东南大学"));
//		nodes.add(new Name().setChineseName("中国药科大学").setEngName("southeast_").setGroupId("southeast"));

		
		nodes.add(new Name().setChineseName("浙江大学").setEngName("zju_0").setGroupId("浙江大学"));
		
		nodes.add(new Name().setChineseName("中国科学技术大学").setEngName("hefei_0").setGroupId("中国科学技术大学"));
		nodes.add(new Name().setChineseName("安徽大学").setEngName("hefei_1").setGroupId("中国科学技术大学"));
		
		nodes.add(new Name().setChineseName("厦门大学").setEngName("xiamen_0").setGroupId("厦门大学"));
		nodes.add(new Name().setChineseName("福州大学").setEngName("xiamen_1").setGroupId("厦门大学"));
		
		nodes.add(new Name().setChineseName("山东大学").setEngName("sdu_0").setGroupId("山东大学"));
		nodes.add(new Name().setChineseName("石油大学").setEngName("sdu_1").setGroupId("山东大学"));
		nodes.add(new Name().setChineseName("中国海洋大学").setEngName("sdu_2").setGroupId("山东大学"));
		
		nodes.add(new Name().setChineseName("郑州大学").setEngName("zzu_0").setGroupId("郑州大学"));
		
		nodes.add(new Name().setChineseName("华中科技大学").setEngName("hust_0").setGroupId("华中科技大学"));
		nodes.add(new Name().setChineseName("武汉大学").setEngName("hust_1").setGroupId("华中科技大学"));
		nodes.add(new Name().setChineseName("中国地质大学").setEngName("hust_2").setGroupId("华中科技大学"));
		nodes.add(new Name().setChineseName("武汉理工大学").setEngName("hust_3").setGroupId("华中科技大学"));
		
		nodes.add(new Name().setChineseName("中南大学").setEngName("changsha_0").setGroupId("中南大学"));
		nodes.add(new Name().setChineseName("湖南师范大学").setEngName("changsha_1").setGroupId("中南大学"));
		nodes.add(new Name().setChineseName("国防科学技术大学").setEngName("changsha_2").setGroupId("中南大学"));
		nodes.add(new Name().setChineseName("湖南大学").setEngName("changsha_3").setGroupId("中南大学"));
		
		nodes.add(new Name().setChineseName("华南理工大学").setEngName("scut_0").setGroupId("华南理工大学"));
		nodes.add(new Name().setChineseName("中山大学").setEngName("scut_1").setGroupId("华南理工大学"));
		nodes.add(new Name().setChineseName("暨南大学").setEngName("scut_2").setGroupId("华南理工大学"));
		nodes.add(new Name().setChineseName("华南师范大学").setEngName("scut_3").setGroupId("华南理工大学"));
		nodes.add(new Name().setChineseName("广西大学").setEngName("scut_4").setGroupId("华南理工大学"));
		
		nodes.add(new Name().setChineseName("重庆大学").setEngName("chongqin_0").setGroupId("重庆大学"));
		
		nodes.add(new Name().setChineseName("电子科技大学").setEngName("chengdu_0").setGroupId("电子科技大学"));
		nodes.add(new Name().setChineseName("西南财经大学").setEngName("chengdu_1").setGroupId("电子科技大学"));
		nodes.add(new Name().setChineseName("西南交通大学").setEngName("chengdu_2").setGroupId("电子科技大学"));
		nodes.add(new Name().setChineseName("四川大学").setEngName("chengdu_3").setGroupId("电子科技大学"));
		nodes.add(new Name().setChineseName("四川农业大学").setEngName("chengdu_4").setGroupId("电子科技大学"));
		nodes.add(new Name().setChineseName("云南大学").setEngName("chengdu_5").setGroupId("电子科技大学"));
		nodes.add(new Name().setChineseName("西北农林科技大学").setEngName("chengdu_6").setGroupId("电子科技大学"));
		nodes.add(new Name().setChineseName("贵州大学").setEngName("chengdu_7").setGroupId("电子科技大学"));
		

		nodes.add(new Name().setChineseName("西安交通大学").setEngName("xian_0").setGroupId("西安交通大学"));
		nodes.add(new Name().setChineseName("西北大学").setEngName("xian_1").setGroupId("西安交通大学"));
		nodes.add(new Name().setChineseName("西北工业大学").setEngName("xian_2").setGroupId("西安交通大学"));
		nodes.add(new Name().setChineseName("西安电子科技大学").setEngName("xian_3").setGroupId("西安交通大学"));
		nodes.add(new Name().setChineseName("第四军医大学").setEngName("xian_4").setGroupId("西安交通大学"));
		nodes.add(new Name().setChineseName("长安大学").setEngName("xian_5").setGroupId("西安交通大学"));
		
		nodes.add(new Name().setChineseName("兰州大学").setEngName("lzu_0").setGroupId("兰州大学"));
		nodes.add(new Name().setChineseName("新疆大学").setEngName("lzu_1").setGroupId("兰州大学"));
		
		return nodes;
	}
	public static String engToChinese(String engName){
		List<Name> nameList = getNodeList();
		String chineseName = "此节点未命名";
		for (Name name : nameList) {
			
			if(engName.equals(name.getEngName())){
				chineseName = name.getChineseName();
				break;
			}
		}
		return chineseName;
	}
	public static List<String> getCernetNode(){
		List<String> nodes = new ArrayList<String>();
		nodes.add("buaa");
		nodes.add("bupt");
		nodes.add("changsha");
		nodes.add("chengdu");
		nodes.add("chongqin");
		nodes.add("dalian");
		nodes.add("fudan");
		nodes.add("hefei");
		nodes.add("hit");
		nodes.add("hust");
		nodes.add("jlu");
		nodes.add("lzu");
		nodes.add("northeast");
		nodes.add("pku");
		nodes.add("scut");
		nodes.add("sdu");
		nodes.add("sjtu");
		nodes.add("southeast");
		nodes.add("thu");
		nodes.add("tju");
		nodes.add("tongji");
		nodes.add("xiamen");
		nodes.add("xian");
		nodes.add("zju");
		nodes.add("zzu");
		return nodes;
	}
	public static Map<String ,Name> getCernetNameNode(){
		Map<String ,Name> nodes = new HashMap<String,Name>();
		nodes.put("thu",new Name().setChineseName("清华大学 ").setEngName("thu_0").setGroupId("thu"));
		
		nodes.put("pku",new Name().setChineseName("北京大学").setEngName("pku_0").setGroupId("pku"));
		
		nodes.put("bupt",new Name().setChineseName("北京邮电大学").setEngName("bupt_0").setGroupId("bupt"));
		
		nodes.put("buaa",new Name().setChineseName("北京航空航天大学").setEngName("buaa_0").setGroupId("buaa"));
		
		nodes.put("tju",new Name().setChineseName("天津大学").setEngName("tju_0").setGroupId("tju"));
		
		nodes.put("dalian",new Name().setChineseName("大连理工大学").setEngName("dalian_0").setGroupId("dalian"));
		
		nodes.put("northeast",new Name().setChineseName("东北大学").setEngName("northeast_0").setGroupId("northeast"));
		
		nodes.put("jlu",new Name().setChineseName("吉林大学").setEngName("jlu_0").setGroupId("jlu"));
		
		nodes.put("hit",new Name().setChineseName("哈尔滨工业大学").setEngName("hit_0").setGroupId("hit"));
		
		nodes.put("fudan",new Name().setChineseName("复旦大学").setEngName("fudan_0").setGroupId("fudan"));
		
		nodes.put("tongji",new Name().setChineseName("同济大学").setEngName("tongji_0").setGroupId("tongji"));
		
		nodes.put("sjtu",new Name().setChineseName("上海交通大学").setEngName("sjtu_0").setGroupId("sjtu"));
		
		nodes.put("southeast",new Name().setChineseName("东南大学").setEngName("southeast_0").setGroupId("southeast"));

		
		nodes.put("zju",new Name().setChineseName("浙江大学").setEngName("zju_0").setGroupId("zju"));
		
		nodes.put("hefei",new Name().setChineseName("中国科学技术大学").setEngName("hefei_0").setGroupId("hefei"));
		
		nodes.put("xiamen",new Name().setChineseName("厦门大学").setEngName("xiamen_0").setGroupId("xiamen"));
		
		nodes.put("sdu",new Name().setChineseName("山东大学").setEngName("sdu_0").setGroupId("sdu"));
		
		nodes.put("zzu",new Name().setChineseName("郑州大学").setEngName("zzu_0").setGroupId("zzu"));
		
		nodes.put("hust",new Name().setChineseName("华中科技大学").setEngName("hust_0").setGroupId("hust"));
		
		nodes.put("changsha",new Name().setChineseName("中南大学").setEngName("changsha_0").setGroupId("changsha"));
		
		nodes.put("scut",new Name().setChineseName("华南理工大学").setEngName("scut_0").setGroupId("scut"));
		
		nodes.put("chongqin",new Name().setChineseName("重庆大学").setEngName("chongqin_0").setGroupId("chongqin"));
		
		nodes.put("chengdu",new Name().setChineseName("电子科技大学").setEngName("chengdu_0").setGroupId("chengdu"));
		

		//此节点主节点为西安交通大学,但是IP地址统计的Txt第一条为西北大学,按Txt把西北大学作为为主节点
		nodes.put("xian",new Name().setChineseName("西安交通大学").setEngName("xian_0").setGroupId("xian"));
		
		nodes.put("lzu",new Name().setChineseName("兰州大学").setEngName("lzu_0").setGroupId("lzu"));
		
		return nodes;
	}
	public static void main(String[] args) {
//		System.out.println(NodeUtil.engToChinese("thu"));
//		System.out.println((NodeUtil.getNodeList().size()));
		System.out.println((NodeUtil.getCernetNameNode().size()));
//		List<Name> name = NodeUtil.getNodeList();
//		for (Name name2 : name) {
//			System.out.println("engName = "+name2.getEngName());
//			System.out.println("chineseName = "+name2.getChineseName());
//			System.out.println("id = "+name2.getGroupId());
//		}
	}
}
