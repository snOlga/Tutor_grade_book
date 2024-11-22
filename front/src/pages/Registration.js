import React from 'react';
import RegistrationForm from '../components/RegistrationForm';
import '../styles/styles.css';

function Registration() {
    return (
        <div className='registration-page'>
            <div className="app-container">
                <h1>Register</h1>
                <RegistrationForm />
            </div>
        </div>
    );
}

export default Registration;
