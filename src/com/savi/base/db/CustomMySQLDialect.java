package com.savi.base.db;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;

public class CustomMySQLDialect extends MySQL5Dialect {
    public CustomMySQLDialect() {
        super();
        this.registerHibernateType(Types.REAL, Hibernate.FLOAT.getName());
        this.registerHibernateType(Types.TIMESTAMP, Hibernate.DATE.getName());  
        this.registerHibernateType(Types.BIGINT, Hibernate.LONG.getName());
        this.registerHibernateType(Types.BOOLEAN, Hibernate.BOOLEAN.getName());
    }
}
