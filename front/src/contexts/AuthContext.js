import React, { createContext, useContext, useEffect, useState } from 'react';
import { getRoles, ROLES } from '../utils/auth';

const AuthContext = createContext();

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};

export const AuthProvider = ({ children }) => {
    const [isTutor, setTutorRole] = useState(false);
    const [isStudent, setStudentRole] = useState(false);
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        checkRoles();
    }, []);

    const checkRoles = () => {
        const roles = getRoles();
        setTutorRole(roles.includes(ROLES.TUTOR));
        setStudentRole(roles.includes(ROLES.STUDENT));
        setIsAuthenticated(roles.length > 0);
    };

    const value = {
        isTutor,
        isStudent,
        isAuthenticated,
        checkRoles,
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};