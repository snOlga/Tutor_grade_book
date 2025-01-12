import React, { useEffect, useState } from 'react';
import '../styles/header_style.css'
import { getCurrentUserEmail, getRoles, ROLES } from '../App';
import { Navigate, useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

function Header({ openLessonCreationModal, openLessonsRequests, openChat }) {
    const [currentUser, setUser] = useState({})
    const [incomeRequestsAmount, setIncomeAmount] = useState(0)
    const navigate = useNavigate()

    useEffect(() => {
        loadUser()
        fetchIncomeRequests()
    }, [])

    function loadUser() {
        fetch('http://localhost:18018/participator/with_email/' + getCurrentUserEmail(), {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setUser(data)
            })
    }

    function fetchIncomeRequests() {
        fetch("http://localhost:18018/lesson_requests/income/with_user/" + getCurrentUserEmail(), {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setIncomeAmount(data.length)
            })
    }

    return (
        <div className='header'>
            <a href='/' className='logo'>
                <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 24 24" height="1.2em" width="1.2em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0z"></path><path d="M21 5c-1.11-.35-2.33-.5-3.5-.5-1.95 0-4.05.4-5.5 1.5-1.45-1.1-3.55-1.5-5.5-1.5S2.45 4.9 1 6v14.65c0 .25.25.5.5.5.1 0 .15-.05.25-.05C3.1 20.45 5.05 20 6.5 20c1.95 0 4.05.4 5.5 1.5 1.35-.85 3.8-1.5 5.5-1.5 1.65 0 3.35.3 4.75 1.05.1.05.15.05.25.05.25 0 .5-.25.5-.5V6c-.6-.45-1.25-.75-2-1zm0 13.5c-1.1-.35-2.3-.5-3.5-.5-1.7 0-4.15.65-5.5 1.5V8c1.35-.85 3.8-1.5 5.5-1.5 1.2 0 2.4.15 3.5.5v11.5z"></path><path d="M17.5 10.5c.88 0 1.73.09 2.5.26V9.24c-.79-.15-1.64-.24-2.5-.24-1.7 0-3.24.29-4.5.83v1.66c1.13-.64 2.7-.99 4.5-.99zM13 12.49v1.66c1.13-.64 2.7-.99 4.5-.99.88 0 1.73.09 2.5.26V11.9c-.79-.15-1.64-.24-2.5-.24-1.7 0-3.24.3-4.5.83zM17.5 14.33c-1.7 0-3.24.29-4.5.83v1.66c1.13-.64 2.7-.99 4.5-.99.88 0 1.73.09 2.5.26v-1.52c-.79-.16-1.64-.24-2.5-.24z"></path></svg>
                <div>
                    Gradebook
                </div>
            </a>
            <div className='right-section'>
                {
                    (getRoles().includes(ROLES.TUTOR) && openLessonCreationModal != null) &&
                    <button onClick={() => openLessonCreationModal(true)}>Create Lesson</button>
                }
                {
                    openLessonsRequests != null &&
                    <button className='lessons-requests-button' onClick={() => openLessonsRequests(true)}>
                        <div>
                            Lessons Requests
                        </div>
                        {
                            (incomeRequestsAmount > 0) &&
                            <div className='requests-counter'>
                                {incomeRequestsAmount > 9 ? "!" : incomeRequestsAmount}
                            </div>
                        }
                    </button>
                }
                <button
                    className='button-no-style'
                    onClick={() => openChat(true)}>{
                        ChatIcon()
                    }</button>
                {
                    currentUser != {} &&
                    <button
                        className='button-no-style'
                        onClick={() => {
                            navigate('/account/' + currentUser.humanReadableID)
                            window.location.reload()
                        }}>{
                            AccountIcon()
                        }</button>
                }
                {
                    currentUser != {} &&
                    <button
                        className='button-no-style'
                        onClick={() => {
                            Cookies.remove('token')
                            window.location.reload()
                        }}>{
                            LogOutIcon()
                        }</button>
                }
            </div>
        </div>
    );
};

export default Header;

function ChatIcon() {
    return (
        <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 24 24" height="1.2em" width="1.2em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0V0z"></path><path d="M4 4h16v12H5.17L4 17.17V4m0-2c-1.1 0-1.99.9-1.99 2L2 22l4-4h14c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2H4zm2 10h8v2H6v-2zm0-3h12v2H6V9zm0-3h12v2H6V6z"></path></svg>
    )
}

function LogOutIcon() {
    return (
        <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 24 24" height="1.2em" width="1.2em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0z"></path><path d="m17 7-1.41 1.41L18.17 11H8v2h10.17l-2.58 2.58L17 17l5-5zM4 5h8V3H4c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h8v-2H4V5z"></path></svg>
    )
}

function AccountIcon() {
    return (
        <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 24 24" height="1.2em" width="1.2em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0z"></path><path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zM7.35 18.5C8.66 17.56 10.26 17 12 17s3.34.56 4.65 1.5c-1.31.94-2.91 1.5-4.65 1.5s-3.34-.56-4.65-1.5zm10.79-1.38a9.947 9.947 0 0 0-12.28 0A7.957 7.957 0 0 1 4 12c0-4.42 3.58-8 8-8s8 3.58 8 8c0 1.95-.7 3.73-1.86 5.12z"></path><path d="M12 6c-1.93 0-3.5 1.57-3.5 3.5S10.07 13 12 13s3.5-1.57 3.5-3.5S13.93 6 12 6zm0 5c-.83 0-1.5-.67-1.5-1.5S11.17 8 12 8s1.5.67 1.5 1.5S12.83 11 12 11z"></path></svg>
    )
}
