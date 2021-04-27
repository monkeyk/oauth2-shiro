#oauth2-shiro


整合<a href="http://oltu.apache.org/">Apache Oltu</a> 与 <a href="http://shiro.apache.org/">Shiro</a>. 提供一个轻量的OAUTH2应用框架.

并根据不同的应用场景提供不同的实现(如web场景,移动设备).

<h3>最新的代码请访问 Gitee 仓库代码：https://gitee.com/mkk/oauth2-shiro</h3>

该项目与<a href="https://github.com/monkeyk/spring-oauth-server">spring-oauth-server</a>实现相同的需求与场合.
只是在实现上使用的技术不同(spring-oauth-server使用Spring Security + spring-security-oauth2实现; oauth2-oltu实现);
相比spring-oauth-server, oauth2-oltu具有如下特点:

<div>
    <ul>
        <li><p><strong>更加透明</strong> -- 每一步实现都有可以查看的, 更容易理解的源代码, 一目了然</p></li>
        <li><p><strong>更多的可自定义与可扩展</strong> -- 不管是ERROR返回信息的内容或格式, 都可根据需要自定义, 对请求参数,处理细节等可添加更多的具体实现</p></li>
        <li><p><strong>可读性更强</strong> -- 由于Shiro, Oltu 没有Spring Security,spring-security-oauth2 的门槛高, 所有代码都是常用的Controller或Java Bean实现各项业务, 更可读,更易于理解</p></li>
        <li><p><strong>模块化</strong> -- 得益于Oltu的模块化设计, 将<code>authz</code>, <code>resources</code>分开成不同的模块, 使用时可根据实际需要将二者合并在一个项目中或拆分为不同的模块</p></li>
    </ul>
</div>

<hr/>
<strong>OAuth2下一代身份认证授权协议OIDC实现: <a href="https://github.com/monkeyk/MyOIDC">MyOIDC</a></strong>
<hr/>
<div>
    <h3>主要技术及版本</h3>
    Spring -- 3.2.2.RELEASE
    <br/>
    oltu  -- 1.0.2
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
<hr/>
<div>
    <h3>项目模块说明</h3>
    <p>oauth2-shiro项目使用模块化开发, 以实现"高内聚, 低耦合"目标, 更符合实际项目需要; 分为三个模块: authz, core 与 resources, 具体说明如下</p>
    <ul>
        <li><p><code>authz</code> 实现使用各类grant_type去获取token业务逻辑----获取access_token</p></li>
        <li><p><code>core</code> 将公共部分提取到该模块中, 减少重复代码, 保证一致性, 如定义ClientDetails, AccessToken;  authz, resources 模块都依赖于该模块</p></li>
        <li><p><code>resources</code> 资源管理模块,将受OAUTH保护的资源(URI)放在这里----使用access_token</p></li>
    </ul>
</div>


<div>
    <h3>在线测试</h3>
    <ul>
        <li><p><code>authz</code> <a href="http://andaily.com/authz/">http://andaily.com/authz/</a></p></li>
        <li><p><code>resources</code> <a href="http://andaily.com/rs/">http://andaily.com/rs/</a></p></li>
    </ul>
</div>

<div>
    <h3>Redis版本</h3>
    <ul>
        <li><p><code>Redis + MySQL</code> <a href="https://github.com/monkeyk/oauth2-shiro-redis">https://github.com/monkeyk/oauth2-shiro-redis</a></p></li>
        <li><p><code>Redis</code> <a href="https://github.com/monkeyk/oauth2-shiro-redis/tree/redis">https://github.com/monkeyk/oauth2-shiro-redis/tree/redis</a></p></li>
    </ul>
</div>


<div>
    <h3>如何使用</h3>
<ol>
<li>
    项目是Maven管理的, 需要本地安装maven(开发用的maven版本号为3.1.0), 还有MySql(开发用的mysql版本号为5.6)
</li>
<li>
    <a href="https://github.com/monkeyk/oauth2-shiro/archive/master.zip">下载</a>(或clone)项目到本地
</li>
<li>
    项目由三个模块(core,authz,resources)组成, core是一个Java项目(jar), authz与resources是Java Web项目(.war)
</li>
<li>
    创建MySQL数据库(如数据库名 oauth2_shiro), 并运行相应的SQL脚本(脚本文件位于others/database目录),
    <br/>
    运行脚本的顺序: oauth2-shiro.ddl -> initial-db.ddl
</li>
<li>
    依次修改authz模块的配置文件authz.properties(位于模块的src/main/resources目录)与resources模块的配置文件resources.properties(位于模块的src/main/resources目录);
    修改配置文件中的数据库连接信息(包括username, password等), 都连接到数据库oauth2_shiro
</li>
<li>
将本地项目导入到IDE(如Intellij IDEA)中,配置Tomcat(或类似的servlet运行服务器), 并启动Tomcat(默认端口为8080);
<br/>
注意将项目的 contextPath(根路径) 设置为 'os'.
<br/>
   另: 也可通过maven package命令将项目编译为war文件(os.war), 注意编译时每个模块的pom.xml文件中配置的数据库连接信息, 可在Maven命令中添加 -Dmaven.test.skip=true 忽略测试;
         将authz模块与resources模块生成的war放在Tomcat中并启动(注意: 这种方式需要将 authz.properties与resources.properties 加入到classpath中并正确配置数据库连接信息).
</li>
<li>
    参考<a href="https://github.com/monkeyk/oauth2-shiro/blob/master/others/oauth_test.txt">oauth_test.txt</a>(位于others目录)的内容并测试之(也可在浏览器中访问相应的地址,如: http://localhost:8080/os/).
</li>
</ol>
</div>



<hr/>
<strong>支持的 grant_type</strong>
<br/>
说明 oauth2-shiro 项目支持的grant_type(授权方式)与功能
<ol>
    <li><code>authorization_code</code> -- 授权码模式(即先登录获取code,再获取token)</li>
    <li><code>password</code> -- 密码模式(将用户名,密码传过去,直接获取token)</li>
    <li><code>refresh_token</code> -- 刷新access_token</li>
    <li><code>implicit(token)</code> -- 简化模式(在redirect_uri 的Hash传递token; Auth客户端运行在浏览器中,如JS,Flash)</li>
    <li><code>client_credentials</code> -- 客户端模式(无用户,用户向客户端注册,然后客户端以自己的名义向'服务端'获取资源)</li>
</ol>



<hr/>
<h3>开发计划</h3>
<p>
从 0.2版本开始将项目的所有计划的开发内容列出来, 方便大家跟进, 也欢迎你加入.
<br/>
项目的开发管理使用开源项目 <a href="http://git.oschina.net/mkk/andaily-developer">andaily-developer</a>.
</p>
<p>
    计划加入Spring-Boot的实现
</p>
<ul>
       <li>
            <p>
                Version: <strong>0.3</strong> [pending]
                <br/>
                Date: 2016-07-16 / ------
            </p>
            <ol>
                <li><p><del>(152) - oltu版本升级到1.0.2 并完成测试.</del></p></li>
                <li><p>(153) - 尝试添加并实现OIDC在 oauth2-shiro中</p></li>
                <li><p><del>(161) - 增加必要的代码注释与配置注释, 更易理解</del></p></li>
                <li><p><del>implicit模式不需要带上client_secret</del></p></li>
            </ol>
            <br/>
       </li>
       <li>
            <p>
                Version: <strong>0.2</strong> [finished]
                <br/>
                Date: 2016-05-26 / 2016-07-03
            </p>
            <ol>
                <li><p><del>(66) - 更新首页UI, 参照spring-oauth-server</del></p></li>
                <li><p><del>(67) - client details overview</del></p></li>
                <li><p><del>(68) - client details testing</del></p></li>
                <li><p><del>(69) - user add/edit, overview</del></p></li>
                <li><p><del>(70) - 添加API使用说明, 举例各个场景 </del></p></li>
                <li><p><del>(71) - 发布到测试服务器 </del></p></li>
                <li><p><del>(72) - resources模块更新UI说明 </del></p></li>
            </ol>
            <br/>
       </li>
</ul>


<hr/>
<strong>Project Log</strong>
<p>
    <ol>
        <li><p><em>2015-05-17</em>     Initial project, start push code (private)</p></li>
        <li><p><em>2015-07-16</em>     <a href="http://andaily.com/blog/?p=312">oauth2-shiro项目开发状态(7月)</a></p></li>
        <li><p><em>2015-09-06</em>     <a href="http://andaily.com/blog/?p=325">oauth2-shiro项目开发状态(8月)</a></p></li>
        <li><p><em>2015-09-06</em>     项目由 私有 变为 开源, 开发 resource 模块</p></li>
        <li><p><em>2015-09-26</em>     版本0.1 开发完毕, 发布 <strong>0.1-beta</strong> 版本</p></li>
        <li><p><em>2015-10-07</em>     重构项目结构, 发布 <strong>0.1-rc</strong> 版本</p></li>
        <li><p><em>2016-05-26</em>     开始开发 <strong>0.2</strong> 版本</p></li>
        <li><p><em>2016-07-02</em>     添加在线测试环境</p></li>
        <li><p><em>2016-08-17</em>     发布 <strong>0.2</strong> 版本</p></li>
        <li><p><em>2017-01-21</em>      加入到GitHub中, Git@OSC地址: http://git.oschina.net/mkk/oauth2-shiro</p></li>
    </ol>
</p>



<hr/>

<div id="trend">
    <h3>项目动态</h3>
    <ul>
        <li><p><a href="http://www.oschina.net/news/66844/oauth2-shiro-0-1-rc">oauth2-shiro 0.1-rc 发布</a> 2015-10-07</p></li>
        <li><p><a href="http://www.oschina.net/news/66567/oauth2-shiro-0-1-beta">oauth2-shiro 0.1-beta 发布</a> 2015-09-26</p></li>
        <li><p><a href="http://andaily.com/blog/?p=325">oauth2-shiro项目开发状态(8月)</a> 2015-09-06</p></li>
        <li><p><a href="http://andaily.com/blog/?p=312">oauth2-shiro项目开发状态(7月)</a> 2015-07-16</p></li>
    </ul>
</div>

<hr/>
<div>
    <h3>姊妹项目</h3>
    <ul>
        <li><p><a href="http://git.oschina.net/shengzhao/spring-oauth-server">spring-oauth-server</a> Spring Security与OAUTH2的完整整合项目</p></li>
        <li><p><a href="http://git.oschina.net/mkk/spring-oauth-client">spring-oauth-client</a> Oauth 客户端(client)测试项目</p></li>
    </ul>
</div>



<hr/>
<h4>
    与Oauth2相关的技术文章请访问 <a href="http://andaily.com/blog/?cat=19">http://andaily.com/blog/?cat=19</a> (不断更新与Oauth相关的文章)
</h4>
<p>
    <strong>问答与讨论</strong>
    <br/>
    与项目相关的，与Oauth相关的问题与回答，以及各类讨论请访问<br/>
    <a href="http://andaily.com/blog/?dwqa-question_category=oauth">http://andaily.com/blog/?dwqa-question_category=oauth</a>
</p>

<hr/>
<p>
    <strong>捐助</strong>
    <br/>
    支付宝: monkeyking1987@126.com (**钊)
    <br/>
    明瑞 -- 5元
    <br/>
    Triton -- 8.8元
    <br/>
    半个鼠标 -- 10元
    <br/>
    张宏俊 -- 20元 (2018-03-28)
</p>

<hr/>
<p>
 关注更多我的开源项目请访问 <a href="http://andaily.com/my_projects.html">http://andaily.com/my_projects.html</a>
</p>
<h3>
 若需更多的商业技术支持请联系 <a href="mailto:sz@qc8.com">sz@qc8.com</a>
 或访问 <a href="http://monkeyk.com/kso/" target="_blank">http://monkeyk.com/kso/</a>
</h3>
<p>
    <img src="http://77g1is.com1.z0.glb.clouddn.com/wechat_qrcode.jpg" alt="WeChat"/>
</p>
