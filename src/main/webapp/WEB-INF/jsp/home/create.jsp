<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta name="decorator" content="login"/>

<script type="text/javascript">
	(function($){
		$(document).ready(function(){
			$("#formCreateUser").validate({ 
                 rules: {
                	 'user.name': { 
                    	 required : true,
                    	 minlength : 3,
                    	 maxlength : 100
                     },
                     'user.login': {
                         required: true,
                         minlength : 3,
                    	 maxlength : 20,
                    	 login: true
//                     	 RegExp : "[a-zA-Z0-9_]"
                     },
                     'user.email' : {
                         required: true,
                         email: true,
                      },
                      'user.password' : {
                          required : true,
                          minlength : 6
                      },
                      're-password' : {
                    	  required : true,
                          minlength : 6,
                          equalTo: '#password'
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
		        <h3 class="panel-title"><fmt:message key="signup"/></h3>
		    </div>
		    <div class="panel-body">
		        <form action="${linkTo[UsersController].add}" method="post" class="form-horizontal" autocomplete="off" id="formCreateUser">
		            <fieldset>
		                <div class="form-group">
							<label class="sr-only" for="name"><fmt:message key="user.name"/></label>
							<input type="text" class="form-control" id="newname" name="user.name" value="${user.name}" placeholder="<fmt:message key="user.name"/>"/>
						</div>
						<div class="form-group">
							<label class="sr-only" for="login"><fmt:message key="user.login"/></label>
							<input type="text" class="form-control" id="newlogin" name="user.login" value="${user.login}" placeholder="<fmt:message key="user.login"/>"/>
						</div>
						<div class="form-group">
							<label class="sr-only" for="email"><fmt:message key="user.email"/></label>
							<input type="text" class="form-control" id="newemail" name="user.email" value="${user.email}" placeholder="<fmt:message key="user.email"/>"/>
						
						</div>
						<div class="form-group">
							<label class="sr-only" for="password"><fmt:message key="user.password"/></label>
							<input type="password" id="password" class="form-control" name="user.password" value="${user.password}" placeholder="<fmt:message key="user.password"/>"/>
						</div>
						
						<div class="form-group">
							<label class="sr-only" for="password"><fmt:message key="user.password"/></label>
							<input type="password" id="re-password" class="form-control" name="re-password" value="" placeholder="<fmt:message key="recovery.repassword"/>"/>
						</div>
						
		                <!-- Change this to a button or input when using this as a form -->
		                <button type="submit" id="submit" class="btn btn-lg btn-primary btn-block">
							<fmt:message key="create"/>
						</button>
		              </fieldset>
		          </form>
		          <a id="return" class="btn btn-lg btn-link btn-block" href="<c:url value="/"/>"><fmt:message key="button.back"/></a>
		      </div>
		  </div>
		  
    </div>
</div>