package com.itheima.domain;

import java.util.List;

public class QueryVO {
    private User user;
    /**
     * 要查询多个人的id
     */
    private List<Integer> ids;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}