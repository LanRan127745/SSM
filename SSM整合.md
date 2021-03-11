# 导包

## Spring

### AOP核心

```
aopalliance-1.0.jar
spring-aspects-5.2.3.RELEASE.jar 
aspectjrt-1.6.11.jar 	 //aspectjrt包是aspectj的runtime包
aspectjweaver-1.6.11.jar //aspectjweaver是aspectj的织入包
cglib-2.1.jar			 //Cglib包是用来动态代理用的,基于类的代理
```

### IOC核心

```
spring-beans-4.0.0.RELEASE
spring-core-4.0.0.RELEASE
spring-context-4.0.0.RELEASE
spring-expression-4.0.0.RELEASE
spring-aop-5.2.3.RELEASE.jar  //用于注解时使用的包
commons-logging-1.2.jar
```

### jdbc核心

```
spring-jdbc-4.0.0.RELEASE
spring-orm(Object Relation Mapping)-4.0.0.RELEASE
spring-tx-4.0.0.RELEASE (事务)
```

### 测试

```
spring-test-4.0.0.RELEASE
```



## SpringMVC

```
spring-web-4.0.0.RELEASE 和原生的web相关（对应黑色的servlet模块）
spring-webmvc-4.0.0.RELEASE  开发web项目的（web）
```

### 数据校验

```
validation-api-1.1.0.CR1.jar
jboss-logging-3.1.1.GA.jar
hibernate-validator-annotation-processor-5.0.0.CR2.jar
hibernate-validator-5.0.0.CR2.jar
classmate-0.8.0.jar
```

### Ajax

```
jackson-core-2.9.5.jar
jackson-annotations-2.9.5.jar
jackson-databind-2.9.5.ja
```

Spring 5.0 要和2.9.5 目前我配置的jar包类型（可以正常使用）



## Mybatis

### mybatis核心

```
mybatis-3.5.6.jar
```

### 数据源、驱动

```
c3p0-0.9.1.2.jar
mysql-connector-java-8.0.22.jar
```

### 日志

```
log4j-1.2.17.jar
```

### PageHelper

```
pagehelper-5.2.0.jar
jsqlparser-3.2.jar
```

或

```
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>5.2.0</version>
</dependency>
```

### MBG

```
mybatis-generator-core-1.4.0.jar
```



MyBaits-Spring 整合对应版本

![image-20210306194354870](SSM整合.assets/image-20210306194354870.png)



# 必要配置文件

### web配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--配置Spring容器启动-->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <!--指定Spring配置文件位置-->
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>

    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    
    <!--配置SpringMVC的前端控制器-->
    <servlet>
        <servlet-name>springDispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:applicationContext-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springDispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--两个标准配置-->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--Rest风格-->
    <filter>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```



### Spring配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

<!--Spring除过控制器不写，剩下的业务逻辑组件都要，包括dao、service-->
    <context:component-scan base-package="com.zzw" >
        <!--扫描排出不写 use-default-filters="false"-->
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--导入外部配置文件-->
    <context:property-placeholder location="classpath:dbconfig.properties"/>

    <!--1、配置数据源-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!-- ${username}如果key是username，name 默认spring框架调用的当前操作系统的账号 解决方案：可以统一给key加一个前缀 -->
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="maxPoolSize" value="${jdbc.maxPoolSize}"/>
        <property name="minPoolSize" value="${jdbc.minPoolSize}"/>
    </bean>

    <!--2、配置mybatis操作数据库-->
    <bean class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--指定配置文件位置-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <!--指定数据源-->
        <property name="dataSource" ref="dataSource"/>
        <!--指定xml映射文件的位置-->
        <property name="mapperLocations" value="classpath:mapper/*.xml"/>
    </bean>
<!--    将每一个dao接口实现类加入到ioc容器中-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.zzw.mapper"/>
    </bean>

    <!--3、配置事务管理器，控制住数据源里面的链接的关闭和提交-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--4、基于xml配置事务，哪些方法切入事务还要写切入点表达式-->
    <aop:config>
        <!--配置切入点表达式 任意返回值 service下的任意类的任意方法的任意参数-->
        <aop:pointcut id="txPoint" expression="execution(* com.zzw.service.*.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPoint"/>
    </aop:config>

    <!--配置事务增强、事务属性、事务建议
        transaction-manager：指定要配置的事务管理器的id-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <!--配置事务属性-->
        <tx:attributes>
            <tx:method name="*" rollback-for="java.lang.Exception"/>
            <tx:method name="get*" read-only="true"/>
            <tx:method name="insert*" isolation="DEFAULT"/>
        </tx:attributes>
    </tx:advice>
</beans>
```



### SpringMVC配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!--SpringMVC只扫描控制器：禁用默认规则-->
    <context:component-scan base-package="com.zzw.controller" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--扫描静态资源-->
    <mvc:default-servlet-handler/>
    <!--扫描动态资源-->
    <mvc:annotation-driven/>

    <!--配置视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"></property>
        <property name="suffix" value=".jsp"></property>
    </bean>

    <!--配置文件上传解析器，没有包，展示不配-->
<!--    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <property name="defaultEncoding" value="utf-8"></property>
        <property name="maxUploadSize" value="#{1024*1024*20}"></property>
    </bean>-->

</beans>
```



### Mybatis配置文件

好像没啥重要可配的，要点话看之前的就行了



### dbconfig配置文件

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC
jdbc.username=root
jdbc.password=123456
jdbc.maxPoolSize=20
jdbc.minPoolSize=3
```



### log4j配置文件

```properties
# For JBoss: Avoid to setup Log4J outside $JBOSS_HOME/server/default/deploy/log4j.xml!
# For all other servers: Comment out the Log4J listener in web.xml to activate Log4J.
#log4j.rootLogger=INFO, stdout, logfile
log4j.rootLogger=Debug, stdout

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m%n

log4j.appender.A1=org.apache.log4j.RollingFileAppender  
log4j.appender.A1.Encoding=UTF-8  
log4j.appender.A1.File=all.log 

#log4j.appender.logfile=org.apache.log4j.RollingFileAppender
#log4j.appender.logfile.File=system.log
#log4j.appender.logfile.MaxFileSize=512KB
# Keep three backup files.
#log4j.appender.logfile.MaxBackupIndex=3
# Pattern to output: date priority [category] - message
#log4j.appender.logfile.layout=org.apache.log4j.PatternLayout
#log4j.appender.logfile.layout.ConversionPattern=%d %p [%c] - %m%n
```

### 目录结构

![image-20210309174311878](SSM整合.assets/image-20210309174311878.png)



# Mybatis整合Spring

需要的包

```
c3p0-0.9.5.2.jar
mchange-commons-java-0.2.11.jar
```

Spring容器中配置mybatis

```xml
    <!--2、配置mybatis操作数据库-->
    <bean class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--指定配置文件位置-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <!--指定数据源-->
        <property name="dataSource" ref="dataSource"/>
        <!--指定xml映射文件的位置-->
        <property name="mapperLocations" value="classpath:mapper/*.xml"/>
    </bean>
<!--    将每一个dao接口实现类加入到ioc容器中-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.zzw.mapper"/>
    </bean>
```





# 非必要配置文件

### MBG

记得将这个配置文件放置在项目路径下

```xml
<!DOCTYPE generatorConfiguration PUBLIC
        "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <!--配置连接到哪个数据库-->
    <context id="simple" targetRuntime="MyBatis3Simple">
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/mbg?serverTimezone=UTC"
                        userId="root"
                        password="123456"/>

        <!--Pojo生成器
            targetPackage：生成的pojo放在哪个包
            targetProject：放在哪个工程下-->
        <javaModelGenerator targetPackage="com.zzw.entity" targetProject=".\src"/>

        <!--sql映射文件生成器
            targetPackage：指定xml生成的地方-->
        <sqlMapGenerator targetPackage="mapper" targetProject=".\resources"/>

        <!--dao接口生成器-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.zzw.mapper" targetProject=".\src"/>

        <!--指定要逆向生成哪个数据表
            tableName：数据库表名
            domainObjectName：生成的Pojo的类名
            不指定的话默认就是数据库表名-->
        <table tableName="dept" domainObjectName="Dept"/>
        <table tableName="employee" domainObjectName="Employee"/>
        <table tableName="salgrade" domainObjectName="SalGrade"/>
        <table tableName="cat" domainObjectName="Cat"/>
    </context>
</generatorConfiguration>
```

启动生成

```java
public class test {
    public static void main(String args[]) throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("mbg.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        System.out.println(new Date() + "成功生成！");
    }
}
```





# 配置过程中的一些注意事项

## 数据库连接池

在Spring中配置数据库连接池时，${username}如果 key 是username，name 默认spring框架调用的当前操作系统的账号 解决方案：可以统一给key加一个前缀

```xml
<!--1、配置数据源-->
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
    <property name="driverClass" value="${jdbc.driver}"/>
    <property name="jdbcUrl" value="${jdbc.url}"/>
    <property name="user" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
    <property name="maxPoolSize" value="${jdbc.maxPoolSize}"/>
    <property name="minPoolSize" value="${jdbc.minPoolSize}"/>
</bean>
```


## 连接池jar包

之前雷神给的那个包不能用了，应该是版本不兼容，所以用 以下两个包配合使用

```
c3p0-0.9.5.2.jar
mchange-commons-java-0.2.11.jar
```

















