var currentProyectos;
var currentCodigo;
var currentStatusMsg;
var currentUser ={"id":1,"nombre":"user1"};
var currentProyecto;

function actualizar(){
	if(currentCodigo){
		$('#codigoNombreId').text(currentCodigo.nombre);
   	    $('#codigoAreaId').val(currentCodigo.code);
   	    if(currentCodigo.test){
   	    	$('#testNombreId').text(currentCodigo.test.nombre);
   	   	    $('#testAreaId').val(currentCodigo.test.code);
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
	if(currentProyecto){
		$('#currentProyecto').text(currentProyecto.nombre +"-"+currentProyecto.id);
		$(function() {
		    $('#tree1').tree({
		        data: JSON.parse(currentProyecto.tree)
		    });
		});
	}else{
		$('#currentProyecto').text('');
	}
	if(currentStatusMsg){
		$('#currentStatusMsgId').text(currentStatusMsg);
	}else{
		$('#currentStatusMsgId').text('');
	}
	getProyectos();
}
function actualizarProyectos(){
	if(currentProyectos) treeProyectos(currentProyectos);
}

function getCodigo(id){
	
	   jQuery.ajax({
	         type: "GET",
	         url: "http://localhost:8080/pfc/v1/codigos/1",
	         contentType: "application/json; charset=utf-8",
	         dataType: "json",
	         success: function (codigo, status, jqXHR) {
	        	 currentStatusMsg=null;
	        	 currentCodigo=codigo;
	        	 actualizar();
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.status+ "-"+jqXHR.statusText;
	        	 console.log(currentStatusMsg);
	        	 actualizar();
	         }
	   });
	
}

function createCodigo(){
	
	   jQuery.ajax({
	         type: "POST",
	         url: "http://localhost:8080/pfc/v1/codigos",
	         contentType: "application/json; charset=utf-8",
	         data: JSON.stringify({"nombre":"PruebaPost","code":$('#codigoAreaId').val()}),
	         dataType: "json",
	         success: function (codigo, status, jqXHR) {
	        	 currentStatusMsg="Creado "+codigo.nombre;
	        	 currentCodigo=codigo;
	        	 actualizar();
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
	         url: "http://localhost:8080/pfc/v1/codigos/"+id,
	         contentType: "application/json; charset=utf-8",
	         dataType: "json",
	         success: function (codigo, status, jqXHR) {
	        	 currentCodigo="Borrado OK";
	        	 actualizar();
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
	         url: "http://localhost:8080/pfc/v1/proyectos",
	         contentType: "application/json; charset=utf-8",
	         data: JSON.stringify(nuevo),
	         dataType: "json",
	         success: function (proyecto, status, jqXHR) {
	        	 currentStatusMsg="Creado proyecto " + proyecto.nombre;
	        	 currentProyecto=proyecto;
	        	 actualizarProyecto();
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.status+ "-"+jqXHR.statusText;
	        	 console.log(currentStatusMsg);
	        	 actualizarProyecto();
	         }
	   });
	
}

function getProyectos(){
	
	   jQuery.ajax({
	         type: "GET",
	         url: "http://localhost:8080/pfc/v1/proyectos?userId="+currentUser.id,
	         contentType: "application/json; charset=utf-8",
	         dataType: "json",
	         success: function (proyectos, status, jqXHR) {
	        	 currentStatusMsg=null;
	        	 currentProyectos=proyectos;
	        	 actualizarProyectos();
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
		var temp={
                label: proyectos[i].nombre,
                children: [
                    { label: 'child1' },
                    { label: 'child2' }
                ]
            };
		o.push(temp);
		};
		 $('#tree1').tree({
		        data: o
		    });
		 $('#tree1').tree('loadData', o);
		return o;
	
}