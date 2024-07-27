# 一.Stream流操作

流stream是Java8添加的，以一种声名式方法处理数据集合，是对集合（collection）对象功能的增强，与lambda表达式结合，可以提高编程效率，代码的可读性

操作符：中间操作符，终端操作符

### （1）中间操作符

通常对于stream的中间操作，可以视为是源的查询，并且是懒惰式的设计，对于源数据进行的计算只有在需要时才会被执行，与数据库中视图的原理相似；

stream流的强大之处便是在于提供了丰富的中间操作，相比集合或数组这类容器，极大的简化源数据的计算复杂度

一个流可以跟随零个或多个中间操作，其主要目的是打开流，作出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用

中间操作都是惰性化的，仅仅调用这类方法，并没有真正开始流的遍历，真正的遍历需要等到终端操作；

常见的中间操作符：

**filter**

设置条件过滤元素

```java
List strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
List filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
```

**map**

接受一个函数作为参数，这个函数会被应用到每个元素上，并将其映射成一个新的元素

```java
List strings = Arrays.asList("abc", "abc", "bc", "efg", "abcd","jkl", "jkl");
List mapped = strings.stream().map(str->str+"-itcast").collect(Collectors.toList());
```

**distinct**

返回一个元素不重复的流（根据流所生成元素的hashcode和equals方法实现）

```java
List numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
numbers.stream().filter(i -> i % 2 == 0).distinct().forEach(System.out::println);
```

**sorted**

返回排序后的流

```java
List strings1 = Arrays.asList(“abc”, “abd”, “aba”, “efg”, “abcd”,“jkl”, “jkl”);
List sorted1 = strings1.stream().sorted().collect(Collectors.toList());
```

**limit**

返回一个不超过给定长度的流

```java
List strings = Arrays.asList(“abc”, “abc”, “bc”, “efg”, “abcd”,“jkl”, “jkl”);
List limited = strings.stream().limit(3).collect(Collectors.toList());
```

**skip**

返回一个扔掉前n个元素的流

```java
List strings = Arrays.asList(“abc”, “abc”, “bc”, “efg”, “abcd”,“jkl”, “jkl”);
List skiped = strings.stream().skip(3).collect(Collectors.toList());
```

**flatMap**



### （2）终端操作符

Stream执行完终端操作后，无法再进行其他动作，否则会报异常，一个流有且只能有一个终端操作。

终端操作的执行，才会真正开始流的遍历。

**collect**

收集器，将流转换为其他形式

```java
List strings = Arrays.asList(“cv”, “abd”, “aba”, “efg”, “abcd”,“jkl”, “jkl”);
Set set = strings.stream().collect(Collectors.toSet());
List list = strings.stream().collect(Collectors.toList());
Map<String, String> map = strings.stream().collect(Collectors.toMap(v ->v.concat("_name"), v1 -> v1, (v1, v2) -> v1));
```

**forEach**

遍历

```java
List strings = Arrays.asList(“cv”, “abd”, “aba”, “efg”, “abcd”,“jkl”, “jkl”);
strings.stream().forEach(s -> out.println(s));
```

**findFirst**

返回第一个元素

```java
List strings = Arrays.asList(“cv”, “abd”, “aba”, “efg”, “abcd”,“jkl”, “jkl”);
Optional first = strings.stream().findFirst();
```

findAny

返回当前流的任意元素

```java
List strings = Arrays.asList(“cv”, “abd”, “aba”, “efg”, “abcd”,“jkl”, “jkl”);
Optional any = strings.stream().findAny();
```

**count**

总数

```java
List strings = Arrays.asList(“cv”, “abd”, “aba”, “efg”, “abcd”,“jkl”, “jkl”);
long count = strings.stream().count();
```

**sum**

求和

```java
int sum = userList.stream().mapToInt(User::getId).sum();
```

**max** 

最大值

```java
int max = userList.stream().max(Comparator.comparingInt(User::getId)).get().getId();
```

**min**

最小值

```java
int min = userList.stream().min(Comparator.comparingInt(User::getId)).get().getId();
```

**anyMatch**

匹配是否至少一个元素匹配，返回boolean

```java
boolean anyMatcher = kouhiMLst.stream().anyMatch(x->x.getHoubetsuNo().equals("22"));
```

**allMatch**

检查是否所有元素都匹配，返回boolean

```java
boolean allMatcher = kouhiMLst.stream().anyMatch(x->x.getHoubetsuNo().equals("22"));
```

**noneMatch**

检查是否没有元素匹配

```java
List strings = Arrays.asList(“abc”, “abd”, “aba”, “efg”, “abcd”,“jkl”, “jkl”);
boolean b = strings.stream().noneMatch(s -> s == “abc”);
```

**reduce**

```java
```

```java
public class KouhiMDto extends DbDtoBase implements ModelMapperBaseObject {
    private String houbetsuNo = null;
    private String seidoKbn = null;
    private Date startDate = null;
    private Date endDate = null;
    private String kouhiName = null;
    private String kouhiShortName = null;
    private Integer kouhiPriorityOrder = null;
    private String tekiyouKbn = null;
    private String tsukigendogakuConfigKbn = null;
    private String futanWariaiKbn = null;
    private BigDecimal futanWariai = null;
    private String patientFutanGendogakuKbn = null;
    private String taishouHokenKbn = null;
    private String taishouHokenGendogakuKbn = null;
    private Boolean isShotokuKbnTokkijikouHitsuyou = null;
}

/*中间操作符*/
List<KouhiMDto> filter = kouhiMLst.stream().filter(x->x.getHoubetsuNo().compareTo("22")>0).collect(Collectors.toList());
//map 方法需要注意的是返回的类型，不是原本的数据类型
List<String> mappper = kouhiMLst.stream().map(x->x.getHoubetsuNo()+"33").collect(Collectors.toList());

List<KouhiMDto> distincter = kouhiMLst.stream().distinct().collect(Collectors.toList());
//排序，根据法别番号倒序排序
List<KouhiMDto> sorter1 = kouhiMLst.stream().sorted(Comparator.comparing(KouhiMDto::getHoubetsuNo).reversed()).collect(Collectors.toList());

List<KouhiMDto> sorter2 = kouhiMLst.stream().sorted(Comparator.comparing(KouhiMDto::getHoubetsuNo)).collect(Collectors.toList());

List<KouhiMDto> limiter = kouhiMLst.stream().sorted(Comparator.comparing(KouhiMDto::getHoubetsuNo)).limit(4).collect(Collectors.toList());

List<KouhiMDto> skipper = kouhiMLst.stream().sorted(Comparator.comparing(KouhiMDto::getHoubetsuNo)).skip(4).collect(Collectors.toList());

/*终端操作符*/
long count = kouhiMLst.stream().filter(x->x.getHoubetsuNo().compareTo("22")>0).count();

KouhiMDto findFirster = kouhiMLst.stream().sorted(Comparator.comparing(KouhiMDto::getHoubetsuNo)).findFirst().get();

KouhiMDto findAnyer = kouhiMLst.stream().filter(x->x.getHoubetsuNo().compareTo("55")>0).findAny().get();

kouhiMLst.stream().forEach(x->System.out.println(x.getHoubetsuNo()));

String maxer = kouhiMLst.stream().max(Comparator.comparing(KouhiMDto::getHoubetsuNo)).get().getHoubetsuNo();

String miner = kouhiMLst.stream().min(Comparator.comparing(KouhiMDto::getHoubetsuNo)).get().getHoubetsuNo();

boolean anyMatcher = kouhiMLst.stream().anyMatch(x->x.getHoubetsuNo().equals("123"));

boolean allMatcher = kouhiMLst.stream().allMatch(x->x.getHoubetsuNo().equals("22"));

boolean noneMatcher = kouhiMLst.stream().noneMatch(x->x.getHoubetsuNo().equals("123"));

```



# 二、集合

java集合是替换掉定长的数组的一种引用数据类型

**数组和集合的区别：**

1.数组既可以存储基本数据类型，也可以存储引用类型，基础数据类型存储的是值，引用类型存储的是地址值；

集合只能存储引用数据类型，存储基本类型的话会自动装箱变成对象

2.数组的长度是固定的，不能自动增长；集合的长度是可变的，可以根据元素的变化而变化

3.数组只能存储一种数据类型，集合可以存储不同数据类型

![集合框架](E:\work\java集合.gif)



**ArrayList解读：**

```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
    private static final long serialVersionUID = 8683452581122892189L;

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Shared empty array instance used for empty instances.
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer. Any
     * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * will be expanded to DEFAULT_CAPACITY when the first element is added.
     */
    transient Object[] elementData; // non-private to simplify nested class access

    /**
     * The size of the ArrayList (the number of elements it contains).
     *
     * @serial
     */
    private int size;

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param  initialCapacity  the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
}
```

DEFAULT_CAPACITY是默认容量，初始值为10,

如果是new一个arrayList，实际上DEFAULT_CAPACITY还没用到，实际大小还是0，当用到add方法时，会用到DEFAULT_CAPACITY

```java
public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
}

private void ensureCapacityInternal(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
}

private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
}

```

---

# 三、Java注解

---

# 四、常用方法

---

# 五、枚举使用

Java枚举用来定义一组固定的常量，枚举是一个类，可以拥有方法和字段，可以实现接口，但不能继承其他类。

```java
public enum MonthEnum{
    January,
    February,
    March,
    April,
    May,
    June,
    July,
    August,
    September,
    October,
    November,
    December
}
//可以调用ordinal()方法获得默认的排序值
//也可以通过添加字段，构造方法来给枚举设置值
public enum WeekEnum {
    Monday(1,"星期一"),
    Tuesday(2,"星期二"),
    Wendesday(3,"星期三");

    private int code;
    private String description;

    WeekEnum(){

    }

    WeekEnum(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

```

