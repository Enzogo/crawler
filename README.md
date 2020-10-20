# crawler
爬虫与ES数据分析的一个样例实现

项目目标:
  - 爬取sina的手机站数据
  - 使用数据困存储并进行数据分析
  - 随着数据库的增长，迁移到ES
  - 做一个简单到"新闻搜索引擎""
    
选择新闻网站的原因:

新闻类的数据 反爬取措施不严格 并且新浪是UTF-8编码

##### 搭建开发环境 配置CircleCI自动化代码检查


```
        1
        
    2       3    
    
4       5        6
DFS 深度优先遍历  1->2->4->5->6->3
BFS 广度优先遍历  1->2->3->4->5->6
```

链接池

从池子中拿到一个链接 判断是否处理过？ 是？返回上一步 否？判断是我们想要的吗？是？处理，并把新得到的
链接放入连接池 判断是否是新闻页面 是？存储 否？返回第一步？ 否？ 返回第一步

注意：添加在ArrayList中的链接后要清除 否则会只处理第一次添加进未处理链接池中的链接，linkPool.remove();

ArrayList从尾部删除更有效率

Removes the element at the specified position in this list (optional
operation).  Shifts any subsequent elements to the left (subtracts one
from their indices).  Returns the element that was removed from the
list.