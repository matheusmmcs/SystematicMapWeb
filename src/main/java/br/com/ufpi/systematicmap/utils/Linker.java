package br.com.ufpi.systematicmap.utils;

import java.lang.reflect.Method;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.proxy.MethodInvocation;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.proxy.SuperMethod;

@RequestScoped
public class Linker {
	
	@Inject	private Proxifier proxifier;
	@Inject private Router router;
	@Inject	private HttpServletRequest request;
	
	private Class<?> controllerType;
	private Method method;
	private Object[] args;

	public String getURL() {
		return "http://" + request.getHeader("Host") + request.getContextPath()	+ getURI();
	}

	private String getURI() {
		return router.urlFor(controllerType, method, args);
	}

	@SuppressWarnings("unchecked")
	public <T> T buildLinkTo(T controller){
		return (T) buildLinkTo(controller.getClass());
	}
	
	public <T> T buildLinkTo(Class<T> controllerType) {
		this.controllerType = controllerType;
		@SuppressWarnings("unchecked")
		MethodInvocation<T> invocation = new CacheInvocation();
		return proxifier.proxify(controllerType,invocation);
	}
	
	@SuppressWarnings("rawtypes")
	class CacheInvocation implements MethodInvocation{
		public Object intercept(Object proxy, Method method, Object[] args,
				SuperMethod superMethod) {
			Linker.this.method = method;
			Linker.this.args = args;
			return null;
		}
	}	
	
//	public <T> T buildLinkTo(Class<T> controllerType) {
//		this.controllerType = controllerType;
//		return proxifier.proxify(controllerType, new MethodInvocation<T>() {
//			@Override
//			public Object intercept(T proxy, Method method, Object[] args, SuperMethod superMethod) {
//				Linker.this.method = method;
//				Linker.this.args = args;
//				return null;
//			}
//		});
//	}
}