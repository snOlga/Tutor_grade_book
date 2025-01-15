import React, { useState } from 'react';
import '../../styles/chat_style.css'
import { getRoles, ROLES, getCurrentUserEmail } from '../../App';

function LessonCard({ lesson, lessonDate, openDeletionModal, setLessonToDelete, setLessonInfoModalState }) {

    const topPositionLesson = (lessonDate.getHours() - 7) * 60 + lessonDate.getMinutes() + 10;
    const isTutor = getRoles().includes(ROLES.TUTOR)
    const isStudent = getRoles().includes(ROLES.STUDENT)
    const isAdmin = getRoles().includes(ROLES.ADMIN)
    const isUserOwner = (getCurrentUserEmail() == lesson.owner.email) || isAdmin
    const isParticipator = (lesson.users.map(user => user.email).includes(getCurrentUserEmail()))

    function participate() {
        fetch(process.env.REACT_APP_ROOT_PATH + 'lesson_requests/create', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                isDeleted: false,
                sender: {
                    email: getCurrentUserEmail()
                },
                reciever: {
                    email: lesson.owner.email
                },
                lesson: {
                    id: lesson.id
                }
            })
        })
            .then(response => response.json())
            .then(data => {
                console.log(data)
            })
    }

    return (
        <>
            <div className="lesson" style={{ top: topPositionLesson + 'px' }} >
                <div className="lesson-time">
                    <div>
                        {lessonDate.toLocaleTimeString().substring(0, 5) + " - " + (new Date(lessonDate.getTime() + lesson.durationInMinutes * 60 * 1000)).toLocaleTimeString().substring(0, 5)}
                    </div>
                    {
                        (isTutor && isUserOwner)
                        &&
                        <a onClick={e => {
                            openDeletionModal(true)
                            setLessonToDelete(lesson)
                            e.stopPropagation()
                        }} className='card-bin'>
                            <BinIcon />
                        </a>
                    }
                </div>
                <div className="lesson-details" onClick={() => setLessonInfoModalState({ isOpen: true, lesson: lesson })}>
                    <h4 className="lesson-heading">
                        {lesson.heading}
                    </h4>
                    <div className='lesson-card-content'>
                        <div>
                            Lesson by
                        </div>
                        <a href={'/account/' + lesson.owner.humanReadableID} className='link'>{(lesson.owner.name + " " + lesson.owner.secondName)}</a>
                        {
                            (isStudent && !isParticipator) &&
                            <button className="participate-button" onClick={e => {
                                participate()
                                e.stopPropagation()
                            }}>Participate!</button>
                        }
                    </div>
                </div>
            </div>
        </>
    );
};

export default LessonCard;

export function BinIcon() {
    return (
        <svg stroke="currentColor" fill="#EB7C6C" stroke-width="0" viewBox="0 0 24 24" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0V0z"></path><path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM8 9h8v10H8V9zm7.5-5-1-1h-5l-1 1H5v2h14V4z"></path></svg>
    )
}
