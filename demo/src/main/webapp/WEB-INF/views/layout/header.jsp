<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Library Management System - Manage Books and Authors">
    <title>${pageTitle} | Library Manager</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

        :root {
            --bg-dark:     #0d1117;
            --bg-card:     #161b22;
            --bg-input:    #0d1117;
            --border:      #30363d;
            --accent:      #7c3aed;
            --accent-glow: rgba(124,58,237,0.35);
            --accent2:     #06b6d4;
            --text-pri:    #e6edf3;
            --text-sec:    #8b949e;
            --success:     #238636;
            --success-bg:  #0f2a16;
            --error:       #f85149;
            --error-bg:    #2d0a0a;
            --radius:      12px;
            --shadow:      0 8px 32px rgba(0,0,0,0.4);
        }

        body {
            font-family: 'Inter', sans-serif;
            background: var(--bg-dark);
            color: var(--text-pri);
            min-height: 100vh;
        }

        /* NAV */
        nav {
            background: rgba(22,27,34,0.95);
            backdrop-filter: blur(12px);
            border-bottom: 1px solid var(--border);
            padding: 0 2rem;
            display: flex;
            align-items: center;
            height: 64px;
            position: sticky;
            top: 0;
            z-index: 100;
        }
        .nav-brand {
            font-size: 1.25rem;
            font-weight: 700;
            background: linear-gradient(135deg, var(--accent), var(--accent2));
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            text-decoration: none;
            margin-right: 2.5rem;
        }
        .nav-links { display: flex; gap: 0.5rem; }
        .nav-link {
            color: var(--text-sec);
            text-decoration: none;
            padding: 0.5rem 1rem;
            border-radius: 8px;
            font-size: 0.9rem;
            font-weight: 500;
            transition: all 0.2s;
        }
        .nav-link:hover, .nav-link.active {
            color: var(--text-pri);
            background: rgba(124,58,237,0.15);
        }
        .nav-link.active { color: var(--accent); }

        /* MAIN LAYOUT */
        main {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2.5rem 1.5rem;
        }

        /* PAGE HEADER */
        .page-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 2rem;
        }
        .page-header h1 {
            font-size: 1.8rem;
            font-weight: 700;
            color: var(--text-pri);
        }
        .page-header h1 span {
            background: linear-gradient(135deg, var(--accent), var(--accent2));
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        /* BUTTONS */
        .btn {
            display: inline-flex;
            align-items: center;
            gap: 0.4rem;
            padding: 0.55rem 1.2rem;
            border-radius: 8px;
            font-size: 0.875rem;
            font-weight: 500;
            cursor: pointer;
            border: none;
            text-decoration: none;
            transition: all 0.2s;
        }
        .btn-primary {
            background: linear-gradient(135deg, var(--accent), #6d28d9);
            color: #fff;
            box-shadow: 0 0 0 0 var(--accent-glow);
        }
        .btn-primary:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 20px var(--accent-glow);
        }
        .btn-secondary {
            background: var(--bg-card);
            color: var(--text-sec);
            border: 1px solid var(--border);
        }
        .btn-secondary:hover {
            color: var(--text-pri);
            border-color: var(--accent);
        }
        .btn-sm { padding: 0.3rem 0.75rem; font-size: 0.8rem; }

        /* TABLE */
        .table-card {
            background: var(--bg-card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            overflow: hidden;
            box-shadow: var(--shadow);
        }
        table { width: 100%; border-collapse: collapse; }
        thead { background: rgba(124,58,237,0.08); }
        th {
            text-align: left;
            padding: 1rem 1.25rem;
            font-size: 0.75rem;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.08em;
            color: var(--text-sec);
            border-bottom: 1px solid var(--border);
        }
        td {
            padding: 0.9rem 1.25rem;
            font-size: 0.9rem;
            color: var(--text-pri);
            border-bottom: 1px solid rgba(48,54,61,0.5);
            vertical-align: middle;
        }
        tr:last-child td { border-bottom: none; }
        tr:hover td { background: rgba(124,58,237,0.04); }

        /* BADGES */
        .badge {
            display: inline-block;
            padding: 0.2rem 0.6rem;
            border-radius: 20px;
            font-size: 0.75rem;
            font-weight: 500;
        }
        .badge-purple { background: rgba(124,58,237,0.2); color: #a78bfa; }
        .badge-cyan   { background: rgba(6,182,212,0.2);  color: #67e8f9; }

        /* FORM */
        .form-card {
            background: var(--bg-card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            padding: 2rem;
            max-width: 640px;
            box-shadow: var(--shadow);
        }
        .form-group { margin-bottom: 1.25rem; }
        .form-group label {
            display: block;
            font-size: 0.85rem;
            font-weight: 500;
            color: var(--text-sec);
            margin-bottom: 0.4rem;
        }
        .form-group input,
        .form-group select {
            width: 100%;
            background: var(--bg-input);
            border: 1px solid var(--border);
            border-radius: 8px;
            padding: 0.65rem 0.9rem;
            color: var(--text-pri);
            font-size: 0.9rem;
            font-family: 'Inter', sans-serif;
            transition: border-color 0.2s, box-shadow 0.2s;
        }
        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: var(--accent);
            box-shadow: 0 0 0 3px var(--accent-glow);
        }
        .form-group select option { background: var(--bg-card); }
        .form-actions {
            display: flex;
            gap: 0.75rem;
            margin-top: 1.75rem;
        }

        /* ALERTS */
        .alert {
            padding: 0.85rem 1.25rem;
            border-radius: 8px;
            font-size: 0.875rem;
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
            gap: 0.6rem;
        }
        .alert-success {
            background: var(--success-bg);
            border: 1px solid var(--success);
            color: #3fb950;
        }
        .alert-error {
            background: var(--error-bg);
            border: 1px solid var(--error);
            color: var(--error);
        }

        /* EMPTY STATE */
        .empty-state {
            text-align: center;
            padding: 4rem 2rem;
            color: var(--text-sec);
        }
        .empty-state .icon { font-size: 3rem; margin-bottom: 1rem; }
        .empty-state p { margin-bottom: 1.5rem; }

        /* COUNT PILL */
        .count-pill {
            background: rgba(124,58,237,0.15);
            color: var(--accent);
            border-radius: 20px;
            padding: 0.15rem 0.65rem;
            font-size: 0.8rem;
            font-weight: 600;
            margin-left: 0.5rem;
        }
    </style>
</head>
<body>
<nav>
    <a href="/" class="nav-brand">📚 LibraryManager</a>
    <div class="nav-links">
        <a href="/books"   class="nav-link ${pageTitle.contains('Book')   ? 'active' : ''}">Books</a>
        <a href="/authors" class="nav-link ${pageTitle.contains('Author') ? 'active' : ''}">Authors</a>
    </div>
</nav>
<main>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">✅ ${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">⚠️ ${errorMessage}</div>
    </c:if>
