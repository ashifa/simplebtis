<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Unexpected Error</title>
</head>

<body>
<h2>An unexpected error has occured</h2>



<hr/>

<h3>Error Message</h3>

<s:actionerror />

<p>
    <s:property value="%{exception.message}"/>
</p>

<hr/>

<h3>Technical Details</h3>

<p>
    <s:property value="%{exceptionStack}"/>
</p>

 
</body>
</html>
