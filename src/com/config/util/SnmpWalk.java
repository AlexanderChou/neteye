package com.config.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

/*
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
*/
public class SnmpWalk {
	public void Walk(String host, String community, String oid, ArrayList key,
			ArrayList value, String version) {
		try {
			// String osName = System.getProperty("os.name" );
			String[] cmd = new String[3];
			if (host.indexOf(":") != -1) {
				host = "udp6:" + host;
			}
			cmd[0] = "snmpwalk -Cc -v " + version + " -c " + community + " " + host + " " + oid;
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd[0]);

			String output;
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(proc.getInputStream()));
			while ((output = bufferedReader.readLine()) != null) {
				if (output.indexOf("No Response from") > 0 || output.indexOf("No Such Object available on this agent at this OID") > 0) {
					break;
				}
				if (!output.equals("")) {
					int index1 = output.indexOf(oid);
					int index2 = output.indexOf(" = ");
					String strValue = output.substring(index2 + 3);
					int temp33 = (index1 + oid.length() + 1);
					if (temp33 > index2) {
						continue;
					} else {
						key.add(output.substring(temp33, index2));
					}
					if (strValue.indexOf("STRING:") < 0) {
						int leftbrace = strValue.indexOf('(');
						int rightbrace = strValue.indexOf(')');
						if (rightbrace > leftbrace && leftbrace > 0) {
							strValue = strValue.substring(leftbrace + 1,rightbrace);
						} else {
							int comma = strValue.indexOf(':');
							if (comma > 0)
								strValue = strValue.substring(comma + 1).trim();
						}
					} else {
						int comma = strValue.indexOf(':');
						if (comma > 0)
							strValue = strValue.substring(comma + 1).trim();
					}
					value.add(strValue);
				}
			}
			proc.waitFor();
			// int exitVal = proc.waitFor();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void walkPart(String host, String community, String oid,ArrayList key, ArrayList value, ArrayList IPv6Str, String version) {
		try {
			String temp = "";
			String[] cmd = new String[3];
			if (host.indexOf(":") != -1) {
				host = "udp6:" + host;
			}

			cmd[0] = "snmpwalk -Cc -v " + version + " -c " + community + " "+ host + " " + oid + " " + "-Ob";
			String prefixStr = oid;
			if (oid.indexOf("55.1.8.1.2") > 0) {
				prefixStr = "SNMPv2-SMI::mib-2.55.1.8.1.2";
			}

			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd[0]);

			String output;
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(proc.getInputStream()));
			while ((output = bufferedReader.readLine()) != null) {
				if (output.indexOf("No Response from") > 0	|| output.indexOf("No Such Object available on this agent at this OID") > 0) {
					break;
				}
				if (!output.equals("")) {
					int index1 = output.indexOf(prefixStr);
					int index2 = output.indexOf(" = ");
					String strValue = output.substring(index2 + 3);
					int temp33 = (index1 + prefixStr.length() + 1);
					if (temp33 > index2) {
						continue;
					} else {
						String tempStr = output.substring(temp33, index2);
						temp = tempStr.substring(tempStr.indexOf(".") + 1);
						if (temp.indexOf("254") != -1) {
							continue;
						} else {
							key.add(tempStr.substring(0, tempStr.indexOf(".")));
							IPv6Str.add(temp);
						}
					}
					if (strValue.indexOf("STRING:") < 0) {
						int leftbrace = strValue.indexOf('(');
						int rightbrace = strValue.indexOf(')');
						if (rightbrace > leftbrace && leftbrace > 0) {
							int comma = strValue.indexOf(':');
							if (comma > 0){
								strValue = strValue.substring(comma + 1,leftbrace);
							}
						} else {
							int comma = strValue.indexOf(':');
							if (comma > 0)
								strValue = strValue.substring(comma + 1).trim();
						}
					} else {
						int comma = strValue.indexOf(':');
						if (comma > 0)
							strValue = strValue.substring(comma + 1).trim();
					}
					value.add(strValue);
				}
			}// Endof while
			proc.waitFor();

			// int exitVal = proc.waitFor();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void walkPartForIPv6(String host, String community, String oid,
			ArrayList key, ArrayList value, ArrayList IPv6Str, String version) {
		boolean flag = false;
		try {
			String[] cmd = new String[3];
			if (host.indexOf(":") != -1) {
				host = "udp6:" + host;
			}
			if (key != null && key.size() > 0) {
				oid = oid + "." + key.get(0).toString();
				flag = true;
			}
			cmd[0] = "snmpwalk -Cc -v " + version + " -c " + community + " " + host + " " + oid + " " + " -Obn";
			String prefixStr = oid;
			if (oid.indexOf("55.1.8.1.2") > 0) {
				prefixStr = "SNMPv2-SMI::mib-2.55.1.8.1.2";
			}

			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd[0]);
			String output;
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(proc.getInputStream()));
			while ((output = bufferedReader.readLine()) != null) {
				if (output.indexOf("No Response from") > 0	|| output.indexOf("No Such Object available on this agent at this OID") > 0) {
					break;
				}
				if (!output.equals("")) {
					int index1 = output.indexOf(prefixStr);
					int index2 = output.indexOf(" = ");
					String strValue = output.substring(index2 + 3);
					int temp33 = (index1 + prefixStr.length() + 1);
					if (temp33 > index2) {
						continue;
					} else {
						String tempStr = output.substring(temp33, index2);
						String temp = tempStr;
						if (!flag) {
							temp = tempStr.substring(tempStr.indexOf(".") + 1);
						}
						if (!temp.equals("")) {
							if (temp.indexOf("254") != -1) {
								continue;
							} else {
								IPv6Str.add(temp);
								int comma = strValue.indexOf(':');
								if (comma > 0)
									strValue = strValue.substring(comma + 1)
											.trim();
								value.add(strValue);
								break;
							}
						}
					}
				}
			}// Endof while
			proc.waitFor();
			// int exitVal = proc.waitFor();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public String toHex(String temp1) {
		String[] arr = temp1.split("\\.");

		StringBuffer str = new StringBuffer();
		Vector vec = new Vector();
		for (int i = 0; i < arr.length; i = i + 2) {
			String hexStr1 = "";
			try {
				hexStr1 = Integer.toHexString(Integer.parseInt(arr[i]));
			} catch (NumberFormatException e) {
				return "-1";
			}
			String hexStr2 = "";
			try {
				hexStr2 = Integer.toHexString(Integer.parseInt(arr[i + 1]));
			} catch (NumberFormatException e) {
				return "-1";
			}
			if (hexStr2.length() == 1) {
				hexStr2 = "0" + hexStr2;
			}
			String temp = hexStr1 + hexStr2;
			if (!hexStr1.equals("0")) {
				vec.add(hexStr1 + hexStr2);
				str.append(hexStr1);
				str.append(hexStr2);
			} else {
				if (!hexStr2.equals("0")) {
					if ((hexStr2.substring(0, 1)).equals("0")) {
						str.append(hexStr2.substring(1, 2));
						vec.add(hexStr2.substring(1, 2));
					} else {
						str.append(hexStr2);
						vec.add(hexStr2);
					}
				} else {
					str.append("0");
					vec.add(0);
				}
			}
			str.append(":");
		}
		return str.toString().substring(0, (str.toString()).length() - 1);
	}
}
