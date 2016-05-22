<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
	(function($){
		$(document).ready(function(){
			$("#formAddArticle").validate({ 
                 rules: {
                	 'article.author': { 
                    	 required : true,
                    	 maxlength: 2000
                     },
                     'article.title': { 
                    	 required : true,
                    	 maxlength: 2000
                     },
                     'article.keywords': { 
                    	 required : true,
                    	 maxlength: 2000
                     },
                     'article.journal': { 
                    	 required : true
                     },
                     'article.abstrct': { 
                    	 required : true
                     },
                     'article.year': { 
                    	 required : true
                     },
                     'article.journal': { 
                    	 required : true
                     }
                  }
            , messages: {
            	 'article.author': { 
                	 required : '<fmt:message key="required"/>',
                	 maxlength: '<fmt:message key="article.author.maxlength"/>'
                 },
                 'article.title': { 
                	 required : '<fmt:message key="required"/>',
                	 maxlength: '<fmt:message key="article.title.maxlength"/>'
                 },
                 'article.keywords': { 
                	 required : '<fmt:message key="required"/>',
                	 maxlength: '<fmt:message key="article.keywords.maxlength"/>'
                 }
                  }
			});
		});
	})(jQuery);
</script>

<ol class="breadcrumb u-margin-top" style="margin-top: 0px;">
  <li><a href="<c:url value="/" />"><fmt:message key="home"/></a></li>
  <li><a href="${linkTo[MapStudyController].show(map.id)}"><fmt:message key="mapstudy.details"/></a></li>
  <li><a href="${linkTo[MapStudyController].identification(map.id)}"><fmt:message key="mapstudy.searching"/></a></li>
  <li class="active"><fmt:message key="mapstudy.article.addmanually"/></li>
</ol>

<h3 class="color-primary">
	<fmt:message key="mapstudy.article.addmanually" />
	<a id="return" class="btn btn-default pull-right" href="${linkTo[MapStudyController].identification(map.id)}"><fmt:message key="button.back"/></a>
</h3>
<hr/>

<div class="well">
	<div class="row">
		<form action="${linkTo[MapStudyController].addmanuallyarticlesform}" method="post" id="formAddArticle">
		<input type="hidden" name="mapId" value="${map.id}">
			<div class="form-group">
				<label for="author" class=""><fmt:message key="mapstudy.article.author"/></label>
				<input type="text" class="form-control" name="article.author" id="author" value="${article.author}"/>
			</div>
			
			<div class="form-group">
				<label for="title" class=""><fmt:message key="mapstudy.article.title"/></label>
				<input type="text" class="form-control" name="article.title" id="title" value="${article.title}"/>
			</div>
			
			<div class="form-group">
				<label for="keywords" class=""><fmt:message key="mapstudy.article.key"/></label>
				<input type="text" class="form-control" name="article.keywords" id="keywords" value="${article.keywords}"/>
			</div>
			
			<div class="form-group">
				<label for="journal" class=""><fmt:message key="mapstudy.article.journal"/></label>
				<input type="text" class="form-control" name="article.journal" id="journal" value="${article.journal}"/>
			</div>
			
			<div class="form-group">
				<label for="abstract" class=""><fmt:message key="mapstudy.article.abstract"/></label>
				<textarea class="form-control" name="article.abstrct" rows="3" cols="" id="abstract">${article.abstrct}</textarea>
			</div>
			
			<div class="form-group">
				<label for="year" class=""><fmt:message key="mapstudy.article.year"/></label>
				<input type="number" class="form-control" name="article.year" id="year" value="${article.year}" width="4"/>
			</div>
			
			<div class="form-group">
				<label for="doctype" class=""><fmt:message key="mapstudy.article.doctype"/></label>
				<input type="text" class="form-control" name="article.docType" id="doctype" value="${article.docType}"/>
			</div>
			
			<div class="form-group">
				<label for="language" class=""><fmt:message key="mapstudy.article.language"/></label>
				<input type="text" class="form-control" name="article.language" id="language" value="${article.language}"/>
			</div>
			
			<div class="form-group">
				<label for="doi" class=""><fmt:message key="mapstudy.article.doi"/></label>
				<input type="text" class="form-control" name="article.doi" id="doi" value="${article.doi}"/>
			</div>
			
			<div class="form-group">
				<label for="url" class=""><fmt:message key="mapstudy.article.url"/></label>
				<input type="text" class="form-control" name="article.url" id="url" value="${article.url}"/>
			</div>
			
			<div class="form-group pull-right">	
				<button type="submit" class="btn btn-primary">Adicionar</button>
			</div>	
		</form>
	</div>		
</div>