<%@ taglib uri="/struts-tags" prefix="s"%>

<s:div cssClass="dashline" cssStyle="background-color: #f0f0f0;">
	<s:div cssClass="insider">
		<a class="menu"
			href="<s:url action="telnetAction"  includeParams="none" />">telnet</a>
		<a class="menu"
			href="<s:url action="graphicalLogAction"  includeParams="none" />">graphicalLog</a>
		<a class="menu"
			href="<s:url action="telnetAdminAction" includeParams="none" />">telnetAdmin</a>
		<a class="menu"
			href="<s:url action="listVisitorAction" includeParams="none" />">listVisitor</a>

		<img alt="aaa" height=50 src=<s:url value="logo.jpg"/>
			style="display: none;" />
		<s:property value="#session.user"></s:property>
	</s:div>
	<s:div cssStyle="float: right;  ">Presented by ZHAO Jian (305020571)</s:div>
	<s:div cssStyle="clear:both;"></s:div>
</s:div>
