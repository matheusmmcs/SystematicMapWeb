<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){
	var percent = $('.progress-bar').attr("style");
	$('.progress-bar').attr('style', percent.replace(",", "."));	
}); 
</script>

<h3 class="color-primary">
	<fmt:message key="mapstudy" />
	- ${map.title}
</h3>

<div class="row">
	<div class="col-lg-6">

		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.details" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<p>
					<strong> <fmt:message key="mapstudy.title" />:
					</strong> ${map.title}
				<p>
				<p>
					<strong> <fmt:message key="mapstudy.description" />:
					</strong> ${map.description}
				<p>
				<p>
					<strong> <fmt:message key="mapstudy.evaluation.rate" />:
<!-- 					<div class="progress"> -->
<%--   						<div class="progress-bar" role="progressbar" aria-valuenow="${percentEvaluated}" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em; width: ${percentEvaluated}%"> --%>
<%--   						${percentEvaluated}% --%>
<!--   						</div> -->
<!-- 					</div> -->

					<div class="progress">
  						<div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="${percentEvaluated}" aria-valuemin="0" aria-valuemax="100" style="width: ${percentEvaluated}%">
    						${percentEvaluated}%
  						</div>
					</div>
<%-- 					</strong> ${percentEvaluated}%  --%>
<%-- 				<a class="btn btn-primary pull-right" href="${linkTo[MapStudyController].evaluate(map.id)}"><fmt:message key="mapstudy.evaluate" /></a> --%>
				<div class="clear-both"></div>
				<p>
				<p>
<%-- 					<strong> <fmt:message key="mapstudy.evaluations.results" />: --%>
<!-- 					</strong>  -->
<%-- 					<a class="btn btn-default pull-right" href="${linkTo[MapStudyController].showEvaluates(map.id)}"><fmt:message key="view" /></a> --%>
				<div class="clear-both"></div>
				<p>
				
				<a class="btn btn-primary" href="${linkTo[MapStudyController].evaluate(map.id)}"><fmt:message key="mapstudy.evaluate" /></a>
				<a class="btn btn-default" href="${linkTo[MapStudyController].showEvaluates(map.id)}"><fmt:message key="view" /></a>
								
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.members" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<h4>
					<fmt:message key="mapstudy.members.add" />
				</h4>
				<form action="${linkTo[MapStudyController].addmember}" method="post">
					<input type="hidden" name="id" value="${map.id}" />

					<div class="form-group">
						<div class="col-lg-9 padding-left-none">
							<select data-placeholder="<fmt:message key="mapstudy.members.choose" />" class="form-control select2" 
								name="userId" tabindex="2">
         						<c:forEach var="member" items="${mapStudyArentUsers}">
									<option value="${member.id}" data-email="${member.email}">${member.name}</option>
								</c:forEach>
				            </select>
						</div>
						<button type="submit" id="submit" class="btn btn-large btn-primary col-lg-3 float-right">
							<fmt:message key="add" />
						</button>
						<div class="clear-both"></div>
					</div>
					<div class="checkbox">
							<label>
	      						<input type="checkbox" name="notify"> <fmt:message key="notify.member" />
	    					</label>
	    				</div>
				</form>

				
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>#</th>
								<th><fmt:message key="mapstudy.members" /></th>
								<th><fmt:message key="mapstudy.evaluations.percentconclusion" /></th>
								<c:if test="${map.isCreator(userInfo.user)}">
								<th><fmt:message key="remove" /></th>
								</c:if>
								
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mapStudyUsers}" var="member" varStatus="s">
								<tr>
									<td>${s.index + 1}</td>
									<td>${member.key.name}</td>
									<td>${member.value}</td>
									<c:if test="${map.isCreator(userInfo.user)}">
									<c:choose>
									    <c:when test="${!map.isCreator(member.key)}">
									        <td class="text-center"><a class="btn btn-danger confirmation-modal" data-conf-modal-body="<fmt:message key="member.excluir.message" />" href="${linkTo[MapStudyController].removemember(map.id, member.key.id)}"><i class="glyphicon glyphicon-remove"></i></a></td>
									    </c:when>
									    <c:otherwise>
									        <td></td>
									    </c:otherwise>
									</c:choose>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>

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
					method="post">
					<input type="hidden" name="id" value="${map.id}" />
					<div class="form-group">
						<div class="col-lg-9 padding-left-none">
							<input type="text" class="form-control" id="incdescription"
								name="criteria.description"
								placeholder="<fmt:message key="mapstudy.inclusion.criteria"/>" />
						</div>
						<button type="submit" id="submit"
							class="btn btn-large btn-primary col-lg-3 float-right">
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
									<td class="text-center"><a class="btn btn-danger" href="${linkTo[MapStudyController].removeinclusioncriteriapage(map.id, criteria.id)}"><i
											class="glyphicon glyphicon-remove"></i></a></td>
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
					method="post">
					<input type="hidden" name="id" value="${map.id}" />
					<div class="form-group">
						<div class="col-lg-9 padding-left-none">
							<input type="text" class="form-control" id="excdescription"	name="criteria.description"
								placeholder="<fmt:message key="mapstudy.exclusion.criteria"/>" />
						</div>
						<button type="submit" id="submit" class="btn btn-large btn-primary col-lg-3 float-right">
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
									<td class="text-center"><a class="btn btn-danger" href="${linkTo[MapStudyController].removeexclusioncriteriapage(map.id, criteria.id)}"><i
											class="glyphicon glyphicon-remove"></i></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>

	</div>
	<div class="col-lg-6">

		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.articles" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">

				<h4>
					<fmt:message key="mapstudy.article.add" />
				</h4>
				<form action="${linkTo[MapStudyController].addarticles}" enctype="multipart/form-data" method="post">
					<input type="hidden" name="id" value="${map.id}" />
					<div class="form-group">
						<div class="row">
							<div class="fileupload fileupload-new" data-provides="fileupload">
								<div class="col-lg-7">
									<span class="btn btn-file btn-default"> <span class="fileupload-new"><fmt:message key="mapstudy.article.add.choose" /></span> 
									<span class="fileupload-exists">Change</span> <input type="file" name="upFile" /></span> 
									<span class="fileupload-preview"></span> <a href="#"
										class="close fileupload-exists" data-dismiss="fileupload"
										style="float: none">&times;</a>
								</div>
							</div>
						</div>
						<div class="row" style="margin-top: 10px">
							<div class="col-lg-7">
								<select class="form-control" name="source">
									<c:forEach var="source" items="${sources}">
										<option value="${source}">${source}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-lg-5">
								<button type="submit" id="submit"
									class="btn btn-large btn-primary pull-right">
									<fmt:message key="mapstudy.article.add" />
								</button>
								<div class="clear-both"></div>
							</div>

						</div>

					</div>
				</form>

				<hr />

				<h4>
					<fmt:message key="mapstudy.articles.list" />
				</h4>
				<div class="dataTable_wrapper">
					<table
						class="table table-striped table-bordered table-hover personalized-table">
						<thead>
							<tr>
								<th class="text-center">ID</th>
								<th class="text-center"><fmt:message
										key="mapstudy.article.title" /></th>
								<th class="text-center"><fmt:message
										key="mapstudy.article.classification" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="article" items="${map.articles}" varStatus="s">
								<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} gradeA">
									<td>${article.id}</td>
									<td>${article.title}</td>
									<td>${article.classification}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<hr />

				<h4>
					<fmt:message key="mapstudy.article.refine" />
				</h4>
				<form action="${linkTo[MapStudyController].refinearticles}"
					method="post">
					<input type="hidden" name="id" value="${map.id}" />

					<div class="form-group">
						<label for="levenshtein">Levenshtein (similaridade de
							títulos)</label>
						<div class="col-lg-12">
							<input type="text" class="form-control" id="levenshtein"
								name="levenshtein" placeholder="Levenshtein" />
						</div>
					</div>

					<hr />

					<div class="form-group">
						<label for="regex">Regex (termo:regex;...)</label>
						<div class="col-lg-12">
							<textarea class="form-control" name="regex" rows="5" cols="">automatico:(automat.*|semiautomati.*|semi-automati.*);web:(web|website|internet|www);usabilidade:(usability|usable);tecnica:(evalu.*|assess.*|measur.*|experiment.*|stud.*|test.*|method.*|techni.*|approach.*)</textarea>
						</div>
					</div>

					<hr />

					<div class="form-group">
						<label for="limiartitulo">Limiar T&iacute;tulo</label>
						<div class="col-lg-12">
							<input type="text" class="form-control" id="limiartitulo"
								name="limiartitulo" placeholder="Limiar T&iacute;tulo" />
						</div>
					</div>

					<div class="form-group">
						<label for="limiarabstract">Limiar Abstract</label>
						<div class="col-lg-12">
							<input type="text" class="form-control" id="limiarabstract"
								name="limiarabstract" placeholder="Limiar Abstract" />
						</div>
					</div>

					<div class="form-group">
						<label for="limiarkeywords">Limiar Keywords</label>
						<div class="col-lg-12">
							<input type="text" class="form-control" id="limiarkeywords"
								name="limiarkeywords" placeholder="Limiar Keywords" />
						</div>
					</div>

					<div class="form-group">
						<label for="limiartotal">Limiar Total</label>
						<div class="col-lg-12">
							<input type="text" class="form-control" id="limiartotal"
								name="limiartotal" placeholder="Limiar Total" />
						</div>
					</div>

					<button type="submit" id="submit"
						class="btn btn-large btn-primary pull-right"
						style="display: inline-block; margin: 15px;">
						<fmt:message key="mapstudy.article.refine" />
					</button>

					<div class="clear-both"></div>

				</form>


			</div>
		</div>

	</div>
</div>
