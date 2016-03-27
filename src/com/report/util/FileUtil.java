package com.report.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.base.util.Constants;

public class FileUtil {
	private String path = Constants.webRealPath + "file/report/";

	public void createFile1(File oldFile, File newFile, String template) {
		String oldName = oldFile.getName().substring(0,
				oldFile.getName().lastIndexOf("."));
		try {
			BufferedReader in = new BufferedReader(new FileReader(oldFile
					.getAbsoluteFile()));
			String s = new String();
			String newStr = new String();

			if (newFile.exists()) {
				newFile.delete();
			} else {
				newFile.createNewFile();
			}
			while ((s = in.readLine()) != null) {
				if (s.indexOf(oldName) >= 0) {
					newStr = s.replaceAll(oldName, template);
				} else {
					newStr = s;
				}
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						newFile.getAbsoluteFile(), true));
				writer.write(newStr, 0, newStr.length());
				writer.newLine();
				writer.flush();
				writer.close();
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteFile(String template) {
		File f = new File(path);
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					File temp = files[i];
					if (temp.getName().equals("SelfReport.jrxml")
							|| temp.getName().equals("desc_report.jrxml")
							|| temp.getName().indexOf(".rtf") != -1
							|| temp.getName().indexOf(".jrprint") != -1
							|| temp.getName().equals("null.gif")
							|| temp.getName().equals("curves_report.jrxml")
							|| temp.getName().equals("CustomersReport.jrxml")
							|| temp.getName().equals("flow_report.jrxml")
							|| temp.getName().equals("run12_report.jrxml")
							|| temp.getName().equals("run13_report.jrxml")
							|| temp.getName().equals("run_report.jrxml")) {
						continue;
					} else {
						files[i].delete();
					}
				}// Endof if(files[i].isFile())
			}// Endof for
		}
	}
}
