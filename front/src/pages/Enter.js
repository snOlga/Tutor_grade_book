import React, { useState } from 'react';
import RegistrationForm from '../features/auth/RegistrationForm';
import LoginForm from '../features/auth/LoginForm';
import '../styles/styles.css';

function Enter() {
    const [isRegstration, showRegistration] = useState(false)
    const [isLogin, showLogin] = useState(true)

    function resetForm() {
        showRegistration(!isRegstration)
        showLogin(!isLogin)
    }

    return (
        <div className='registration-page'>
            <div className="app-container" >
                {
                    isRegstration &&
                    <div>
                        <h1>Register</h1>
                        <a className="reg-selector"
                            onClick={resetForm}>Log in</a>
                        <RegistrationForm />
                    </div>
                }
                {
                    isLogin &&
                    <div>
                        <h1>Log in</h1>
                        <a className="reg-selector"
                            onClick={resetForm}>Register</a>
                        <LoginForm />
                    </div>
                }
            </div>
        </div>
    );
}

export default Enter;
