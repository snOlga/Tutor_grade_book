import React, { useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Enter from './pages/Enter'
import TutorCalendar from './pages/TutorCalendar'

function App() {
    const isTutor = false
    const isStudent = false
    return (
        <>
            <div className="min-h-screen">
                <Router>
                    <Routes>
                        {
                            !isStudent && !isTutor && <Route path="/" element={<Enter />} />
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