<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	(function($) {
		$(document).ready(function() {
			
		var isLogado = function() {
			if ('${userInfo.user}' == null) {
				window.location.reload();
			}
		};
	
		// activenav deve ser o id do nav
		var newnavactive = function(activenavId) {
			console.log('activenavId: ', activenavId);
			// Tira status ativo do nav
			var navactualId = $('.mynav.active').attr('id');
			console.log('navactualId: ', navactualId);
			
			if (navactualId == undefined) {
				navactualId = $('#mydiv').val().substr(3);
				console.log('ficou indefinido: ', navactualId);
			}
	
			$('#' + navactualId).removeClass('active');
	
			// Esconde a div relacionada ao nav que estava ativo
			var divatual = '#div' + navactualId.replace('#', '');
			$(divatual).addClass('hide');
			
			console.log('divatual: ', divatual);
	
			// Muda status do nav selecionado para ativo
			$(activenavId).addClass('active'); // adiciona classe active no nav selecionado 
	
			// Exibe a div relacionada ao nav
			var divnovo = 'div' + activenavId.substr(1);
			
			$('#mydiv').val(divnovo);
			$('#' + divnovo).removeClass('hide');
			
			console.log('divnovo: ', divnovo);
		};
				
		var mydivid = '#' + $('#mydiv').val().substr(3);
		newnavactive(mydivid);
							
		$(document).on('click', '.mynav', function(event) {
			event.preventDefault();
			//event.stopPropagation();

			isLogado();
			var id = '#' + $(this).attr('id');			
			newnavactive(id);
		});

		$("#formInclusion").validate({
			rules : {
				'criteria.description' : {
					required : true
				/*, minlength : 1*/
				}
			},
			messages : {
				'criteria.description' : {
					required : '<fmt:message key="required" />'
					/*,minlength: '<fmt:message key="mapstudy.min.title" />'*/
				}
			}
		});

		$("#formExclusion").validate({
			rules : {
				'criteria.description' : {
					required : true
				/*, minlength : 1*/
				}
			},
			messages : {
				'criteria.description' : {
					required : '<fmt:message key="required" />'
					/*,minlength: '<fmt:message key="mapstudy.min.title" />'*/
				}
			}
		});
		
		// Parte do formulário de extração
		//Remove um campo
		var removeField = function(obj) {
			$(obj).remove();
		};

		// Adiciona questões
		addQuestion = function(divObj) {
			time = new Date().getTime();
			fieldContent = '<div class="form-group group_question" id="group_question_'+time+'">'
					+ '<div class="row">'
					+ '<div class="col-md-6">'
					+ '<label for="quest_'+time+'" class=""><fmt:message key="mapstudy.question.name" /></label> <input type="text"'
					+ 'class="form-control group_question_name" id="quest_'
					+ time
					+ '" name="questions[]"'
					+ 'placeholder="<fmt:message key="mapstudy.question.name"/>" />'
					+ '</div>'
					+ '<div class="col-md-3">'
					+ '<label for="type_'+time+'" class=""><fmt:message key="mapstudy.question.type" /></label>'
					+ '<select class="form-control selectiontype group_question_type" name="types[]" id="type_'+time+'">'
					+ '<c:forEach var="type" items="${questionTypes}">'
					+ '<option value="${type}">${type.description}</option>'
					+ '</c:forEach>'
					+ '</select>'
					+ '</div>'
					+ '<div class="col-md-3">'
					+ '<label class="" for="btnquestionremove_'+time+'">&nbsp;&nbsp;&nbsp;</label>'
					+ '<a href="#" class="btn btn-danger buttonquestionremove form-control" id="btnquestionremove_'+time+'" idquest="group_question_'+time+'">'
					+ '<i class="glyphicon glyphicon-remove"></i> <fmt:message key="mapstudy.question.remove" />'
					+ '</a>'
					+ '</div>'
					+ '</div>'
					+ '<hr />' + '</div>';
			divObj.append(fieldContent);
		};

		var addAlternative = function(divObj, isFirst, questassociation) {
			time = new Date().getTime();
			var content = '<div class="form_group group_alternative" id="group_alternative_'+time+'">'
					+ '<div class="col-md-6">'
					+ '<label for="alternative_'+time+'" class="">'
					+ '<fmt:message key="mapstudy.alternative.value" /></label> '
					+ '<input type="text" class="form-control group_alternative_value" id="alternative_'
					+ time
					+ '" name="alternatives[].value"'
					+ 'placeholder="<fmt:message key="mapstudy.alternative.value"/>" questassociation="'
					+ questassociation + '"/>' + '</div>';

			if (isFirst == true) {
				content += '<div class="col-md-3">'
						+ '<label class="" for="buttonalternativeadd_'+time+'">&nbsp;&nbsp;&nbsp;</label>'
						+ '<a href="#" class="btn btn-success buttonalternativeadd form-control" id="buttonalternativeadd_'+time+'" questassociation="'+questassociation+'">'
						+ '<i class="glyphicon glyphicon-plus"></i> <fmt:message key="mapstudy.alternative.add" />'
						+ '</a>' + '</div>' + '</div>';
			} else {
				content += '<div class="col-md-1">'
						+ '<label class="" for="buttonalternativeremove_'+time+'">&nbsp;&nbsp;&nbsp;</label>'
						+ '<a href="#" alternativeid="group_alternative_'+time+'" class="btn btn-danger buttonalternativeremove form-control" id="buttonalternativeremove_'+time+'">'
						+ '<i class="glyphicon glyphicon-remove"></i>'
						+
						//	 				<fmt:message key="mapstudy.alternative.remove" />
						'</a>' + '</div>' + '</div>';
			}

			$(divObj).append(content);
		};

		// Adiciona questão
		$(document).on('click', '.buttonquestionadd', function(event) {
			event.preventDefault();
			isLogado();

			divObj = $('#' + 'allquestions');
			addQuestion(divObj);
		});

		// Remove questão
		$(document).on('click', '.buttonquestionremove', function(event) {
			event.preventDefault();
			isLogado();
			
			var id = $(this).attr('idquest');
			removeField($('#' + id));
		});

		// Adiciona ALternativa
		$(document).on('click', '.buttonalternativeadd', function(event) {
			event.preventDefault();
			isLogado();

			var myid = $(this).attr('questassociation');
			addAlternative($('#group_question_'	+ myid), false, myid);
		});

		// Remove ALternativa
		$(document).on('click', '.buttonalternativeremove',	function(event) {
			event.preventDefault();
			isLogado();	

			var id = $(this).attr('alternativeid');
			removeField($('#' + id));
		});

		// Captura seleção de tipo
		$(document).on('change', '.selectiontype', function() {
			isLogado();
			console.log('type: ', $(this).val());
			if ($(this).val() == 'SIMPLE') {
				// se existir alternatives remover todas
				var myid = $(this).attr('id');
				myid = myid.substring(myid.lastIndexOf('_') + 1, myid.length);
				var objId = "#group_question_" + myid;
				
				// assim ele vai pegar os group_alternative "filhos" de objId
				$(objId	+ " .group_alternative").each(function(index) {
					$(this).remove();
				});

			} else {
			// inserir alternative
				var myid = $(this).attr('id');
				myid = myid.substring(myid.lastIndexOf('_') + 1, myid.length);
				var objId = "#group_question_" + myid;

				if ($(objId).children('.group_alternative').length == 0) {
					addAlternative($(objId), true, myid);
				} else {
					console.log('ja existe alternative');
				}
			}
		});

		//Salvar Formulário
		$(document)	.on('click', '.buttonextraction', function(event) {
			event.preventDefault();
			var questions = [];
			
			$('.group_question').each(function(idx,	elem) {
				var $elem = $(elem);
				var question = {};
				question.id = null;
				question.name = $elem.find('.group_question_name').val();
				question.type = $elem.find('.group_question_type').val();
				question.alternatives = [];
				
				$elem.find('.group_alternative').each(function(idx_a, elem_a) {
					var alternative = {};
					alternative.id = null;
					alternative.value = $(elem_a).find('.group_alternative_value').val();
					
					if (alternative.value != null && alternative.value != "") {
						question.alternatives.push(alternative);
					}
				});

				if (question.name != null && question.name != "") {
					questions.push(question);
				}
				
			});

			var address = "${linkTo[ExtractionController].formAjax}";
			var mapid = $('#mapid').val();

			var questionVO = {
				"mapid" : mapid,
				"questions" : questions
			};

			param = {
				"questionVO" : questionVO
			};
			
			console.log(param);
			
			/*
			$.ajax({
				url : address,
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				type : 'POST',
				traditional : true,
				data : JSON.stringify(param),
				success : function(data) {
					console.log(data);
				},
				error : function(e) {
					console.error(e);
				}
			});
			*/
		});
		
		/* QUESTOES PARA EXTRACAO */
		
		var mapId = $('#mapid').val();
		var $divsAlternatives = $('.subquestion-hasalternatives'),
			$divAddSubquestionExt = $('#div-add-subquestion-extraction'),
			$divTableSubquestionExt = $('#div-table-subquestions-extraction'),
			$divAlternativesExt = $('#form-add-subquestion-extraction-alternatives');
		
		function showListSubQuesiton() {
			$divAddSubquestionExt.hide();
			$divTableSubquestionExt.show();
		}
			
		function showFormSubQuesiton() {
			$divAddSubquestionExt.show();
			$divTableSubquestionExt.hide();
		}
		
		$(document).on('click', '#btn-add-subquestion-extraction', function(e) {
			e.preventDefault();
			renderQuestionNew();
		});
		
		$(document).on('click', '#btn-back-subquestion-extraction', function(e) {
			e.preventDefault();
			loadAllQuestions(function(){
				showListSubQuesiton();
			});
		});
		
		$(document).on('click', '#btn-add-alternative-subquestion-extraction', function(e) {
			e.preventDefault();
			$divAlternativesExt.append(renderAlternative());
		});
		
		$(document).on('click', '.subquestion-alternative-rmv', function(e) {
			e.preventDefault();
			$(this).closest('.subquestion-alternative').remove();
		});
		
		$(document).on('change', '#subquestion-type', function(e) {
			var val = $(this).val();
			if (val == 'LIST' || val == 'MULT') {
				$divsAlternatives.show();
			} else {
				$divAlternativesExt.html('');
				$divsAlternatives.hide();
			}
		});
		
		$(document).on('click', '#btn-save-subquestion-extraction', function(e){
			e.preventDefault();
			
			var question = {
				name: $('#subquestion-name').val(),
				type: $('#subquestion-type').val()
			};
			var alternatives = [];
			var questionId = $('#subquestion-id').val();
			if (questionId) {
				question.id = questionId;
			}
			
			$('.subquestion-alternative').each(function(idx){
				var id = $(this).find('.subquestion-alternative-id').val();
				var title = $(this).find('.subquestion-alternative-title').val();
				alternatives.push({
					value: title
				});
			});
			question.alternatives = alternatives;
			
			saveQuestion(question);
		});
		
		$(document).on('click', '.subquestions-extraction-edit', function(e){
			e.preventDefault();
			var questionId = $(this).attr('data-question-id');
			loadQuestion(questionId, function(data) {
				if (data.status == 'SUCESSO' && data.data) {
					renderQuestionEdit(data.data);
				}
			});
		});

		function loadAllQuestions(callback) {
			$.ajax({
				url : '${linkTo[ExtractionController].loadQuestions}',
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				type : 'POST',
				data : JSON.stringify({
					"mapid" : mapId
				}),
				success : function(data) {
					var html = '';
					for (var i in data) {
						var q = data[i];
						html += renderLinhaQuestion(q.id, q.name, q.type);
					}
					$('#table-subquestions-extraction tbody').html(html);
					if(callback && typeof(callback) === "function") {
			            callback(data);
			        }
				},
				error : function(e) {
					console.log(e);
				}
			});
		}
		
		function loadQuestion(questionid, callback, callbackError) {
			$.ajax({
				url : '${linkTo[ExtractionController].getQuestion}',
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				type : 'POST',
				data : JSON.stringify({
					"mapid" : mapId,
					"questionId" : questionid
				}),
				success : function(data) {
					if(callback && typeof(callback) === "function") {
			            callback(data);
			        }
				},
				error : function(e) {
					if(callbackError && typeof(callbackError) === "function") {
						callbackError(e);
			        }
				}
			});
		}

		function saveQuestion(question) {
			$.ajax({
				url : '${linkTo[ExtractionController].addQuestion}',
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				type : 'POST',
				traditional : true,
				data : JSON.stringify({
					"mapid" : mapId,
					"question" : question
				}),
				success : function(data) {
					loadAllQuestions(function(){
						showListSubQuesiton();
					});
				},
				error : function(e) {
					console.error(e);
				}
			});
		}
		
		function removeQuestion(questionid) {
			$.ajax({
				url : '${linkTo[ExtractionController].removeQuestion}',
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				type : 'POST',
				traditional : true,
				data : JSON.stringify({
					"mapid" : mapId,
					"questionId" : questionid
				}),
				success : function(data) {
					console.log(data);
					loadAllQuestions();
				},
				error : function(e) {
					console.error(questionid, e);
				}
			});
		}
		
		function renderQuestionNew() {
			$('#subquestion-id').val('');
			$('#subquestion-name').val('');
			$divAlternativesExt.html('');
			showFormSubQuesiton();
		}
		
		function renderQuestionEdit(question) {
			$('#subquestion-id').val(question.id);
			$('#subquestion-name').val(question.name);
			$('#subquestion-type').val(question.type).trigger('change');
			
			$divAlternativesExt.html('');
			for (var i in question.alternatives) {
				var a = question.alternatives[i];
				$divAlternativesExt.append(renderAlternative(a.id, a.value));
			}
			showFormSubQuesiton();
		}
		
		function renderLinhaQuestion(id, name, type) {
			var str = '<tr><td>'+name+'</td><td>'+type+'</td><td class="text-center">';
			str += '<a class="btn btn-primary subquestions-extraction-edit" data-question-id="'+id+'" href="#"><i class="glyphicon glyphicon-pencil"></i></a>';
			str += '<a class="btn btn-danger confirmation-modal subquestions-extraction-remove" data-question-id="'+id+'" data-conf-modal-body="<fmt:message key="mapstudy.excluir.message" />" href="#" data-conf-modal-callback="window.removeSubQuestion('+id+')" ><i class="glyphicon glyphicon-remove"></i></a>';
			str += '</td></tr>';
			return str;
		}
	
		function renderAlternative(id, val) {
			val = val ? val : '';
			id = id ? id : '';
			var result = '<div class="form-group subquestion-alternative"><div class="row"><div class="col-sm-10">';
			result += '<input class="subquestion-alternative-id" type="hidden" value="' + id + '"/>';
			result += '<input type="text" class="form-control subquestion-alternative-title" placeholder="Alternativa" value="' + val + '"/>'; 
			result += '</div><div class="col-sm-2"><a class="btn btn-danger subquestion-alternative-rmv" href="#"><i class="glyphicon glyphicon-remove"></i> <fmt:message key="remove"/></a></div></div></div>';
			return result;
		}
		
		window.removeSubQuestion = function(questionid) {
			removeQuestion(questionid);
		}
		
		//init
		$divsAlternatives.hide();
		loadAllQuestions(function(){
			showListSubQuesiton();
		});
		
		
	});
})(jQuery);
</script>

<ol class="breadcrumb u-margin-top">
  <li><a href="<c:url value="/" />"><fmt:message key="home"/></a></li>
  <li><a href="${linkTo[MapStudyController].show(map.id)}"><fmt:message key="mapstudy.details"/></a></li>
  <li class="active"><fmt:message key="mapstudy.planning"/></li>
</ol>

<h3 class="color-primary">
	<fmt:message key="mapstudy" />	- ${map.title}
	<a id="return" class="btn btn-default pull-right" href="${linkTo[MapStudyController].show(map.id)}"><fmt:message key="button.back"/></a>
</h3>

<form><input type="hidden" name="mydiv" value="${mydiv}" id="mydiv"/></form>
<input type="hidden" name="mapid" id="mapid" value="${map.id}" />

<ul class="nav nav-tabs" style="background-color: #f8f8f8;">
<!-- border: 1px solid #E7E7E7;"> -->
  <li role="presentation" class="mynav" id="goals"><a href="#"><fmt:message key="mapstudy.goals" /> <!--  <span class="glyphicon glyphicon-ok"></span>--></a></li>
  <li role="presentation" class="mynav" id="question"><a href="#" ><fmt:message key="mapstudy.research.question" /></a></li>
  <li role="presentation" class="mynav" id="string"><a href="#" ><fmt:message key="mapstudy.search.string" /></a></li>
  <li role="presentation" class="mynav" id="extraction"><a href="#" ><fmt:message key="mapstudy.form" /></a></li>
  <li role="presentation" class="mynav" id="criterias"><a href="#" ><fmt:message key="mapstudy.inclusion.and.exclusion.criterias" /></a></li>
</ul>

<p>

<!-- Formulário de extração -->
<div  id="divextraction" class="hide">
	<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.form" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div id="div-table-subquestions-extraction">
					<h4>
						Listagem de Subquestões para Extração
						<a id="btn-add-subquestion-extraction" href="#" class="btn btn-primary u-btn-pull-right">Adicionar Subquestão</a> 
					</h4>
					<hr/>
					<table id="table-subquestions-extraction" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th style="width: 60%" class="text-center">Título</th>
								<th style="width: 20%" class="text-center">Tipo</th>
								<th style="width: 20%" class="text-center">Ações</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="question" items="${questions}" varStatus="s">
								<tr>
									<td>${question.name}</td>
									<td>${question.type.description}</td>
									<td class="text-center">
										<a class="btn btn-primary subquestions-extraction-edit" data-question-id="${question.id}" href="#"><i class="glyphicon glyphicon-pencil"></i></a>
										<a class="btn btn-danger confirmation-modal subquestions-extraction-remove" data-question-id="${question.id}" data-conf-modal-body="<fmt:message key="mapstudy.excluir.message" />" href="#" data-conf-modal-callback="window.removeSubQuestion(${question.id})" ><i class="glyphicon glyphicon-remove"></i></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
				<div id="div-add-subquestion-extraction">
					<h4>
						Adicionar Subquestão para Extração
						<a id="btn-back-subquestion-extraction" href="#" class="btn btn-default u-btn-pull-right">Voltar para Listagem de Subquestões</a> 
					</h4>
					<hr/>
					<form action="#" method="post" id="form-add-subquestion-extraction">
						<input type="hidden" name="id" id="subquestion-id" value="" />
						<div class="form-group">
							<div class="row">
								<div class="col-sm-6">
									<label for="subquestion-name" class=""><fmt:message key="mapstudy.question.name"/></label>
									<input type="text" class="form-control" name="name" id="subquestion-name" value=""/>
								</div>
								<div class="col-sm-6">
									<label for="subquestion-type" class=""><fmt:message key="mapstudy.question.type" /></label> 
									<select class="form-control selectiontype group_question_type" name="type" id="subquestion-type">
										<c:forEach var="type" items="${questionTypes}">
											<option value="${type}">${type.description}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						
						<div class="form-group subquestion-hasalternatives">
							<hr/>
							<h4>
								Alternativas da Subquestão
								<a class="btn btn-default u-btn-pull-right" id="btn-add-alternative-subquestion-extraction" href="#">Adicionar Alternativa</a>
							</h4>
							<div id="form-add-subquestion-extraction-alternatives">
							</div>
						</div>
					
						<div class="form-group">	
							<hr/>
							<button type="submit" class="btn btn-primary pull-right" id="btn-save-subquestion-extraction">Salvar</button>
						</div>	
					</form>
				</div>
				
			</div>
		</div>
</div>

<!-- Objetivos -->

<div  id="divgoals" class="hide">
	<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.goals" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<h4>
					<fmt:message key="mapstudy.goals.add" />
				</h4>
				<form action="${linkTo[MapStudyController].addgoals}" method="post" id="formGoals">
					<input type="hidden" name="id" id="id" value="${map.id}" />
					<div class="form-group">
						<div class="col-lg-9 padding-left-none">
<%-- 							<input type="text" class="form-control" id="goal" name="goal" placeholder="<fmt:message key="mapstudy.goals"/>" /> --%>
							<textarea rows="5" class="form-control" id="goal" name="goals" placeholder="<fmt:message key="mapstudy.goals"/>"></textarea>
						</div>
						<button type="submit" id="buttongoals" class="btn btn-large btn-primary col-lg-3 float-right buttongoals">
							<fmt:message key="add" />
						</button>
						<div class="clear-both"></div>
					</div>
				</form>
				
				<c:if test="${map.goals != null}">				
				<div class="row">
  					<div class="col-lg-12">
					<h4><fmt:message key="mapstudy.goals"/></h4>
					<hr />
						<p> 
							<span>${map.goals}</span>
						<p>
						<hr />
					</div>
				</div>
				</c:if>

<!-- 				<div class="table-responsive"> -->
<!-- 					<table class="table table-striped table-bordered table-hover"> -->
<!-- 						<thead> -->
<!-- 							<tr> -->
<%-- 								<th><fmt:message key="mapstudy.goals" /></th> --%>
<%-- 								<th><fmt:message key="update" /></th> --%>
<!-- 							</tr> -->
<!-- 						</thead> -->
<!-- 						<tbody> -->
<!-- 								<tr> -->
<%-- 									<td>${map.goals}</td> --%>
<%-- 									<td class="text-center"><a class="btn btn-primary" href="${linkTo[MapStudyController].updategoals(map.id, map.goals)}"><i class="fa fa-edit"></a></td> --%>
<!-- 								</tr>								 -->
<!-- 						</tbody> -->
<!-- 					</table> -->
<!-- 				</div> -->
			</div>
		</div>
</div>

<!-- Questões de Pesquisa -->
<div  id="divquestion" class="hide">
	<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.research.question" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<h4>
					<fmt:message key="mapstudy.research.question.add" />
				</h4>
				<form action="${linkTo[MapStudyController].addquestion}"
					method="post" id="formQuestion">
					<input type="hidden" name="id" id="id" value="${map.id}" />
					<div class="form-group">
						<div class="col-lg-9 padding-left-none">
							<input type="text" class="form-control" id="questiondescription" name="description"
								placeholder="<fmt:message key="mapstudy.research.question.description"/>" />
						</div>
						<button type="submit" id="buttonquestion" class="btn buttonquestion btn-large btn-primary col-lg-3 float-right">
							<fmt:message key="add" />
						</button>
						<div class="clear-both"></div>
					</div>
				</form>

				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th><fmt:message key="mapstudy.research.question" /></th>
								<th><fmt:message key="actions" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="quest" items="${map.researchQuestions}" varStatus="s">
								<tr>
									<td>${quest.description}</td>
									<td class="text-center">
										<a class="btn btn-danger" href="${linkTo[MapStudyController].removequestion(map.id, quest.id)}"><i class="glyphicon glyphicon-remove"></i></a>
										<a class="btn btn-primary" href="${linkTo[MapStudyController].editquestion(map.id, quest.id)}"><i class="glyphicon glyphicon-edit"></i></a>
									</td>
								</tr>								
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>

</div>

<!-- String de Busca -->
<div  id="divstring" class="hide">
	<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.search.string" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<h4>
					<fmt:message key="mapstudy.search.string.add" />
				</h4>
				<form action="${linkTo[MapStudyController].addstring}" method="post" id="formString">
					<input type="hidden" name="id" id="id" value="${map.id}" />
					<div class="form-group">
						<div class="col-lg-6">
<%-- 							<input type="text" class="form-control" id="stringdescription" name="string" placeholder="<fmt:message key="mapstudy.search.string.description"/>" /> --%>
							<textarea rows="5" class="form-control" id="stringdescription" name="string" placeholder="<fmt:message key="mapstudy.search.string.description"/>"></textarea>
						</div>
						
						<div class="col-lg-4">
								<select class="form-control" name="source">
									<c:forEach var="source" items="${sources}">
										<option value="${source}">${source.description}</option>
									</c:forEach>
								</select>
						</div>						
						
						<button type="submit" id="buttonquestion" class="btn buttonquestion btn-primary">
							<fmt:message key="add" />
						</button>
						<div class="clear-both"></div>
					</div>
				</form>
				
				<div class="row">
  					<div class="col-lg-12">
					<h4><fmt:message key="mapstudy.search.string.list"/></h4>
					<hr />
				
					<c:forEach var="search" items="${map.searchString}" varStatus="s">
						<p> 
							<strong><fmt:message key="mapstudy.search.string.source"/> :</strong> <span>${search.source.description}</span>
						<p>
						<p> 
							<strong><fmt:message key="mapstudy.search.string"/> :</strong>
						<p>	
							<span>${search.description}</span>
						<p>
						<hr />
					</c:forEach>
					
					</div>
				</div>

<!-- 				<div class="table-responsive"> -->
<!-- 					<table class="table table-striped table-bordered table-hover"> -->
<!-- 						<thead> -->
<!-- 							<tr> -->
<%-- 								<th><fmt:message key="mapstudy.search.string" /></th> --%>
<%-- 								<th><fmt:message key="mapstudy.search.string.source" /></th> --%>
<%-- 								<th><fmt:message key="actions" /></th> --%>
<!-- 							</tr> -->
<!-- 						</thead> -->
<!-- 						<tbody> -->
<%-- 							<c:forEach var="search" items="${map.searchString}" varStatus="s"> --%>
<!-- 								<tr> -->
<%-- 									<td>${search.description}</td> --%>
<%-- 									<td>${search.source}</td> --%>
<!-- 									<td class="text-center"> -->
<!-- 										<a class="btn btn-danger" href="#"><i class="glyphicon glyphicon-remove"></i></a> -->
<!-- 										<a class="btn btn-primary" href="#"><i class="glyphicon glyphicon-edit"></i></a> -->
<!-- 									</td> -->
<!-- 								</tr>								 -->
<%-- 							</c:forEach> --%>
<!-- 						</tbody> -->
<!-- 					</table> -->
<!-- 				</div> -->
			</div>
		</div>

</div>



<!-- Criterios de inclusão e exclusão -->

<div  id="divcriterias" class="hide">
	<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.inclusion.criterias" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<h4>
					<fmt:message key="mapstudy.inclusion.criteria.add" />
				</h4>
				<form action="${linkTo[MapStudyController].addinclusion}"
					method="post" id="formInclusion">
					<input type="hidden" name="id" id="id" value="${map.id}" />
					<div class="form-group">
						<div class="col-lg-9 padding-left-none">
							<input type="text" class="form-control" id="incdescription" name="description" placeholder="<fmt:message key="mapstudy.inclusion.criteria"/>" />
						</div>
						<button type="submit" id="buttoninclusion"
							class="btn buttoninclusion btn-large btn-primary col-lg-3 float-right">
							<fmt:message key="add" />
						</button>
						<div class="clear-both"></div>
					</div>
				</form>

				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th><fmt:message key="mapstudy.criteria.description" /></th>
								<th><fmt:message key="remove" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="criteria" items="${map.inclusionCriterias}"
								varStatus="s">
								<tr>
									<td>${criteria.description}</td>
									<td class="text-center">
										<a class="btn btn-danger" href="${linkTo[MapStudyController].removeinclusioncriteriapage(map.id, criteria.id)}"><i class="glyphicon glyphicon-remove"></i></a>
										<a class="btn btn-primary" href="${linkTo[MapStudyController].editinclusioncriteria(map.id, criteria.id)}"><i class="glyphicon glyphicon-edit"></i></a>
									</td>
								
								</tr>								
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.exclusion.criterias" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<h4>
					<fmt:message key="mapstudy.exclusion.criteria.add" />
				</h4>
				<form action="${linkTo[MapStudyController].addexclusion}"
					method="post" id="formExclusion">
					<input type="hidden" name="id" id="id" value="${map.id}" />
					<div class="form-group">
						<div class="col-lg-9 padding-left-none">
							<input type="text" class="form-control" id="excdescription"	name="description"
								placeholder="<fmt:message key="mapstudy.exclusion.criteria"/>" />
						</div>
						<button type="submit" id="buttonexclusion" class="btn buttonexclusion btn-large btn-primary col-lg-3 float-right">
							<fmt:message key="add" />
						</button>
						<div class="clear-both"></div>
					</div>
				</form>

				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th><fmt:message key="mapstudy.criteria.description" /></th>
								<th><fmt:message key="remove" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="criteria" items="${map.exclusionCriterias}"
								varStatus="s">
								<tr>
									<td>${criteria.description}</td>
									<td class="text-center">
										<a class="btn btn-danger" href="${linkTo[MapStudyController].removeexclusioncriteriapage(map.id, criteria.id)}"><i class="glyphicon glyphicon-remove"></i></a>
										<a class="btn btn-primary" href="${linkTo[MapStudyController].editexclusioncriteria(map.id, criteria.id)}"><i class="glyphicon glyphicon-edit"></i></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
</div>
