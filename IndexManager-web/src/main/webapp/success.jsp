<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2017/7/31
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form action="http://localhost:8080/server/hello/user/upfile" method="post" enctype="multipart/form-data" >
    <p>name:<input type="text" name="name"/></p>
    <p>file:<input type="file" name="file"/>
    <p><input type="submit" value="sub"/></p>
</form>
