<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${isEdit ? 'Edit Book' : 'Add New Book'}" scope="request"/>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="page-header">
    <h1>${isEdit ? 'Edit' : 'Add New'} <span>Book</span></h1>
    <a href="/books" class="btn btn-secondary">← Back to Books</a>
</div>

<div class="form-card">
    <form action="${formAction}" method="post">
        <c:if test="${isEdit}">
            <input type="hidden" name="id" value="${book.id}"/>
        </c:if>

        <div class="form-group">
            <label for="title">Book Title *</label>
            <input type="text" id="title" name="title" value="${book.title}"
                   placeholder="e.g. 1984" required/>
        </div>

        <div class="form-group">
            <label for="authorId">Author *</label>
            <select id="authorId" name="authorId" required>
                <option value="">-- Select Author --</option>
                <c:forEach var="author" items="${authors}">
                    <option value="${author.id}"
                        ${not empty book.author and book.author.id == author.id ? 'selected' : ''}>
                        ${author.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="genre">Genre *</label>
            <input type="text" id="genre" name="genre" value="${book.genre}"
                   placeholder="e.g. Dystopian" required/>
        </div>

        <div class="form-group">
            <label for="publishedYear">Published Year *</label>
            <input type="number" id="publishedYear" name="publishedYear" value="${book.publishedYear}"
                   placeholder="e.g. 1949" min="1000" max="2099" required/>
        </div>

        <div class="form-group">
            <label for="isbn">ISBN *
                <span style="color:var(--text-sec); font-weight:normal;">(must be unique)</span>
            </label>
            <input type="text" id="isbn" name="isbn" value="${book.isbn}"
                   placeholder="e.g. 978-0451524935" required/>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">
                ${isEdit ? '💾 Save Changes' : '➕ Add Book'}
            </button>
            <a href="/books" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>
