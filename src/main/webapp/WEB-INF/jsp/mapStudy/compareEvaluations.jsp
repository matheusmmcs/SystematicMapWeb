<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h3 class="color-primary"><fmt:message key="mapstudy.evaluations.compare"/> - ${mapStudy.title}</h3>

<div class="row">
  	<div class="col-lg-12">
  		
		<h4>
			<fmt:message key="mapstudy.evaluations.all"/>
		</h4>
		<div class="dataTable_wrapper">
			<table
				class="table table-striped table-bordered table-hover personalized-table">
				<thead>
					<tr>
						<th class="text-center">ID</th>
						<c:forEach var="m" items="${members}" varStatus="s">
							<th class="text-center">${m.login}</th>
						</c:forEach>
						<th class="text-center"><fmt:message key="mapstudy.evaluations.article.final.evaluate" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="acvo" items="${articles}" varStatus="s">
						<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} gradeA">
							<td>${acvo.article.id}</td>
							<c:forEach var="u" items="${members}" varStatus="s">
								<td>${acvo.getEvaluationClassification(u)}</td>
							</c:forEach>
							<td>${acvo.article.showFinalEvaluation()}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<hr/>
		
		<h4>
			<fmt:message key="mapstudy.evaluations.article.final.evaluate" />
		</h4>
		<form action="${linkTo[MapStudyController].finalEvaluate}" method="post">
			<input type="hidden" name="mapStudyId" value="${mapStudy.id}" />
			
			<div class="form-group">
				<div class="col-lg-4">
					<input type="text" class="form-control" id="articleId" name="articleId" placeholder="<fmt:message key="mapstudy.evaluations.articleid" />"/>
				</div>
				
				<div class="col-lg-6">
					<select class="form-control" name="evaluation">
						<c:forEach var="es" items="${evaluationStatus}">
							<option value="${es}">${es}</option>
						</c:forEach>
					</select>
				</div>
				
				<button type="submit" id="submit" class="col-lg-2 btn btn-large btn-primary float-right">
					<fmt:message key="mapstudy.evaluations.article.evaluate"/>
				</button>
				<div class="clear-both"></div>
			</div>
		</form>
		
		<hr/>
		
		<h4>
			<fmt:message key="mapstudy.evaluations.accepted"/>
		</h4>
		<div class="dataTable_wrapper">
			<table
				class="table table-striped table-bordered table-hover personalized-table">
				<thead>
					<tr>
						<th class="text-center">ID</th>
						<c:forEach var="m" items="${members}" varStatus="s">
							<th class="text-center">${m.login}</th>
						</c:forEach>
						<th class="text-center"><fmt:message key="mapstudy.evaluations.article.final.evaluate" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="acvo" items="${articlesAccepted}" varStatus="s">
						<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} gradeA">
							<td>${acvo.article.id}</td>
							<c:forEach var="u" items="${members}" varStatus="s">
								<td title="${u.login} - ${acvo.getEvaluationClassification(u)}">${acvo.getEvaluationClassification(u)}</td>
							</c:forEach>
							<td>${acvo.article.showFinalEvaluation()}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
  		
	</div>
</div>
