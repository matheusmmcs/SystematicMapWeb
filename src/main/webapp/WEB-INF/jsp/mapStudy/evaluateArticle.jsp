<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h3 class="color-primary"><fmt:message key="mapstudy.evaluation"/> - ${map.title} (${percentEvaluated}%)</h3>

<div class="row">
  	<div class="col-lg-12">
  		
		<h2><fmt:message key="mapstudy.article"/> - ${article.id}</h2>
		
		<p> 
			<strong>
				<fmt:message key="mapstudy.article.title"/>:
			</strong> ${article.title}
		<p>
		
		<p> 
			<strong>
				<fmt:message key="mapstudy.article.abstract"/>:
			</strong> ${article.abstrct}
		<p>
		
		<p> 
			<strong>
				<fmt:message key="mapstudy.article.key"/>:
			</strong> ${article.keywords}
		<p>
		
		<p> 
			<strong>
				<fmt:message key="mapstudy.article.author"/>:
			</strong> ${article.author}
		<p>
		
		<hr/>
		
		<form action="${linkTo[MapStudyController].includearticle}" method="post">
			<input type="hidden" name="mapid" value="${map.id}" />
			<input type="hidden" name="articleid" value="${article.id}" />
			<input type="hidden" name="nextArticleId" value="${nextArticleId}" />
			
			<p> 
				<strong>
					<fmt:message key="mapstudy.inclusion.criterias"/>:
				</strong><br/>
				<c:forEach items="${inclusionOrdered}" var="criteria" varStatus="c">
				
					<c:set var="containsExc" value="false" />
					<c:forEach var="done" items="${evaluationDone.inclusionCriterias}">
					  <c:if test="${criteria.id eq done.id}">
					    <c:set var="containsExc" value="true" />
					  </c:if>
					</c:forEach>
					
	                <input type="checkbox" name="inclusions[${c.index}]" value="${criteria.id}" ${containsExc ? 'checked="checked"' : '' } />  
	                ${criteria.description}<br/>  
	            </c:forEach>
			<p>
			
			<p> 
				<strong>
					<fmt:message key="mapstudy.article.comments"/>:
				</strong><br/>
				<textarea class="form-control" name="comment" rows="3" cols="">${evaluationDone.comment}</textarea>
			<p>
			
			<button type="submit" id="submit" class="btn btn-large btn-success" style="display: inline-block;">
				<fmt:message key="mapstudy.include"/>
			</button>
		</form>
		
		<hr/>
			
		<form action="${linkTo[MapStudyController].excludearticle}" method="post">
			<input type="hidden" name="mapid" value="${map.id}" />
			<input type="hidden" name="articleid" value="${article.id}" />
			<input type="hidden" name="nextArticleId" value="${nextArticleId}" />
			
			<p> 
				<strong>
					<fmt:message key="mapstudy.exclusion.criterias"/>:
				</strong><br/>
				
				<c:forEach items="${exclusionOrdered}" var="criteria" varStatus="c">
				
					<c:set var="containsExc" value="false" />
					<c:forEach var="done" items="${evaluationDone.exclusionCriterias}">
					  <c:if test="${criteria.id eq done.id}">
					    <c:set var="containsExc" value="true" />
					  </c:if>
					</c:forEach>
				
	                <input type="checkbox" name="exclusions[${c.index}]" value="${criteria.id}" ${containsExc ? 'checked="checked"' : '' } />
	                ${criteria.description}<br/>
	            </c:forEach>
			<p>
			
			<p> 
				<strong>
					<fmt:message key="mapstudy.article.comments"/>:
				</strong><br/>
				<textarea class="form-control" name="comment" rows="3" cols="">${evaluationDone.comment}</textarea>
			<p>
			
			<button type="submit" id="submit" class="btn btn-large btn-danger" style="display: inline-block;">
				<fmt:message key="mapstudy.exclude"/>
			</button>
		</form>
  		
	</div>
</div>

<hr/>

<div class="row">
	<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.articles.list" /></b>
			</div>
			<div class="panel-body">
			
				<h4>
					<fmt:message key="mapstudy.articles.list.toreview"/>
				</h4>
				<div class="dataTable_wrapper">
					<table
						class="table table-striped table-bordered table-hover personalized-table">
						<thead>
							<tr>
								<th class="text-center">ID</th>
								<th class="text-center"><fmt:message key="mapstudy.article.title" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="article" items="${articlesToEvaluate}" varStatus="s">
								<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} gradeA">
									<td>${article.id}</td>
									<td><a href="${linkTo[MapStudyController].evaluateArticle(map.id, article.id)}">${article.title}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
				<hr/>
				
				<h4>
					<fmt:message key="mapstudy.articles.list.evaluated"/>
				</h4>
				<div class="dataTable_wrapper">
					<table
						class="table table-striped table-bordered table-hover personalized-table">
						<thead>
							<tr>
								<th class="text-center">ID</th>
								<th class="text-center"><fmt:message key="mapstudy.article.title" /></th>
								<th class="text-center"><fmt:message key="mapstudy.article.evaluation" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="eval" items="${evaluations}" varStatus="s">
								<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} gradeA">
									<td>${eval.article.id}</td>
									<td><a href="${linkTo[MapStudyController].evaluateArticle(map.id, eval.article.id)}">${eval.article.title}</a></td>
									<td>${eval.classification}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
</div>
