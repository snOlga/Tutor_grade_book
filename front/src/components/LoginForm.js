import React, { useState } from 'react';

const LoginForm = () => {
    const [formData, setFormData] = useState({
        email: '',
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
                email: formData.email,
                password: formData.password
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.isSuccessful === "true") {
                    let expires = (new Date(Date.now() + 86400 * 1000)).toUTCString();
                    document.cookie = "token=" + data.token + "; expires=" + expires
                    window.location.reload();
                }
            })
    }

    return (
        <form className="registration-form" onSubmit={handleSubmit}>
            {error && <p className="error-message">{error}</p>}
            <div className="form-group">
                <label className="req-label" htmlFor="email">Email</label>
                <input
                    type="email"
                    id="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    placeholder="Enter your email"
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
            <button type="submit" className="submit-button">Log in</button>
        </form>
    );
};

export default LoginForm;