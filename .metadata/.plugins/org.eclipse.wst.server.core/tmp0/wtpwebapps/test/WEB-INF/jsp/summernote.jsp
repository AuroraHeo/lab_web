<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP with Summernote</title>
    <!-- Summernote CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-bs4.min.css" rel="stylesheet">
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <!-- Popper.js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <!-- Summernote JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-bs4.min.js"></script>
</head>
<body>
    <h1>Welcome to JSP with Summernote</h1>
    
    <% 
    String content = request.getParameter("content");
    if (content != null) { 
    %>
        <h2>Form Submitted</h2>
        <div>
            <h3>Content:</h3>
            <div><%= content %></div>
        </div>
    <% 
    } else { 
    %>
        <form action="index.jsp" method="post">
            <label for="editor">Content:</label>
            <textarea id="summernote" name="content"></textarea>
            <script>
                $(document).ready(function() {
                    $('#summernote').summernote({
                        height: 300, // set editable area's height
                        placeholder: 'Write here...',
                        toolbar: [
                            ['style', ['style']],
                            ['font', ['bold', 'italic', 'underline', 'clear']],
                            ['fontname', ['fontname']],
                            ['color', ['color']],
                            ['para', ['ul', 'ol', 'paragraph']],
                            ['table', ['table']],
                            ['insert', ['link', 'picture', 'video']],
                            ['view', ['fullscreen', 'codeview', 'help']]
                        ]
                    });
                });
            </script>
            <br>
            <input type="submit" value="Submit">
        </form>
    <% 
    } 
    %>
</body>
</html>
