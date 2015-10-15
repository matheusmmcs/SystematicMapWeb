<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
// 	(function($){
// 		$(document).ready(function(){
// 			$("#formAddArticle").validate({ 
//                  rules: {
//                 	 'mapstudy.title': { 
//                     	 required : true,
//                     	 minlength : 3
//                      },
//                      'mapstudy.description': {
//                          required: true,
//                          minlength : 3
//                      }
//                   }, messages: {
//                 	  'mapstudy.title': {
//                           required: '<fmt:message key="required" />',
//                           minlength: '<fmt:message key="mapstudy.min.title" />'
//                       },
//                       'mapstudy.description': {
//                           required: '<fmt:message key="required" />',
//                           minlength: '<fmt:message key="mapstudy.min.description" />'
//                       }
//                   }
// 			});
// 		});
// 	})(jQuery);
</script>

<h3 class="color-primary"><fmt:message key="mapstudy.article.addmanually" /></h3>

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
<%-- 				<input type="text" class="form-control" name="article.abstrct" id="abstract" value="${article.abstrct}"/> --%>
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
				<button type="submit" class="btn btn-primary"><fmt:message key="mapstudy.article.addmanually"/></button>
			</div>	
		</form>
	</div>		
</div>