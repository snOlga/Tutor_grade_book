import React, { useEffect, useState } from 'react';
import '../../styles/lesson_creation_modal_style.css'
import '../../styles/lesson_info_modal_style.css'

function InfoLessonModal({ currentLesson, closeModal }) {
    const lessonDate = new Date(currentLesson.startTime)
    const [isEditState, setEdit] = useState(
        {
            title: false,
            humanReadableId: false,
            description: false,
            startDate: false,
            startTime: false,
            endTime: false,
            isOpen: false,
            users: false
        }
    )
    const [newLesson, setNewLesson] = useState(
        {
            title: currentLesson.heading,
            humanReadableId: currentLesson.humanReadableId,
            description: currentLesson.description,
            startDate: lessonDate.toISOString().substring(0, 10) + "",
            startTime: lessonDate.toLocaleTimeString().substring(0, 5),
            endTime: (new Date(lessonDate.getTime() + currentLesson.durationInMinutes * 60 * 1000)).toLocaleTimeString().substring(0, 5),
            isOpen: currentLesson.isOpen,
            studentParticipators: currentLesson.users.filter(user => user.roles.includes('STUDENT')),
            tutorParticipators: currentLesson.users.filter(user => user.roles.includes('TUTOR'))
        }
    )
    const [allSubjects, setAllSubjects] = useState([])
    const [studentParticipator, setStudentParticipator] = useState([])
    const [tutorParticipator, setTutorParticipator] = useState([])

    useEffect(() => {
        fetchSubjects()
    }, [])

    function fetchSubjects() {
        fetch('http://localhost:18018/subjects', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setAllSubjects(data)
            })
    }

    function getTutorParticipator() {
        fetch('http://localhost:18018/participator/tutors/' + tutorParticipator, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setNewLesson({ ...newLesson, tutorParticipators: [...newLesson.tutorParticipators, data] })
            })
    }

    function getStudentParticipator() {
        fetch('http://localhost:18018/participator/students/' + studentParticipator, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setNewLesson({ ...newLesson, studentParticipators: [...newLesson.studentParticipators, data] })
            })
    }

    return (
        <div className='all-window' onClick={() => closeModal(false)}>
            <div className='modal-holder' onClick={e => e.stopPropagation()}>
                <div className='heading'>
                    <div>
                        Lesson
                    </div>
                </div>
                <div className='content'>
                    <div className='left-part'>
                        <div className='field-holder'>
                            <label className='info-edit-label' htmlFor="title">
                                <div>
                                    Lesson Title
                                </div>
                                {
                                    !isEditState.title &&
                                    <button className='button-no-style' onClick={() => setEdit({ ...isEditState, title: true })}>
                                        <EditIcon />
                                    </button>
                                }
                            </label>
                            {
                                !isEditState.title && <div name="title" className='plain-text'>{currentLesson.heading}</div>
                            }
                            {
                                isEditState.title &&
                                <div className='edit-holder'>
                                    <input
                                        name="title"
                                        type="text"
                                        onChange={(e) => setNewLesson({ ...newLesson, title: e.target.value })}
                                        placeholder={currentLesson.heading}
                                        required />
                                    <div className='edit-holder-buttons'>
                                        <button>
                                            Submit
                                        </button>
                                        <button onClick={() => setEdit({ ...isEditState, title: false })}>
                                            Cancel
                                        </button>
                                    </div>
                                </div>
                            }
                        </div>

                        <div className='field-holder'>
                            <label className='info-edit-label' htmlFor="humanReadableId">
                                <div>
                                    Lesson ID
                                </div>
                                {
                                    !isEditState.humanReadableId &&
                                    <button className='button-no-style' onClick={() => setEdit({ ...isEditState, humanReadableId: true })}>
                                        <EditIcon />
                                    </button>
                                }
                            </label>
                            {
                                !isEditState.humanReadableId && <div name="humanReadableId" className='plain-text'>{currentLesson.humanReadableId}</div>
                            }
                            {
                                isEditState.humanReadableId &&
                                <div className='edit-holder'>
                                    <input
                                        name="humanReadableId"
                                        type="text"
                                        onChange={(e) => setNewLesson({ ...newLesson, humanReadableId: e.target.value })}
                                        placeholder={currentLesson.humanReadableId}
                                        required />
                                    <div className='edit-holder-buttons'>
                                        <button>
                                            Submit
                                        </button>
                                        <button onClick={() => setEdit({ ...isEditState, humanReadableId: false })}>
                                            Cancel
                                        </button>
                                    </div>
                                </div>
                            }
                        </div>

                        <div className='field-holder'>
                            <label className='info-edit-label' htmlFor="description">
                                <div>
                                    Description
                                </div>
                                {
                                    !isEditState.description &&
                                    <button className='button-no-style' onClick={() => setEdit({ ...isEditState, description: true })}>
                                        <EditIcon />
                                    </button>
                                }
                            </label>
                            {
                                !isEditState.description && <div name="description" className='plain-text'>{currentLesson.description}</div>
                            }
                            {
                                isEditState.description &&
                                <div className='edit-holder'>
                                    <textarea
                                        name="description"
                                        cols="30"
                                        rows="10"
                                        onChange={(e) => setNewLesson({ ...newLesson, description: e.target.value })}
                                        placeholder={currentLesson.description} />
                                    <div className='edit-holder-buttons'>
                                        <button>
                                            Submit
                                        </button>
                                        <button onClick={() => setEdit({ ...isEditState, description: false })}>
                                            Cancel
                                        </button>
                                    </div>
                                </div>
                            }
                        </div>

                        <div className='field-holder'>
                            <label className='info-edit-label' htmlFor="subject">
                                <div>
                                    Subject
                                </div>
                                {
                                    !isEditState.subject &&
                                    <button className='button-no-style' onClick={() => setEdit({ ...isEditState, subject: true })}>
                                        <EditIcon />
                                    </button>
                                }
                            </label>
                            {
                                !isEditState.subject && <div name="subject" className='plain-text'>{currentLesson.subject.name}</div>
                            }
                            {
                                isEditState.subject &&
                                <div className='edit-holder'>
                                    {
                                        allSubjects.map(subject => {
                                            return (
                                                <div className='radio-holder'>
                                                    <input type="radio" name="subject" id={subject.name} checked={currentLesson.subject.name == subject.name} />
                                                    <label htmlFor={subject.name}>{subject.name}</label>
                                                </div>
                                            )
                                        })
                                    }
                                    <div className='edit-holder-buttons'>
                                        <button>
                                            Submit
                                        </button>
                                        <button onClick={() => setEdit({ ...isEditState, subject: false })}>
                                            Cancel
                                        </button>
                                    </div>
                                </div>
                            }
                        </div>

                        <div className='field-holder'>
                            <label className='info-edit-label' htmlFor="date">
                                <div>
                                    Date
                                </div>
                                {
                                    !isEditState.date &&
                                    <button className='button-no-style' onClick={() => setEdit({ ...isEditState, date: true })}>
                                        <EditIcon />
                                    </button>
                                }
                            </label>
                            {
                                !isEditState.date && <div name="date" className='plain-text'>{newLesson.startDate}</div>
                            }
                            {
                                isEditState.date &&
                                <div className='edit-holder'>
                                    <input
                                        name="date"
                                        type="date"
                                        value={newLesson.startDate}
                                        onChange={(e) => setNewLesson({ ...newLesson, startDate: e.target.value })}
                                        required />
                                    <div className='edit-holder-buttons'>
                                        <button>
                                            Submit
                                        </button>
                                        <button onClick={() => setEdit({ ...isEditState, date: false })}>
                                            Cancel
                                        </button>
                                    </div>
                                </div>
                            }
                        </div>

                        <div className='field-holder'>
                            <label className='info-edit-label' htmlFor="time">
                                <div>
                                    Time
                                </div>
                                {
                                    !isEditState.time &&
                                    <button className='button-no-style' onClick={() => setEdit({ ...isEditState, time: true })}>
                                        <EditIcon />
                                    </button>
                                }
                            </label>
                            {
                                !isEditState.time && <div name="time" className='plain-text'>
                                    {newLesson.startTime + " - " + newLesson.endTime}
                                </div>
                            }
                            {
                                isEditState.time &&
                                <div className='edit-holder'>
                                    <label htmlFor="from">From</label>
                                    <input
                                        name="from"
                                        type="time"
                                        onChange={(e) => setNewLesson({ ...newLesson, startTime: e.target.value })}
                                        value={newLesson.startTime}
                                        required />
                                    <label htmlFor="till">Till</label>
                                    <input
                                        name="till"
                                        type="time"
                                        onChange={(e) => setNewLesson({ ...newLesson, endTime: e.target.value })}
                                        value={newLesson.endTime}
                                        required />
                                    <div className='edit-holder-buttons'>
                                        <button>
                                            Submit
                                        </button>
                                        <button onClick={() => setEdit({ ...isEditState, time: false })}>
                                            Cancel
                                        </button>
                                    </div>
                                </div>
                            }
                        </div>

                    </div>
                    <div>

                        <div className='field-holder'>
                            <label className='info-edit-label' htmlFor="isOpen">
                                <div>
                                    Is lesson opened for everyone?
                                </div>
                                {
                                    !isEditState.isOpen &&
                                    <button className='button-no-style' onClick={() => setEdit({ ...isEditState, isOpen: true })}>
                                        <EditIcon />
                                    </button>
                                }
                            </label>
                            {
                                !isEditState.isOpen && <div name="isOpen" className='plain-text'>
                                    {currentLesson.isOpen && <YesIcon />}
                                    {!currentLesson.isOpen && <NoIcon />}
                                </div>
                            }
                            {
                                isEditState.isOpen &&
                                <div className='edit-holder'>
                                    <label className="toggle-switch">
                                        <input
                                            name="toggle"
                                            type="checkbox"
                                            onChange={() => setNewLesson({ ...newLesson, isOpen: (!newLesson.isOpen) })} 
                                            checked={newLesson.isOpen}/>
                                        <span className="slider"></span>
                                    </label>
                                    <label htmlFor="toggle">Make lesson open for everyone!</label>
                                    <div className='edit-holder-buttons'>
                                        <button>
                                            Submit
                                        </button>
                                        <button onClick={() => setEdit({ ...isEditState, isOpen: false })}>
                                            Cancel
                                        </button>
                                    </div>
                                </div>
                            }
                        </div>

                        <div className='field-holder'>
                            <label className='info-edit-label' htmlFor="invite_tutors">
                                <div>
                                    Participators
                                </div>
                                {
                                    !isEditState.users &&
                                    <button className='button-no-style' onClick={() => setEdit({ ...isEditState, users: true })}>
                                        <EditIcon />
                                    </button>
                                }
                            </label>
                            {
                                !isEditState.users &&
                                <div className='field-holder'>
                                    {
                                        newLesson.tutorParticipators.map(user => {
                                            return (
                                                <div>
                                                    <a href={'/' + user.humanReadableID} className='link'>{(user.name + " " + user.secondName)}</a>
                                                </div>
                                            )
                                        })
                                    }
                                    {
                                        newLesson.studentParticipators.map(user => {
                                            return (
                                                <div>
                                                    <a href={'/' + user.humanReadableID} className='link'>{(user.name + " " + user.secondName)}</a>
                                                </div>
                                            )
                                        })
                                    }
                                </div>
                            }
                            {
                                isEditState.users &&
                                <div className='field-holder'>
                                    <label htmlFor="invite_tutors">Invite another tutors</label>
                                    <input
                                        name="invite_tutors"
                                        type="text"
                                        onChange={(e) => setTutorParticipator(e.target.value)} />
                                    <button
                                        type='button'
                                        className='third-class-button'
                                        onClick={getTutorParticipator}>Invite</button>
                                    {
                                        newLesson.tutorParticipators.map(tutor =>
                                            <div className='participator-holder'>
                                                <div>
                                                    <a href={'/' + tutor.humanReadableID} className='link'>{(tutor.name + " " + tutor.secondName)}</a>
                                                </div>
                                                <a onClick={() => {
                                                    setNewLesson({ ...newLesson, tutorParticipators: newLesson.tutorParticipators.filter(participator => participator != tutor) })
                                                }}>
                                                    <svg stroke="currentColor" fill="#EB7C6C" stroke-width="0" viewBox="0 0 24 24" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0V0z"></path><path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM8 9h8v10H8V9zm7.5-5-1-1h-5l-1 1H5v2h14V4z"></path></svg>
                                                </a>
                                            </div>
                                        )
                                    }
                                </div>
                            }
                            {
                                isEditState.users &&
                                <div className='field-holder'>
                                    <label htmlFor="invite_students">Invite students</label>
                                    <input
                                        name="invite_students"
                                        type="text"
                                        onChange={(e) => setStudentParticipator(e.target.value)} />
                                    <button
                                        type='button'
                                        className='third-class-button'
                                        onClick={getStudentParticipator}>Invite</button>
                                    {
                                        newLesson.studentParticipators.map(student =>
                                            <div className='participator-holder'>
                                                <div>
                                                    <a href={'/' + student.humanReadableID} className='link'>{(student.name + " " + student.secondName)}</a>
                                                </div>
                                                <a onClick={() => {
                                                    setNewLesson({ ...newLesson, studentParticipators: newLesson.studentParticipators.filter(participator => participator != student) })
                                                }}>
                                                    <svg stroke="currentColor" fill="#EB7C6C" stroke-width="0" viewBox="0 0 24 24" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0V0z"></path><path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM8 9h8v10H8V9zm7.5-5-1-1h-5l-1 1H5v2h14V4z"></path></svg>
                                                </a>
                                            </div>
                                        )
                                    }
                                </div>
                            }
                        </div>

                        <div className='field-holder'>
                            <label className='info-edit-label' htmlFor="owner">
                                <div>
                                    Lesson owner
                                </div>
                            </label>
                            <div name="owner">
                                <a href={'/' + currentLesson.owner.humanReadableID} className='link'>{(currentLesson.owner.name + " " + currentLesson.owner.secondName)}</a>
                            </div>
                        </div>
                    </div>
                    <div className='buttons'>
                        <button type='button' onClick={() => closeModal(false)}>Exit</button>
                    </div>
                </div>
            </div>
        </div >
    );
};

export default InfoLessonModal;


function EditIcon() {
    return (
        <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 24 24" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0z"></path><path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04a.996.996 0 0 0 0-1.41l-2.34-2.34a.996.996 0 0 0-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"></path></svg>
    )
}

function YesIcon() {
    return (
        <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 24 24" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0z"></path><path d="M9 16.2 4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z"></path></svg>
    )
}

function NoIcon() {
    return (
        <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 24 24" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0V0z"></path><path d="M19 6.41 17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12 19 6.41z"></path></svg>
    )
}