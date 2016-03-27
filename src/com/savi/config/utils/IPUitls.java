package com.savi.config.utils;

public class IPUitls {
	public static char[] ipv4ToBinary(final String ipv4) {
		if (ipv4 == null)
			return null;

		String[] ipsDecimal = ipv4.split("\\.");
		if (ipsDecimal.length != 4)
			return null;

		String ipsBinary = "";

		for (int i = 0; i < ipsDecimal.length; i++){
			String temp= Integer.toBinaryString(Integer.parseInt(ipsDecimal[i]));
			for(int j = temp.length(); j < 8; j++)
				temp = "0" + temp;
			ipsBinary += temp;
		}
		
		return ipsBinary.toCharArray();
	}

	public static char[] ipv6ToBinary(final String ipv6) {
		if (ipv6 == null)
			return null;

		String ip = new String(ipv6);
		int i = 0;

		if ((i = ipv6.indexOf("::")) != -1) {
			ip = ip.substring(0, i) + ":0000:0000:0000:0000:0000:0000:"
					+ ip.substring(i + 2);
		}

		String[] ipsHex = ip.split(":");

		if (ipsHex.length != 8)
			return null;

		String ipsBinary = "";

		for (i = 0; i < ipsHex.length; i++){
			String temp = Integer.toBinaryString(Integer.parseInt(ipsHex[i], 16));
			for(int j = temp.length(); j < 16; j++)
				temp = "0" + temp;
			ipsBinary += temp;
		}

		return ipsBinary.toCharArray();
	}
	
	public static String binaryToIPv4(char[] binary){
		String ip = "";
		
		for(int i = 0; i < 4; i++){
			char[] c = copyPart(binary, 8 * i, 8);
			ip += Integer.parseInt(new String(c), 2);
			if(i != 3) ip += ".";
		}
	
		return ip;
	}
	
	public static String binaryToIPv6(char[] binary){
		String ip = "";
		
		for(int i = 0; i < 8; i++){
			char[] c = copyPart(binary, 16 * i, 16);
			String temp = Integer.toHexString(Integer.parseInt(new String(c), 2)).toUpperCase();
			for(int j = temp.length(); j < 4; j++)
				temp = "0" + temp;
			ip += temp;
			if(i != 7) ip+=":";
		}

		ip = ip.replaceFirst(":0000:0000:0000:0000:0000:0000:", "::");

		return ip;
	}
	
	private static char[] copyPart(char[] c, int start, int length){
		char[] temp = new char[length];
		for(int i = 0; i < length; i++)
			temp[i] = c[i + start];
		
		return temp;
	}

	public static char[] increment(char[] ipBinary) {
		int i = ipBinary.length - 1;
		
		while (i >= 0  && ipBinary[i] == '1') {
			ipBinary[i--] = '0';
		}
		
		if (i > 0)
			ipBinary[i] = '1';
		
		return ipBinary;
	}
	
	public static boolean equal(char[] ipBinary1, char[] ipBinary2 ){
		if(ipBinary1.length != ipBinary2.length) return false;
		
		for(int i = 0; i < ipBinary1.length; i++)
			if(ipBinary1[i] != ipBinary2[i]) return false;
		
		return true;
	}
	
	public static void main(String[] args){
		String ip = "192.168.1.1";
		char[] c = ipv4ToBinary(ip);
		
//		String ip = "FF0c::000F";
//		char[] c = ipv6ToBinary(ip);
		
		System.out.println(new String(c));
		System.out.println(binaryToIPv4(c));
		//System.out.println(binaryToIPv6(c));
		
		for(int i=0; i < c.length; i++){
			c = increment(c);

			System.out.println(new String(c));
			System.out.println(binaryToIPv4(c));
			//System.out.println(binaryToIPv6(c));
		}
	}
}
