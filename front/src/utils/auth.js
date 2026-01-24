import { jwtDecode } from "jwt-decode"

export const ROLES = {
    TUTOR: 'TUTOR',
    STUDENT: 'STUDENT',
    ADMIN: 'ADMIN'
}

export function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

export function getRoles() {
    const currentToken = getCookie("token")
    if (currentToken == '')
        return []
    const decodedBody = jwtDecode(currentToken)
    const authorities = decodedBody.authorities
    if (Array.isArray(authorities))
        return authorities.map(auth => auth.authority)
    else
        return []
}

export function getCurrentUserEmail() {
    const currentToken = getCookie("token")
    if (currentToken == '')
        return ""
    const decodedBody = jwtDecode(currentToken)
    return decodedBody.sub
}