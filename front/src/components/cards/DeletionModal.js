import React, { useState } from 'react';
import '../../styles/calendar_style.css'

function DeletionModal({ valueToDelete, closeModal, lessonDate }) {

    const topPositionLesson = (lessonDate.getHours() - 7) * 60 + lessonDate.getMinutes() + 10;

    function handleDeletion(lesson) {
        fetch(process.env.REACT_APP_ROOT_PATH + 'lessons/delete/' + lesson.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                window.location.reload()
            })
    }

    return (
        <div className="lesson" style={{ top: topPositionLesson + 'px' }}>
            <h4>Want to delete?</h4>
            <p>It will disapear for everyone!</p>
            <div className='deletion-modal-buttons'>
                <button onClick={() => handleDeletion(valueToDelete)} className="delete-button">Delete</button>
                <button onClick={() => closeModal(false)}>Cancel</button>
            </div>
        </div>
    );
};

export default DeletionModal;
