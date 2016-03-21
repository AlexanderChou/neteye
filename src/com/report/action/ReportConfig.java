package com.report.action;

import com.opensymphony.xwork2.ActionSupport;

public class ReportConfig extends ActionSupport {
    private String password;
    private String faultIP = "202.38.120.243";//故障后台IP
    private String flowIP = "202.38.120.243";//流量后台IP
    private String webIP = "202.38.120.247";//BGP前台IP
    private String dataIP = "2001:da8:1:100::1";//BGP监控服务器的IP
    private String comment;//点评
    private String events;//重要事件
    private String fault;//主干线路故障率
    private String usability;//核心节点可用性
    private String faultOfLine;//接入线路故障率
    private String usabilityOfCNGI;//CNGI-6IX核心链路可用率
    private String sumOfLine;//主干传输线路事件统计
    private String sumOfDevice;//主干运行设备事件统计
    private String flowOfInput;//主干网入流量分布
    private String flowOfOutput;//主干网出流量分布
    private String flowOfBorder;//CERNET2边界流量
    private String flowOfInternet;//CERNET2网间流量
    private String flowOfIntranet;//CERNET2网内流量
    private String flowOfBJ;//北京交换中心流量
    private String flowOfSH;//上海交换中心流量
    private String faultTitle="主干线路故障率";
    private String usabilityTitle="核心节点可用性";
    private String faultOfLineTitle="接入线路故障率";
    private String usabilityOfCNGITitle="CNGI-6IX核心链路可用率";
    private String sumOfLineTitle="主干传输线路事件统计";
    private String sumOfDeviceTitle="主干运行设备事件统计";
    private String flowOfInputTitle="主干网入流量分布";
    private String flowOfOutputTitle="主干网出流量分布";
    private String flowOfBorderTitle="CERNET2边界流量";
    private String flowOfInternetTitle="CERNET2网间流量";
    private String flowOfIntranetTitle="CERNET2网内流量";
    private String flowOfBJTitle="北京交换中心流量";
    private String flowOfSHTitle="上海交换中心流量";

	public String execute() {
        try {
        		StringBuffer content = new StringBuffer();
        		//主干线路故障率：
        		content.append("北京-武汉10G||").append("\n");
        		content.append("武汉-广州10G||").append("\n");
        		content.append("武汉-南京10G||").append("\n");
        		content.append("南京-上海10G||").append("\n");
        		content.append("北京-郑州2.5G||").append("\n");
        		content.append("郑州-西安2.5G||").append("\n");
        		content.append("西安-兰州2.5G||").append("\n");
        		content.append("西安-成都2.5G||").append("\n");
        		content.append("成都-重庆2.5G||").append("\n");
        		content.append("武汉-重庆2.5G||").append("\n");
        		content.append("武汉-长沙2.5G||").append("\n");
        		content.append("广州-厦门2.5G||").append("\n");
        		content.append("上海-杭州2.5G||").append("\n");
        		content.append("厦门-杭州2.5G||").append("\n");
        		content.append("南京-合肥2.5G||").append("\n");
        		content.append("天津-济南2.5G||").append("\n");
        		content.append("北京-天津2.5G||").append("\n");
        		content.append("北京-沈阳2.5G||").append("\n");
        		content.append("沈阳-大连2.5G||").append("\n");
        		content.append("沈阳-长春2.5G||").append("\n");
        		content.append("长春-哈尔滨2.5G||").append("\n");
        		content.append("济南-合肥2.5G||").append("\n");
        		setFault(content.toString());
        		//核心节点可用性：
        		content.delete(0, content.toString().length());
        		content.append("北京核心J||2001:DA8:1:FF::1").append("\n");
        		content.append("武汉核心J||2001:DA8:1:FF::2").append("\n");
        		content.append("广州核心J||2001:DA8:1:FF::3").append("\n");
        		content.append("南京核心J||2001:DA8:1:FF::4").append("\n");
        		content.append("上海核心J||2001:DA8:1:FF::5").append("\n");
        		content.append("郑州核心B||2001:DA8:1:FF::6").append("\n");
        		content.append("天津核心B||2001:DA8:1:FF::7").append("\n");
        		content.append("济南核心B||2001:DA8:1:FF::8").append("\n");
        		content.append("沈阳核心B||2001:DA8:1:FF::9").append("\n");
        		content.append("大连核心B||2001:DA8:1:FF::10").append("\n");
        		content.append("长春核心B||2001:DA8:1:FF::11").append("\n");
        		content.append("哈尔滨核心B||2001:DA8:1:FF::12").append("\n");
        		content.append("北大核心B||2001:DA8:1:FF::13").append("\n");
        		content.append("北邮核心B||2001:DA8:1:FF::14").append("\n");
        		content.append("北航核心B||2001:DA8:1:FF::15").append("\n");
        		content.append("西安核心H||2001:DA8:1:FF::16").append("\n");
        		content.append("成都核心H||2001:DA8:1:FF::17").append("\n");
        		content.append("重庆核心H||2001:DA8:1:FF::18").append("\n");
        		content.append("兰州核心H||2001:DA8:1:FF::19").append("\n");
        		content.append("合肥核心H||2001:DA8:1:FF::20").append("\n");
        		content.append("杭州核心H||2001:DA8:1:FF::21").append("\n");
        		content.append("厦门核心H||2001:DA8:1:FF::22").append("\n");
        		content.append("长沙核心H||2001:DA8:1:FF::23").append("\n");
        		content.append("复旦核心H||2001:DA8:1:FF::24").append("\n");
        		content.append("同济核心H||2001:DA8:1:FF::25").append("\n");
        		setUsability(content.toString());
        		//接入线路故障率：
        		content.delete(0, content.toString().length());
        		content.append("北京-边界2.5G||2001:da8:1:101::2").append("\n");
        		content.append("武汉-边界2.5G||2001:da8:1:102::2").append("\n");
        		content.append("广州-边界2.5G||2001:da8:1:103::2").append("\n");
        		content.append("南京-边界2.5G||2001:da8:1:104::2").append("\n");
        		content.append("上海-边界2.5G||2001:da8:1:105::2").append("\n");
        		content.append("郑州-边界2.5G||2001:da8:1:106::2").append("\n");
        		content.append("天津-边界2.5G||2001:da8:1:107::2").append("\n");
        		content.append("济南-边界2.5G||2001:da8:1:108::2").append("\n");
        		content.append("沈阳-边界2.5G||2001:da8:1:109::2").append("\n");
        		content.append("大连-边界2.5G||2001:da8:1:10a::2").append("\n");
        		content.append("长春-边界2.5G||2001:da8:1:10b::2").append("\n");
        		content.append("哈尔滨-边界2.5G||2001:da8:1:10c::2").append("\n");
        		content.append("北大-边界2.5G||2001:da8:1:10d::2").append("\n");
        		content.append("北邮-边界2.5G||2001:da8:1:10e::2").append("\n");
        		content.append("北航-边界2.5G||2001:da8:1:10f::2").append("\n");
        		content.append("西安-边界2.5G||2001:da8:1:110::2").append("\n");
        		content.append("成都-边界2.5G||2001:da8:1:111::2").append("\n");
        		content.append("重庆-边界2.5G||2001:da8:1:112::2").append("\n");
        		content.append("兰州-边界2.5G||2001:da8:1:113::2").append("\n");
        		content.append("杭州-边界2.5G||2001:da8:1:115::2").append("\n");
        		content.append("厦门-边界2.5G||2001:da8:1:116::2").append("\n");
        		content.append("长沙-边界2.5G||2001:da8:1:117::2").append("\n");
        		content.append("复旦-边界2.5G||2001:da8:1:118::2").append("\n");
        		content.append("同济-边界2.5G||2001:da8:1:119::2").append("\n");
        		setFaultOfLine(content.toString());
        		//cngi-6ix核心链路可用率：
        		content.delete(0, content.toString().length());
        		content.append("SW1-CERNET2||2001:252:0:1::1").append("\n");
        		content.append("SW1-中国电信||2001:252:0:1::2").append("\n");
        		content.append("SW1-中国联通||2001:252:0:1::3").append("\n");
        		content.append("SW1-中国网通||2001:252:0:1::4").append("\n");
        		content.append("SW1-中国移动||2001:252:0:1::5").append("\n");
        		content.append("SW1-中国铁通||2001:252:0:1::6").append("\n");
        		content.append("SW1-CERNET||2001:252:0:1::1001").append("\n");
        		content.append("SW1-CJ-IPv6||2001:252:0:1::1002").append("\n");
        		content.append("SW1-NSFCNET||2001:252:0:1::1003").append("\n");
        		content.append("SW1-RS1||2001:252:0:1::101").append("\n");
        		content.append("SW1-RS2||2001:252:0:1::102").append("\n");
        		content.append("SW2-RS1||2001:252:0:2::101").append("\n");
        		content.append("SW2-RS2||2001:252:0:2::102").append("\n");
        		content.append("RS1-RS2||2001:252:0:100::1").append("\n");
        		content.append("RS1-HK||2001:252:0:101::1").append("\n");
        		content.append("RS1-HK_B||2001:252:0:103::1").append("\n");
        		content.append("RS1-KREONet2_1||2001:320:8300:30::11").append("\n");
        		content.append("RS1-KREONet2_2||2001:320:8300:40::19").append("\n");
        		content.append("RS2-TEIN2||2001:254:1:c::2").append("\n");
        		content.append("RS2-APAN||2001:200:e000:25::5d67:1").append("\n");
        		content.append("HK-HK6IX||2001:7fa:0:1::ca28:a1be").append("\n");
        		setUsabilityOfCNGI(content.toString());
        		//主干网入/出流量分布：
        		content.delete(0, content.toString().length());
        		content.append("北京||2001:da8:1:ff::1,34,91").append("\n");
        		content.append("武汉||2001:da8:1:ff::2,48").append("\n");
        		content.append("广州||2001:da8:1:ff::3,41").append("\n");
        		content.append("南京||2001:da8:1:ff::4,40").append("\n");
        		content.append("上海||2001:da8:1:ff::5,45,56").append("\n");
        		content.append("郑州||202.38.120.197,768").append("\n");
        		content.append("天津||202.38.120.198,768").append("\n");
        		content.append("济南||202.38.120.199,2048").append("\n");
        		content.append("沈阳||202.38.120.200,1024").append("\n");
        		content.append("大连||202.38.120.201,1024").append("\n");
        		content.append("长春||202.38.120.202,1792").append("\n");
        		content.append("哈尔滨||202.38.120.203,1024").append("\n");
        		content.append("北大||202.38.120.204,1536").append("\n");
        		content.append("北邮||2001:da8:1:ff::32,48").append("\n");
        		content.append("北航||202.38.120.206,1280").append("\n");
        		content.append("西安||202.38.120.207,134217990").append("\n");
        		content.append("成都||202.38.120.208,201326854").append("\n");
        		content.append("重庆||202.38.120.209,268435718").append("\n");
        		content.append("兰州||202.38.120.210,67109126").append("\n");
        		content.append("合肥||202.38.120.211,268435718").append("\n");
        		content.append("杭州||202.38.120.212,402653446").append("\n");
        		content.append("厦门||202.38.120.213,67109126").append("\n");
        		content.append("长沙||202.38.120.214,738197766").append("\n");
        		content.append("复旦||202.38.120.215,268435718").append("\n");
        		content.append("同济||202.38.120.216,201326854").append("\n");
        		setFlowOfInput(content.toString());
        		setFlowOfOutput(content.toString());
        		//主干传输线路事件统计
        		content.delete(0, content.toString().length());
        		content.append("割接倒纤||").append("\n");
        		content.append("自然灾害||").append("\n");
        		content.append("人为破坏||").append("\n");
        		content.append("其它原因||").append("\n");
        		setSumOfLine(content.toString());
        		//主干运行设备事件统计
        		content.delete(0, content.toString().length());
        		content.append("设备故障||").append("\n");
        		content.append("设备维护||").append("\n");
        		content.append("意外断电||").append("\n");
        		content.append("其它原因||").append("\n");
        		setSumOfDevice(content.toString());
        		//CERNET2边界流量
        		content.delete(0, content.toString().length());
        		content.append("北京-边界_2.5G||2001:da8:1:ff::1_34").append("\n");
        		content.append("北京-边界日立_2.5G||2001:da8:1:ff::1_91").append("\n");
        		content.append("武汉-边界_2.5G||2001:da8:1:ff::2_48").append("\n");
        		content.append("广州-边界_2.5G||2001:da8:1:ff::3_41").append("\n");
        		content.append("南京-边界_2.5G||2001:da8:1:ff::4_40").append("\n");
        		content.append("上海-边界_2.5G||2001:da8:1:ff::5_45").append("\n");
        		content.append("上海-边界日立_2.5G||2001:da8:1:ff::5_56").append("\n");
        		content.append("郑州备份-边界_2.5G||202.38.120.197_768").append("\n");
        		content.append("天津-边界_2. 5G||202.38.120.198_768").append("\n");
        		content.append("济南-边界_2.5G||202.38.120.199_2048").append("\n");
        		content.append("沈阳-边界_2.5G||202.38.120.200_1024").append("\n");
        		content.append("大连-边界_2.5G||202.38.120.201_1024").append("\n");
        		content.append("长春-边界_2.5G||202.38.120.202_1792").append("\n");
        		content.append("哈尔滨-边界_2.5G||202.38.120.203_1024").append("\n");
        		content.append("北大-边界_2.5G||202.38.120.204_1536").append("\n");
        		content.append("北邮备份-边界_2.5G||2001:da8:1:ff::32_48").append("\n");
        		content.append("北航-边界_2.5G||202.38.120.206_1280").append("\n");
        		content.append("西安-边界_2.5G||202.38.120.207_134217990").append("\n");
        		content.append("成都-边界_2.5G||202.38.120.208_201326854").append("\n");
        		content.append("重庆-边界_2.5G||202.38.120.209_268435718").append("\n");
        		content.append("兰州-边界_2.5G||202.38.120.210_67109126").append("\n");
        		content.append("合肥-边界_2.5G||202.38.120.211_268435718").append("\n");
        		content.append("杭州-边界_2.5G||202.38.120.212_402653446").append("\n");
        		content.append("厦门-边界_2.5G||202.38.120.213_67109126").append("\n");
        		content.append("长沙-边界_2.5G||202.38.120.214_738197766").append("\n");
        		content.append("复旦-边界_2.5G||202.38.120.215_268435718").append("\n");
        		content.append("同济-边界_2.5G||202.38.120.216_201326854").append("\n");
        		setFlowOfBorder(content.toString());
        		//CERNET2网间流量
        		content.delete(0, content.toString().length());
        		content.append("北京核心-rx1_1G||2001:da8:1:ff::1_36").append("\n");
        		content.append("北京核心-CNGI-6IX_1G||2001:da8:1:ff::1_66").append("\n");
        		content.append("北京核心-r0a_1G||2001:da8:1:ff::1_90").append("\n");
        		content.append("上海核心-3TNET_1G||2001:da8:1:ff::5_53").append("\n");
        		content.append("上海核心-CNGI-6IX-SH_1G||2001:da8:1:ff::5_58").append("\n");
        		setFlowOfInternet(content.toString());
        		//CERNET2网内流量
        		content.delete(0, content.toString().length());
        		content.append("北京-武汉_10G||2001:da8:1:ff::1_22").append("\n");
        		content.append("武汉-广州_10G||2001:da8:1:ff::2_44").append("\n");
        		content.append("武汉-南京10G||2001:da8:1:ff::2_45").append("\n");
        		content.append("南京-上海_10G||2001:da8:1:ff::4_38").append("\n");
        		content.append("北京-郑州B_2.5G||2001:da8:1:ff::1_24").append("\n");
        		content.append("郑州-西安_2.5G||202.38.120.197_1792").append("\n");
        		content.append("西安-兰州_2.5G||202.38.120.207_268435718").append("\n");
        		content.append("西安-成都2.5G||202.38.120.207_67109126").append("\n");
        		content.append("成都-重庆_2.5G||202.38.120.208_134217990").append("\n");
        		content.append("武汉-重庆_2.5G||2001:da8:1:ff::2_46").append("\n");
        		content.append("武汉-长沙_2.5G||2001:da8:1:ff::2_47").append("\n");
        		content.append("广州-厦门_2.5G||2001:da8:1:ff::3_59").append("\n");
        		content.append("上海-杭州_2.5G||2001:da8:1:ff::5_42").append("\n");
        		content.append("厦门-杭州_2.5G||202.38.120.213_939524358").append("\n");
        		content.append("南京-合肥_2.5G||2001:da8:1:ff::4_39").append("\n");
        		content.append("天津-济南_2.5G||202.38.120.198_512").append("\n");
        		content.append("北京-天津B_2.5G||2001:da8:1:ff::1_26").append("\n");
        		content.append("北京-沈阳B_2.5G||2001:da8:1:ff::1_28").append("\n");
        		content.append("沈阳-大连_2.5G||202.38.120.200_2048").append("\n");
        		content.append("沈阳-长春_2.5G||202.38.120.200_1536").append("\n");
        		content.append("长春-哈尔滨_2.5G||202.38.120.202_2048").append("\n");
        		content.append("合肥-济南_2.5G||202.38.120.211_335544582").append("\n");
        		setFlowOfIntranet(content.toString());
        		//北京交换中心流量
        		content.delete(0, content.toString().length());
        		content.append("RS1-KREONet2-1_1G||210.25.189.1_49").append("\n");
        		content.append("RS1-KREONet2-2_1G||210.25.189.1_50").append("\n");
        		content.append("RS2-TEIN2_1G||210.25.189.2_59").append("\n");
        		content.append("RS2-APAN_1G||210.25.189.2_61").append("\n");
        		content.append("RS1-HK_10G||210.25.189.1_38").append("\n");
        		content.append("RS1-HK_CERNET-b_1G||210.25.189.1_47").append("\n");
        		content.append("HK-CERNET_3750-1_1G||210.25.189.3_12").append("\n");
        		content.append("HK-CERNET_3750-2_1G||210.25.189.3_14").append("\n");
        		content.append("HK-ANC_2.5G||210.25.189.3_18").append("\n");
        		content.append("RS2-RS1_10G||210.25.189.2_56").append("\n");
        		content.append("SW1-RS2_10G||210.25.189.67_2").append("\n");
        		content.append("SW1-RS1_10G||210.25.189.67_3").append("\n");
        		content.append("RS1-r0k_155M||210.25.189.1_39").append("\n");
        		content.append("SW1-中国电信_1G||210.25.189.67_15").append("\n");
        		content.append("SW1-中国联通_1G||210.25.189.67_16").append("\n");
        		content.append("SW1-中国网通_1G||210.25.189.67_17").append("\n");
        		content.append("SW1-中国移动_1G||210.25.189.67_18").append("\n");
        		content.append("SW1-中国铁通_10G||210.25.189.67_1").append("\n");
        		content.append("SW1-CERNET_1G||210.25.189.67_19").append("\n");
        		content.append("SW1-CJIPv6_1G||210.25.189.67_20").append("\n");
        		content.append("SW1-NSFCNET_1G||210.25.189.67_21").append("\n");
        		content.append("SW2-RS1_10G||210.25.189.99_34144315").append("\n");
        		content.append("SW2-RS2_10G||210.25.189.99_34406459").append("\n");
        		setFlowOfBJ(content.toString());
        		//上海交换中心流量
        		content.delete(0, content.toString().length());
        		content.append("上海HW-上海6IX_1G||219.243.214.254_134217985").append("\n");
        		content.append("上海HW-上海_2.5G||219.243.214.254_201326854").append("\n");
        		setFlowOfSH(content.toString());
        	return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionMessage("初始化失败!");
           return "error";
        } 
    }
	
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
	public String getWebIP() {
		return webIP;
	}
	public void setWebIP(String webIP) {
		this.webIP = webIP;
	}
	public String getDataIP() {
		return dataIP;
	}
	public void setDataIP(String dataIP) {
		this.dataIP = dataIP;
	} 
	public String getFaultIP() {
		return faultIP;
	}
	public void setFaultIP(String faultIP) {
		this.faultIP = faultIP;
	}
	public String getFlowIP() {
		return flowIP;
	}
	public void setFlowIP(String flowIP) {
		this.flowIP = flowIP;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	public String getFault() {
		return fault;
	}

	public void setFault(String fault) {
		this.fault = fault;
	}

	public String getUsability() {
		return usability;
	}

	public void setUsability(String usability) {
		this.usability = usability;
	}

	public String getFaultOfLine() {
		return faultOfLine;
	}

	public void setFaultOfLine(String faultOfLine) {
		this.faultOfLine = faultOfLine;
	}

	public String getUsabilityOfCNGI() {
		return usabilityOfCNGI;
	}

	public void setUsabilityOfCNGI(String usabilityOfCNGI) {
		this.usabilityOfCNGI = usabilityOfCNGI;
	}

	public String getSumOfLine() {
		return sumOfLine;
	}

	public void setSumOfLine(String sumOfLine) {
		this.sumOfLine = sumOfLine;
	}

	public String getSumOfDevice() {
		return sumOfDevice;
	}

	public void setSumOfDevice(String sumOfDevice) {
		this.sumOfDevice = sumOfDevice;
	}

	public String getFlowOfInput() {
		return flowOfInput;
	}

	public void setFlowOfInput(String flowOfInput) {
		this.flowOfInput = flowOfInput;
	}

	public String getFlowOfOutput() {
		return flowOfOutput;
	}

	public void setFlowOfOutput(String flowOfOutput) {
		this.flowOfOutput = flowOfOutput;
	}

	public String getFlowOfBorder() {
		return flowOfBorder;
	}

	public void setFlowOfBorder(String flowOfBorder) {
		this.flowOfBorder = flowOfBorder;
	}

	public String getFlowOfInternet() {
		return flowOfInternet;
	}

	public void setFlowOfInternet(String flowOfInternet) {
		this.flowOfInternet = flowOfInternet;
	}

	public String getFlowOfIntranet() {
		return flowOfIntranet;
	}

	public void setFlowOfIntranet(String flowOfIntranet) {
		this.flowOfIntranet = flowOfIntranet;
	}

	public String getFlowOfBJ() {
		return flowOfBJ;
	}

	public void setFlowOfBJ(String flowOfBJ) {
		this.flowOfBJ = flowOfBJ;
	}

	public String getFlowOfSH() {
		return flowOfSH;
	}

	public void setFlowOfSH(String flowOfSH) {
		this.flowOfSH = flowOfSH;
	}

	public String getFaultTitle() {
		return faultTitle;
	}

	public void setFaultTitle(String faultTitle) {
		this.faultTitle = faultTitle;
	}

	public String getUsabilityTitle() {
		return usabilityTitle;
	}

	public void setUsabilityTitle(String usabilityTitle) {
		this.usabilityTitle = usabilityTitle;
	}

	public String getFaultOfLineTitle() {
		return faultOfLineTitle;
	}

	public void setFaultOfLineTitle(String faultOfLineTitle) {
		this.faultOfLineTitle = faultOfLineTitle;
	}

	public String getUsabilityOfCNGITitle() {
		return usabilityOfCNGITitle;
	}

	public void setUsabilityOfCNGITitle(String usabilityOfCNGITitle) {
		this.usabilityOfCNGITitle = usabilityOfCNGITitle;
	}

	public String getSumOfLineTitle() {
		return sumOfLineTitle;
	}

	public void setSumOfLineTitle(String sumOfLineTitle) {
		this.sumOfLineTitle = sumOfLineTitle;
	}

	public String getSumOfDeviceTitle() {
		return sumOfDeviceTitle;
	}

	public void setSumOfDeviceTitle(String sumOfDeviceTitle) {
		this.sumOfDeviceTitle = sumOfDeviceTitle;
	}

	public String getFlowOfInputTitle() {
		return flowOfInputTitle;
	}

	public void setFlowOfInputTitle(String flowOfInputTitle) {
		this.flowOfInputTitle = flowOfInputTitle;
	}

	public String getFlowOfOutputTitle() {
		return flowOfOutputTitle;
	}

	public void setFlowOfOutputTitle(String flowOfOutputTitle) {
		this.flowOfOutputTitle = flowOfOutputTitle;
	}

	public String getFlowOfBorderTitle() {
		return flowOfBorderTitle;
	}

	public void setFlowOfBorderTitle(String flowOfBorderTitle) {
		this.flowOfBorderTitle = flowOfBorderTitle;
	}

	public String getFlowOfInternetTitle() {
		return flowOfInternetTitle;
	}

	public void setFlowOfInternetTitle(String flowOfInternetTitle) {
		this.flowOfInternetTitle = flowOfInternetTitle;
	}

	public String getFlowOfIntranetTitle() {
		return flowOfIntranetTitle;
	}

	public void setFlowOfIntranetTitle(String flowOfIntranetTitle) {
		this.flowOfIntranetTitle = flowOfIntranetTitle;
	}

	public String getFlowOfBJTitle() {
		return flowOfBJTitle;
	}

	public void setFlowOfBJTitle(String flowOfBJTitle) {
		this.flowOfBJTitle = flowOfBJTitle;
	}

	public String getFlowOfSHTitle() {
		return flowOfSHTitle;
	}

	public void setFlowOfSHTitle(String flowOfSHTitle) {
		this.flowOfSHTitle = flowOfSHTitle;
	}
}
