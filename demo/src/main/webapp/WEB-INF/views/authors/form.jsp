<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${isEdit ? 'Edit Author' : 'Add New Author'}" scope="request"/>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="page-header">
    <h1>${isEdit ? 'Edit' : 'Add New'} <span>Author</span></h1>
    <a href="/authors" class="btn btn-secondary">← Back to Authors</a>
</div>

<div class="form-card">
    <form action="${formAction}" method="post">
        <c:if test="${isEdit}">
            <input type="hidden" name="id" value="${author.id}"/>
        </c:if>

        <div class="form-group">
            <label for="name">Full Name *</label>
            <input type="text" id="name" name="name" value="${author.name}"
                   placeholder="e.g. J.K. Rowling" required/>
        </div>

        <div class="form-group">
            <label for="nationality">Nationality *</label>
            <input type="text" id="nationality" name="nationality" value="${author.nationality}"
                   placeholder="e.g. British" required/>
        </div>

        <div class="form-group">
            <label for="birthYear">Birth Year *</label>
            <input type="number" id="birthYear" name="birthYear" value="${author.birthYear}"
                   placeholder="e.g. 1965" min="1000" max="2099" required/>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">
                ${isEdit ? '💾 Save Changes' : '➕ Add Author'}
            </button>
            <a href="/authors" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
