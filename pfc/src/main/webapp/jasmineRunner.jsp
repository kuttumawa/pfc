<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Jasmine Spec Runner v2.3.4</title>

<!--   <link rel="shortcut icon" type="image/png" href="jasmine-2.3.4/jasmine_favicon.png"> -->
  <link rel="stylesheet" href="jasmine-2.3.4/jasmine.css">

  <script src="jasmine-2.3.4/jasmine.js"></script>
  <script src="jasmine-2.3.4/jasmine-html.js"></script>
  <script src="jasmine-2.3.4/boot.js"></script>
<%if(request.getParameter("codigoId")!=null){%>
  <!-- include source files here... -->
  <script src="v1/codigos/<%= request.getParameter("codigoId") %>/js"></script>
  <!-- include spec files here... -->
  <script src="v1/codigos/<%= request.getParameter("codigoId") %>/test/js"></script>
<%}%>

  
  </head>

<body>

</body>
</html>
