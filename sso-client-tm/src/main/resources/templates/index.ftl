<!DOCTYPE html>
<html lang="en" >
<head>
	<title>天猫</title>
</head>
<body>
	<span>欢迎 ${(account.username)!''}访问</span>
	<a href="${Request['logoutUrl']}">退出</a>
</body>
</html>