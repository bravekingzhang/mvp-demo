# MVP-DEMO
一个属于你自己的关于推荐最近电影的app

###电影爬虫采用python写的
* 地址 https://github.com/bravekingzhang/moveSpider
  单线程而已，初步实现，后期打算做成多线程，加速爬取效率。
  爬取的结果存了两种，html，json，json用户我们的移动app

###web服务器
  我的测试机，一个php文件，就两行代码而已，of参数html表示输出网页，json表示输出json，太加单，就不建一个项目咯，后期分页处理考虑加上。
  
### app侧

* 全部使用原生控件编写，拒绝使用自定义组件，google已经对UI做的很好了，何必装逼，页面全部采用fragment，activity只作为容器装fragment而已。

* 已经采用mvp模式重构，欢迎大家劈砖

* 引入了rxandroid  ，响应式编程，绝对是未来的趋势

* 引入了rxbinding ，为嘛要这玩意，看看这里你就了解了https://medium.com/@diolor/improving-ux-with-rxjava-4440a13b157f#.prs4xw4jh


#无耻的秀一下
![image](https://github.com/bravekingzhang/yours/blob/master/screenshot/device-2015-12-27-160354.png)
![image](https://github.com/bravekingzhang/yours/blob/master/screenshot/device-2015-12-27-160433.png)

#todo

* **【已经支持】将RXandriod引入，从而使这个app的整个架构称得上是全球最流行的架构，不管他适不适合，反正麻雀虽小，五脏得俱全吧~

*  【已经引入】使用realm（好像最近口碑确实不错）作为我们的数据库缓存，这样，即便没有网络，我们也能嗨一番，不是吗？

*  丰富我们的python爬虫，准备采用多线程爬取，以及解决爬取内容太过单一的问题，爬虫项目在这里https://github.com/bravekingzhang/moveSpider

*  重构我们的web接口，使得其支持分页加载，现在一次性将所有数据返回，确实挺拽的。

*  【已经支持】准备引入 butterknife  https://github.com/JakeWharton/butterknife~

*  准备引入retrofit  https://github.com/square/retrofit

*  【已经引入】RxBinding也准备引入，防重复提交是就一行代码，爽

*  准备支持一个新的条目，小说列表，因此需要我们也需要重构我们的爬虫，目标起点网。个人比较喜欢玄幻系列的

*  准备支持电影搜索功能。



