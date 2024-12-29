import React, { useEffect, useState } from 'react';
import '../styles/lesson_creation_modal_style.css'

function CreateLessonModal({ closeModal }) {
    const [newLesson, setNewLesson] = useState(
        {
            title: "",
            description: "",
            startDate: new Date().toISOString().substring(0, 10) + "",
            startTime: "",
            endTime: "",
            isOpen: false,
            studentParticipators: [],
            tutorParticipators: []
        }
    )
    const [studentParticipator, setStudentParticipator] = useState([])

    const [allSubjects, setAllSubjects] = useState([])

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

    console.log(newLesson.studentParticipators.concat(newLesson.tutorParticipators))

    function submitForm() {
        let startTimestamp = (newLesson.startDate + "T" + newLesson.startTime)
        let endTimeStamp = (newLesson.startDate + "T" + newLesson.endTime)
        let duration = (new Date(endTimeStamp)) - (new Date(startTimestamp))
        fetch('http://localhost:18018/lessons/create', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                startTime: startTimestamp,
                duration: Math.round(duration / 60000),
                subject: {
                    id: 0,
                    name: "string",
                    analogyNames: "string"
                },
                isOpen: newLesson.isOpen,
                isDeleted: false,
                description: newLesson.description,
                heading: newLesson.title,
                owner: {
                    email: localStorage.getItem("subject")
                },
                users: [
                    {
                        name: "string",
                        secondName: "string",
                        humanReadableID: "string"
                    }
                ]
            })
        })
            .then(response => response.json())
            .then(data => {
                //setLessons(data)
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
                    Creation Lesson Form
                </div>
                <form>
                    <div className='content'>
                        <div className='left-part'>
                            <div className='field-holder'>
                                <label htmlFor="title">Lesson Title</label>
                                <input
                                    name="title"
                                    type="text"
                                    onChange={(e) => setNewLesson({ ...newLesson, title: e.target.value })}
                                    required />
                            </div>
                            <div className='field-holder'>
                                <label htmlFor="description">Description</label>
                                <textarea
                                    name="description"
                                    cols="30"
                                    rows="10"
                                    onChange={(e) => setNewLesson({ ...newLesson, description: e.target.value })} />
                            </div>
                            <div className='field-holder'>
                                <label>Subject</label>
                                {
                                    allSubjects.map(subject => {
                                        return (
                                            <div className='radio-holder'>
                                                <input type="radio" name="subject" id={"subject_" + subject.name} />
                                                <label htmlFor={"subject_" + subject.name}>{subject.name}</label>
                                            </div>
                                        )
                                    })
                                }
                            </div>
                            <div className='field-holder'>
                                <label htmlFor="date">Date</label>
                                <input
                                    name="date"
                                    type="date"
                                    value={newLesson.startDate}
                                    onChange={(e) => setNewLesson({ ...newLesson, startDate: e.target.value })}
                                    required />
                            </div>
                            <div className='field-holder'>
                                <p>
                                    Time
                                </p>
                                <label htmlFor="from">From</label>
                                <input
                                    name="from"
                                    type="time"
                                    onChange={(e) => setNewLesson({ ...newLesson, startTime: e.target.value })}
                                    required />
                                <label htmlFor="till">Till</label>
                                <input
                                    name="till"
                                    type="time"
                                    onChange={(e) => setNewLesson({ ...newLesson, endTime: e.target.value })}
                                    required />
                            </div>
                        </div>
                        <div>
                            <div className='field-holder'>
                                <label htmlFor="invite_tutors">Invite another tutors</label>
                                <input
                                    name="invite_tutors"
                                    type="text" />
                                <button
                                    type='button'
                                    className='third-class-button'>Invite</button>
                            </div>
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
                                        <div>
                                            <a href={'/' + student.humanReadableID} className='link'>{(student.name + " " + student.secondName)}</a>
                                        </div>
                                    )
                                }
                            </div>
                            <div className="field-holder-flex">
                                <label className="toggle-switch">
                                    <input name="toggle" type="checkbox" />
                                    <span className="slider"></span>
                                </label>
                                <label htmlFor="toggle">Make lesson closed</label>
                            </div>
                        </div>
                        <div className='buttons'>
                            <button type='submit' onClick={submitForm}>Create</button>
                            <button type='button' onClick={() => closeModal(false)}>Exit</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default CreateLessonModal;
