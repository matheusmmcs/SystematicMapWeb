<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta name="decorator" content="login"/>

<script type="text/javascript">
	(function($){
		$(document).ready(function(){
			$("#formRecoveryPassword").validate({ 
                 rules: {
                	 'password' : {
                          required : true,
                          minlength : 6
                      },
                      'repassword' : {
                    	  required : true,
                          minlength : 6,
                          equalTo: '#password'
                      }
                  }, messages: {
                	  'password': {
                          required: '<fmt:message key="required" />',
                          minlength: '<fmt:message key="password_min" />'
                      },
                      'repassword': {
                          required: '<fmt:message key="required" />',
                          minlength: '<fmt:message key="password_min" />',
                          equalTo: '<fmt:message key="password_different" />'
                      }
                  }
			});
		});
	})(jQuery);
</script>

<div class="row">
	<div class="col-md-4">
	</div>
	
	<div class="col-md-4">
		<div class="login-panel panel panel-default">
		   <div class="panel-heading">
		       <h3 class="panel-title"><fmt:message key="recovery.password"/></h3>
		   </div>
		   <div class="panel-body">
		       <form action="${linkTo[EmailController].newPassword}" method="post" class="form-horizontal" id="formRecoveryPassword">
		           <fieldset>
					<div class="form-group">
						<label class="sr-only" for="password"><fmt:message key="recovery.chance.password"/></label>
						<input type="password" class="form-control" id="password" name="password" placeholder="<fmt:message key="user.password"/>" />	
						<input type="hidden" class="form-control" id="code" name="code" value="${code}"/>
					</div>
					
					<div class="form-group">
						<label class="sr-only" for="repassword"><fmt:message key="recovery.chance.password"/></label>
						<input type="password" class="form-control" id="repassword" name="repassword" placeholder="<fmt:message key="recovery.repassword"/>" />
					</div>
		
		               <!-- Change this to a button or input when using this as a form -->
		               <button type="submit" id="submit" class="btn btn-lg btn-primary btn-block" style="margin-bottom: 10px;">
							<fmt:message key="salve"/>
						</button>
		             </fieldset>
		         </form>
		         <a id="return" class="btn btn-lg btn-link btn-block" href="<c:url value="/"/>"><fmt:message key="button.back"/></a>
		     </div>
		 </div>
	</div>
</div>



