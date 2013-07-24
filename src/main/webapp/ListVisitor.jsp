<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<link href="<s:url value="/mystyle.css"/>" rel="stylesheet"
	type="text/css" />
<title>visiting log</title>
</head>
<body>
	<jsp:include page="HeadMenu.jsp" />

	<s:div cssClass="dashline">

		<table border="1">
			<tr>
				<th>hostName</th>
				<th>IP</th>
				<th>port</th>
				<th>date</th>
			</tr>

			<s:iterator value="visitorList" status="rowstatus" var="iter">

				<s:if test="#rowstatus.odd == true">
					<tr class="even">
				</s:if>
				<s:else>
					<tr>
				</s:else>
				<td><s:property value="#iter.hostName" /></td>
				<td><s:property value="#iter.ip" /></td>
				<td><s:property value="#iter.port" /></td>
				<td><s:property value="#iter.date" /></td>
				</tr>

			</s:iterator>
		</table>

	</s:div>


</body>
</html>
