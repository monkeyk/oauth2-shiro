#oauth2-shiro


整合<a href="http://oltu.apache.org/">Apache Oltu</a> 与 <a href="http://shiro.apache.org/">Shiro</a>. 提供一个轻量的OAUTH2应用框架.

并根据不同的应用场景提供不同的实现(如web场景,移动设备).

该项目与<a href="http://git.oschina.net/shengzhao/spring-oauth-server">spring-oauth-server</a>实现相同的需求与场合.
只是在实现上使用的技术不同(spring-oauth-server使用Spring Security + spring-security-oauth2实现; oauth2-oltu实现);
相比spring-oauth-server, oauth2-oltu具有如下特点:

<div>
    <ul>
        <li><p><strong>更加透明</strong> -- 每一步实现都有可以查看的, 更容易理解的代码, 一目也然</p></li>
        <li><p><strong>更多的可自定义与可扩展</strong> -- 不管是ERROR返回信息的内容或格式, 都可根据需要自定义, 对请求参数,处理细节等可添加更多的具体实现</p></li>
        <li><p><strong>可读性更强</strong> -- 由于Shiro, Oltu 没有Spring Security,spring-security-oauth2 的门槛高, 所有代码都是常用的Controller或Java Bean实现各项业务, 更可读,更易于理解</p></li>
        <li><p><strong>模块化</strong> -- 得益于Oltu的模块化设计, 将<code>authzserver</code>, <code>resourceserver</code>分开成不同的模块, 使用时可根据实际需要将二者合并在一个项目中可拆分为不同的模块</p></li>
    </ul>
</div>

<div>
    <h3>主要技术及版本</h3>
    Spring -- 3.2.2.RELEASE
    <br/>
    oltu  -- 1.0.0
    <br/>
    shiro -- 1.2.3
    <br/>
    MySQL -- 5.6
</div>

<div>
    <h3>开发环境</h3>
    <ul>
        <li><p>JDK -- 1.7.0_40</p></li>
        <li><p>Maven -- 3.1.0</p></li>
        <li><p>MySQL -- 5.6.23-log</p></li>
    </ul>
</div>

<div>
    <h3>如何使用</h3>
<ol>
<li>
    项目是Maven管理的, 需要本地安装maven(开发用的maven版本号为3.1.0), 还有MySql(开发用的mysql版本号为5.6)
</li>
<li>
    <a href="http://git.oschina.net/mkk/oauth2-shiro/repository/archive?ref=master">下载</a>(或clone)项目到本地
</li>
<li>
    创建MySQL数据库(如数据库名 oauth2_shiro), 并运行相应的SQL脚本(脚本文件位于others/database目录),
    <br/>
    运行脚本的顺序: oauth2-shiro.ddl -> initial-db.ddl
</li>
<li>
    修改<a href="#">oauth2-shiro.properties</a>(位于src/main/resources目录)中的数据库连接信息(包括username, password等)
</li>
<li>
将本地项目导入到IDE(如Intellij IDEA)中,配置Tomcat(或类似的servlet运行服务器), 并启动Tomcat(默认端口为8080);
<br/>
注意将项目的 contextPath(根路径) 设置为 'os'.
<br/>
   另: 也可通过maven package命令将项目编译为war文件(os.war),
         将war放在Tomcat中并启动(注意: 这种方式需要将 oauth2-shiro.properties 加入到classpath中并正确配置数据库连接信息).
</li>
<li>
    参考<a href="http://git.oschina.net/mkk/oauth2-shiro/blob/master/others/oauth_test.txt">oauth_test.txt</a>(位于others目录)的内容并测试之(也可在浏览器中访问相应的地址,如: http://localhost:8080/os/).
</li>
</ol>
</div>

<div id="trend">
    <h3>项目动态</h3>
    <ul>
        <li><p><a href="http://andaily.com/blog/?p=325">oauth2-shiro项目开发状态(8月)</a></p></li>
        <li><p><a href="http://andaily.com/blog/?p=312">oauth2-shiro项目开发状态(7月)</a></p></li>
    </ul>
</div>

<div>
    <h3>相关项目</h3>
    <ul>
        <li><p><a href="http://git.oschina.net/shengzhao/spring-oauth-server">spring-oauth-server</a> Spring Security与OAUTH2的完整整合项目</p></li>
        <li><p><a href="http://git.oschina.net/mkk/spring-oauth-client">spring-oauth-client</a> Oauth 客户端(client)测试项目</p></li>
    </ul>
</div>


