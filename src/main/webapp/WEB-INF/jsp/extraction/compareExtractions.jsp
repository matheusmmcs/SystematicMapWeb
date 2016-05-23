<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ol class="breadcrumb u-margin-top">
  <li><a href="<c:url value="/" />"><fmt:message key="home"/></a></li>
  <li><a href="${linkTo[MapStudyController].list}"><fmt:message key="mapstudy.short.list"/></a></li>
  <li><a href="${linkTo[MapStudyController].show(mapStudy.id)}"><fmt:message key="mapstudy.details"/></a></li>
  <li><a href="${linkTo[ExtractionController].showExtractionEvaluates(mapStudy.id)}"><fmt:message key="mapstudy.viewextractions"/></a></li>
  <li class="active"><fmt:message key="mapstudy.extractions.compare"/></li>
</ol>

<h3 class="color-primary">
	<fmt:message key="mapstudy.extractions.compare"/> - ${mapStudy.title}
	<a id="return" class="btn btn-default pull-right" href="${linkTo[ExtractionController].showExtractionEvaluates(mapStudy.id)}"><fmt:message key="button.back"/></a>
</h3>

<div class="row">
  	<div class="col-lg-12">
  		<h4>
			<fmt:message key="mapstudy.extractions.all"/>
		</h4>
		<div class="dataTable_wrapper">
			<table
				class="table table-striped table-bordered table-hover personalized-table">
				<thead>
					<tr>
<!-- 						<th class="text-center">ID</th> -->
						<th class="">Quest&otilde;es</th>
						<th class="">Extra&ccedil;&otilde;es</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="ext" items="${articles.get(0).extractions}" varStatus="s">
						<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} gradeA article-to-read">
<%-- 							<td class="question-id">${ext.question.id}</td> --%>
							<td class="question-name">${ext.question.name}</td>
							<td class="alternatives-values">
								<select data-placeholder="<fmt:message key="mapstudy.alternatives.choose" />" class="form-control select2" name="alternativeId" tabindex="2">
									<c:forEach var="alt" items="${ext.userAndAlternatives}">
										<option value="${alt.alternative.id}" data-email="${alt.user.name}">${alt.alternative.value}</option>
									</c:forEach>
								</select>							
							</td>							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
  	</div>
</div>  	