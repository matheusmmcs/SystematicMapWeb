<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
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
    <meta name="description" content="">
    <meta name="author" content="">
	
	<title>Systematic Map</title>
	
	<decorator:head/>
	
	
    <!-- Bootstrap Core CSS -->
    <link href="<c:url value="/vendor/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <!-- MetisMenu CSS -->
    <link href="<c:url value="/vendor/metisMenu/metisMenu.min.css" />" rel="stylesheet">
    <!-- Timeline CSS -->
<%--     <link href="<c:url value="/css/timeline.css" />" rel="stylesheet"> --%>
    <!-- Custom CSS -->
    <link href="<c:url value="/css/sb-admin-2.css" />" rel="stylesheet">
    <!-- Morris Charts CSS -->
<%--     <link href="<c:url value="/vendor/morris/morris.css" />" rel="stylesheet"> --%>
    <!-- Custom Fonts -->
    <link href="<c:url value="/vendor/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css">
    
    <!-- DataTables CSS -->
    <link href="<c:url value="/vendor/datatables/css/dataTables.bootstrap.css" />" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="<c:url value="/vendor/datatables/css/dataTables.responsive.css" />" rel="stylesheet">
    
    <!-- Autocomplete -->
    <link href="<c:url value="/vendor/chosen/chosen.min.css" />" rel="stylesheet">
    <link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css" rel="stylesheet" />

	<link href="<c:url value="/css/bootstrap-fileupload.min.css"/>" rel="stylesheet" type="text/css"/>
	<link href="<c:url value="/css/systematicmap.css" />" rel="stylesheet">
	
	
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    
    <!-- jQuery -->
    <script src="<c:url value="/vendor/jquery/jquery.min.js" />"></script>
    <!-- jQuery Validate-->
    <script src="<c:url value="/vendor/jquery/jquery.validate.min.js" />"></script>
    <script src="<c:url value="/vendor/jquery/localization/messages_pt_BR.min.js" />"></script>
    
    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/js/bootstrap-fileupload.min.js"/>"></script>
    
    <script src="<c:url value="/vendor/datatables/js/jquery.dataTables.min.js" />"></script>
    <script src="<c:url value="/vendor/datatables/js/dataTables.bootstrap.min.js" />"></script>
    
    <script type="text/javascript">
		(function($){
			$(document).ready(function(){
				$.validator.addMethod("login", function(value, element) {
					  return this.optional(element) || /^[a-zA-Z0-9_]+$/.test(value);
				}, "Utilize somente letras, números e  _");
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
    
</head>
<body>
	<div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${linkTo[HomeController].home}"><fmt:message key="mapstudy.web" /></a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
            	<li>
            		<span class="color-primary">
                		${userInfo.user.name}
                	</span>
            	</li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="${linkTo[MapStudyController].list}"><i class="fa fa-user fa-fw"></i> <fmt:message key="mapstudy.my.short" /></a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="${linkTo[HomeController].logout}"><i class="fa fa-sign-out fa-fw"></i> <fmt:message key="logout" /></a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li class="sidebar-search">
                            <div class="input-group custom-search-form">
                                <input type="text" class="form-control" placeholder="Search...">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button" style="padding: 9px;">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                            </div>
                            <!-- /input-group -->
                        </li>
                        <li>
                            <a href="${linkTo[MapStudyController].list}"><i class="fa fa-dashboard fa-fw"></i> <fmt:message key="mapstudy.my.short" /></a>
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>
        
        <div id="page-wrapper">

	<c:if test="${not empty errors}">
		<div class="alert alert-danger">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<c:forEach items="${errors}" var="error">
				<b><fmt:message key="${error.category}"/></b> - ${error.message} <br/>
			</c:forEach>
		</div>
	</c:if>
	
	<c:if test="${not empty notice}">
		<div class="alert alert-success"> 
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<b><fmt:message key="${notice.category}"/></b> - ${notice.message} <br/>
			 
		</div>
	</c:if>

			<decorator:body/>
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- Metis Menu Plugin JavaScript -->
    <script src="<c:url value="/vendor/metisMenu/metisMenu.min.js" />"></script>
    <script src="<c:url value="/vendor/chosen/chosen.jquery.min.js" />"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>

    <!-- Morris Charts JavaScript -->
<%--     <script src="<c:url value="/vendor/raphael/raphael-min.js" />"></script> --%>
<%--     <script src="<c:url value="/vendor/morris/morris.min.js" />"></script> --%>
<%--     <script src="<c:url value="/js/morris-data.js" />"></script> --%>

    <!-- Custom Theme JavaScript -->
    <script src="<c:url value="/js/sb-admin-2.js" />"></script>
    
    <script>
		$(document).ready(function() {
			//tabela personalizada
		    $('.personalized-table').dataTable( {
		        "scrollY":        "300px",
		        "scrollCollapse": true,
		        "paging":         false
		    });
		    $('.personalized-table-simple').dataTable();

		    //autocomplete
		    $('.chosen-select').chosen({
		    	allow_single_deselect:true,
		    	search_contains:true,
		    	no_results_text:'<fmt:message key="${autocomplete.nothing}"/>'
			});

		    function formatState (state) {
		    	  if (!state.id) { return state.text; }
		    	  var $state = $(
		    	    '<span class="select2-name">' + state.text + '<i class="select2-email">' + $(state.element).data('email') + '</i></span>'
		    	  );
		    	  
		    	  console.log(state);
		    	  return $state;
		   	};
		    $('.select2').select2({
		    	 allowClear: true,
		    	 templateResult: formatState
			});
		});
	</script>
</body>
</html>