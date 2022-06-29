package hu.simda.musicmanagerserver.annotation

import hu.simda.musicmanagerserver.service.exceptions.UnauthenticatedAccessException
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.aop.Pointcut
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component


//@Aspect
//@Component
//@Order(1)
class SecurityGraphQLAspect {
    /**
     * All graphQLResolver methods can be called only by authenticated user.
     *
     * @Unsecured annotated methods are excluded
     */
    @Before("allGraphQLResolverMethods() && isDefinedInApplication() && !isMethodAnnotatedAsUnsecured()")
    fun doSecurityCheck() {
        if (SecurityContextHolder.getContext() == null || SecurityContextHolder.getContext().authentication == null ||
            !SecurityContextHolder.getContext().authentication.isAuthenticated ||
            AnonymousAuthenticationToken::class.java.isAssignableFrom(SecurityContextHolder.getContext().authentication.javaClass)
        ) {
            throw UnauthenticatedAccessException("Sorry, you should log in first to do that!")
        }
    }

    /**
     * @AdminSecured annotated methods can be called only by admin authenticated user.
     */
    @Before("isMethodAnnotatedAsAdminUnsecured()")
    fun doAdminSecurityCheck() {
        if (!isAuthorized()) {
            throw UnauthenticatedAccessException("Sorry, you do not have enough rights to do that!")
        }
    }

    /**
     * Matches all beans that implement [com.coxautodev.graphql.tools.GraphQLResolver] as
     * `UserMutation`, `UserQuery`
     * extend GraphQLResolver interface
     */
    @org.aspectj.lang.annotation.Pointcut("target(com.coxautodev.graphql.tools.GraphQLResolver)")
    private fun allGraphQLResolverMethods() {
        //leave empty
    }

    /**
     * Matches all beans in com.zerofiltre.samplegraphqlerrorhandling package
     */
    @org.aspectj.lang.annotation.Pointcut("within(hu.simda.musicmanagerserver..*)")
    private fun isDefinedInApplication() {
        // leave empty
    }

    /**
     * Any method annotated with @Unsecured
     */
    @org.aspectj.lang.annotation.Pointcut("@annotation(hu.simda.musicmanagerserver.annotation.Unsecured)")
    private fun isMethodAnnotatedAsUnsecured() {
        // leave empty
    }

    /**
     * Any method annotated with @AdminSecured
     */
    @org.aspectj.lang.annotation.Pointcut("@annotation(hu.simda.musicmanagerserver.annotation.AdminSecured)")
    private fun isMethodAnnotatedAsAdminUnsecured() {
        // leave empty
    }


    private fun isAuthorized(): Boolean {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        if (authentication != null) {
            val authorities: Collection<GrantedAuthority> = authentication.authorities
            for (auth in authorities) {
                if (auth.authority == "ROLE_ADMIN") return true
            }
        }
        return false
    }
}