import React, { useState } from 'react';
import { jwtDecode } from "jwt-decode";

const LoginForm = () => {
    const [formData, setFormData] = useState({
        login: '',
        password: '',
    });

    const [error, setError] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        fetch('http://localhost:18018/auth/log_in', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                login: formData.username,
                password: formData.password
            })
        })
    }

    return (
        <form className="registration-form" onSubmit={handleSubmit}>
            {error && <p className="error-message">{error}</p>}
            <div className="form-group">
                <label className="req-label" htmlFor="login">Email</label>
                <input
                    type="text"
                    id="login"
                    name="login"
                    value={formData.login}
                    onChange={handleChange}
                    placeholder="Enter your login"
                    required
                />
            </div>
            <div className="form-group">
                <label className="req-label" htmlFor="password">Password</label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    placeholder="Enter your password"
                    required
                />
            </div>
            <button type="submit" className="submit-button">Register</button>
        </form>
    );
};

export default LoginForm;
