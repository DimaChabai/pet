<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger" role="alert">
        <fmt:message key="${errorMessage}"/>
    </div>
</c:if>