<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="js/pfc.js"></script>
<script src="js/tree.jquery.js"></script>
<link rel="stylesheet" type="text/css" href="css/pfc.css">
<link rel="stylesheet" type="text/css" href="css/jqtree.css">
</head>
<body>

<div id="header">
<span id=currentUser>usuario</span>
<h1>Pfc Behauvioral testing</h1>

</div>

<div id="nav">
<div id="tree1"></div>
</div>
<div id="info">Proyecto Actual : <span id="currentProyecto"></span><span id="currentStatusMsgId"></span></div>
<div id="botonera"> <button style="font-size: .7em;" id="create-proyecto">Nuevo Proyecto</button> <button style="font-size: .7em;" id="create-codigo">Nuevo C�digo</button></div>
<div id="section">
<h2>C�digo: <span id="codigoNombreId"></span></h2>
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

<div id="footer">
Copyright � David LR
</div>





</body>
</html>

<jsp:include page="dialog.jsp"></jsp:include>



<script>
var datax = [
            {
                label: 'node1',
                children: [
                    { label: 'child1' },
                    { label: 'child2' }
                ]
            },
            {
                label: 'node2',
                children: [
                    { label: 'child3' }
                ]
            }
        ];
        

</script>