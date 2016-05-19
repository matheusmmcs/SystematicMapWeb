<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
	$(document).ready(function(){
		var alternatives = function(questionId, div){
			var address = "${linkTo[ExtractionController].alternatives}";
			var param = {"questionId": questionId};
			$.ajax({
		        url: address,
		        type: 'GET',
				data: param,
		        success: function (data) {
// 			        console.log(data);
			        activeDiv(div, data, questionId);
		        },
				error: function(e){
					console.error(e);
				}
			});	
		};		
		
		var activeDiv = function(divId, alternatives, questionId){
			var Obj = $('#' + divId);
			Obj.empty();
			
			 	var content =	'<p /><div class="widget widget_tally_box">' +
							'<div class="x_panel">' +
							  '<div class="x_title">' +
							    '<b>Alternativas</b>' +
// 							    '<ul class="nav navbar-right panel_toolbox in">' +
// 							      '<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>' +
// 							      '<li><a class="close-link"><i class="fa fa-close"></i></a></li>' +
// 							    '</ul>' +
							    '<div class="clearfix"></div>' +
							  '</div>' +
							  '<div class="x_content">' +
							    '<div>' +
							      '<ul class="list-inline widget_tally">';
							      for (var int = 0; int < alternatives.length; int++) {
							    	  content += '<li><p><span class="month2 alternative-value" alt-id='+alternatives[int].id+'>'+alternatives[int].value+'</span></p></li>';							      }
							      content += '</ul>' +
							    '</div>' +
							  '</div>' +
							'</div>'+			
			 		'</div>';	
			 		
			 		Obj.append(content);
			
		};
		
// 		var listIdsXAndY = function (){
// 			var listX = $('#divquestion_x').find('.alternative-value'); 
// 			var listIdsX = [];
// 			$.each(listX, function(){
// 				listIdsX.push($(this).attr('alt-id'))
// 			});
			
// 			var listY = $('#divquestion_y').find('.alternative-value'); 
// 			var listIdsY = [];
// 			$.each(listY, function(){
// 				listIdsY.push($(this).attr('alt-id'))
// 			});
			
			
// 			console.log(listIdsX);
// 			console.log(listIdsY);
// 		}
		
		$(document).on('change', '.select-quest', function(){
			var question_id = $(this).val();
			var div = 'div' + $(this).attr('id');
			
			alternatives(question_id, div);			
		});
		
		var bubbleData = function (mapid, q1, q2){
			var address = "${linkTo[GraphicsController].bubble}";
			var param = {"mapid": mapid, "q1": q1, "q2": q2};
			$.ajax({
		        url: address,
		        type: 'GET',
				data: param,
		        success: function (data) {
			        console.log(data);
		        },
				error: function(e){
					console.error(e);
				}
			});	
		};
		
		$(document).on('click', '.buttonbubble', function(event){
			event.preventDefault();
			
			var q1 = $('#question_x').val();
			var q2 = $('#question_y').val();
			var mapid = $('#mapid').html();
			
			console.log(q1, q2, mapid);		
			
			bubbleData(mapid, q1, q2);	
		});
		
		
		
	});
</script>	

<ol class="breadcrumb u-margin-top">
  <li><a href="<c:url value="/" />"><fmt:message key="home"/></a></li>
  <li><a href="${linkTo[MapStudyController].show(map.id)}"><fmt:message key="mapstudy.details"/></a></li>
  <li class="active"><fmt:message key="mapstudy.report"/></li>
</ol>

<h3 class="color-primary">
	<fmt:message key="mapstudy.report.results"/> - ${map.title}
	<a id="return" class="btn btn-default pull-right" href="${linkTo[MapStudyController].show(map.id)}"><fmt:message key="button.back"/></a>
</h3>

<span id="mapid" class="hide">${map.id}</span>

<div class="row">
	<div class="col-lg-12">	
		<div class="panel panel-primary">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.graphs"/></b>
			</div>
			<div class="panel-body">
<!-- 		<div class="col-md-6 widget widget_tally_box"> -->
<!--           <div class="x_panel fixed_height_100"> -->
				<div class="x_panel">
				  <div class="x_title">
				 		<b>Bubble Plot - Questões</b>
				    <ul class="nav navbar-right panel_toolbox">
				      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
				      <li><a class="close-link"><i class="fa fa-close"></i></a></li>
				    </ul>
				    <div class="clearfix"></div>
				  </div>
				  <div class="x_content">		
				    <div>				    
				    	<div class="form-group">
							<div class="row">
								<div class="col-md-6">
									<label for="question_x" class="">Eixo X</label> 									
									<select class="form-control select-quest" name="question_x" id="question_x">
										<option selected value="-1">Selecione uma questão</option>
										<c:forEach var="q" items="${questions}">
											<option value="${q.id}">${q.name}</option>
										</c:forEach>
									</select>
									
									<!-- 						Questão X -->				
								<div id="divquestion_x"></div>		
									
								</div>
								
								<div class="col-md-6">
									<label for="question_y" class="">Eixo Y</label> 
									<select class="form-control select-quest" name="question_y" id="question_y">
										<option selected value="-1">Selecione uma questão</option>
										<c:forEach var="q" items="${questions}">
											<option value="${q.id}">${q.name}</option>
										</c:forEach>
									</select>
									
								<!-- 						Questão Y -->
								<div id="divquestion_y"></div>	
									
								</div>
							</div>										
						</div>	
						<div class="form-group row">
							<div class="col-md-12">	
								<hr />
						    	<a href="#" class="btn btn-primary buttonbubble"><i class="fa fa-print"></i> Exibir Gráfico </a>
						    	<a href="#" class="btn btn-primary"><i class="fa fa-cloud-download"></i> Exportar (.csv)</a>
						    	<a href="#" class="btn btn-primary"><i class="fa fa-cloud-upload"></i> Importar (.csv)</a>
				    		</div>
						</div>
				    </div>
				  </div>
				</div>			
<!-- 		</div> -->
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

$('.close-link').click(function () {
    var $BOX_PANEL = $(this).closest('.x_panel');

    $BOX_PANEL.remove();
});

//Panel toolbox
$(function () {
    $('.collapse-link').on('click', function() {
        var $BOX_PANEL = $(this).closest('.x_panel'),
            $ICON = $(this).find('i'),
            $BOX_CONTENT = $BOX_PANEL.find('.x_content');
        
        // fix for some div with hardcoded fix class
        if ($BOX_PANEL.attr('style')) {
            $BOX_CONTENT.slideToggle(200, function(){
                $BOX_PANEL.removeAttr('style');
            });
        } else {
            $BOX_CONTENT.slideToggle(200); 
            $BOX_PANEL.css('height', 'auto');  
        }

        $ICON.toggleClass('fa-chevron-up fa-chevron-down');
    });

    $('.close-link').click(function () {
        var $BOX_PANEL = $(this).closest('.x_panel');

        $BOX_PANEL.remove();
    });
});
</script>		