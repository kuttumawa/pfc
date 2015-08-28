<!DOCTYPE html>
<html>
<head>
<script src="js/jquery.min.js"></script>
<script src="js/pfc.js"></script>
<script src="js/tree.jquery.js"></script>
<link rel="stylesheet" type="text/css" href="css/pfc.css">
<link rel="stylesheet" type="text/css" href="css/jqtree.css">
<jsp:include page="codemirror.jsp" />
<jsp:include page="dialog.jsp" />
<script>
$(function(){init();});
</script>
</head>
<body>

<div id="header">
<span id=currentUser>
<span id="loginId" style="cursor:pointer;" onclick="">login</span>/
<span id="signupId" style="cursor:pointer;" onclick="">Signup</span>
</span>
<span style="margin-left:20px;float:left;font-size: 150%;">Pfc Behauvioral testing
<a style="margin-left:10px;font-size: 70%;color:blue;" target="_blank" href="v1/api/index.html">api</a></span>

<span id="toolbar" style="margin-right:450px" class="ui-widget-header ui-corner-all">
<button style="font-size: .7em;" id="create-proyecto">Proyecto</button>
<button style="font-size: .7em;" id="delete-proyecto" onClick="borrarProyecto();">Proyecto</button>
<button style="font-size: .7em;" id="create-codigo">Código</button>
<button style="font-size: .7em;" id="delete-codigo" onClick="borrarCodigo();">Código</button>
<button style="font-size: .7em;" id="saveButton" onClick="saveCodigo();">Grabar</button>
<button style="font-size: .7em;" id="executeButton" onClick="executeCodigo();">Ejecutar</button>
<button style="font-size: .7em;"  id="revisarButton" onClick="revisarCodigo();">Revisar</button>
<button style="font-size: .7em;"  id="runTestButton" onClick="testCodigo();">Test</button>
<button style="font-size: .7em;"  id="shareButton">Compartir</button>
<button style="font-size: .7em;"  id="minificarButton" onClick="minificarCodigo();">Minificar</button>
<button style="font-size: .7em;"  id="optimizarButton" onClick="optimizarCodigo();">Optimizar</button>

<!-- <div id="info">Proyecto Actual : <span id="currentProyecto"></span><span id="currentStatusMsgId"></span></div> -->
</span>
</div>
<div id="nav">
<div id="tree1"></div>
</div>




<div id="section">
<span>Código: <span id="codigoNombreId"></span></span>
<p>
<textarea id="codigoAreaId" rows="20" cols="65"></textarea>
<textarea id="resultadoId" rows="5" cols="68" ></textarea>
</p>
</div>

<div id="section">
<span>Test: <span id="testNombreId"></span></span> 
<p>
<textarea id="testAreaId" rows="20" cols="65"></textarea>

</p>
</div>

<!-- <div id="section"> -->
<!-- <h2>Resultado: </span></h2> -->
<!-- <p> -->
<!-- <textarea id="resultadoId" rows="5" cols="65"></textarea> -->
<!-- </p> -->
<!-- </div> -->

<div id="">
<iframe id="testBrowser" src="jasmineRunner.jsp" width="75%" height="300px" seamless>
</iframe>
</div>



<div id="footer">
Copyright © David LR
</div>





</body>
</html>




