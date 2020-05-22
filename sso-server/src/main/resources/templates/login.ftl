<!DOCTYPE html>
<html lang="en" >
<head>
	<title>认证中心</title>
</head>
<body>
		
	<form action="/login" method="post">
		<h3>认证中心</h3>
		<#if error??>
			<span style="color:red">账号错误，请重新填写</span><br/>
        </#if>
        
		<input type="hidden" name="redirectUrl" value="${redirectUrl}" />
		<input type="text" placeholder="Username" name="username" required=""/>
		<br/>
		<input type="password" placeholder="Password" name="password" required=""/>
		<br/>
		<label><input type="checkbox" value="remembr-me"/> 记住我</label>
		<button type="submit">登录</button>
	</form>
</body>
</html>