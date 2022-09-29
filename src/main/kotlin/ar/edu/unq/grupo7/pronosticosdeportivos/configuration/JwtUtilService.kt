package ar.edu.unq.grupo7.pronosticosdeportivos.configuration

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors


@Service
class JwtUtilService {
    fun extractUsername(token: String?): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun extractExpiration(token: String?): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
        return claimsResolver.apply(extractAllClaims(token))
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJwt(token).getBody()
    }

    private fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims: MutableMap<String, Any?> = HashMap()
        val rol = userDetails.authorities.stream().collect(Collectors.toList())[0]
        claims["rol"] = rol
        return createToken(claims, userDetails.username)
    }

    private fun createToken(claims: Map<String, Any?>, subject: String): String {
        return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
            .compact()
    }

    fun validateToken(token: String?, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private val JWT_SECRET_KEY = "TExBVkVfTVVZX1NFQ1JFVEE="

    companion object {
        const val JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 8L // 8 Horas
    }
}
