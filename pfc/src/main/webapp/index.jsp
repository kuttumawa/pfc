<!DOCTYPE html>
<html>
<head>
<script src="js/jquery.min.js"></script>
<script src="js/pfc.js"></script>
<script src="js/tree.jquery.js"></script>
<link rel="stylesheet" type="text/css" href="css/pfc.css">
<link rel="stylesheet" type="text/css" href="css/jqtree.css">

<script>
$(function(){init();});
</script>
</head>
<body>

<div id="header">
<span id=currentUser><span style="cursor:pointer;" onclick="alert('xxx');createUsuario('dlr','dlrpass')">login</span></span>
<h1>Pfc Behauvioral testing</h1>

</div>

<div id="nav">
<div id="tree1"></div>
</div>
<div id="info">Proyecto Actual : <span id="currentProyecto"></span><span id="currentStatusMsgId"></span></div>
<button style="font-size: .7em;" id="create-signup">Registrarse</button></div>
<button style="font-size: .7em;" id="create-login">Entar</button></div>
<div id="botonera"> <button style="font-size: .7em;" id="create-proyecto">Nuevo Proyecto</button>
 <button style="font-size: .7em;" id="create-codigo">Nuevo Código</button></div>
<button style="font-size: .7em;"  onClick="saveCodigo();">Save</button>
<button style="font-size: .7em;" onClick="executeCodigo();">Execute</button>
<button style="font-size: .7em;"  onClick="revisarCodigo();">Revisar</button>
<div id="section">
<h2>Código: <span id="codigoNombreId"></span></h2>
<p>
<textarea id="codigoAreaId" rows="20" cols="70"></textarea>
</p>
</div>

<div id="section">
<h2>Test: <span id="testNombreId"></span></h2>
<p>
<textarea id="testAreaId" rows="20" cols="70"></textarea>
</p>
</div>

<div id="section">
<h2>Resultado: </span></h2>
<p>
<textarea id="resultadoId" rows="20" cols="70"></textarea>
</p>
</div>

<div id="footer">
Copyright © David LR
</div>





</body>
</html>

<jsp:include page="dialog.jsp"></jsp:include>

