package com.hoperun.chorny.customlistview.entity;

import java.util.List;

/**
 * Created by Chorny on 2017/2/25.
 */
public class ResultEntity {
    private String tittle;
    private List<ItemEntity> rows;

    public ResultEntity() {
    }

    public ResultEntity(String tittle, List<ItemEntity> rows) {
        this.tittle = tittle;
        this.rows = rows;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public List<ItemEntity> getRows() {
        return rows;
    }

    public void setRows(List<ItemEntity> rows) {
        this.rows = rows;
    }
}
