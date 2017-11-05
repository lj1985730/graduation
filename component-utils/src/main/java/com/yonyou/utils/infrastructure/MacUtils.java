package com.yonyou.utils.infrastructure;

import com.graduation.core.base.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 核心-MAC地址工具
 * @author Liu Jun at 2017-10-27 13:07:03
 * @since v1.0.0
 */
public class MacUtils {

	/**
	 * 获取当前操作系统名称. return 操作系统名称 例如:windows,Linux,Unix等.
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	 * 获取Unix网卡的mac地址.
	 * @return mac地址
	 */
	public static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process;
		try {
			//Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			int index;
			while ((line = bufferedReader.readLine()) != null) {
				// 寻找标示字符串[hwaddr]
				index = line.toLowerCase().indexOf("hwaddr");
				if (index != -1) {
					//取出mac地址并去除2边空格
					mac = StringUtils.substringAfter(line, "hwaddr").trim();
					break;
				}
			}
		} catch (IOException e) {
			throw new BusinessException("读取mac错误！", e);
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return mac;
	}

	/**
	 * 获取Linux网卡的mac地址.
	 * @return mac地址
	 */
	public static String getLinuxMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process;
		try {
			//linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			int index;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("硬件地址");
				if (index != -1) {
					// 取出mac地址并去除2边空格
					mac = StringUtils.substringAfter(line, "硬件地址").trim();
					break;
				}
			}
		} catch (IOException e) {
			throw new BusinessException("读取mac错误！", e);
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		// 取不到，试下Unix方式
		if (mac == null) {
			return getUnixMACAddress();
		}

		return mac;
	}

	/**
	 * 获取windows网卡的mac地址.
	 * @return mac地址
	 */
	public static String getWindowsMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process;
		try {
			//windows下的命令，显示信息中包含有mac地址信息
			process = Runtime.getRuntime().exec("ipconfig /all");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			int index;
			while ((line = bufferedReader.readLine()) != null) {
				// 寻找标示字符串[physical address]
//				index = line.toLowerCase().indexOf("physical address");
//				if (index != -1) {
				if (line.split("-").length == 6) {
					index = line.indexOf(":");
					if (index != -1) {
						//取出mac地址并去除2边空格
						mac = line.substring(index + 1).trim();
					}
					break;
				}
				index = line.toLowerCase().indexOf("物理地址");
				if (index != -1) {
					index = line.indexOf(":");
					if (index != -1) {
						// 取出mac地址并去除2边空格
						mac = line.substring(index + 1).trim();
					}
					break;
				}
			}
		} catch (IOException e) {
			throw new BusinessException("读取mac错误！", e);
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return mac;
	}

	public static String getMac(){
		String os = getOSName();
		String mac;
		if (os.startsWith("windows")) {
			mac = getWindowsMACAddress();
		} else if (os.startsWith("linux")) {
			mac = getLinuxMACAddress();
		} else {
			mac = getUnixMACAddress();
		}
		return mac == null ? "" : mac;
	}
}