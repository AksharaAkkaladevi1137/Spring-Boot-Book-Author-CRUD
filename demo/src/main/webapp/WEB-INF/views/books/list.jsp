<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="pageTitle" value="All Books" scope="request"/>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="page-header">
    <h1>All <span>Books</span> <span class="count-pill">${fn:length(books)}</span></h1>
    <a href="/books/new" class="btn btn-primary">＋ Add Book</a>
</div>

<c:choose>
    <c:when test="${empty books}">
        <div class="table-card">
            <div class="empty-state">
                <div class="icon">📖</div>
                <p>No books yet. Add your first book!</p>
                <a href="/books/new" class="btn btn-primary">Add Book</a>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="table-card">
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Genre</th>
                        <th>Year</th>
                        <th>ISBN</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="book" items="${books}" varStatus="status">
                        <tr>
                            <td><span class="badge badge-purple">${status.index + 1}</span></td>
                            <td><strong>${book.title}</strong></td>
                            <td>
                                <c:if test="${not empty book.author}">
                                    <span class="badge badge-cyan">${book.author.name}</span>
                                </c:if>
                            </td>
                            <td>${book.genre}</td>
                            <td>${book.publishedYear}</td>
                            <td style="font-family: monospace; font-size:0.8rem; color: var(--text-sec);">${book.isbn}</td>
                            <td>
                                <a href="/books/edit/${book.id}" class="btn btn-secondary btn-sm">✏️ Edit</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
