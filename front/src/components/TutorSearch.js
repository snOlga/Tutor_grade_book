import React, { useState } from 'react';
import '../styles/search_style.css'
import { refreshAccessToken } from '../services/auth'

function TutorSearch({ setLessons }) {

    function getTutorParticipator(tutorId) {
        fetch(process.env.REACT_APP_ROOT_PATH + 'participators/tutor/' + tutorId, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                loadLessons(data)
            }).catch(() => refreshAccessToken())
    }

    function loadLessons(tutor) {
        fetch(process.env.REACT_APP_ROOT_PATH + 'lessons/user/' + tutor.email, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setLessons(data.filter(lesson => lesson.isOpen))
            }).catch(() => refreshAccessToken())
    }

    return (
        <div className='search-holder'>
            <div>Find tutor's lessons?</div>
            <input
                type="text"
                className='search-input'
                placeholder="Start typing tutor's id..."
                onChange={e => getTutorParticipator(e.target.value)} />
        </div>
    );
};

export default TutorSearch;
