<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"	prefix="decorator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="EaSII Team">
    <meta name="description" content="TheEnd">
	
	<title>Systematic Map</title>
	<link rel="shortcut icon" href="<c:url value="/images/books.png" />" type="image/png">
	
	<decorator:head/>
	
    <!-- Bootstrap Core CSS -->
    <link href="<c:url value="/vendor/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="<c:url value="/vendor/metisMenu/metisMenu.min.css"/>" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<c:url value="/css/sb-admin-2.css" />" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<c:url value="/vendor/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css">
    
   	<link href="<c:url value="/css/systematicmap.css" />" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!-- jQuery -->
    <script src="<c:url value="/vendor/jquery/jquery.min.js" />"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js" />"></script>
    <!-- jQuery Validate-->
    <script src="<c:url value="/vendor/jquery/jquery.validate.min.js" />"></script>
    <script src="<c:url value="/vendor/jquery/localization/messages_pt_BR.min.js" />"></script>
    
    
    <script type="text/javascript">
		(function($){
			$(document).ready(function(){
				$.validator.addMethod("login", function(value, element) {
					  return this.optional(element) || /^[a-zA-Z0-9_]+$/.test(value);
				}, '<fmt:message key="invalid_login" />');
				$.validator.setDefaults({
				    errorClass: "control-label control-label-block",
				    onkeyup: function(element) { $(element).valid()},
				    highlight: function(element) {
				    	if(!$(element).closest('.form-group').is('.has-feedback')){
				    		 $(element).closest('.form-group').addClass('has-feedback');
					    }
					    
					    $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
					    var id = this.idOrName (element);
					    var id2 = id + '-men';
						id = id + '-icon';
						$('#'+id.toString()).remove();	
						$('#'+id2.toString()).remove();
						 
						$( element ).attr( "aria-describedby", id);
					    $(element).after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="false" id = "'+id2+'"></span>').after('<span id="'+id+'" class="sr-only">(error)</span>');						 
				    },
				    unhighlight: function(element) {
				    	if(!$(element).closest('.form-group').is('.has-feedback')){
				    		 $(element).closest('.form-group').addClass('has-feedback');
					    }
					    
						$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
						var id = this.idOrName (element);
						var id2 = id + '-men';
						id = id + '-icon';
						$('#'+id.toString()).remove();	
						$('#'+id2.toString()).remove();
						$( element ).attr( "aria-describedby", id);
				        $(element).after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="false" id = "'+id2+'"></span>').after('<span id="'+id+'" class="sr-only">(success)</span>'); 
				    },
				    errorPlacement: function (error, element) {
				        if (element.prop('type') === 'checkbox' || element.prop('type') === 'radio') {
				            error.insertAfter(element.parent());
				        } else {
				            error.insertAfter(element);
				        }
				    }
				});
			});
		})(jQuery);
	</script>
	
<script>
    $('.carousel').carousel({
        interval: 3000
    })
</script>
</head>
<body>
<!-- 	<div> -->
<!--         Navigation -->
        <div class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
            </div>
            <ul class="nav navbar-top-links navbar-right">
	            <li>
	            	 <a class="page-scroll" href="${linkTo[HomeController].home}"><fmt:message key="home" /></a>
	            </li>
            	<li>
            		 <a class="page-scroll" href="${linkTo[HomeController].login}"><fmt:message key="login" /></a>
            	</li>
            	<li>
            		 <a class="page-scroll" href="${linkTo[HomeController].create}"><fmt:message key="signup" /></a>
            	</li>
            	<li>
            		 <a class="page-scroll" href="#"><fmt:message key="contact" /></a>
            	</li>
            	
            </ul>	
<!--         </nav> -->
    </div>    

	<div class="container">
		<div class="row">
<!-- 			<div class="col-md-4 col-md-offset-4"> -->
					<div id="messages"></div>
					<c:if test="${not empty errors}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  						<span class="sr-only">Error:</span>
  						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
<!-- 						<button type="button" class="close" data-dismiss="alert">&times;</button> -->
						<c:forEach items="${errors}" var="error">
							<b><fmt:message key="${error.category}" /></b> - <fmt:message
								key="${error.message}" />
							<br />
						</c:forEach>
					</div>
				</c:if>
				<c:if test="${not empty notice}">
					<div class="alert alert-info alert-dismissible" role="alert">
<!-- 						<button type="button" class="close" data-dismiss="alert">&times;</button> -->
							<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<%-- <c:forEach items="${notices}" var="notice"> --%>
						<b><fmt:message key="${notice.category}" /></b> - <fmt:message
							key="${notice.message}" />
						<br />
						<%--</c:forEach> --%>
					</div>
				</c:if>
				
				<c:if test="${not empty warning}">
					<div class="alert alert-warning alert-dismissible" role="alert" id="warning">
						<button type="button" class="close" data-dismiss="alert" data-hide="alert">&times;</button>
						<b><fmt:message key="${warning.category}" /></b> - <fmt:message key="${warning.message}" />
						<br />
					</div>
				</c:if>
				
<%-- 				<decorator:body/>			 --%>
				
			</div>
					
		<decorator:body/>

	</div>

<%-- 	<decorator:body/>	 --%>
	
    <!-- Footer -->
    <footer>
        <div class="container text-center">
<!--             <p>Copyright &copy; Your Website 2014</p> -->
            <p>© 2015-2016. Todos os direitos reservados.</p>
            <p><a href="http://easii.ufpi.br">EASII</a> - Laborat&oacute;rio de Engenharia de Software e Inform&aacute;tica Industrial</p>
        </div>
    </footer>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="<c:url value="/vendor/metisMenu/metisMenu.min.js" />"></script>

    <!-- Custom Theme JavaScript -->
    <script src="<c:url value="/js/sb-admin-2.js" />"></script>
</body>

<script type="text/javascript" src="<c:url value="/js/capt.mining.useskill.4-oracle.js"/>"></script>
<script type="text/javascript">

		//caso utilize JQuery no sistema, utilize o Jquery.ready() e o paramatro "waitdomready" = false 
		jQuery(document).ready(function(){
			try{
				useskill_capt_onthefly({
					client: "THEEND", //nome abreviado do sistema monitorado
					version: 1, //versao do script de captura
		
					url: "http://easii.ufpi.br/oracle-test/backend/actions",
// 					url: "http://10.94.15.204:8080/oracle-test/backend/actions",
					waitdomready: false, //se for verdadeiro, aguarda o evento DOMContentLoaded ser disparado, caso contrario ja executa a captura
					sendactions: true, //enviar acoes para o servidor
					debug: false, //apresentar dados no console
					
					onthefly: true, //para scripts inseridos via onthefly
					plugin: false, //apenas para script via plugin do chrome
					
					captureback: false, //captura de eventos de voltar -> con
					capturehashchange: false, //capturar eventos de mudanca de hash
					jheat: false, //para sistemas baseados no jheat
					timetosubmit: 120, //periodicidade em segundos para envio de eventos
					
					//dados do usuario logado:
					username: "usuarioTeste", //pode ser retornado por funcao ou uma string
					role: "user", //pode ser retornado por funcao ou uma string,

					oracleElements: ["#teste1", "#teste2", "#teste3", ".abc"]
				});
			} catch(e) {
				console.log('usability analytics stopped!');
			}
		});
	</script>

</html>