<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="pageTitle" value="All Authors" scope="request"/>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="page-header">
    <h1>All <span>Authors</span> <span class="count-pill">${fn:length(authors)}</span></h1>
    <a href="/authors/new" class="btn btn-primary">＋ Add Author</a>
</div>

<c:choose>
    <c:when test="${empty authors}">
        <div class="table-card">
            <div class="empty-state">
                <div class="icon">✍️</div>
                <p>No authors yet. Add your first author!</p>
                <a href="/authors/new" class="btn btn-primary">Add Author</a>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="table-card">
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Nationality</th>
                        <th>Birth Year</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="author" items="${authors}" varStatus="status">
                        <tr>
                            <td><span class="badge badge-purple">${status.index + 1}</span></td>
                            <td><strong>${author.name}</strong></td>
                            <td><span class="badge badge-cyan">${author.nationality}</span></td>
                            <td>${author.birthYear}</td>
                            <td>
                                <a href="/authors/edit/${author.id}" class="btn btn-secondary btn-sm">✏️ Edit</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
