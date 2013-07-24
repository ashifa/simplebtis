<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<link href="<s:url value="/mystyle.css"/>" rel="stylesheet"
	type="text/css" />
<title>Update the default settings</title>
</head>
<body>

	<jsp:include page="HeadMenu.jsp" />
	<s:div cssClass="dashline">
		<s:div cssClass="insider">
			<table border="1" class="adminList">
				<tr>
					<th>CMD name</th>
					<th>CMD value</th>
					<th>Remove</th>
					<th>Edit</th>
				</tr>
				<s:iterator value="CMDMap" status="rowstatus">
					<s:url var="removeUrl" action="telnetAdminAction_remove">
						<s:param name="editKey" value="key" />
					</s:url>
					<s:url var="editUrl" action="telnetAdminAction">
						<s:param name="editKey" value="key" />
						<s:param name="editValue" value="value" />
					</s:url>
					<s:if test="#rowstatus.odd == true">
						<tr class="even">
							<td><s:property value="key" /></td>
							<td><s:property value="value" /></td>

							<td><a href="${removeUrl}">Remove</a></td>
							<td><a href="${editUrl}">Edit</a></td>
						</tr>
					</s:if>
					<s:else>
						<tr>
							<td><s:property value="key" /></td>
							<td><s:property value="value" /></td>
							<td><a href="${removeUrl}">Remove</a></td>
							<td><a href="${editUrl}">Edit</a></td>
						</tr>
					</s:else>

				</s:iterator>
				<tr>
					<th>Target/Bay name</th>
					<th>IP</th>
					<th>Remove</th>
					<th>Edit</th>
				</tr>
				<s:iterator value="TargetMap" status="rowstatus">
					<s:url var="removeUrl" action="telnetAdminAction_remove">
						<s:param name="editKey" value="key" />
					</s:url>
					<s:url var="editUrl" action="telnetAdminAction">
						<s:param name="editKey" value="key" />
						<s:param name="editValue" value="value" />
					</s:url>
					<s:if test="#rowstatus.odd == true">
						<tr class="even">
							<td><s:property value="key" /></td>
							<td><s:property value="value" /></td>

							<td><a href="${removeUrl}">Remove</a></td>
							<td><a href="${editUrl}">Edit</a></td>
						</tr>
					</s:if>
					<s:else>
						<tr>
							<td><s:property value="key" /></td>
							<td><s:property value="value" /></td>
							<td><a href="${removeUrl}">Remove</a></td>
							<td><a href="${editUrl}">Edit</a></td>
						</tr>
					</s:else>

				</s:iterator>
			</table>
		</s:div>
		<s:div cssClass="insider">
			<s:form action="telnetAdminAction_edit">
				<s:textfield name="editKey" label="key" value="%{editKey}"
					cssStyle="width:350px" />
				<s:textfield name="editValue" label="value" value="%{editValue}"
					cssStyle="width:350px" />
				<s:submit label="submit" />
			</s:form>
		</s:div>
		<s:div cssStyle="clear:both;"></s:div>
	</s:div>

</body>
</html>