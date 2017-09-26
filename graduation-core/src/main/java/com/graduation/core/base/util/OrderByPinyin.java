/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package com.graduation.core.base.util;
  

import java.sql.Types;  
  

import org.hibernate.Criteria;  
import org.hibernate.HibernateException;  
import org.hibernate.criterion.CriteriaQuery;  
import org.hibernate.criterion.Order;  
import org.hibernate.engine.spi.SessionFactoryImplementor; 
import org.hibernate.type.Type;  

public class OrderByPinyin extends Order {  
    private String encoding = "GBK";  
    private boolean ascending;  
    private boolean ignoreCase;  
    private String propertyName;  
  
    @Override  
    public String toString() {  
        return "CONVERT( " + propertyName + " USING " + encoding + " ) " + (ascending ? "asc" : "desc");  
    }  
  
    @Override  
    public Order ignoreCase() {  
        ignoreCase = true;  
        return this;  
    }  
  
    /** 
     * Constructor for Order. 
     */  
    protected OrderByPinyin(String propertyName, boolean ascending) {  
        super(propertyName, ascending);  
        this.propertyName = propertyName;  
        this.ascending = ascending;  
    }  
  
    /** 
     * Constructor for Order. 
     */  
    protected OrderByPinyin(String propertyName, String dir) {  
        super(propertyName, dir.equalsIgnoreCase("ASC"));
        this.ascending = dir.equalsIgnoreCase("ASC");
        this.propertyName = propertyName;  
    }  
  
    /** 
     * Render the SQL fragment 
     */
    @Override  
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {  
        String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, propertyName);  
        Type type = criteriaQuery.getTypeUsingProjection(criteria, propertyName);  
        StringBuilder fragment = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {  
            SessionFactoryImplementor factory = criteriaQuery.getFactory();  
            boolean lower = ignoreCase && type.sqlTypes(factory)[i] == Types.VARCHAR;  
            if (lower) {  
                fragment.append(factory.getDialect().getLowercaseFunction()).append('(');  
            }  
            fragment.append("CONVERT( " + columns[i] + " USING " + encoding + " )");  
            if (lower)  {
                fragment.append(')');
            }
            fragment.append(ascending ? " asc" : " desc");
            if (i < columns.length - 1)  
                fragment.append(", ");  
        }  
        return fragment.toString();  
    }  
  
    /** 
     * Ascending order 
     * @param propertyName 属性名
     * @return Order 
     */  
    public static Order asc(String propertyName) {  
        return new OrderByPinyin(propertyName, true);  
    }  
  
    /** 
     * Descending order 
     * @param propertyName 属性名
     * @return Order 
     */  
    public static Order desc(String propertyName) {  
        return new OrderByPinyin(propertyName, false);  
    }  
} 