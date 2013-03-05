<%@page contentType="text/html" pageEncoding="windows-31j"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-31j">
        <title>Register</title>
    </head>
    <body>
        <h1>Create new account</h1>
	<form name='f' action="<c:url value='j_spring_security_check' />"
		method='POST'>

		<table>
                        <tr>
				<td>Name:</td>
				<td><input type='text' name='j_name' value=''>
				</td>
			</tr>
                        <tr>
				<td>Email:</td>
				<td><input type='text' name='j_username' value=''>
				</td>
			</tr>
			<tr>
				<td>Username:</td>
				<td><input type='text' name='j_username' value=''>
				</td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='j_password' />
				</td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="submit" />
				</td>
			</tr>
			<tr>
				<td colspan='2'><input name="reset" type="reset" />
				</td>
			</tr>
		</table>

	</form>
    </body>
</html>
