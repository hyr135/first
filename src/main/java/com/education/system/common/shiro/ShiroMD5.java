package com.education.system.common.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;

public class ShiroMD5 {
	/**
	 * 用户账号
	 * @param loginame 用户账号
	 * @param password 用户密码
	 * @return 获取密文密码
	 */
	public static String GetPwd(String loginame, String password) {
		// 密码加盐
		Md5Hash md5 = new Md5Hash(password, loginame, 1024);// 1024-哈希码
		String newMd5Password = md5.toHex();
		System.out.println("加盐后的密码是:" + newMd5Password);
		// 返回新密码
		return newMd5Password;
	}

	public static void main(String[] args) {
		//GetPwd("admin", "123");
		GetPwd("小萝莉small", "123");
	}

}
