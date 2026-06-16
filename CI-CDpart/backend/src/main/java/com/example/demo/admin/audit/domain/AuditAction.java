package com.example.demo.admin.audit.domain;

public enum AuditAction {

    USER_CREATE,
    USER_UPDATE,
    USER_DELETE,

    USER_STATUS_CHANGE,

    ROLE_ASSIGN,
    ROLE_REMOVE,

    LOGIN,
    LOGOUT
}