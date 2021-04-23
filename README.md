# Filling holiday table
<p>This program parse <a href="http://xmlcalendar.ru">production calendar</a> and fill your table in <b>postgresql</b> database. Below are short recommendations before starting the source code. </p>

## Refactor configuration file

<p>Insert here instead of "*" your connection url, username for database and password</p>

```xml 
<property name="connection.url">******</property>
<property name="connection.username">******</property>
<property name="connection.password">******</property>
```

<p>For example:</p>

```xml
<property name="connection.url"> jdbc:mysql://127.0.0.1:3306/myLocalBase</property>
<property name="connection.username">admin</property>
<property name="connection.password">iAmAdmin!</property>
```
### P.S. Be careful!! 

This property run command ```drop table if exists holidays cascade```

```xml
<property name="hbm2ddl.auto">create</property>
```

If you don't need it, replace for this 

```xml
<property name="hbm2ddl.auto">validate</property>
```



## Dependencies and links

This project use dependencies:

1. postgresql: <a href="https://mvnrepository.com/artifact/org.postgresql/postgresql/42.2.19">maven</a>. <a href="https://jdbc.postgresql.org/">documentation link</a>
2. hibernate: <a href="https://mvnrepository.com/artifact/org.hibernate/hibernate-core/5.4.28.Final">maven</a>. <a href="https://hibernate.org/orm/documentation/5.4/">documentation link</a>
3. projectlombok:  <a href="https://mvnrepository.com/artifact/org.projectlombok/lombok/1.18.18">maven</a>. <a href="https://projectlombok.org/features/all">documentation link</a>
4. json-simple <a href="https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple/1.1.1">maven</a>. <a href="https://code.google.com/archive/p/json-simple/">documentation link</a>
5. jsoup <a href="https://mvnrepository.com/artifact/org.jsoup/jsoup/1.13.1">maven</a>. <a href="https://jsoup.org/">documentation link</a>