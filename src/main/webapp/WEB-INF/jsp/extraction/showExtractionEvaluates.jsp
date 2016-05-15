<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
$(document).ready(function(){
	var tableToEvaluate = $('.datatable-to-evaluate').DataTable({
        "initComplete": function () {
            var api = this.api();
            var data = api.data().toArray();
            api.clear();
            for (var d in data) {
                api.row.add(data[d]);
            }
            api.draw();
            tableToEvaluate = api;
        }
    });
	var tableEvaluated = $('.datatable-evaluated').DataTable();
// 	{
//     "scrollY":        "300px",
//     "scrollCollapse": true,
//     "paging":         false
// }

		
	var actualizeArticle = function(article, extraction){
		console.log('entra atualiza article', article);
		//alterar a url para caso seja realizado F5
		var url = window.location.href;
		url = url.substr(0, url.lastIndexOf('/')) + '/' + article.id;

		console.log(url);
		window.history.pushState("", "", url);
		//window.location.reload();

		//alterar dados do artigo
		$('#articleReadId').html(article.id);
		$('#articleReadTitle').html(article.title);
		$('#articleReadAbstract').html(article.abstrct);
		$('#articleReadKeywords').html(article.keywords);
		$('#articleReadSource').html(article.source);
		$('#articleReadAuthor').html(article.author);


		//funcoes auxiliares para evitar repeticao de codigo
		var changeFormsIds = function(formId, article){
			$('#articleid').val(article.id);
		}

		var setQuestions = function (extraction){
			if (extraction.length > 0){
				$.each(extraction, function( index, elemento ) {
					console.log('set');
					  console.log(elemento.question.id + " | " + elemento.question.type + " | " + elemento.question.name);

					  if (elemento.question.type == 'LIST'){
// 						  var alternative_selected = elemento.alternative;
// 						  '<option selected="selected" value="15">2</option>'
						  $.each(elemento.question.alternatives, function( index2, elemento2 ) {
							  if (elemento2.value == elemento.alternative.value){
								  console.log('alternativa selecionada: ' + elemento.alternative.value);
// 								  $("#alternative_id_" + index).attr('selectedIndex', index2);
								$("#alternative_id_" + index).val(elemento.alternative.id);	
								var test = $('#select2-alternative_id_' + index + '-container');
								test.attr('title', elemento.alternative.value);
								test.html(elemento.alternative.value);
// 								$('select#alternative_id_' + index +' option').eq(index).css('backgroundColor', 'blue');
							  }
							  console.log(elemento2.id + " | " + elemento2.value);
						  });
					  }else{
// 						  $(elem_a).find('.group_alternative_value').val(elemento.alternative.value);
							$('#alternative_value_' + index).val(elemento.alternative.value);
					  }
// 					  console.log('alternativa selecionada: ' + elemento.alternative.value);
// 					  console.log('alternativas da questão: ');
				});
			}
		}

		var resetQuestions = function (){
			console.log('entrou reset');
			$('.group_question').each(function(idx, elem){
				var $elem = $(elem);				
				$elem.find('.group_alternative').each(function(idx_a, elem_a) {
					console.log('reset: ' + idx_a);
					$(elem_a).find('.group_alternative_id').val('');
					var obj = $('#alternative_id_' + idx + ' option:selected');
					console.log(obj);
					obj.remove();
					var test = $('#select2-alternative_id_' + idx + '-container');
					test.attr('title', '');
					test.html('');
					console.log(obj);
// 					$(elem_a).find('.group_alternative_id').text('');
					$(elem_a).find('.group_alternative_value').val('');
				});

			});
		}

		changeFormsIds('#forExtraction', article);
		resetQuestions();
		
		var user = '${userInfo.user}';
		console.log('user: ', user)
		
		if (extraction != null) {
			console.log('extraction: ', extraction);
			// Pegar dados da extração caso exista e adicionar a tela
			// Fazer uma forma de pegar os dados preenchidos na extração e passar para o formulário carregado na pagina
// 			if(extraction.length > 0){
				setQuestions(extraction);	
// 			}		
		}
	}

	// ajax para carregar artigos na pagina
	$(document).on('click', '.readArticle', function(event){
		event.preventDefault();
		var actualid = $(this).attr("actualid");
		var url = "${linkTo[ExtractionController].loadArticleAjax(0, 1)}";
		url = url.replace("1", actualid);

		$.ajax({ 
			url: url,
			type: 'GET',
			success: function(data){
				var article = data['article'];
				var extraction = data['extraction'];
				console.log('article read: ', article);
				console.log('extraction: ', extraction);
				actualizeArticle(article, extraction);
			},
			error: function(e){
				console.error(e);
			}
		});
	});
});
</script>


<ol class="breadcrumb u-margin-top">
  <li><a href="<c:url value="/" />"><fmt:message key="home"/></a></li>
  <li><a href="${linkTo[MapStudyController].show(map.id)}"><fmt:message key="mapstudy.details"/></a></li>
  <li class="active"><fmt:message key="mapstudy.viewextractions"/></li>
</ol>

<h3 class="color-primary">
	<fmt:message key="mapstudy.extractions.results"/> - ${map.title}
	<a id="return" class="btn btn-default pull-right" href="${linkTo[MapStudyController].show(map.id)}"><fmt:message key="button.back"/></a>
</h3>

<div class="row">
	<div class="col-lg-12">
	
		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.details"/></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<p> 
					<strong>
						<fmt:message key="mapstudy.title"/>:
					</strong> ${map.title}
				<p>
				
				<p> 
					<strong>
						<fmt:message key="mapstudy.description"/>:
					</strong> ${map.description}
				<p>
				
				<p>
					<strong>
						<fmt:message key="mapstudy.extraction.rate"/>:
					</strong> ${percentEvaluated}%
				<p>
				
				<hr/>
				
				<p>
					<strong>
						<fmt:message key="mapstudy.extractions.compare"/>:
					</strong>
					<c:if test="${percentEvaluatedDouble >= 100}">
						<a class="btn btn-primary" href="${linkTo[ExtractionController].compare(map.id)}"><fmt:message key="mapstudy.evaluations.compare"/></a>
					</c:if>
					<c:if test="${percentEvaluatedDouble < 100}">
						<fmt:message key="mapstudy.extractions.compare.undone"/>
					</c:if>
					<div class="clear-both"></div>
				<p>
				
				<p>
					<strong>
						<fmt:message key="mapstudy.extractions.export"/>:
					</strong> 
					<a class="btn btn-default" href="${linkTo[ExtractionController].downloadMine(map.id)}"><fmt:message key="mapstudy.extractions.export.csv.mine"/></a>
					<a class="btn btn-default" href="${linkTo[ExtractionController].downloadAll(map.id)}"><fmt:message key="mapstudy.extractions.export.csv.all"/></a>
					<div class="clear-both"></div>
				<p>
				 
			</div>
		</div>
		
<!-- 		Inicio artigos -->

<div class="clear-both"></div>

<!-- <div class="row"> -->
<!--   	<div class="col-lg-12"> -->
  	
  		<div class="hide" id="infoarticle">
  		
		<h2><fmt:message key="mapstudy.article"/> - <span id="articleReadId">${article.id}</span></h2>
		
		<p> 
			<strong>
				<fmt:message key="mapstudy.article.title"/>:
			</strong> <span id="articleReadTitle">${article.title}</span>
		<p>
		
		<p> 
			<strong>
				<fmt:message key="mapstudy.article.abstract"/>:
			</strong> <span id="articleReadAbstract">${article.abstrct}</span>
		<p>
		
		<p> 
			<strong>
				<fmt:message key="mapstudy.article.key"/>:
			</strong> <span id="articleReadKeywords">${article.keywords}</span>
		<p>
		
		<p> 
			<strong>
				<fmt:message key="mapstudy.article.author"/>:
			</strong> <span id="articleReadAuthor">${article.author}</span>
		<p>
		
		<p> 
			<strong>
				<fmt:message key="mapstudy.article.source"/>:
			</strong> <span id="articleReadSource">${article.source}</span>
		<p>
		
		<hr/>
		
		</div>
		<div class="panel panel-default">
		<div class="panel-heading">
			<b><fmt:message key="mapstudy.form" /></b>
		</div>
		<!-- /.panel-heading -->
		<div class="panel-body">
			<h4>
				<fmt:message key="mapstudy.extractions.data" />
			</h4>
<%-- 			<form action="${linkTo[ExtractionController].evaluateAjax}" method="post" id="#forExtraction"> --%>
				<input type="hidden" name="mapid"  id="mapid" value="${map.id}" />
				<input type="hidden" name="articleid" id="articleid" value="${article.id}" />
				<input type="hidden" id="articlesource" name="articlesource" value="${article.source}" />

					<c:forEach var="question" items="${form.questions}" varStatus="q">
						<div class="form-group group_question">
						<input type="hidden" name="questions[${q.index}].id" value="${question.id}" class="group_question_id" id="question_id_${q.index}"/>
						
						<div class="padding-left-none">
							<strong class="group_question_name" id="question_name_${q.index}">${question.name} :</strong>
						</div>
						<div class="float-right group_alternative">
							<input type="text" class="form-control group_alternative_value" name="alternatives[${q.index}].value" id="alternative_value_${q.index}" value="${article.alternative(question, userInfo.user).value}" disabled/>
						</div>		
						</div>
					</c:forEach>					
					<div class="clear-both"></div>
<!-- 				</form> -->
		</div>
	</div>
<!-- </div> -->

<!-- <div class="row"> -->
	<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.articles.list" /></b>
			</div>
			<div class="panel-body">			
				<h4>
					<fmt:message key="mapstudy.articles.list.extracted"/>
				</h4>
				<div class="dataTable_wrapper">
					<table
						class="table table-striped table-bordered table-hover datatable-evaluated">
						<thead>
							<tr>
								<th class="text-center">ID</th>
								<th class="text-center"><fmt:message key="mapstudy.article.title" /></th>
								<th class="text-center"><fmt:message key="mapstudy.article.source" /></th>
							</tr>
						</thead>
						<tbody class="tBodyArticlesEvaluate">
							<c:forEach var="eval" items="${extractions}" varStatus="s">
								<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} gradeA">
									<td>${eval.id}</td>
<%-- 									<td><a class="btnEvaluate" href="${linkTo[MapStudyController].evaluateArticle(map.id, eval.article.id)}">${eval.article.title}</a></td> --%>
									<td><a class="readArticle" actualid="${eval.id}" href="${linkTo[ExtractionController].loadArticleAjax(map.id, eval.id)}">${eval.title}</a></td>
									<td>${eval.source}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
<!-- </div> -->
</div>
</div>