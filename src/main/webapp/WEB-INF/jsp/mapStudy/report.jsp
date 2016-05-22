<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="<c:url value="/vendor/highcharts/highcharts.js" />"></script>
<!-- <script src="https://code.highcharts.com/highcharts-3d.js"></script> -->
<%-- <script src="<c:url value="/vendor/highcharts/highcharts-3d.js" />"></script> --%>
<script src="<c:url value="/vendor/highcharts/exporting.js" />"></script>

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
		
		var graphPieView = function (sources, id){
			// Build the chart
	        $('#'+id).highcharts({
	            chart: {
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: false,
	                type: 'pie'
	            },
	            title: {
	                text: sources.title
	            },
// 	            tooltip: {
// 	                //pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
// 	            	pointFormat: '<b>{point.percentage:.1f}%</b>'
// 	            },
		tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.percent:.2f}%</b> of total<br/>'
        },
	            plotOptions: {
	            	pie: {
	                    allowPointSelect: true,
	                    cursor: 'pointer',
	                    dataLabels: {
	                        enabled: true,
	                        format: '{point.name}: {point.y}'
	                    },
	                    showInLegend: true
	                }
	            },
	            series: [{
	                name: sources.name,
	                colorByPoint: sources.colorByPoint,
	                data: sources.data//,
// 	                drilldown: sources.data.drilldown
	            }],
	            drilldown :{
	            	series: sources.series
	            }
	        });
		};
		
		var graphColumnView = function (data, divId){
			$('#'+divId).highcharts({
		        chart: {
		            type: 'column',
		            options3d: {
		                enabled: true,
		                alpha: 10,
		                beta: 25,
		                depth: 70
		            }
		        },
		        title: {
		            text: data.title
		        },
		        subtitle: {
		            text: data.subTitle
		        },
		        plotOptions: {
		            column: {
		                depth: 25
		            }
		        },
		        xAxis: {
		            categories: data.categories
		        },
		        yAxis: {
		            title: {
		                text: data.yAxis
		            }
		        },
		        series: [{
		            name: data.name,
		            data: data.data
		        }]
		    });
		};
		
		var graphData = function (mapid, address, divId, type){
			var param = {"mapid": mapid};
			$.ajax({
		        url: address,
		        type: 'GET',
				data: param,
		        success: function (data) {
		        	if (type === 'pie'){
		        		graphPieView(data, divId);
		        	}else if (type == 'column'){
		        		graphColumnView(data, divId);
		        	}
		        	
// 			        console.log(data);
		        },
				error: function(e){
					console.error(e);
				}
			});	
		};
		
		var activeGraph = function (graph){
			var mapid = $('#mapid').html();
			
			switch (graph) {
			case "0":
				var address = "${linkTo[GraphicsController].articlesSources}";
				var divId = "pie-articles-source";
				
				graphData(mapid, address, divId, 'pie');
				break;
				
			case "1":
				var address = "${linkTo[GraphicsController].articlesEvaluates}";
				var divId = "pie-articles-selection";
				
				graphData(mapid, address, divId, 'pie');
				break;
			case "2":
				var address = "${linkTo[GraphicsController].articlesRefine}";
				var divId = "pie-articles-refine";
				
				graphData(mapid, address, divId, 'pie');
				break;
			case "3":
				var address = "${linkTo[GraphicsController].articlesYear}";
				var divId = "column-articles-year";
				
				graphData(mapid, address, divId, 'column');
				break;
			default:
				break;
			}
		};	
		
		 $('.collapse-link').on('click', function() {
		        var $BOX_PANEL = $(this).closest('.x_panel'),
		            $ICON = $(this).find('i'),
		            $BOX_CONTENT = $BOX_PANEL.find('.x_content');
		        
		        
		        // fix for some div with hardcoded fix class
		        if ($BOX_PANEL.attr('style')) {
		            $BOX_CONTENT.slideToggle(200, function(){
		                $BOX_PANEL.removeAttr('style');
		            });
		            // se possuir gráfico fazer chamada
		            console.log('chamada de gráfico');
		            
		            var graph = $BOX_PANEL.attr('graph');
		            
		            console.log(graph);
		            
		            activeGraph(graph);
		            
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
			
<!-- 			BUBBLE -->
			
<!-- 		<div class="widget widget_tally_box"> -->
<!--           <div class="x_panel fixed_height_100"> -->
				<div class="x_panel" style="height: auto;">
				  <div class="x_title">
				 		<b>Bubble Plot - Questões</b>
				    <ul class="nav navbar-right panel_toolbox">
				      <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
				      <li><a class="close-link"><i class="fa fa-close"></i></a></li>
				    </ul>
				    <div class="clearfix"></div>
				  </div>
				  <div class="x_content" style="display: none;">		
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


<!-- 			PIE ARTICLES SOURCE -->

			<div class="widget widget_tally_box">
				<div class="x_panel" style="height: auto;" graph="0">
				  <div class="x_title">
				 		<b>Artigos por base de busca</b>
				    <ul class="nav navbar-right panel_toolbox">
				      <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
				      <li><a class="close-link"><i class="fa fa-close"></i></a></li>
				    </ul>
				    <div class="clearfix"></div>
				  </div>
				  <div class="x_content" style="display: none;">		
				    <div id="pie-articles-source"></div>
				    
<!-- 				    <a href="#" class="btn btn-primary btnArticlesSources"><i class="fa fa-print"></i> Exibir Gráfico </a> -->
				    
				  </div>
				</div>			
			</div>

<!-- 			PIE ARTICLES ACCEPET AND REJECT -->

			<div class="widget widget_tally_box">
				<div class="x_panel" style="height: auto;" graph="1">
				  <div class="x_title">
				 		<b>Artigos fase de seleção</b>
				    <ul class="nav navbar-right panel_toolbox">
				      <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
				      <li><a class="close-link"><i class="fa fa-close"></i></a></li>
				    </ul>
				    <div class="clearfix"></div>
				  </div>
				  <div class="x_content" style="display: none;">		
				    <div id="pie-articles-selection"></div>
				    
<!-- 				    <a href="#" class="btn btn-primary btnArticlesSelection"><i class="fa fa-print"></i> Exibir Gráfico </a> -->
				    
				  </div>
				</div>			
			</div>

<!-- 			PIE ARTICLES FILTER -->
			<div class="widget widget_tally_box">
				<div class="x_panel" style="height: auto;" graph="2">
				  <div class="x_title">
				 		<b>Artigos refinados</b>
				    <ul class="nav navbar-right panel_toolbox">
				      <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
				      <li><a class="close-link"><i class="fa fa-close"></i></a></li>
				    </ul>
				    <div class="clearfix"></div>
				  </div>
				  <div class="x_content" style="display: none;">		
				    <div id="pie-articles-refine"></div>
				    
<!-- 				    <a href="#" class="btn btn-primary btnArticlesRefine"><i class="fa fa-print"></i> Exibir Gráfico </a> -->
				    
				  </div>
				</div>			
			</div>
			
			<!-- 			COLUMN ARTICLES FILTER -->
			<div class="widget widget_tally_box">
				<div class="x_panel" style="height: auto;" graph="3">
				  <div class="x_title">
				 		<b>Publicações por Ano</b>
				    <ul class="nav navbar-right panel_toolbox">
				      <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
				      <li><a class="close-link"><i class="fa fa-close"></i></a></li>
				    </ul>
				    <div class="clearfix"></div>
				  </div>
				  <div class="x_content" style="display: none;">		
				    <div id="column-articles-year"></div>
				  </div>
				</div>			
			</div>
			

			</div>
		</div>
	</div>
</div>