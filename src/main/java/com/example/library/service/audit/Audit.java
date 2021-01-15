package com.example.library.service.audit;


public class Audit {

    public final static String ADD_ACTION  = "INSERT into audit(idAction, tableName, description, actionDate) values (null, ?, ?, ?)";
    public final static String GET_ALL = "getAll";
    public final static String ADDED = "added";
    public final static String GET_BY_NAME = "getByName";
    public final static String GET_BY_ID = "getById";
    public final static String INCREASE = "addedCopies";
    public final static String DELETED = "deleted";

}
