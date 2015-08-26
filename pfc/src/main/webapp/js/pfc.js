var currentProyectos;
var currentCodigo;
var currentStatusMsg;
var currentUser;
var currentProyecto;
var editorCode;
var editorTest;

function init(){
	 $('#resultadoId').hide();
	 $('#tree1').tree({
	        data: [],
	        autoOpen:0
	    });
	 $('#tree1').bind(
			    'tree.click',
			    function(event) {
			        var node = event.node;
			        console.log(node.name);
			        node.call(node);
			    }
			);
	 CodeMirror.commands.autocomplete = function(cm) {
	        cm.showHint({hint: CodeMirror.hint.anyword,hint:CodeMirror.hint.javascript});
	      };
	 editorCode = CodeMirror.fromTextArea(document.getElementById("codigoAreaId"), {
		    lineNumbers: true,
		    mode: "javascript",
		    gutters: ["CodeMirror-lint-markers"],
		    lint: true,
		    extraKeys: {"Ctrl-Space": "autocomplete"}
		  });
	 editorTest = CodeMirror.fromTextArea(document.getElementById("testAreaId"), {
		    lineNumbers: true,
		    mode: "javascript",
		    gutters: ["CodeMirror-lint-markers"],
		    lint: true,
		    extraKeys: {"Ctrl-Space": "autocomplete"}
		  });
}
function treeSelectCodigoActivo(){
	if(!currentCodigo) return;
	var node = $('#tree1').tree('getNodeById', 'c'+currentCodigo.id);
	$('#tree1').tree('selectNode', node);	
}
function actualizarCodigo(){
	actualizar();
	actualizarProyecto();
}
function actualizar(){
	testCodigo();
	$('#resultadoId').hide();
	if(currentProyecto){
		$('#currentProyecto').text(currentProyecto.nombre +"-"+currentProyecto.id);
	}else{
		$('#currentProyecto').text('');
	}
	if(currentCodigo){
		
		$('#codigoNombreId').text(currentCodigo.nombre);
   	    // $('#codigoAreaId').val(currentCodigo.code);
		editorCode.getDoc().setValue(currentCodigo.code!=null?currentCodigo.code:'');
   	    if(currentCodigo.test){
   	    	$('#testNombreId').text(currentCodigo.test.nombre);
   	   	    // $('#testAreaId').val(currentCodigo.test.code);
   	    	editorTest.getDoc().setValue(currentCodigo.test.code!=null?currentCodigo.test.code:'');
   	    }else{
   	     $('#testNombreId').text('');
    	 //$('#testAreaId').val('');
   	     editorTest.getDoc().setValue('');
   	   
   	    }
	}else{
		$('#codigoNombreId').text('');
   	    // $('#codigoAreaId').val('');
		editorCode.getDoc().setValue('');
   	    $('#testNombreId').text('');
   	    editorTest.getDoc().setValue('');
	}
	if(currentStatusMsg){
		$('#currentStatusMsgId').text(currentStatusMsg);
	}else{
		$('#currentStatusMsgId').text('');
	}
	if(currentUser){
		$('#currentUser').text(currentUser.nombre);
	}else{
		$('#currentUser').text('?');
	}
}
function actualizarProyecto(){
	actualizar();
	actualizarProyectos();
}
function actualizarProyectos(){
	actualizar();
	if(currentUser)getProyectos();
	
}

function getCodigo(id){
	   jQuery.ajax({
	         type: "GET",
	         url: "v1/codigos/"+id,
	         contentType: "application/json; charset=utf-8",
	         beforeSend: function(xhr) { xhr.setRequestHeader("Authorization", "Basic " + btoa(currentUser.nombre + ":" + currentUser.password)); },
	         dataType: "json",
	         success: function (codigo, status, jqXHR) {
	        	 currentStatusMsg=null;
	        	 currentCodigo=codigo;
	        	 actualizarCodigo();
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.status+ "-"+jqXHR.statusText;
	        	 console.log(currentStatusMsg);
	        	 actualizar();
	         }
	   });
	
}
function borrarCodigo(){
	   jQuery.ajax({
	         type: "DELETE",
	         url: "v1/proyectos/"+currentProyecto.id+"/codigos/"+currentCodigo.id,
	         contentType: "application/json; charset=utf-8",
	         beforeSend: function(xhr) { xhr.setRequestHeader("Authorization", "Basic " + btoa(currentUser.nombre + ":" + currentUser.password)); },
	         dataType: "json",
	         success: function (codigo, status, jqXHR) {
	        	 currentStatusMsg="Borrado  "+ codigo.nombre;
	        	 currentCodigo=null;
	        	 actualizarCodigo();
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.status+ "-"+jqXHR.statusText;
	        	 console.log(currentStatusMsg);
	        	 actualizar();
	         }
	   });
	
}
function executeCodigo(){
	   jQuery.ajax({
	         type: "GET",
	         url: "v1/codigos/"+currentCodigo.id+"/ejecutar",
	         contentType: "application/json; charset=utf-8",
	         beforeSend: function(xhr) { xhr.setRequestHeader("Authorization", "Basic " + btoa(currentUser.nombre + ":" + currentUser.password)); },
	         dataType: "json",
	         success: function (resultado, status, jqXHR) {
	        	 $('#resultadoId').val(resultado.resultado);
	        	 $('#resultadoId').show();
	         },
	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.status+ "-"+jqXHR.statusText;
	        	 console.log(currentStatusMsg);
	        	 actualizar();
	         }
	   });
	
}
function revisarCodigo(){
	   jQuery.ajax({
	         type: "GET",
	         url: "v1/codigos/"+currentCodigo.id+"/revisar",
	         contentType: "application/json; charset=utf-8",
	         beforeSend: function(xhr) { xhr.setRequestHeader("Authorization", "Basic " + btoa(currentUser.nombre + ":" + currentUser.password)); },
             dataType: "json",
	         success: function (resultado, status, jqXHR) {
	        	 $('#resultadoId').val(resultado.resultado);
	         },
	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.status+ "-"+jqXHR.statusText;
	        	 console.log(currentStatusMsg);
	        	 actualizar();
	         }
	   });
	
}

function createCodigo(nombre,descripcion){
	   jQuery.ajax({
	         type: "POST",
	         url: "v1/proyectos/"+currentProyecto.id+"/codigos",
	         contentType: "application/json; charset=utf-8",
	         beforeSend: function(xhr) { xhr.setRequestHeader("Authorization", "Basic " + btoa(currentUser.nombre + ":" + currentUser.password)); },		        
	         data: JSON.stringify({"nombre":nombre,"descripcion":descripcion}),
	         dataType: "json",
	         success: function (codigo, status, jqXHR) {
	        	 currentStatusMsg="Creado "+codigo.nombre;
	        	 currentCodigo=codigo;
	        	 actualizarCodigo();
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.status+ "-"+jqXHR.statusText;
	        	 console.log(currentStatusMsg);
	             actualizar();
	         }
	   });
	
}



function createProyecto(nombre,descripcion){
	var nuevo={id:"",nombre:nombre,descripcion:descripcion,user:currentUser};
	   jQuery.ajax({
	         type: "POST",
	         url: "v1/proyectos",
	         contentType: "application/json; charset=utf-8",
	         beforeSend: function(xhr) { xhr.setRequestHeader("Authorization", "Basic " + btoa(currentUser.nombre + ":" + currentUser.password)); },		        
	         data: JSON.stringify(nuevo),
	         dataType: "json",
	         success: function (proyecto, status, jqXHR) {
	        	 currentStatusMsg="Creado proyecto " + proyecto.nombre;
	        	 currentProyecto=proyecto;
	        	 currentCodigo=null;
	        	 actualizarProyecto();
	        	 
	        	 
	        	
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.responseJSON.detail;
	        	 console.log(currentStatusMsg);
	        	 actualizar();
	        	 
	         }
	   });
	
}

function getProyectos(){
	   jQuery.ajax({
	         type: "GET",
	         url: "v1/proyectos",
	         contentType: "application/json; charset=utf-8",
	         beforeSend: function(xhr) { xhr.setRequestHeader("Authorization", "Basic " + btoa(currentUser.nombre + ":" + currentUser.password)); },     
	         dataType: "json",
	         success: function (proyectos, status, jqXHR) {
	        	 currentStatusMsg=null;
	        	 currentProyectos=proyectos;
	        	 treeProyectos(proyectos);
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.status+ "-"+jqXHR.statusText;
	        	 console.log(currentStatusMsg);
	        	 
	         }
	   });
	
}
function borrarProyecto(){
	    jQuery.ajax({
	         type: "DELETE",
	         url: "v1/proyectos/"+currentProyecto.id,
	         contentType: "application/json; charset=utf-8",
	         beforeSend: function(xhr) { xhr.setRequestHeader("Authorization", "Basic " + btoa(currentUser.nombre + ":" + currentUser.password)); },
	         dataType: "json",
	         success: function (codigo, status, jqXHR) {
	        	 currentStatusMsg="Borrado  "+ codigo.nombre;
	        	 currentCodigo=null;
	        	 currentCodigo=null;
	        	 actualizarCodigo();
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.status+ "-"+jqXHR.statusText;
	        	 console.log(currentStatusMsg);
	        	 actualizar();
	         }
	   });
	
}

function treeProyectos(proyectos){
	var o=[];
	for (var i=0; i < proyectos.length; i++){
		console.log(proyectos[i].nombre);
		var codigos_temp=[];
		if(proyectos[i].codigos && proyectos[i].codigos.length>0){
		for (var j=0; j < proyectos[i].codigos.length; j++){
			console.log(proyectos[i].codigos[j].nombre);
			codigos_temp.push({label: proyectos[i].codigos[j].nombre+"-c"+proyectos[i].codigos[j].id,
				id:"c"+proyectos[i].codigos[j].id,
				idd:proyectos[i].codigos[j].id,
				call:cambiarCodigo});
		}
		}
		var temp={
                label: proyectos[i].nombre +"-p"+proyectos[i].id,
                id : "p"+proyectos[i].id,
                idd : proyectos[i].id,
                type:'PADRE',
                call: function(){},
                children: codigos_temp
            };
		o.push(temp);
		};
		
		 $('#tree1').tree('loadData', o);
		 treeSelectCodigoActivo();
		return o;
	
}
function getProyecto(id){
	   jQuery.ajax({
	         type: "GET",
	         url: "v1/proyectos/"+id,
	         contentType: "application/json; charset=utf-8",
	         beforeSend: function(xhr) { xhr.setRequestHeader("Authorization", "Basic " + btoa(currentUser.nombre + ":" + currentUser.password)); },		        
	         dataType: "json",
	         success: function (proyecto, status, jqXHR) {
	        	 currentStatusMsg=null;
	        	 currentProyecto=proyecto;
	        	 actualizar();
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.status+ "-"+jqXHR.statusText;
	        	 console.log(currentStatusMsg);
	        	 actualizar();
	         }
	   });
	
}
function cambiarCodigo(node){
	getCodigo(node.idd);
	getProyecto(node.parent.idd);
	
}


function createUsuario(nombre,password){
	var nuevo={id:"","nombre":nombre,"password":password};
	   jQuery.ajax({
	         type: "POST",
	         url: "v1/users",
	         contentType: "application/json; charset=utf-8",
	         data: JSON.stringify(nuevo),
	         dataType: "json",
	         success: function (usuario, status, jqXHR) {
	        	 currentStatusMsg="Creado usuario " + usuario.nombre +"-"+ usuario.id;
	        	 currentUser=usuario;
	        	 actualizar();
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.responseJSON.detail;
	        	 console.log(currentStatusMsg);
	        	 actualizar();
	        	 
	         }
	   });
	
}
function loginUsuario(nombre,password){
	   jQuery.ajax({
	         type: "POST",
	         url: "v1/users/login",
	         contentType: "application/json; charset=utf-8",
	         beforeSend: function(xhr) { xhr.setRequestHeader("Authorization", "Basic " + btoa(nombre + ":" + password)); },		        
	         dataType: "json",
	         success: function (usuario, status, jqXHR) {
	        	 currentStatusMsg="Usuario logado " + usuario.nombre +"-"+ usuario.id;
	        	 currentUser=usuario;
	        	 actualizar();
	        	 getProyectos();
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.responseJSON.detail;
	        	 console.log(currentStatusMsg);
	        	 actualizar();
	        	 
	         }
	   });
	
}

function saveCodigo(){
	   currentCodigo.code=editorCode.getDoc().getValue();
	   if(!currentCodigo.test){
		   currentCodigo.test={};  
	   }
	   currentCodigo.test.code= editorTest.getDoc().getValue();
	   currentCodigo.test.nombre="test."+currentCodigo.nombre;
	   
	   jQuery.ajax({
	         type: "POST",
	         url: "v1/codigos",
	         contentType: "application/json; charset=utf-8",
	         beforeSend: function(xhr) { xhr.setRequestHeader("Authorization", "Basic " + btoa(currentUser.nombre + ":" + currentUser.password)); },		        
	         data: JSON.stringify(currentCodigo),
	         dataType: "json",
	         success: function (codigo, status, jqXHR) {
	        	 currentStatusMsg="Grabado "+codigo.nombre;
	        	 currentCodigo=codigo;
	        	 actualizarCodigo();
	        	 testCodigo();
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.status+ "-"+jqXHR.statusText;
	        	 console.log(currentStatusMsg);
	             actualizar();
	         }
	   });
	
}
function compartirCodigo(nombreUsuario,whatToShare){
	    jQuery.ajax({
	         type: "POST",
	         url: "v1/proyectos/"+currentProyecto.id+"/codigos/"+currentCodigo.id+"/users/",
	         contentType: "application/json; charset=utf-8",
	         beforeSend: function(xhr) { xhr.setRequestHeader("Authorization", "Basic " + btoa(currentUser.nombre + ":" + currentUser.password)); },		        
	         data: [nombreUsuario,whatToShare],
	         dataType: "json",
	         success: function (codigo, status, jqXHR) {
	        	 currentStatusMsg="Compartido "+codigo.nombre +" con usuario " + nombreUsuario;
	        	 currentCodigo=codigo;
	        	 actualizarCodigo();
	        	 testCodigo();
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.status+ "-"+jqXHR.statusText;
	        	 console.log(currentStatusMsg);
	             actualizar();
	         }
	   });
	
}

function testCodigo(){
	if(currentCodigo){
	  document.getElementById('testBrowser').src = 'jasmineRunner.jsp?codigoId='+currentCodigo.id;
    }
}
