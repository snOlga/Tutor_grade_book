import React, { useEffect, useState } from 'react';
import '../styles/notifications_style.css'
import { getSubject } from '../App';

function NotificationsHolder() {

    useEffect(() => {
        fetchOutcomeRequests()
        fetchIncomeRequests()
    }, [])

    function fetchOutcomeRequests() {
        fetch("http://localhost:18018/lesson_requests/outcome/with_user/" + getSubject(), {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                console.log("outcome")
                console.log(data)
            })
    }

    function fetchIncomeRequests() {
        fetch("http://localhost:18018/lesson_requests/income/with_user/" + getSubject(), {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                console.log("income")
                console.log(data)
            })
    }

    return (
        <div className='notifications-holder'>
            aaa
        </div>
    );
};

export default NotificationsHolder;
