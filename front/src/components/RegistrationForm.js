import React, { useState } from 'react';
import { jwtDecode } from "jwt-decode";

const RegistrationForm = () => {
    const [formData, setFormData] = useState({
        username: '',
        secondName: '',
        phone: '',
        email: '',
        password: '',
        confirmPassword: ''
    });

    const [error, setError] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (formData.password !== formData.confirmPassword) {
            setError('Passwords do not match!');
        } else {
            setError('');
            fetch('http://localhost:18018/auth/sign_up', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    name: formData.username,
                    secondName: formData.secondName,
                    phone: formData.phone,
                    email: formData.email,
                    password: formData.password
                })
            })
        }
    }

    return (
        <form className="registration-form" onSubmit={handleSubmit}>
            {error && <p className="error-message">{error}</p>}
            <div className="form-group">
                <label className="req-label" htmlFor="username">Name</label>
                <input
                    type="text"
                    id="username"
                    name="username"
                    value={formData.username}
                    onChange={handleChange}
                    placeholder="Enter your name"
                    required
                />
            </div>
            <div className="form-group">
                <label htmlFor="secondName">Second name</label>
                <input
                    type="text"
                    id="secondName"
                    name="secondName"
                    value={formData.secondName}
                    onChange={handleChange}
                    placeholder="Enter your second name"
                />
            </div>
            <div className="form-group">
                <label htmlFor="phone">Phone</label>
                <input
                    type="number"
                    id="phone"
                    name="phone"
                    value={formData.phone}
                    onChange={handleChange}
                    placeholder="Enter your phone"
                />
            </div>
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
            <div className="form-group">
                <label className="req-label" htmlFor="confirmPassword">Confirm Password</label>
                <input
                    type="password"
                    id="confirmPassword"
                    name="confirmPassword"
                    value={formData.confirmPassword}
                    onChange={handleChange}
                    placeholder="Confirm your password"
                    required
                />
            </div>
            <button type="submit" className="submit-button">Register</button>
        </form>
    );
};

export default RegistrationForm;
