<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="js/pfc.js"></script>
<link rel="stylesheet" type="text/css" href="css/pfc.css">
</head>
<body>

<div id="header">
<h1>Pfc Behauvioral testing</h1>
</div>

<div id="nav">
<ul>
<li><div onclick="getCodigo(1)">getCodigo(1)</div></li>
<li><div onclick="createCodigo()">createCodigo</div></li>
<li><div onclick="borrarCodigo(1)">borrarCodigo(1)</div></li>
<li><div onclick="createProyecto()">createProyecto()</div></li>
</ul>
</div>
<div id="section">
<div id="currentStatusMsgId"></div>
<h2>Código: <span id="codigoNombreId"></span></h2>
<p>
<textarea id="codigoAreaId" rows="20" cols="75"></textarea>
</p>
</div>

<div id="section">
<h2>Test: <span id="testNombreId"></span></h2>
<p>
<textarea id="testAreaId" rows="20" cols="75"></textarea>
</p>
</div>

<div id="footer">
Copyright © David LR
</div>

</body>
</html>
