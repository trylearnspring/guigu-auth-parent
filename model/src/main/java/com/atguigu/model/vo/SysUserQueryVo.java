package com.atguigu.model.vo;

import java.io.Serializable;

/**
 * <p>
 * 用户查询实体
 * </p>
 */
public class SysUserQueryVo implements Serializable {

    private static final long serialVersionUID = 3666440873612428816L;
    private String keyword;

    private String createTimeBegin;
    private String createTimeEnd;

    private Long roleId;
    private Long postId;
    private Long deptId;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setCreateTimeBegin(String createTimeBegin) {
        this.createTimeBegin = createTimeBegin;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
