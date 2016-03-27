package com.savi.base.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BathImport {
	public static String constructIPv6(String ipv4){
		String tempIpv4[]=ipv4.split("\\.");
		String C=tempIpv4[2];
		String D=tempIpv4[3];
		String CHex=Integer.toHexString(Integer.parseInt(C));
		String ipv6="2402:F000:5:"+CHex+"01::"+C+":"+D;
		return ipv6;
	}
	public static void main(String[] args) {
		File excelFile=new File("D:/1.txt");
		File importFile=new File("D:/result.txt");
		try {
				BufferedReader br=new BufferedReader(new FileReader(excelFile));
				BufferedWriter bw=new BufferedWriter(new FileWriter(importFile,true));
				while(true){
					String line=br.readLine();
					System.out.println(line);
					if(line==null)break;
					String[] switchInfo=line.split("\t");
					System.out.println(switchInfo[2]);
					String ipv6=constructIPv6(switchInfo[2]);
					String ipv4Array[]=switchInfo[2].split("\\.");
					String switchName=switchInfo[0]+"-"+switchInfo[1]+"-"+ipv4Array[2]+"."+ipv4Array[3];
					String newLineInfo=switchInfo[0]+"||"+ipv6+"|"+switchName+"|"+"锐捷"+"|"
					+"2c|tUnEtswItch|tUnEtswItch";
					bw.write(newLineInfo);
					bw.newLine();
				}
				bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
