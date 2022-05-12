<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>
<h1>当前有 <span><%=request.getServletContext().getAttribute("OnlineCount")%>/span>  人在线</h1>
<h1>当前有 <span><%=application.getAttribute("OnlineCount")%>/span>  人在线</h1>
<h1>当前有 <span>${ServletContext}/span>  人在线</h1>
<h1>当前有 <span>${OnlineCount}/span>  人在线</h1>

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


</body>
</html>
