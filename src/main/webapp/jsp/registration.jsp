<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="page.content.title"/></title>
    <link rel='stylesheet' href='webjars/bootstrap/4.3.1/css/bootstrap.min.css'>
    <script type="text/javascript"
            src="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body class="d-flex flex-column h-100">
<jsp:include page="part/index_header.jsp"/>
<jsp:include page="part/message.jsp"/>
<jsp:include page="part/error_message.jsp"/>
<div class="container">
    <form method="post" class="was-validated" novalidate>
        <input type="text" name="command" value="registration" hidden>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp" required>
            <small id="emailHelp" class="form-text text-muted"><fmt:message key="page.content.email_help_text"/></small>
            <div class="invalid-feedback">
                <fmt:message key="page.content.invalid_email_text"/>
            </div>
        </div>
        <div class="form-group">
            <label for="phone_number"><fmt:message key="page.content.phone_number_input"/></label>
            <input class="form-control" id="phone_number" name="phone_number" pattern="\+375((29)|(44)|(33)|(25)) ?\d{7}" required>
            <div class="invalid-feedback">
                <fmt:message key="page.content.invalid_phone_text"/>
            </div>
        </div>
        <div class="form-group">
            <label for="password"><fmt:message key="page.content.password_label"/></label>
            <input type="password" class="form-control" id="password" name="password" pattern=".{7,}" required>
            <div class="invalid-feedback">
                <fmt:message key="page.content.invalid_password_text"/>
            </div>
        </div>
        <button type="submit" class="btn btn-primary btn-block">
            <fmt:message key="page.content.register_button"/></button>
    </form>
</div>
<jsp:include page="part/footer.jsp"/>
</body>
</html>
