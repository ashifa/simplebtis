<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<link href="<s:url value="/mystyle.css"/>" rel="stylesheet"
	type="text/css" />
<title>login page</title>
</head>
<body>
	<jsp:include page="HeadMenu.jsp" />

	<s:div
		cssStyle="margin: 10px; margin-top: 0px; padding: 10px;  float: left ">
		<s:form action="loginAction_checkUserAccount">
			<s:textfield key="user" cssStyle="width:350px" />
			<s:textfield key="password" cssStyle="width:350px" />
			<s:submit label="submit" />
		</s:form>
	</s:div>


</body>
</html>