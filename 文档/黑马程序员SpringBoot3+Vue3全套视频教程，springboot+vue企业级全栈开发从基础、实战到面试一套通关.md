视频链接：

https://www.bilibili.com/list/watchlater?oid=577871341&bvid=BV14z4y1N7pg&spm_id_from=333.1007.top_right_bar_window_view_later.content.click

前置条件：jdk17，idea2021+，maven3.5+，vscode

## 一、基础篇

### 1.springboot入门：

1. 创建maven工程
2. 导入spring-boot-starter-web起步依赖
3. 编写controller
4. 提供启动类

<p style="color:red">注：springboot3.0以上只能在jdk17以后版本使用</p>

### 2.整合mybatis

（1）在pom中添加mybatis依赖

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>
```

（2）添加mysql依赖

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>
```

（3）在配置文件中添加配置

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test
    username: root
    password: dingtao19960923
```

（4）添加mapper接口，要加上注解@Mapper

（5）添加Service接口

（6）添加ServiceImpl类，要添加@Service注解

（7）添加实体类，对应数据库的相应表结构

（8）添加controller



整合过程中出现下面的错误：

A component required a bean of type 'com.itheima.springbootmybatis.mapper.UserMapper' that could not be found.

mybatis和springboot版本不适配，将mybatis版本提高到3.0.3

---

### 3.Bean管理

- Bean扫描
- Bean注册
- 注册条件

**Bean扫描**

spring boot项目在启动的时候，会扫描启动类所在的包及其子包下面的bean对象，因为启动类的注解@SpringBootApplication组合了@ComponentScan这个注解，如果要扫描这个包外的Bean对象，需要用到@ComponentScan(basePackages = "包名")

---

**Bean注册**

| 注解        | 说明                 | 位置                                            |
| ----------- | -------------------- | ----------------------------------------------- |
| @Component  | 声明bean的基础注解   | 不属于下面的三类时，用此注解                    |
| @Controller | @Component的衍生注解 | 标注在controller上                              |
| @Service    | @Component的衍生注解 | 标注在业务实现类上                              |
| @Repository | @Component的衍生注解 | 标注在数据访问类上，由于和mybatis的整合，用的少 |

如果要注册的bean对象来自第三方组件（不是自定义的），是无法使用@Component以及衍生注解的，spring提供了@Bean和@Import注解解决这个问题

（1）@Bean方法

新建一个包config，新建一个CommonConfig类，CommonConfig要加上@Configuration注解，写一个方法，返回类型是需要注入的类，然后在这个方法上添加@Bean注解即可，Bean对象的默认名字就是方法名，如果需要自己定义，@Bean("bean名称")

```java
@Configuration
public class CommonConfig {

    @Bean
    public FileTestUtil fileTestUtil() {
        return new FileTestUtil();
    }

    /**
     * 如果方法内部需要使用ioc容器中已经存在的bean对象，只需要在方法上声明即可，spring会自动注入
     * @param fileTestUtil
     * @return
     */
    @Bean("fff")
    public CoreTestConstants coreTestConstants(FileTestUtil fileTestUtil) {
        System.out.println(fileTestUtil);
        return new CoreTestConstants();
    }
}
```



（2）@Import方法

@Import(类名.class)，如果有多个需要扫描的类，可以自定义一个类实现ImportSelector接口，重写selectImports方法，在里面将需要扫描的类名放进去

```java
public class CommonImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.itheima.config.CommonConfig"};
    }
}
```

**注册条件**

springboot提供了设置注册生效条件的注解@Conditional

| 注解                      | 说明                                     |
| ------------------------- | ---------------------------------------- |
| @ConditionalOnProperty    | 配置文件存在对应的属性，才声明该bean     |
| @ConditionalOnMissingBean | 当不存在当前类型的bean时，才声明该bean   |
| @ConditionalOnClass       | 当前环境存在指定的这个类时，才声明该bean |

### 4.自动配置原理

遵循约定大于配置的原则，在boot程序启动后，起步依赖中的一些bean对象会自动注入到ioc容器中

---

## 二、实战篇

**使用spring validation，对注册接口的参数合法性进行校验**

（1）引入spring validation依赖

（2）在参数前面添加@Pattern注解

（3）在controller类上添加@Validation注解

```java
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    //用户名和密码在5-16位
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String userName, @Pattern(regexp = "^\\S{5,16}$")String password) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            userService.register(userName,password);
            return Result.success();
        }
        else {
            return Result.error("用户名已被占用");
        }
    }
}
//pattern不符合的时候，会抛出一个异常
```

参数校验失败异常处理，定义一个全局异常处理器

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())?e.getMessage():"操作失败");
    }
}

//@RestControllerAdvice将全局处理应用程序中所有REST控制器的异常。
//@ExceptionHandler(Exception.class)扫描到所有异常类型
```

