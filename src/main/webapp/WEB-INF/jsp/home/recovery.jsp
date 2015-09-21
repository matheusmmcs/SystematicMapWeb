<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta name="decorator" content="login"/>

<script type="text/javascript">
	(function($){
		$(document).ready(function(){
			$("#formSendEmail").validate({ 
                 rules: {
                	 'email' : {
                         required: true,
                         email: true,
                      }
                  }
			});
		});
	})(jQuery);
</script>

<div class="row">
	<div class="col-md-4 col-md-offset-4">

	<c:if test="${not empty errors}">
		<div class="alert alert-danger">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<c:forEach items="${errors}" var="error">
				<b><fmt:message key="${error.category}"/></b> - ${error.message} <br/>
			</c:forEach>
		</div>
	</c:if>
	
	<c:if test="${not empty notice}">
		<div class="alert alert-success"> 
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<fmt:message key="${notice}"/> 
		</div>
	</c:if>	
		
<div class="login-panel panel panel-default">
		    <div class="panel-heading">
		        <h3 class="panel-title"><fmt:message key="recovery.password"/></h3>
		    </div>
		    <div class="panel-body">
<%-- 		     --%>
		        <form action="${linkTo[EmailController].recoveryPassword}" method="post" class="form-horizontal" autocomplete="off" id="formSendEmail">
		            <fieldset>
						<div class="form-group">
							<label class="sr-only" for="email"><fmt:message key="user.email"/></label>
							<input type="email" class="form-control" id="email" name="email" placeholder="<fmt:message key="user.email"/>" />
						</div>

		                <!-- Change this to a button or input when using this as a form -->
		                <button type="submit" id="submit" class="btn btn-lg btn-primary btn-block">
							<fmt:message key="send"/>
						</button>
		              </fieldset>
		          </form>
		          <a id="return" class="btn btn-lg btn-link btn-block" href="<c:url value="/"/>"><fmt:message key="button.back"/></a>
		      </div>
		  </div>
<%--    <a id="return" class="btn btn-lg btn-default" href="<c:url value="/"/>" style="text-align: left;"><fmt:message key="button.back"/></a> --%>
    </div>
</div>