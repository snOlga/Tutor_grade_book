import React from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import { AuthProvider, useAuth } from './contexts/AuthContext'
import Enter from './pages/Enter'
import PageCalendar from './pages/PageCalendar'
import Account from './pages/Account'

function App() {
    return (
        <AuthProvider>
            <AppContent />
        </AuthProvider>
    );
}

function AppContent() {
    const { isStudent, isTutor } = useAuth();

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