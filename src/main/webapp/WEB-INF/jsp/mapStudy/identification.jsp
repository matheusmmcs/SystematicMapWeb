<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){
	$('#levenshtein').popover();
	$('#regexterm').popover();	
	$('#limiartitulo').popover();	
	$('#limiarabstract').popover();	
	$('#limiarkeywords').popover();	
	$('#limiartotal').popover();	
}); 
</script>

<ol class="breadcrumb u-margin-top">
  <li><a href="<c:url value="/" />"><fmt:message key="home"/></a></li>
  <li><a href="${linkTo[MapStudyController].show(map.id)}"><fmt:message key="mapstudy.details"/></a></li>
  <li class="active"><fmt:message key="mapstudy.searching"/></li>
</ol>

<h3 class="color-primary">
	<fmt:message key="mapstudy" /> - ${map.title} <a id="return" class="btn btn-default pull-right" href="${linkTo[MapStudyController].show(map.id)}"><fmt:message key="button.back"/></a>
</h3>

<div class="row">
	<div class="panel panel-default">
		<div class="panel-heading"><b><fmt:message key="mapstudy.articles" /></b></div>
		<div class="panel-body">
			<h4><fmt:message key="mapstudy.article.add" /></h4><hr/>
			<form action="${linkTo[MapStudyController].addarticles}" enctype="multipart/form-data" method="post">
				<input type="hidden" name="id" value="${map.id}" />
				<div class="form-group">
					<div class="row">
						<div class="fileupload fileupload-new" data-provides="fileupload">
							<div class="col-lg-3">
								<span class="btn btn-file btn-default"> <span class="fileupload-new"><fmt:message key="mapstudy.article.add.choose" /></span> 
								<span class="fileupload-exists">Change</span> <input type="file" name="upFile" /></span> 
								<span class="fileupload-preview"></span> <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none">&times;</a>
							</div>
						</div>
						<div class="col-lg-6">
							<select class="form-control" name="source">
								<c:forEach var="source" items="${sources}">
									<option value="${source}">${source.description}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-lg-3" style="text-align: right">
							<button type="submit" id="submit" class="btn btn-large btn-primary">
								<fmt:message key="mapstudy.articles.add.bibtex" />
							</button>
							<div class="clear-both"></div>
						</div>
					</div>
				</div>
			</form>
			<hr/>
			<div class="row">
				<div class="col-md-12 text-right">
					<a style="width: 220px;" class="btn btn-large btn-primary" href="${linkTo[MapStudyController].addmanuallyarticles(map.id)}">
						<fmt:message key="mapstudy.article.addmanually" />
					</a>
					<a style="width: 220px;" class="btn btn-large btn-danger" href="${linkTo[MapStudyController].removearticles(map.id)}">
						<fmt:message key="mapstudy.articles.remove" />
					</a>
				</div>
			</div>
				<div class="clear-both"></div><hr />
				<h4><fmt:message key="mapstudy.articles.list" /></h4>
				<div class="dataTable_wrapper">
					<table class="table table-striped table-bordered table-hover personalized-table">
						<thead>
							<tr>
								<th class="text-center">ID</th>
								<th class="text-center"><fmt:message key="mapstudy.article.score" /></th>
								<th class="text-center"><fmt:message key="mapstudy.article.title" /></th>
								<th class="text-center"><fmt:message key="mapstudy.article.classification" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="article" items="${map.articles}" varStatus="s">
								<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} gradeA">
									<td>${article.id}</td>
									<td>${article.score}</td>
									<td>${article.title}</td>
									<td>${article.classification.description}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<hr/>
				<h4><fmt:message key="mapstudy.article.refine" /></h4>
				<form action="${linkTo[MapStudyController].refinearticles}" method="post">
					<input type="hidden" name="id" value="${map.id}" />
					<div class="form-group">
						<label for="levenshtein">Levenshtein (similaridade de t�tulos)</label>
						<input type="text" value="0" class="form-control" id="levenshtein" name="levenshtein" placeholder="Levenshtein" 
						data-toggle="popover" data-trigger="hover" data-placement="left" data‐container="body" data-content='<fmt:message key="mapstudy.article.refine.levenshtein" />' data-title='<fmt:message key="mapstudy.article.refine.information" />'/>
					</div>
					<div class="form-group">
						<label for="regex">Regex (termo:regex;...)</label>
						<textarea class="form-control" id="regexterm" name="regex" rows="5" cols=""	data-toggle="popover" data-trigger="hover" data-placement="left" data‐container="body" data-content='<fmt:message key="mapstudy.article.refine.regexterm" />' data-title='<fmt:message key="mapstudy.article.refine.information" />'>automatico:(automat.*|semiautomati.*|semi-automati.*);web:(web|website|internet|www);usabilidade:(usability|usable);tecnica:(evalu.*|assess.*|measur.*|experiment.*|stud.*|test.*|method.*|techni.*|approach.*)</textarea>
					</div>
					<div class="form-group">
						<label for="limiartitulo">Limiar T&iacute;tulo</label>
						<input type="text" value="0" class="form-control" id="limiartitulo" name="limiartitulo" placeholder="Limiar T&iacute;tulo" 
						data-toggle="popover" data-trigger="hover" data-placement="left" data‐container="body" data-content='<fmt:message key="mapstudy.article.refine.limiartitulo" />' data-title='<fmt:message key="mapstudy.article.refine.information" />'/>
					</div>
					<div class="form-group">
						<label for="limiarabstract">Limiar Abstract</label>
						<input type="text"  value="0" class="form-control" id="limiarabstract" name="limiarabstract" placeholder="Limiar Abstract" 
						data-toggle="popover" data-trigger="hover" data-placement="left" data‐container="body" data-content='<fmt:message key="mapstudy.article.refine.limiarabstract" />' data-title='<fmt:message key="mapstudy.article.refine.information" />'/>
					</div>
					<div class="form-group">
						<label for="limiarkeywords">Limiar Keywords</label>
						<input type="text"  value="0" class="form-control" id="limiarkeywords" name="limiarkeywords" placeholder="Limiar Keywords" 
						data-toggle="popover" data-trigger="hover" data-placement="left" data‐container="body" data-content='<fmt:message key="mapstudy.article.refine.limiarkeywords" />' data-title='<fmt:message key="mapstudy.article.refine.information" />'/>
					</div>
					<div class="form-group">
						<label for="limiartotal">Limiar Total</label>
						<input type="text"  value="0" class="form-control" id="limiartotal" name="limiartotal" placeholder="Limiar Total" 
						data-toggle="popover" data-trigger="hover" data-placement="left" data‐container="body" data-content='<fmt:message key="mapstudy.article.refine.limiartotal" />' data-title='<fmt:message key="mapstudy.article.refine.information" />'/>
					</div>
					<div class="form-group text-right">
						<button type="submit" id="submit" class="btn btn-large btn-primary" style="width: 130px;">
							<fmt:message key="mapstudy.article.refine" />
						</button>
						<a style="width: 130px; margin-left: 5px;" class="btn btn-large btn-danger" href="${linkTo[MapStudyController].unrefinearticles(map.id)}">Desfazer Refino</a>
					</div>
					<!-- <form action="${linkTo[MapStudyController].unrefinearticles}" method="post">
						<input type="hidden" name="id" value="${map.id}" />
						<button type="submit" id="submit" class="btn btn-large btn-danger pull-right" style="display: inline-block; margin: 15px;">
							<fmt:message key="mapstudy.article.unrefine" />
						</button>
					</form>  -->
				</form>
			</div>
		</div>
</div>