<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${not empty articles}">
<h3 class="color-primary"><fmt:message key="mapstudy.articles.list"/></h3>
<form action="${linkTo[MapStudyController].removearticlesform}" method="post" id="formRemoveArticles">
<table class="table table-striped table-bordered table-hover personalized-table-simple">
	<thead>
		<tr>
			<th width="2%" class="text-center"><fmt:message key="remove"/></th>
			<th width="2%" class="text-center"><fmt:message key="id"/></th>
			<th width="96%" class="text-center"><fmt:message key="mapstudy.article"/></th>			
		</tr>
	</thead>
	<tbody>
		<c:forEach var="article" items="${articles}" varStatus="s">
			<tr>
					<td>
						<div class="text-center">
							<input type="checkbox" name="articlesIds[${s.index}]" value="${article.id}"></input>
						</div>
					</td>
					<td>
						<div class="text-center">
							${article.id}
						</div>
					</td>
					<td>${article.title}</td>
				</tr>
		</c:forEach>
	</tbody>
</table>
		<input type="hidden" name="mapId" id="mapId" value="${map.id}"/>	
		<a style="text-align: left;" id="return" class="btn btn-lg btn-link" href="${linkTo[MapStudyController].show(map.id)}"><fmt:message key="button.back"/></a>
		<button style="text-align: right;" type="submit" class="btn btn-lg btn-danger"><i class="glyphicon glyphicon-remove"></i> <fmt:message key="remove"/></button>
	</form>
</c:if>
