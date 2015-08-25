<link rel="stylesheet" href="css/jquery-ui.min.css">
<script src="js/jquery-ui.min.js"></script>
  
  <style>
   /* body { font-size: 62.5%; }*/
    label, input { display:block; }
    /* input.text { margin-bottom:12px; width:95%; padding: .4em; } */
    fieldset { padding:0; border:0; margin-top:25px; }
    h1 { font-size: 1.2em; margin: .6em 0; }
    div#users-contain { width: 350px; margin: 20px 0; }
    div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
    div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
  </style>
  <script>
  var dialog,dialogLogin,dialogSignup,shareCodigo;
  $(function() {
     var form,
      name = $( "#name" ),
      descripcion = $( "#descripcion" ),
      login_user = $( "#login_user" ),
      login_password = $( "#login_password" ),
      signup_user = $( "#signup_user" ),
      signup_password = $( "#signup_password" ),
      signup_password_bis = $( "#signup_password_bis" ),
      share_user=$( "#share_user" ),
      allFields = $( [] ).add( name ).add( descripcion ).add( login_user ).
                   add( login_password ).add( signup_user ).add( signup_password ).add( signup_password_bis ).add( share_user ),
      tips = $( ".validateTips" );
    var x=1;
    function updateTips( t ) {
      tips
        .text( t )
        .addClass( "ui-state-highlight" );
      setTimeout(function() {
        tips.removeClass( "ui-state-highlight", 1500 );
      }, 500 );
    }
 
    function checkLength( o, n, min, max ) {
      if ( o.val().length > max || o.val().length < min ) {
        o.addClass( "ui-state-error" );
        updateTips( "Length of " + n + " must be between " +
          min + " and " + max + "." );
        return false;
      } else {
        return true;
      }
    }
 
    function checkRegexp( o, regexp, n ) {
      if ( !( regexp.test( o.val() ) ) ) {
        o.addClass( "ui-state-error" );
        updateTips( n );
        return false;
      } else {
        return true;
      }
    }
    function checkEquals( o, p, n ) {
        if ( !(o.val()===p.val())) {
          o.addClass( "ui-state-error" );
          p.addClass( "ui-state-error" );
          updateTips( n );
          return false;
        } else {
          return true;
        }
      }
 
    function doit() {
      var valid = true;
      allFields.removeClass( "ui-state-error" );
 
     
     // valid = valid && checkRegexp( name, /^[a-z]([0-9a-z_\s])+$/i, "Username may consist of a-z, 0-9, underscores, spaces and must begin with a letter." );
     // valid = valid && checkRegexp( descripcion, /^([0-9a-zA-Z])+$/, "descripcion field only allow : a-z 0-9" );
      
      if(x==1){
    	  valid = valid && checkLength( name, "username", 1, 16 );
          valid = valid && checkLength( descripcion, "descripcion", 0, 250 );
      }else if (x==2){
    	  valid = valid && checkLength( name, "username", 1, 16 );
          valid = valid && checkLength( descripcion, "descripcion", 0, 250 );
	  }else if (x==3){//login
  		valid = valid && checkLength( login_user, "user", 3, 16 )&& checkLength( login_password, "password", 3, 16 );
  	  }else if (x==4){//sign up
  		valid = valid && checkLength( signup_user, "user", 3, 16 )&& checkLength( signup_password, "password", 3, 16 );
  	    valid = valid && checkEquals(signup_password,signup_password_bis,"Passwords diferentes");
  	  }else if (x==5){//share
    		valid = valid && checkLength( share_user, "user", 3, 16 );
      	  }
     
     
      if ( valid ) {
    	if(x==1) createProyecto(name.val(),descripcion.val());
    	else if (x==2){
    		createCodigo(name.val(),descripcion.val());
    	}
    	else if (x==3){//login
    		loginUsuario(login_user.val(),login_password.val());
    		dialogLogin.dialog( "close" );
    	}else if (x==4){//sign up
    		createUsuario(signup_user.val(),signup_password.val());
    		dialogSignup.dialog( "close" );
    	
        }else if (x==5){//sign up
  		    compartirCodigo(share_user.val());
  		    dialogShare.dialog( "close" );
  	    }
           
        dialog.dialog( "close" );
      }
      return valid;
    }
 
    dialog = $( "#dialog-form" ).dialog({
      autoOpen: false,
      height: 300,
      width: 350,
      modal: true,
      buttons: {
        "Crear": doit,
        Cancel: function() {
          dialog.dialog( "close" );
        }
      },
      close: function() {
    	$('form')[0].reset();
        allFields.removeClass( "ui-state-error" );
      }
    });
    dialogLogin = $( "#dialog-form-login" ).dialog({
        autoOpen: false,
        height: 400,
        width: 450,
        modal: true,
        buttons: {
          "Registrarse": doit,
          Cancel: function() {
        	  dialogLogin.dialog( "close" );
          }
        },
        close: function() {
          $('form')[1].reset();
          allFields.removeClass( "ui-state-error" );
        }
      });
     dialogSignup = $( "#dialog-form-signup" ).dialog({
        autoOpen: false,
        height: 400,
        width: 450,
        modal: true,
        buttons: {
          "Crear": doit,
          Cancel: function() {
        	  dialogSignup.dialog( "close" );
          }
        },
        close: function() {
          $('form')[2].reset();
          allFields.removeClass( "ui-state-error" );
        }
      });
     dialogShare = $( "#dialog-form-share" ).dialog({
         autoOpen: false,
         height: 400,
         width: 450,
         modal: true,
         buttons: {
           "Crear": doit,
           Cancel: function() {
         	  dialogShare.dialog( "close" );
           }
         },
         close: function() {
           $('form')[3].reset();
           allFields.removeClass( "ui-state-error" );
         }
       });
 
    form = dialog.find( "form" ).on( "submit", function( event ) {
      event.preventDefault();
    });
 
    $( "#create-proyecto" ).button().on( "click", function() {
        x=1;
    	dialog.dialog( "open" );
    });
    
    $( "#create-codigo" ).button().on( "click", function() {
        x=2;
    	dialog.dialog( "open" );
      });
    $('#loginId').on( "click", function() {
        x=3;
    	dialogLogin.dialog( "open" );
      });
    $( "#signupId" ).on( "click", function() {
        x=4;
    	dialogSignup.dialog( "open" );
      });
    $( "#shareButton" ).on( "click", function() {
        x=5;
        dialogShare.dialog( "open" );
      });
   


   
        $( "#create-proyecto" ).button({
          text: true,
          icons: {
            primary: "ui-icon-circle-plus"
          }
        });
        $( "#create-codigo" ).button({
          text: true,
          icons: {
            primary: "ui-icon-circle-plus"
          }
        });
        $( "#delete-proyecto" ).button({
            text: true,
            icons: {
              primary: "ui-icon-circle-minus"
            }
          });
          $( "#delete-codigo" ).button({
            text: true,
            icons: {
              primary: "ui-icon-circle-minus"
            }
          });
        $( "#saveButton" ).button({
            text: true,
            icons: {
              primary: "ui-icon-disk"
            }
          });
        $( "#executeButton" ).button({
            text: true,
            icons: {
              primary: "ui-icon-play"
            }
          });
        $( "#revisarButton" ).button({
            text: true,
            icons: {
              primary: "ui-icon-play"
            }
          });
        $( "#runTestButton" ).button({
            text: true,
            icons: {
              primary: "ui-icon-play"
            }
          });
        $( "#shareButton" ).button({
            text: true,
            icons: {
              primary: "ui-icon-person"
            }
          });
        
      

    
  });
  
  
  $(function() {
    
    $( "#share_user" ).autocomplete({
      source: "v1/users",
      minLength: 2,
      select: function( event, ui ) {
        console.log( ui.item ?
          "Selected: " + ui.item.nombre + " aka " + ui.item.id :
          "Nothing selected, input was " + this.value );
      }
    });
  });
 
  </script>
<div id="dialog-form" title="Nuevo">
 
  <form>
    <fieldset>
      <label for="name">Nombre</label>
      <input type="text" name="name" id="name"  class="text ui-widget-content ui-corner-all">
      <label for="descripcion">Descripción</label>
      <textarea id="descripcion" name="descripcion"  class="text ui-widget-content ui-corner-all"></textarea>
      
      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
 
</div>

<div id="dialog-form-login" title="Entrar">
 <form>
    <fieldset>
      <div class="validateTips"></div>
      <label for="login_user">Usuario</label>
      <input type="text" name="login_user" id="login_user"  class="text ui-widget-content ui-corner-all">
      <label for="login_password">Password</label>
      <input type="password" name="login_password" id="login_password" value="" class="text ui-widget-content ui-corner-all">
      
      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>
<div id="dialog-form-signup" title="Registrarse">
 <form>
    <fieldset>
      <div class="validateTips"></div>
      <label for="signup_user">Usuario</label>
      <input type="text" name="signup_user" id="signup_user"  class="text ui-widget-content ui-corner-all">
      <label for="name">Email</label>
      <input type="text" name="email" id="email"  class="text ui-widget-content ui-corner-all">
      <label for="signup_password">Password</label>
      <input type="password" name="signup_password" id="signup_password"  class="text ui-widget-content ui-corner-all">
      <label for="password">Confirmar Password</label>
      <input type="password" name="signup_password_bis" id="signup_password_bis"  class="text ui-widget-content ui-corner-all">
      
      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
 
</div>
<div id="dialog-form-share" title="Compartir">
 <form>
    <fieldset>
      <div class="validateTips"></div>
      <label for="share_user">Usuario</label>
      <input type="text" name="share_user" id="share_user"  class="text ui-widget-content ui-corner-all">
      
      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>
