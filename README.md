#oauth2-shiro


整合<a href="http://oltu.apache.org/">Apache Oltu</a> 与 <a href="http://shiro.apache.org/">Shiro</a>.

并根据不同的应用场景提供不同的实现(如web场景,移动设备).

该项目与<a href="http://git.oschina.net/shengzhao/spring-oauth-server">spring-oauth-server</a>实现相同的需求与场合.
只是在实现上使用的技术不同(spring-oauth-server使用Spring Security + spring-security-oauth2实现; oauth2-oltu实现);
相比spring-oauth-server, oauth2-oltu具有如下特点:

<div>
    <ul>
        <li><p><strong>更加透明</strong> -- 每一步实现都有可以查看的, 更容易理解的代码, 一目也然</p></li>
        <li><p><strong>更多的可自定义与可扩展</strong> -- 不管是ERROR返回信息的内容或格式, 都可根据需要自定义, 对请求参数,处理细节等可添加更多的具体实现</p></li>
        <li><p><strong>可读性更强</strong> -- 由于Shiro, Oltu 没有Spring Security,spring-security-oauth2 的门槛高, 所有代码都是常用的Controller或Java Bean实现各项业务, 更可读,更易于理解</p></li>
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


