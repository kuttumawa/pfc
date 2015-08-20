
var currentCodigo;
var currentStatusMsg;
var currentUser ={"id":null,"nombre":"user1"};
var currentProyecto={"id":"","nombre":"X","descripcion":"XX"}

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
	
}
function actualizarProyecto(){
	alert('Actualizando el proyecto');
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

function createProyecto(){
	
	   jQuery.ajax({
	         type: "POST",
	         url: "http://localhost:8080/pfc/v1/proyectos",
	         contentType: "application/json; charset=utf-8",
	         data: JSON.stringify(currentProyecto),
	         dataType: "json",
	         success: function (proyecto, status, jqXHR) {
	        	 currentStatusMsg="Creado proyecto " + proyecto.nombre;
	        	 currentProyecto=proyecto;
	        	 actualizarProyecto();
	         },

	         error: function (jqXHR, status) {
	        	 currentStatusMsg="Error: "+ jqXHR.status+ "-"+jqXHR.statusText;
	        	 console.log(currentStatusMsg);
	             actualizar();
	         }
	   });
	
}