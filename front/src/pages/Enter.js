import React, { useState } from 'react';
import RegistrationForm from '../components/RegistrationForm';
import LoginForm from '../components/LoginForm';
import '../styles/styles.css';

function Enter({ setRoles }) {
    const [isRegstration, showRegistration] = useState(true)
    const [isLogin, showLogin] = useState(false)

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
                        <RegistrationForm setRoles={setRoles} />
                    </div>
                }
                {
                    isLogin &&
                    <div>
                        <h1>Log in</h1>
                        <a className="reg-selector"
                            onClick={resetForm}>Register</a>
                        <LoginForm setRoles={setRoles} />
                    </div>
                }
            </div>
        </div>
    );
}

export default Enter;
