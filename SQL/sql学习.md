# insert

```sql
INSERT INTO <表名> (字段1, 字段2, ...) VALUES (值1, 值2, ...);
```

# delete

```sql
DELETE FROM <表名> WHERE ...;
```

# update

```sql
UPDATE <表名> SET 字段1=值1, 字段2=值2, ... WHERE ...;
```

# select

```sql
SELECT * FROM <表名>
```



> 假数据



我们先准备好了一个`students`表和一个`classes`表，它们的结构和数据如下：

`students`表存储了学生信息：

| id   | class_id | name | gender | score |
| :--- | :------- | :--- | :----- | :---- |
| 1    | 1        | 小明 | M      | 90    |
| 2    | 1        | 小红 | F      | 95    |
| 3    | 1        | 小军 | M      | 88    |
| 4    | 1        | 小米 | F      | 73    |
| 5    | 2        | 小白 | F      | 81    |
| 6    | 2        | 小兵 | M      | 55    |
| 7    | 2        | 小林 | M      | 85    |
| 8    | 3        | 小新 | F      | 91    |
| 9    | 3        | 小王 | M      | 89    |
| 10   | 3        | 小丽 | F      | 85    |

`classes`表存储了班级信息：

| id   | name |
| :--- | :--- |
| 1    | 一班 |
| 2    | 二班 |
| 3    | 三班 |
| 4    | 四班 |



## 条件查询

SELECT语句可以通过`WHERE`条件来设定查询条件，查询结果是满足查询条件的记录。例如，要指定条件“分数在80分或以上的学生”，写成`WHERE`条件就是`SELECT * FROM students WHERE score >= 80`。

其中，`WHERE`关键字后面的`score >= 80`就是条件。`score`是列名，该列存储了学生的成绩，因此，`score >= 80`就筛选出了指定条件的记录：

```sql
SELECT * FROM students WHERE score >= 80;
```

因此，条件查询的语法就是：

```sql
SELECT * FROM <表名> WHERE <条件表达式>
```

**第一种** 条件表达式可以用`<条件1> AND <条件2>`表达满足条件1并且满足条件2。例如，符合条件“分数在80分或以上”，并且还符合条件“男生”，把这两个条件写出来：

- 条件1：根据score列的数据判断：`score >= 80`；
- 条件2：根据gender列的数据判断：`gender = 'M'`，注意`gender`列存储的是字符串，需要用单引号括起来。

就可以写出`WHERE`条件：`score >= 80 AND gender = 'M'`：

```sql
SELECT * FROM students WHERE score >= 80 AND gender = 'M';
```

**第二种**条件是`<条件1> OR <条件2>`，表示满足条件1或者满足条件2。例如，把上述`AND`查询的两个条件改为`OR`，查询结果就是“分数在80分或以上”或者“男生”，满足任意之一的条件即选出该记录：

```sql
SELECT * FROM students WHERE score >= 80 OR gender = 'M';
```

**第三种**条件是`NOT <条件>`，表示“不符合该条件”的记录。例如，写一个“不是2班的学生”这个条件，可以先写出“是2班的学生”：`class_id = 2`，再加上`NOT`：`NOT class_id = 2`：

```sql
SELECT * FROM students WHERE NOT class_id = 2;
```

**要组合三个或者更多的条件**，就需要用小括号`()`表示如何进行条件运算。例如，编写一个复杂的条件：分数在80以下或者90以上，并且是男生：

```sql
SELECT * FROM students WHERE (score < 80 OR score > 90) AND gender = 'M';
```

如果不加括号，条件运算按照`NOT`、`AND`、`OR`的优先级进行，即`NOT`优先级最高，其次是`AND`，最后是`OR`。加上括号可以改变优先级。

## 投影查询

如果我们只希望返回某些列的数据，而不是所有列的数据，我们可以用`SELECT 列1, 列2, 列3 FROM ...`，让结果集仅包含指定列。这种操作称为**投影查询**。

例如，从`students`表中返回`id`、`score`和`name`这三列：

```sql
SELECT id, score, name FROM students;
```

这样返回的结果集就**只包含了我们指定的列**，并且，结果集的列的顺序和原表可以不一样。

使用`SELECT 列1, 列2, 列3 FROM ...`时，还可以给每一列起个别名，这样，结果集的列名就可以与原表的列名不同。它的语法是`SELECT 列1 别名1, 列2 别名2, 列3 别名3 FROM ...`。



例如，以下`SELECT`语句将列名`score`**重命名**为`points`，而`id`和`name`列名保持不变：

```sql
SELECT id, score points, name FROM students;
```

投影查询同样可以接`WHERE`条件，实现复杂的查询：

```sql
SELECT id, score points, name FROM students WHERE gender = 'M';
```

## 排序

可以加上`ORDER BY`子句。例如按照成绩从低到高进行排序：

```sql
-- 按score从低到高
SELECT id, name, gender, score FROM students ORDER BY score;
```

如果要反过来，按照成绩从高到底排序，我们可以加上`DESC`表示“倒序”：

```sql
SELECT id, name, gender, score FROM students ORDER BY score DESC;
```



如果`score`列有相同的数据，要进一步排序，可以继续添加列名。例如，使用`ORDER BY score DESC, gender`表示先按`score`列倒序，如果有相同分数的，再按`gender`列排序：

```sql

-- 按score, gender排序:
SELECT id, name, gender, score FROM students ORDER BY score DESC, gender;
```



默认的排序规则是`ASC`：“升序”，即从小到大。`ASC`可以省略，即`ORDER BY score ASC`和`ORDER BY score`效果一样。

如果有`WHERE`子句，那么`ORDER BY`子句要放到`WHERE`子句后面。例如，查询一班的学生成绩，并按照倒序排序：

```sql

-- 带WHERE条件的ORDER BY:
SELECT id, name, gender, score
FROM students
WHERE class_id = 1
ORDER BY score DESC;
```

## 分页查询

**第一种**

```sql
select * from students limit 0,5;
```

从第0条记录  向下查询5条记录



**第二种**

```sql
SELECT id, name, gender, score
FROM students
LIMIT 5 OFFSET 0;
```

## 聚合查询

### 一般

对于统计***总数***、***平均数***这类计算，SQL提供了专门的*聚合函数*，使用聚合函数进行查询，就是聚合查询，它可以快速获得结果。

```sql

-- 使用聚合查询:
SELECT COUNT(*) FROM students;
```

`COUNT(*)`表示查询所有列的行数，要注意聚合的计算结果虽然是一个数字，但查询的结果仍然是一个二维表，只是这个二维表只有一行一列，并且列名是`COUNT(*)`。

通常，使用聚合查询时，我们应该给列名设置一个别名，便于处理结果：

```sql
-- 使用聚合查询并设置结果集的列名为num:
SELECT COUNT(*) num FROM students;
```

`COUNT(*)`和`COUNT(id)`实际上是一样的效果。另外注意，聚合查询同样可以使用`WHERE`条件，因此我们可以方便地统计出有多少男生、多少女生、多少80分以上的学生等：

```sql
-- 使用聚合查询并设置WHERE条件:
SELECT COUNT(*) boys FROM students WHERE gender = 'M';
```



除了`COUNT()`函数外，SQL还提供了如下聚合函数：

| 函数 | 说明                                   |
| :--- | :------------------------------------- |
| SUM  | 计算某一列的合计值，该列必须为数值类型 |
| AVG  | 计算某一列的平均值，该列必须为数值类型 |
| MAX  | 计算某一列的最大值                     |
| MIN  | 计算某一列的最小值                     |

注意，`MAX()`和`MIN()`函数并不限于数值类型。如果是字符类型，`MAX()`和`MIN()`会返回排序最后和排序最前的字符。

要统计男生的平均成绩，我们用下面的聚合查询：

```sql

-- 使用聚合查询计算男生平均成绩:
SELECT AVG(score) average FROM students WHERE gender = 'M';
```

要特别注意：如果聚合查询的`WHERE`条件没有匹配到任何行，`COUNT()`会返回0，而`SUM()`、`AVG()`、`MAX()`和`MIN()`会返回`NULL`：

```sql
-- WHERE条件gender = 'X'匹配不到任何行:
SELECT AVG(score) average FROM students WHERE gender = 'X';
```

### 分组

对于聚合查询，SQL还提供了“分组聚合”的功能。我们观察下面的聚合查询：

```sql

-- 按class_id分组:
SELECT COUNT(*) num FROM students GROUP BY class_id;
```

执行这个查询，`COUNT()`的结果不再是一个，而是3个，这是因为，`GROUP BY`子句指定了按`class_id`分组，因此，执行该`SELECT`语句时，会把`class_id`相同的列先分组，再分别计算，因此，得到了3行结果。

但是这3行结果分别是哪三个班级的，不好看出来，所以我们可以把`class_id`列也放入结果集中：

```sql
-- 按class_id分组:
SELECT class_id, COUNT(*) num FROM students GROUP BY class_id;
```



可以使用多个列进行分组。例如，我们想统计各班的男生和女生人数：

```sql

-- 按class_id, gender分组:
SELECT class_id, gender, COUNT(*) num FROM students GROUP BY class_id, gender;

```

```sql
-- 查出每个班级的平均分，结果集应当有3条记录:
select avg(score) ava from students group by class_id;
```

```sql
-- 使用一条SELECT查询查出每个班级男生和女生的平均分：
select avg(score) ava,class_id from students where gender = 'F' group by class_id;
```



## 多表查询

例如，同时从`students`表和`classes`表的“乘积”，即查询数据，可以这么写：

```sql
SELECT * FROM students, classes;
```

这种一次查询两个表的数据，查询的结果也是一个二维表，它是`students`表和`classes`表的“乘积”，即`students`表的每一行与`classes`表的每一行都两两拼在一起返回。结果集的列数是`students`表和`classes`表的列数之和，行数是`students`表和`classes`表的行数之积。

这种多表查询又称笛卡尔查询，使用笛卡尔查询时要非常小心，由于结果集是目标表的行数乘积，对两个各自有100行记录的表进行笛卡尔查询将返回1万条记录，对两个各自有1万行记录的表进行笛卡尔查询将返回1亿条记录。

你可能还注意到了，上述查询的结果集有两列`id`和两列`name`，两列`id`是因为其中一列是`students`表的`id`，而另一列是`classes`表的`id`，但是在结果集中，不好区分。



> 添加WHERE条件的查询

```sql
SELECT
    s.id sid,
    s.name,
    s.gender,
    s.score,
    c.id cid,
    c.name cname
FROM students s, classes c
WHERE s.class_id = c.id;
```

当学生的班级id和 班级的id一致时，则列出该行（**条件查询**）

| sid  | name | gender | score | cid  | cname |
| :--- | :--- | :----- | :---- | :--- | :---- |
| 1    | 小明 | M      | 90    | 1    | 一班  |
| 2    | 小红 | F      | 95    | 1    | 一班  |
| 3    | 小军 | M      | 88    | 1    | 一班  |
| 4    | 小米 | F      | 73    | 1    | 一班  |
| 5    | 小白 | F      | 81    | 2    | 二班  |
| 6    | 小兵 | M      | 55    | 2    | 二班  |
| 7    | 小林 | M      | 85    | 2    | 二班  |
| 8    | 小新 | F      | 91    | 3    | 三班  |
| 9    | 小王 | M      | 89    | 3    | 三班  |
| 10   | 小丽 | F      | 88    | 3    | 三班  |

## 连接查询

连接查询是另一种类型的多表查询。连接查询对多个表进行JOIN运算，简单地说，就是先确定一个主表作为结果集，然后，把其他表的行有选择性地“连接”在主表结果集上。

例如，我们想要选出`students`表的所有学生信息，可以用一条简单的SELECT语句完成：

```sql
SELECT s.id, s.name, s.class_id, s.gender, s.score FROM students s;

```

但是，假设我们希望结果集同时包含所在班级的名称，上面的结果集只有`class_id`列，缺少对应班级的`name`列。

现在问题来了，存放班级名称的`name`列存储在`classes`表中，只有根据`students`表的`class_id`，找到`classes`表对应的行，再取出`name`列，就可以获得班级名称。

这时，连接查询就派上了用场。我们先使用最常用的一种内连接——**INNER JOIN**来实现：

```sqlite
-- 选出所有学生，同时返回班级名称
SELECT s.id, s.name, s.class_id, c.name class_name, s.gender, s.score
FROM students s
INNER JOIN classes c
ON s.class_id = c.id;

```

| id   | name | class_id | class_name | gender | score |
| :--- | :--- | :------- | :--------- | :----- | :---- |
| 1    | 小明 | 1        | 一班       | M      | 90    |
| 2    | 小红 | 1        | 一班       | F      | 95    |
| 3    | 小军 | 1        | 一班       | M      | 88    |
| 4    | 小米 | 1        | 一班       | F      | 73    |
| 5    | 小白 | 2        | 二班       | F      | 81    |
| 6    | 小兵 | 2        | 二班       | M      | 55    |
| 7    | 小林 | 2        | 二班       | M      | 85    |
| 8    | 小新 | 3        | 三班       | F      | 91    |
| 9    | 小王 | 3        | 三班       | M      | 89    |
| 10   | 小丽 | 3        | 三班       | F      | 88    |

> **注意**INNER JOIN查询的写法是：

1. 先确定主表，仍然使用`FROM <表1>`的语法；
2. 再确定需要连接的表，使用`INNER JOIN <表2>`的语法；
3. 然后确定连接条件，使用`ON <条件...>`，这里的条件是`s.class_id = c.id`，表示`students`表的`class_id`列与`classes`表的`id`列相同的行需要连接；
4. 可选：加上`WHERE`子句、`ORDER BY`等子句。

使用别名不是必须的，但可以更好地简化查询语句。

### 内连接

那什么是**内连接**（INNER JOIN）呢？先别着急，有内连接（INNER JOIN）就有**外连接**（OUTER JOIN）。我们把内连接查询改成外连接查询，看看效果：

```sql
-- 使用OUTER JOIN
SELECT s.id, s.name, s.class_id, c.name class_name, s.gender, s.score
FROM students s
RIGHT OUTER JOIN classes c
ON s.class_id = c.id;
```

| id   | name | class_id | class_name | gender | score |
| :--- | :--- | :------- | :--------- | :----- | :---- |
| 1    | 小明 | 1        | 一班       | M      | 90    |
| 2    | 小红 | 1        | 一班       | F      | 95    |
| 3    | 小军 | 1        | 一班       | M      | 88    |
| 4    | 小米 | 1        | 一班       | F      | 73    |
| 5    | 小白 | 2        | 二班       | F      | 81    |
| 6    | 小兵 | 2        | 二班       | M      | 55    |
| 7    | 小林 | 2        | 二班       | M      | 85    |
| 8    | 小新 | 3        | 三班       | F      | 91    |
| 9    | 小王 | 3        | 三班       | M      | 89    |
| 10   | 小丽 | 3        | 三班       | F      | 88    |
| NULL | NULL | NULL     | 四班       | NULL   | NULL  |

执行上述RIGHT OUTER JOIN可以看到，和INNER JOIN相比，RIGHT OUTER JOIN多了一行，多出来的一行是“四班”，但是，学生相关的列如`name`、`gender`、`score`都为`NULL`。

这也容易理解，因为根据`ON`条件`s.class_id = c.id`，`classes`表的id=4的行正是“四班”，但是，`students`表中并不存在class_id=4的行。



### 外连接

有RIGHT OUTER JOIN，就有LEFT OUTER JOIN，以及FULL OUTER JOIN。它们的区别是：

INNER JOIN只返回同时存在于两张表的行数据，由于`students`表的`class_id`包含1，2，3，`classes`表的`id`包含1，2，3，4，所以，INNER JOIN根据条件`s.class_id = c.id`返回的结果集仅包含1，2，3。

RIGHT OUTER JOIN返回右表都存在的行。如果某一行仅在右表存在，那么结果集就会以`NULL`填充剩下的字段。

LEFT OUTER JOIN则返回左表都存在的行。如果我们给students表增加一行，并添加class_id=5，由于classes表并不存在id=5的行，所以，LEFT OUTER JOIN的结果会增加一行，对应的`class_name`是`NULL`：

```sql
-- 先增加一列class_id=5:
INSERT INTO students (class_id, name, gender, score) values (5, '新生', 'M', 88);
-- 使用LEFT OUTER JOIN

SELECT s.id, s.name, s.class_id, c.name class_name, s.gender, s.score
FROM students s
LEFT OUTER JOIN classes c
ON s.class_id = c.id;
```

| id   | name | class_id | class_name | gender | score |
| :--- | :--- | :------- | :--------- | :----- | :---- |
| 1    | 小明 | 1        | 一班       | M      | 90    |
| 2    | 小红 | 1        | 一班       | F      | 95    |
| 3    | 小军 | 1        | 一班       | M      | 88    |
| 4    | 小米 | 1        | 一班       | F      | 73    |
| 5    | 小白 | 2        | 二班       | F      | 81    |
| 6    | 小兵 | 2        | 二班       | M      | 55    |
| 7    | 小林 | 2        | 二班       | M      | 85    |
| 8    | 小新 | 3        | 三班       | F      | 91    |
| 9    | 小王 | 3        | 三班       | M      | 89    |
| 10   | 小丽 | 3        | 三班       | F      | 88    |
| 11   | 新生 | 5        | NULL       | M      | 88    |

最后，我们使用FULL OUTER JOIN，它会把两张表的所有记录全部选择出来，并且，自动把对方不存在的列填充为NULL：

```sql
-- 使用FULL OUTER JOIN
SELECT s.id, s.name, s.class_id, c.name class_name, s.gender, s.score
FROM students s
FULL OUTER JOIN classes c
ON s.class_id = c.id;

```

| id   | name | class_id | class_name | gender | score |
| :--- | :--- | :------- | :--------- | :----- | :---- |
| 1    | 小明 | 1        | 一班       | M      | 90    |
| 2    | 小红 | 1        | 一班       | F      | 95    |
| 3    | 小军 | 1        | 一班       | M      | 88    |
| 4    | 小米 | 1        | 一班       | F      | 73    |
| 5    | 小白 | 2        | 二班       | F      | 81    |
| 6    | 小兵 | 2        | 二班       | M      | 55    |
| 7    | 小林 | 2        | 二班       | M      | 85    |
| 8    | 小新 | 3        | 三班       | F      | 91    |
| 9    | 小王 | 3        | 三班       | M      | 89    |
| 10   | 小丽 | 3        | 三班       | F      | 88    |
| 11   | 新生 | 5        | NULL       | M      | 88    |
| NULL | NULL | NULL     | 四班       | NULL   | NULL  |

对于这么多种JOIN查询，到底什么使用应该用哪种呢？其实我们用图来表示结果集就一目了然了。

假设查询语句是：

```sql
SELECT ... FROM tableA ??? JOIN tableB ON tableA.column1 = tableB.column2;
```

我们把tableA看作左表，把tableB看成右表，那么INNER JOIN是选出两张表都存在的记录：

![](sql学习.assets/1.png)

LEFT OUTER JOIN是选出左表存在的记录：

![](sql学习.assets/2.png)

RIGHT OUTER JOIN是选出右表存在的记录：

![](sql学习.assets/3.png)

FULL OUTER JOIN则是选出左右表都存在的记录：

![](sql学习.assets/4.png)