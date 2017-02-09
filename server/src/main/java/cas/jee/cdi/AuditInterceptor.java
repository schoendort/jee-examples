/*
 * (c) 2015 - 2017 ENisco GmbH &amp; Co. KG
 */
package cas.jee.cdi;

import java.io.Serializable;
import java.util.Arrays;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
public class AuditInterceptor
    implements Serializable
{

    @AroundInvoke
    public Object auditMethodCall(InvocationContext invocationContext)
        throws Exception
    {
        System.out.println("Intercepting call to method: "
            + invocationContext.getMethod().getName() + " args = "
            + Arrays.toString(invocationContext.getParameters()));

        Object result = invocationContext.proceed();

        System.out.println("after method result is " + result);

        return result;
    }

}
