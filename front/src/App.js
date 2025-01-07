import React, { useEffect, useState } from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import { jwtDecode } from "jwt-decode"
import Enter from './pages/Enter'
import PageCalendar from './pages/PageCalendar'
import Account from './pages/Account'

export const ROLES = {
    TUTOR: 'TUTOR',
    STUDENT: 'STUDENT'
}

function App() {
    const [isTutor, setTutorRole] = useState(false);
    const [isStudent, setStudentRole] = useState(false);

    useEffect(() => {
        checkRoles()
    }, [])

    function checkRoles() {
        setTutorRole(getRoles().includes(ROLES.TUTOR))
        setStudentRole(getRoles().includes(ROLES.STUDENT))
    }

    return (
        <>
            <div className="min-h-screen">
                <Router>
                    <Routes>
                        {
                            !isStudent && !isTutor && <Route path="/" element={<Enter />} />
                        }
                        {
                            (isTutor || isStudent) && <Route path="/" element={<PageCalendar />} />
                        }
                        {
                            <Route path="/account/:id" element={<Account />} />
                        }
                    </Routes>
                </Router>
            </div >
        </>
    );
}

export default App;

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