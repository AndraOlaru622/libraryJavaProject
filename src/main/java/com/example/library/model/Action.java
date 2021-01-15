package com.example.library.model;

import java.util.Date;

public class Action {

    private int idAction;
    private String table;
    private String description;
    private String actionDate;

    public Action() {
    }

    public Action(int idAction, String table, String description, String actionDate) {
        this.idAction = idAction;
        this.table = table;
        this.description = description;
        this.actionDate = actionDate;
    }

    public int getIdAction() {
        return idAction;
    }

    public void setIdAction(int idAction) {
        this.idAction = idAction;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }
}
