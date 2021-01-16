package com.education.system.sys.vo;

import com.education.system.sys.entity.BusOrg;

public class BusOrgVo extends BusOrg{

	/**
	 * 电脑
	 */
	private String orgComputerName;

	private String orgStageName;

	public String getOrgComputerName() {
		return orgComputerName;
	}

	public void setOrgComputerName(String orgComputerName) {
		this.orgComputerName = orgComputerName;
	}

	public String getOrgStageName() {
		return orgStageName;
	}

	public void setOrgStageName(String orgStageName) {
		this.orgStageName = orgStageName;
	}

}
