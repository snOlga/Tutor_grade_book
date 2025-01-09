import React, { useState } from 'react';

function TutorSearch({ setLessons }) {

    function getTutorParticipator(tutorId) {
        fetch('http://localhost:18018/participator/tutors/' + tutorId, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                loadLessons(data)
            })
    }

    function loadLessons(tutor) {
        fetch('http://localhost:18018/lessons/with_user/' + tutor.email, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setLessons(data)
            })
    }

    return (
        <div>
            <div>Find tutor's lessons?</div>
            <input
                type="text"
                placeholder="Start typing tutor's id..."
                onChange={e => getTutorParticipator(e.target.value)} />
        </div>
    );
};

export default TutorSearch;
