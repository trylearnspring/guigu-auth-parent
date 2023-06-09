//
//
package com.atguigu.model.vo;

import java.io.Serializable;

/**
 * <p>
 * 角色查询实体
 * </p>
 */
public class SysRoleQueryVo implements Serializable {


	private static final long serialVersionUID = -8450871214260042596L;
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}

