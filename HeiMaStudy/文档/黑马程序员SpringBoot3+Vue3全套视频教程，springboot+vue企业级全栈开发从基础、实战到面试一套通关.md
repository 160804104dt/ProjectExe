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

**参数校验失败异常处理，定义一个全局异常处理器**

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

---

**登录认证**

要求：

承载业务数据，减少后续请求查询数据库的次数

防篡改，保证信息的合法性和有效性

---

**JWT令牌**

- 全称：json web token

- 定义了一种简洁的，自包含的格式，用于通信双方以json的数据格式安全的传输信息

- 组成：

​		第一部分：Header（头），记录令牌类型，签名算法等，例如{"alg":"HS256","type":"JWT"}

​		第二部分：Payload（有效载荷），携带一些自定义信息，默认信息等，例如{"id":"1","username":"Tom"}，不要存放私密数据

​		第三部分：Signature（签名），防止Token被篡改，确保安全性，将header，payload，并加入指定密钥，通过指定签名算法计算而来

通过Base64编码方式将字符串编码成字符串

因为所有请求基本上都需要认证，可以配置一个拦截器，需要定义一个类实现HandlerInterceptor接口，并且重写preHandle方法，在preHandle方法中对token进行验证，如果通过，返回true，如果验证没有通过，返回false，将状态码设置成401。然后写一个web配置类，实现WebMvcConfigurer接口，重写addInterceptors方法，将刚刚的拦截器配置进去，这个地方要注意，登录和注册的接口不应该拦截，要放行

```java
//配置拦截器
@Component
public class LoginInterceptor  implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        try {
            //验证token
            Map<String, Object> claims = JwtUtil.parseToken(token);
            return true;
        } catch (Exception e) {
            //不放行
            response.setStatus(401);
            return false;
        }
    }
}

//将拦截器配置到webConfig中
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录和注册不拦截
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login","/user/register");
    }

}
```

---

**获取用户详细信息**

ThreadLocal提供线程局部变量

用来存储数据：set，get

使用ThreadLocal存储的数据是线程安全的

在登录后在拦截器里将用户名等信息存储到ThreadLocal里，可以供其他请求一起使用

用完要调用remove方法释放，不然可能会发生内存泄漏

---

**更新用户基本信息**
