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
                  }, messages: {
                	  'email': {
                          required: '<fmt:message key="required" />',
                          email: '<fmt:message key="invalid_email" />'
                      }
                  }
			});
		});
	})(jQuery);
</script>

<div class="login-panel panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title"><fmt:message key="recovery.password"/></h3>
    </div>
    <div class="panel-body">
        <form action="${linkTo[EmailController].recoveryPassword}" method="post" class="form-horizontal" id="formSendEmail">
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
