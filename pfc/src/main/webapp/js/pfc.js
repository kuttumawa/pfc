var currentProyectos;
var currentCodigo;
var currentStatusMsg;
var currentUser ={"id":1,"nombre":"user1"};
var currentProyecto;

function init(){
	
	 $('#tree1').tree({
	        data: []
	    });
	 $('#tree1').bind(
			    'tree.click',
			    function(event) {
			        var node = event.node;
			        console.log(node.name);
			        node.call(node);
			    }
			);
}
function actualizarCodigo(){
	actualizar();
	actualizarProyecto();
}
function actualizar(){
	if(currentProyecto){
		$('#currentProyecto').text(currentProyecto.nombre +"-"+currentProyecto.id);
	}else{
		$('#currentProyecto').text('');
	}
	if(currentCodigo){
		$('#codigoNombreId').text(currentCodigo.nombre);
   	    $('#codigoAreaId').val(currentCodigo.code);
   	    if(currentCodigo.test){
   	    	$('#testNombreId').text(currentCodigo.test.nombre);
   	   	    $('#testAreaId').val(currentCodigo.test.code);
   	    }else{
   	     $('#testNombreId').text('');
    	    $('#testAreaId').val('');
   	    }
	}else{
		$('#codigoNombreId').text('');
   	    $('#codigoAreaId').val('');
   	    $('#testNombreId').text('');
   	    $('#testAreaId').val('');
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
function executeCodigo(){
	
	   jQuery.ajax({
	         type: "GET",
	         url: "v1/codigos/"+currentCodigo.id+"/execute",
	         contentType: "application/json; charset=utf-8",
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
function revisarCodigo(){
	
	   jQuery.ajax({
	         type: "GET",
	         url: "v1/codigos/"+currentCodigo.id+"/revisar",
	         contentType: "application/json; charset=utf-8",
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

function borrarCodigo(id){
	
	   jQuery.ajax({
	         type: "DELETE",
	         url: "v1/codigos/"+id,
	         contentType: "application/json; charset=utf-8",
	         dataType: "json",
	         success: function (codigo, status, jqXHR) {
	        	 currentCodigo="Borrado OK";
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
	         url: "v1/proyectos?userId="+currentUser.id,
	         contentType: "application/json; charset=utf-8",
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

function treeProyectos(proyectos){
	var o=[];
	for (var i=0; i < proyectos.length; i++){
		console.log(proyectos[i].nombre);
		var codigos_temp=[];
		if(proyectos[i].codigos && proyectos[i].codigos.length>0){
		for (var j=0; j < proyectos[i].codigos.length; j++){
			console.log(proyectos[i].codigos[j].nombre);
			codigos_temp.push({label: proyectos[i].codigos[j].nombre+"-"+proyectos[i].codigos[j].id,
				id:proyectos[i].codigos[j].id,
				call:cambiarCodigo});
		}
		}
		var temp={
                label: proyectos[i].nombre +"-"+proyectos[i].id,
                id : proyectos[i].id,
                type:'PADRE',
                call:cambiarProyecto,
                children: codigos_temp
            };
		o.push(temp);
		};
		
		 $('#tree1').tree('loadData', o);
		return o;
	
}
function getProyecto(id){
	
	   jQuery.ajax({
	         type: "GET",
	         url: "v1/proyectos/"+id,
	         contentType: "application/json; charset=utf-8",
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
	getCodigo(node.id);
	getProyecto(node.parent.id);
	
}
function cambiarProyecto(node){
	getProyecto(node.id);
	
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
	         type: "GET",
	         url: "v1/users?user="+nombre+"&pass="+password,
	         contentType: "application/json; charset=utf-8",
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
	   currentCodigo.code=$("#codigoAreaId").val();
	   if(!currentCodigo.test){
		   currentCodigo.test={};  
	   }
	   currentCodigo.test.code=$("#testAreaId").val();
	   currentCodigo.test.nombre="test."+currentCodigo.nombre;
	   
	   jQuery.ajax({
	         type: "POST",
	         url: "v1/codigos",
	         contentType: "application/json; charset=utf-8",
	         data: JSON.stringify(currentCodigo),
	         dataType: "json",
	         success: function (codigo, status, jqXHR) {
	        	 currentStatusMsg="Grabado "+codigo.nombre;
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
