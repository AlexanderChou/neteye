package com.report.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcClient;

import com.base.util.Constants;

public class ImageUtil {
	private String path = Constants.webRealPath + "file/report/";

	public Vector getBGPImages(String webIP, String dataIP, String start,
			String end, String template) {
		Vector vec = new Vector();
		Vector images = new Vector();
		URL url;
		try {
			url = new URL("http://" + webIP
					+ "/bgp-cgi/route_for_table.cgi?s_date=" + start + "&days="
					+ end + "&ip=" + dataIP);
			InputStream in = url.openStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(in,
			"latin1"));
			String s;
			while (true) {
				s = bf.readLine();
				if (s == null) {
					break;
				} else {
					if (s.indexOf("<img src") != -1) {
						int aa = s.indexOf("\"");
						int bb = s.lastIndexOf("\"");
						String temp = s.substring(aa + 1, bb);
						vec.add("http://" + webIP + temp);
					}
				}
			}
			images = this.getImages(vec, template, "BGP");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return images;
	}

	public Vector getImages(Vector vec, String template, String flag){
		Vector images = new Vector();
		if (vec != null) {
			if (flag.equals("BGP")) {
				for (int j = 0; j < vec.size() - 1; j++) {
					String imageIP = vec.get(j).toString();
					String imageName = this.part(imageIP, template, j);
					if (imageName != null && !imageName.equals("")) {
						images.add(imageName);
					}
				}//Endof for
			} else if (flag.equals("FLOW")) {
				String imageIP = vec.get(0).toString();
				if (!imageIP.equals("NULL")) {
					String imageName = this.part(imageIP, template, 0);
					if (imageName != null && !imageName.equals("")) {
						images.add(imageName);
					}
				} else {
					images.add("null.gif");
				}
			}
		}
		return images;
	}

	public String part(String imageIP, String template, int count) {
		String temp = "";
		try {
			URL imageurl = new URL(imageIP);
			URLConnection uc = imageurl.openConnection();
			InputStream is = uc.getInputStream();
			File file = new File(path + template + count + ".gif");
			FileOutputStream out = new FileOutputStream(file);
			int i = 0;
			while ((i = is.read()) != -1) {
				out.write(i);
			}
			is.close();
			if (file.exists()) {
				temp = file.getName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	public Vector getFlowImages(Vector v, String template, String flowIP)
			throws Exception {
		XmlRpcClient client = new XmlRpcClient(flowIP);
		Vector result = (Vector) client.execute(
				"ServerManager.DrawAnyTimeForReport", v);
		Vector images = this.getImages(result, template, "FLOW");

		return images;
	}

	public Vector getVectorParam(String routerAndIfIndex, String type,
			String start, String end) {
		Vector vec = new Vector();
		StringTokenizer tokens = new StringTokenizer(routerAndIfIndex, "_");
		while (tokens.hasMoreTokens()) {
			String temp = tokens.nextToken().trim();
			if (temp.indexOf(":") != -1) {//如果是IPv6，将其转化IPv4
				vec.add(StringUtil.changeIP(temp));
			} else {
				vec.add(temp);
			}
		}
		if (type.equals("year")) {
			GregorianCalendar cal = new GregorianCalendar();
			if (cal.isLeapYear(Integer.parseInt(start))) {
				end = "366";
			} else
				end = "365";
			vec.add(start);
			vec.add("01");
			vec.add("01");
			vec.add(end);
		} else if (type.equals("month")) {
			vec.add(start);
			if (end.length() == 1) {
				vec.add("0" + end);
			} else
				vec.add(end);
			vec.add("01");
			end = String.valueOf(WeekUtil.getDaysOfMonth(start, end));
			vec.add(end);
		} else {
			Date date = WeekUtil.getFirstDayOfWeek(Integer.parseInt(start),
					Integer.parseInt(end) - 1);
			String dateTime = MessageFormat.format(
					"{0,date,yyyy-MM-dd HH:mm:ss}", date);
			String startParam = dateTime.substring(0, 10);
			tokens = new StringTokenizer(startParam, "-");
			while (tokens.hasMoreTokens()) {
				vec.add(tokens.nextToken());
			}
			vec.add("7");
		}
		return vec;
	}
}
