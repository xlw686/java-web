# SMBMS

#### 介绍
跟着狂神做超市订单管理系统

狂神这个项目在码云上能找到几个，包括我这个所用到的资源文件都是码云上克隆的。但是很多仓库都是一个空的项目，或者只有狂神视频里的部分内容，对于视频未讲到的内容并不涉及，再者就是对业务逻辑并不是那么完善。比如删除订单和删除供应商需要判断是否为未支付订单，和含有未支付订单的供应商，所以我对这个进行了一个完善。到到项目完成为止，这可能是码云收索“SMBMS 狂神”出现最为完善的一个项目。

#### 简明
1. 解决了js导致的乱码问题：
    1. 用记事本打开js文件，然后另存时选择 **带有BOM的UTF-8** ，然后替换为原有js文件
        ![输入图片说明](https://images.gitee.com/uploads/images/2020/1208/100906_56c56aa0_2336382.png "屏幕截图.png")
    2. 清除浏览器缓存（这也很重要）。
    3. 效果：
        ![输入图片说明](https://images.gitee.com/uploads/images/2020/1216/162504_f05cf213_2336382.png "image-20201216091053496.png")
       
    
2. 数据库连接池使用的是阿里的druid。jar依赖

    ```xml
    <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
            <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>1.2.3</version>
            </dependency>
    
            <!-- https://mvnrepository.com/artifact/commons-dbutils/commons-dbutils -->
            <dependency>
                <groupId>commons-dbutils</groupId>
                <artifactId>commons-dbutils</artifactId>
                <version>1.7</version>
            </dependency>
    ```

    

3. 添加数据是对编码（用户编码、供应商编码和订单编码进行了验证是否存在）进行了校验。
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/1216/162552_b8ca0a33_2336382.png "image-20201216092534703.png")
    
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/1216/162620_83cce950_2336382.png "image-20201216092552823.png")
    
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/1216/162637_67d8a3b8_2336382.png "image-20201216092613453.png")
    

4. 添加了订单管理中查看商品描述功能，现有的jsp文件不含商品描述的展示框，但是数据库中是有数据的，所以我添加了这个功能。
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/1216/162751_4d497e96_2336382.png "image-20201216092906225.png")
    
    ![输入图片说明](https://images.gitee.com/uploads/images/2020/1216/162725_946fcb0c_2336382.png "image-20201216092932175.png")
    

5. 总结：

    + jsp基础，js很重要，很多个js文件因为不懂浪费了很多时间。特别注意是我这里对部分资源文件进行了适当修改

    + sql语句建议先在运行成功后再复制进代码中。

    + 因为我这个是在码云找的资源文件，然后自己加后端代码，在编写过程中发现下载的资源文件并不完善，比如我要在供应商管理页面分页，就发现在providerlist.jsp文件中少了

      ```jsp
      <input type="hidden" name="pageIndex" value="1"/>
      ```

      导致我找错浪费很多时间。

6. 希望大家可以从这个超市管理系统中学到知识。

