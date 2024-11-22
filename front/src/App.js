import React, { useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Registration from './pages/Registration'
import TutorCalendar from './pages/TutorCalendar'

function App() {
    const isTutor = true
    const isStudent = false
    return (
        <>
            <div className="min-h-screen">
                <Router>
                    <Routes>
                        {
                            !isStudent && !isTutor && <Route path="/" element={<Registration />} />
                        }
                        {
                            isStudent && <Route path="/" element={<Registration />} />
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