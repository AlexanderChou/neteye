package com.report.util;


public class StringUtil {
	public static String changeIP(String IP) {
		String changedIP = "";
		if (IP.equals("2001:da8:1:ff::1")) {
			changedIP = "202.38.120.192";
		} else if (IP.equals("2001:da8:1:ff::2")) {
			changedIP = "202.38.120.193";
		} else if (IP.equals("2001:da8:1:ff::3")) {
			changedIP = "202.38.120.194";
		} else if (IP.equals("2001:da8:1:ff::4")) {
			changedIP = "202.38.120.195";
		} else if (IP.equals("2001:da8:1:ff::5")) {
			changedIP = "202.38.120.196";
		} else if (IP.equals("2001:da8:1:ff::6")) {
			changedIP = "202.38.120.197";
		} else if (IP.equals("2001:da8:1:ff::7")) {
			changedIP = "202.38.120.198";
		} else if (IP.equals("2001:da8:1:ff::8")) {
			changedIP = "202.38.120.199";
		} else if (IP.equals("2001:da8:1:ff::9")) {
			changedIP = "202.38.120.200";
		} else if (IP.equals("2001:da8:1:ff::10")) {
			changedIP = "202.38.120.201";
		} else if (IP.equals("2001:da8:1:ff::11")) {
			changedIP = "202.38.120.202";
		} else if (IP.equals("2001:da8:1:ff::12")) {
			changedIP = "202.38.120.203";
		} else if (IP.equals("2001:da8:1:ff::13")) {
			changedIP = "202.38.120.204";
		} else if (IP.equals("2001:da8:1:ff::14")) {
			changedIP = "202.38.120.205";
		} else if (IP.equals("2001:da8:1:ff::15")) {
			changedIP = "202.38.120.206";
		} else if (IP.equals("2001:da8:1:ff::16")) {
			changedIP = "202.38.120.207";
		} else if (IP.equals("2001:da8:1:ff::17")) {
			changedIP = "202.38.120.208";
		} else if (IP.equals("2001:da8:1:ff::18")) {
			changedIP = "202.38.120.209";
		} else if (IP.equals("2001:da8:1:ff::19")) {
			changedIP = "202.38.120.210";
		} else if (IP.equals("2001:da8:1:ff::20")) {
			changedIP = "202.38.120.211";
		} else if (IP.equals("2001:da8:1:ff::21")) {
			changedIP = "202.38.120.212";
		} else if (IP.equals("2001:da8:1:ff::22")) {
			changedIP = "202.38.120.213";
		} else if (IP.equals("2001:da8:1:ff::23")) {
			changedIP = "202.38.120.214";
		} else if (IP.equals("2001:da8:1:ff::24")) {
			changedIP = "202.38.120.215";
		} else if (IP.equals("2001:da8:1:ff::25")) {
			changedIP = "202.38.120.216";
		} else if (IP.equals("2001:da8:1:ff::26")) {
			changedIP = "202.38.120.217";
		} else if (IP.equals("2001:da8:1:ff::27")) {
			changedIP = "202.38.120.218";
		} else if (IP.equals("2001:da8:1:ff::28")) {
			changedIP = "202.38.120.219";
		} else if (IP.equals("2001:da8:1:ff::29")) {
			changedIP = "202.38.120.220";
		} else if (IP.equals("2001:da8:1:ff::30")) {
			changedIP = "202.38.120.221";
		} else if (IP.equals("2001:da8:1:ff::31")) {
			changedIP = "202.38.120.222";
		} else if (IP.equals("2001:da8:1:ff::32")) {
			changedIP = "202.38.120.223";
		} else if (IP.equals("2001:da8:1:ff::33")) {
			changedIP = "202.38.120.224";
		} else if (IP.equals("2001:da8:1:ff::34")) {
			changedIP = "202.38.120.225";
		} else if (IP.equals("2001:da8:1:ff::35")) {
			changedIP = "202.38.120.226";
		} else if (IP.equals("2001:da8:1:ff::36")) {
			changedIP = "202.38.120.227";
		}
		return changedIP;
	}

	public static String changeNum(int count) {
		String numStr = "";
		switch (count) {
		case 0:
			numStr = "一、";
			break;
		case 1:
			numStr = "二、";
			break;
		case 2:
			numStr = "三、";
			break;
		case 3:
			numStr = "四、";
			break;
		case 4:
			numStr = "五、";
			break;
		case 5:
			numStr = "六、";
			break;
		case 6:
			numStr = "七、";
			break;
		case 7:
			numStr = "八、";
			break;
		case 8:
			numStr = "九、";
			break;
		case 9:
			numStr = "十、";
			break;
		}
		return numStr;
	}
}
