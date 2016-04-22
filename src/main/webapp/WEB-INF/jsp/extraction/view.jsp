<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
	<div class="col-lg-12">
		<p>
			<strong> <fmt:message key="mapstudy.article.title" />:</strong> <span id="articleReadTitle">${article.title}</span>
		<p>
	</div>
</div>

<div class="row">
	<div class="panel panel-default">
		<div class="panel-heading">
			<b><fmt:message key="mapstudy.form" /></b>
		</div>
		<!-- /.panel-heading -->
		<div class="panel-body">
			<h4>
				<fmt:message key="mapstudy.form.evaluate" />
			</h4>
			<form action="${linkTo[ExtractionController].evaluate}" method="post">
				<input type="hidden" name="mapid" value="${map.id}" />
				<input type="hidden" name="articleid" value="${article.id}" />

				<div class="form-group">
						<c:forEach var="question" items="${form.questions}" varStatus="q">
						<input type="hidden" name="questions[${q.index}].id" value="${question.id}" />
<%-- 						<input type="hidden" name="questions[${q.index}].name" value="${question.name}" /> --%>
						
						<div class="padding-left-none">
							<strong>${question.name} :</strong>
						</div>
						<div class="float-right">
							<c:if test="${question.type == 'LIST'}">
								<%-- <input type="hidden" class="form-control" name="alternatives[${q.index}].question.id" value="${question.id}"/> --%>
								<select	data-placeholder="<fmt:message key="mapstudy.form.choose" />" class="form-control select2" name="alternatives[${q.index}].id" tabindex="2">
								<c:if test="${article.alternative(question, userInfo.user) != null}">
										<option name="alternatives[${q.index}].id" data-email="" selected="selected" value="${article.alternative(question, userInfo.user).id}">${article.alternative(question, userInfo.user).value}</option>
								</c:if>
								
									<c:forEach var="alt" items="${question.alternatives}">
										<option value="${alt.id}" data-email="">${alt.value}</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${question.type == 'SIMPLE'}">
								<input type="text" class="form-control" name="alternatives[${q.index}].value" value="${article.alternative(question, userInfo.user).value}"/>
<%-- 								<input type="hidden" class="form-control" name="alternatives[${q.index}].question.id" value="${question.id}"/> --%>
							</c:if>							
						</div>		
						<p>				
						</c:forEach>					
						<p>
					<button type="submit" id="submit" class="btn btn-large btn-primary">
						<fmt:message key="salve" />
					</button>
					<div class="clear-both"></div>
				</div>
				</form>
		</div>
	</div>
</div>

<div class="row">
	<div class="panel panel-default">
		<div class="panel-heading">
			<b><fmt:message key="mapstudy.articles.list" /></b>
		</div>
		<div class="panel-body">

			<h4>
				<fmt:message key="mapstudy.articles.list.toreview" />
			</h4>
			<div class="dataTable_wrapper">
				<table
					class="table table-striped table-bordered table-hover datatable-to-evaluate">
					<thead>
						<tr>
							<th class="text-center">ID</th>
							<th class="text-center"><fmt:message
									key="mapstudy.article.title" /></th>
							<th class="text-center"><fmt:message
									key="mapstudy.article.source" /></th>
						</tr>
					</thead>
					<tbody class="tBodyArticlesToEvaluate">
						<c:forEach var="article" items="${articles}"
							varStatus="s">
							<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} gradeA">
								<td>${article.id}</td>
								<td><a class="readArticle" actualid="${article.id}"
									nextid="${article.id }"
									href="${linkTo[ExtractionController].view(map.id, article.id)}">${article.title}</a></td>
								<td>${article.source}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>	