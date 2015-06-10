<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h3 class="color-primary"><fmt:message key="mapstudy.evaluations.results"/> - ${mapStudy.title} </h3>

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
					</strong> ${mapStudy.title}
				<p>
				
				<p> 
					<strong>
						<fmt:message key="mapstudy.description"/>:
					</strong> ${mapStudy.description}
				<p>
				
				<p>
					<strong>
						<fmt:message key="mapstudy.evaluation.rate"/>:
					</strong> ${percentEvaluated}%
				<p>
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.evaluations.results"/></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<p> 
					<strong class="color-primary">
						<fmt:message key="mapstudy.evaluations.count.done"/>:
					</strong> ${countRejected + countAccepted}
				<p>
				
				<p> 
					<strong>
						<fmt:message key="mapstudy.evaluations.count.accepted"/>:
					</strong> ${countAccepted}
				<p>
				
				<p> 
					<strong>
						<fmt:message key="mapstudy.evaluations.count.rejected"/>:
					</strong> ${countRejected}
				<p>
				
				<p> 
					<strong>
						<fmt:message key="mapstudy.evaluations.count.todo"/>:
					</strong> ${countWithoutClassification - countAccepted - countRejected}
				<p>
				
				<hr/>
				
				<p> 
					<strong class="color-primary">
						<fmt:message key="mapstudy.evaluations.count.filtred"/>:
					</strong> ${countClassified}
				<p>
				
				<p> 
					<strong>
						<fmt:message key="mapstudy.evaluations.count.repeated"/>:
					</strong> ${countRepeated}
				<p>
				
				<p> 
					<strong>
						<fmt:message key="mapstudy.evaluations.count.dontmatch"/>:
					</strong> ${countDontMatch}
				<p>
				
				<p> 
					<strong>
						<fmt:message key="mapstudy.evaluations.count.withoutauthors"/>:
					</strong> ${countWithoutAuthors}
				<p>
				
				<p> 
					<strong>
						<fmt:message key="mapstudy.evaluations.count.withoutabstract"/>:
					</strong> ${countWithoutAbstracts}
				<p>
				
				<hr/>
				
				<p> 
					<strong class="color-primary">
						<fmt:message key="mapstudy.evaluations.count.total"/>:
					</strong> ${countClassified+countWithoutClassification}
				<p>
				
				<hr/>
				<!--
				<p>
					<strong>
						<fmt:message key="mapstudy.evaluations.export"/>:
					</strong> 
					<a class="btn btn-primary pull-right" href="${linkTo[MapStudyController].download(mapStudy.id)}"><fmt:message key="mapstudy.evaluations.export.bibtex"/></a>
					<div class="clear-both"></div>
				<p>
				 -->
			</div>
		</div>
		
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.articles.list" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="dataTable_wrapper">
					<table
						class="table table-striped table-bordered table-hover personalized-table">
						<thead>
							<tr>
								<th><fmt:message key="id"/></th>
								<th class="text-center"><fmt:message key="mapstudy.article.title" /></th>
								<th class="text-center"><fmt:message key="mapstudy.article.classification" /></th>
								<th><fmt:message key="mapstudy.article.evaluation"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="article" items="${articles}" varStatus="s">
								<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} gradeA">
									<td>${article.id}</td>
									<td><a href="${linkTo[MapStudyController].evaluateArticle(mapStudy.id, article.id)}">${article.title}</a></td>
									<td>${article.classification}</td>
									<td>${article.getEvaluationClassification(user)}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.evaluations.percent.inclusion" /> - ${countAccepted}</b>
			</div>
			<div class="panel-body">
				<div class="dataTable_wrapper">
					<table
						class="table table-striped table-bordered table-hover personalized-table">
						<thead>
							<tr>
								<th><fmt:message key="mapstudy.inclusion.criteria"/></th>
								<th>Count</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="c" items="${inclusionCriteriasMap}" varStatus="s">
								<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} gradeA">
									<td>${c.key.description}</td>
									<td>${c.value}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.evaluations.percent.exclusion" /> - ${countRejected}</b>
			</div>
			<div class="panel-body">
				<div class="dataTable_wrapper">
					<table
						class="table table-striped table-bordered table-hover personalized-table">
						<thead>
							<tr>
								<th><fmt:message key="mapstudy.exclusion.criteria"/></th>
								<th>Count</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="c" items="${exclusionCriteriasMap}" varStatus="s">
								<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} gradeA">
									<td>${c.key.description}</td>
									<td>${c.value}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>

	</div>
</div>