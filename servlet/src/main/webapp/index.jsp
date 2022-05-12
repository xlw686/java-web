<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java"%>
<html>
<body>
<h2>Hello se!</h2>

<div style="text-align:left">
    <form action="${pageContext.request.contextPath}/login" method="get">
        用户名：<input type="text" name="username"> <br>
        密码： <input type="password" name="password"><br>
        爱好：<input type="checkbox" name="hobbys" value="女孩">女孩
            <input type="checkbox" name="hobbys" value="代码">代码
            <input type="checkbox" name="hobbys" value="唱歌">唱歌
            <input type="checkbox" name="hobbys" value="电影">电影
        <br>
        <input type="submit">
    </form>
</div>

<div>
    <p>
        <a href="http://localhost:8080/servlet/img" >验证码</a>
    </p>
    <p>
        <a href="http://localhost:8080/servlet/down">下载</a>
    </p>
    <p>
        <a href="http://localhost:8080/servlet/redirect">重定向</a>
    </p>
    <p>
        <a href="http://localhost:8080/servlet/cookie">CookieDemo</a>
    </p>
    <p>
        <a href="http://localhost:8080/servlet/session">SessionDemo</a>
    </p>
</div>


</body>
</html>
