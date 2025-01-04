import React, { useEffect, useState } from 'react';
import '../../styles/lesson_creation_modal_style.css'
import '../../styles/lesson_info_modal_style.css'
import { getRoles, ROLES, getCurrentUserEmail } from '../../App';

function InfoLessonModal({ currentLesson, closeModal }) {
    const lessonDate = new Date(currentLesson.startTime)
    const [lessonDTO, setLessonDTO] = useState(currentLesson)
    const [isEditState, setEdit] = useState(
        {
            title: false,
            description: false,
            startDate: false,
            startTime: false,
            endTime: false,
            isOpen: false,
            users: false,
            subject: false
        }
    )
    const [newLesson, setNewLesson] = useState(
        {
            title: lessonDTO.heading,
            humanReadableId: lessonDTO.humanReadableId,
            description: lessonDTO.description,
            startDate: lessonDate.toISOString().substring(0, 10) + "",
            startTime: lessonDate.toLocaleTimeString().substring(0, 5),
            endTime: (new Date(lessonDate.getTime() + lessonDTO.durationInMinutes * 60 * 1000)).toLocaleTimeString().substring(0, 5),
            isOpen: lessonDTO.isOpen,
            studentParticipators: lessonDTO.users.filter(user => user.roles.includes(ROLES.STUDENT)),
            tutorParticipators: lessonDTO.users.filter(user => user.roles.includes(ROLES.TUTOR)),
            subject: lessonDTO.subject.name
        }
    )
    const [allSubjects, setAllSubjects] = useState([])
    const [studentParticipator, setStudentParticipator] = useState([])
    const [tutorParticipator, setTutorParticipator] = useState([])
    const [isTimeOk, setTimeState] = useState(true)
    const isTutor = getRoles().includes(ROLES.TUTOR)
    const isUserOwner = (getCurrentUserEmail() == currentLesson.owner.email)

    useEffect(() => {
        fetchSubjects()
    }, [])

    useEffect(() => {
        checkTime()
    }, [newLesson.startTime, newLesson.endTime])

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

    function prepareStartTime() {
        let timeZoneOffset = Math.round(new Date().getTimezoneOffset() / 60)
        let absTimeZoneOffset = Math.abs(timeZoneOffset)
        let timeZoneStr = String(absTimeZoneOffset).padStart(2, '0')
        let localeISOtimestamp = (newLesson.startDate + "T" + newLesson.startTime + ":00.000" + (timeZoneOffset > 0 ? "-" : "+") + timeZoneStr + ":00")
        let zeroISOtimestamp = new Date(localeISOtimestamp).toISOString()
        return zeroISOtimestamp
    }

    function prepareDuration() {
        let startTimestamp = (newLesson.startDate + "T" + newLesson.startTime)
        let endTimeStamp = (newLesson.startDate + "T" + newLesson.endTime)
        let duration = (new Date(endTimeStamp)) - (new Date(startTimestamp))
        return (Math.round(duration / 60000))
    }

    function submitForm(structure) {
        fetch('http://localhost:18018/lessons/create', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(structure)
        })
            .then(response => response.json())
            .then(data => {
                setLessonDTO(data)
            })
    }

    function checkTime() {
        setTimeState(prepareDuration() > 0)
    }

    return (
        <div className='all-window' onClick={() => window.location.reload()}>
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
                                    (!isEditState.title && isTutor && isUserOwner) &&
                                    <button className='button-no-style' onClick={() => setEdit({ ...isEditState, title: true })}>
                                        <EditIcon />
                                    </button>
                                }
                            </label>
                            {
                                !isEditState.title && <div name="title" className='plain-text'>{lessonDTO.heading}</div>
                            }
                            {
                                (isEditState.title && isTutor && isUserOwner) &&
                                <div className='edit-holder'>
                                    <input
                                        name="title"
                                        type="text"
                                        onChange={(e) => setNewLesson({ ...newLesson, title: e.target.value })}
                                        placeholder={lessonDTO.heading}
                                        required />
                                    <div className='edit-holder-buttons'>
                                        <button onClick={() => {
                                            submitForm({ ...lessonDTO, heading: newLesson.title })
                                            setEdit({ ...isEditState, title: false })
                                        }}>
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
                            </label>
                            <div name="humanReadableId" className='plain-text'>{lessonDTO.humanReadableId}</div>
                        </div>

                        <div className='field-holder'>
                            <label className='info-edit-label' htmlFor="description">
                                <div>
                                    Description
                                </div>
                                {
                                    (!isEditState.description && isTutor && isUserOwner) &&
                                    <button className='button-no-style' onClick={() => setEdit({ ...isEditState, description: true })}>
                                        <EditIcon />
                                    </button>
                                }
                            </label>
                            {
                                !isEditState.description && <div name="description" className='plain-text'>{lessonDTO.description}</div>
                            }
                            {
                                (isEditState.description && isTutor && isUserOwner) &&
                                <div className='edit-holder'>
                                    <textarea
                                        name="description"
                                        cols="30"
                                        rows="10"
                                        onChange={(e) => setNewLesson({ ...newLesson, description: e.target.value })}
                                        placeholder={lessonDTO.description} />
                                    <div className='edit-holder-buttons'>
                                        <button onClick={() => {
                                            submitForm({ ...lessonDTO, description: newLesson.description })
                                            setEdit({ ...isEditState, description: false })
                                        }}>
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
                                    (!isEditState.subject && isTutor && isUserOwner) &&
                                    <button className='button-no-style' onClick={() => setEdit({ ...isEditState, subject: true })}>
                                        <EditIcon />
                                    </button>
                                }
                            </label>
                            {
                                !isEditState.subject && <div name="subject" className='plain-text'>{lessonDTO.subject.name}</div>
                            }
                            {
                                (isEditState.subject && isTutor && isUserOwner) &&
                                <div className='edit-holder'>
                                    {
                                        allSubjects.map(subject => {
                                            return (
                                                <div className='radio-holder'>
                                                    <input
                                                        type="radio"
                                                        name="subject"
                                                        id={subject.name}
                                                        checked={newLesson.subject == subject.name}
                                                        onChange={() => setNewLesson({ ...newLesson, subject: subject.name })} />
                                                    <label htmlFor={subject.name}>{subject.name}</label>
                                                </div>
                                            )
                                        })
                                    }
                                    <div className='edit-holder-buttons'>
                                        <button onClick={() => {
                                            submitForm({ ...lessonDTO, subject: { name: newLesson.subject } })
                                            setEdit({ ...isEditState, subject: false })
                                        }}>
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
                                    (!isEditState.date && isTutor && isUserOwner) &&
                                    <button className='button-no-style' onClick={() => setEdit({ ...isEditState, date: true })}>
                                        <EditIcon />
                                    </button>
                                }
                            </label>
                            {
                                !isEditState.date && <div name="date" className='plain-text'>{newLesson.startDate}</div>
                            }
                            {
                                (isEditState.date && isTutor && isUserOwner) &&
                                <div className='edit-holder'>
                                    <input
                                        name="date"
                                        type="date"
                                        value={newLesson.startDate}
                                        onChange={(e) => setNewLesson({ ...newLesson, startDate: e.target.value })}
                                        required />
                                    <div className='edit-holder-buttons'>
                                        <button onClick={() => {
                                            submitForm({ ...lessonDTO, startTime: prepareStartTime() })
                                            setEdit({ ...isEditState, date: false })
                                        }}>
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
                                    (!isEditState.time && isTutor && isUserOwner) &&
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
                                (isEditState.time && isTutor && isUserOwner) &&
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
                                        <button onClick={() => {
                                            submitForm({ ...lessonDTO, startTime: prepareStartTime(), durationInMinutes: prepareDuration() })
                                            setEdit({ ...isEditState, time: false })
                                        }}
                                            disabled={!isTimeOk}>
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
                                    (!isEditState.isOpen && isTutor && isUserOwner) &&
                                    <button className='button-no-style' onClick={() => setEdit({ ...isEditState, isOpen: true })}>
                                        <EditIcon />
                                    </button>
                                }
                            </label>
                            {
                                !isEditState.isOpen && <div name="isOpen" className='plain-text'>
                                    {lessonDTO.isOpen && <YesIcon />}
                                    {!lessonDTO.isOpen && <NoIcon />}
                                </div>
                            }
                            {
                                (isEditState.isOpen && isTutor && isUserOwner) &&
                                <div className='edit-holder'>
                                    <label className="toggle-switch">
                                        <input
                                            name="toggle"
                                            type="checkbox"
                                            onChange={() => setNewLesson({ ...newLesson, isOpen: (!newLesson.isOpen) })}
                                            checked={newLesson.isOpen} />
                                        <span className="slider"></span>
                                    </label>
                                    <label htmlFor="toggle">Make lesson open for everyone!</label>
                                    <div className='edit-holder-buttons'>
                                        <button onClick={() => {
                                            submitForm({ ...lessonDTO, isOpen: newLesson.isOpen })
                                            setEdit({ ...isEditState, isOpen: false })
                                        }}>
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
                                    (!isEditState.users && isTutor && isUserOwner) &&
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
                                (isEditState.users && isTutor && isUserOwner) &&
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
                                (isEditState.users && isTutor && isUserOwner) &&
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
                            {
                                (isEditState.users && isTutor && isUserOwner) &&
                                <div className='edit-holder-buttons'>
                                    <button onClick={() => {
                                        submitForm({ ...lessonDTO, users: newLesson.studentParticipators.concat(newLesson.tutorParticipators) })
                                        setEdit({ ...isEditState, users: false })
                                    }}>
                                        Submit
                                    </button>
                                    <button onClick={() => setEdit({ ...isEditState, users: false })}>
                                        Cancel
                                    </button>
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
                                <a href={'/' + lessonDTO.owner.humanReadableID} className='link'>{(lessonDTO.owner.name + " " + lessonDTO.owner.secondName)}</a>
                            </div>
                        </div>
                    </div>
                    <div className='buttons'>
                        <button type='button' onClick={() => window.location.reload()}>Exit</button>
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

export function YesIcon() {
    return (
        <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 24 24" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0z"></path><path d="M9 16.2 4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z"></path></svg>
    )
}

export function NoIcon() {
    return (
        <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 24 24" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0V0z"></path><path d="M19 6.41 17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12 19 6.41z"></path></svg>
    )
}