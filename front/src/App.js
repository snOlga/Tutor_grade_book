import React, { useEffect, useState } from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import { jwtDecode } from "jwt-decode"
import Enter from './pages/Enter'
import TutorCalendar from './pages/TutorCalendar'

function App() {
    const [isTutor, setTutorRole] = useState(false);
    const [isStudent, setStudentRole] = useState(false);

    useEffect(() => {
        checkRoles()
    }, [])

    function checkRoles() {
        let currentToken = getCookie("token")
        if (currentToken == '')
            return
        let decodedBody = jwtDecode(currentToken)
        let subject = decodedBody.sub
        localStorage.setItem("subject", subject)
        let authorities = decodedBody.authorities
        let isStudent = false
        let isTutor = false
        if (Array.isArray(authorities)) {
            authorities.forEach((auth) => {
                if (auth.authority == 'STUDENT')
                    isStudent = true
                if (auth.authority == 'TUTOR')
                    isTutor = true
            });
        }
        setTutorRole(isTutor)
        setStudentRole(isStudent)
    }

    function getCookie(cname) {
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

    return (
        <>
            <div className="min-h-screen">
                <Router>
                    <Routes>
                        {
                            !isStudent && !isTutor && <Route path="/" element={<Enter />} />
                        }
                        {
                            isTutor && isStudent && <Route path="/" element={<TutorCalendar />} />
                        }
                        {
                            isStudent && <Route path="/" element={<Enter />} />
                        }
                        {
                            isTutor && <Route path="/" element={<TutorCalendar />} />
                        }
                    </Routes>
                </Router>
            </div >
        </>
    );
}

export default App;