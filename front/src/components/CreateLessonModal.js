import React, { useState } from 'react';
import '../styles/lesson_creation_modal_style.css'

function CreateLessonModal() {
    const [newLesson, setNewLesson] = useState(
        {
            title: "",
            description: "",
            startDate: (new Date()).toISOString().substring(0, 10),
            startTime: "",
            endTime: "",
            isOpen: "",
            studentParticipators: [""],
            tutorParticipators: ["thisUser"]
        }
    )

    function submitForm() {
        fetch('http://localhost:18018/send_lesson', { // TODO: api naming?? 
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: {
                title: newLesson.title,
                description: newLesson.description,
                startTime: (new Date(newLesson.startDate + " " + newLesson.startTime)),
                duration: (new Date(newLesson.startDate + " " + (newLesson.endTime - newLesson.startDate)).getMinutes()),
                isOpen: newLesson.isOpen,
                studentParticipators: newLesson.studentParticipators,
                tutorParticipators: newLesson.tutorParticipators
            }
        })
            .then(response => response.json())
            .then(data => {
                //setLessons(data)
            })
    }
    //"startTime": "December 20, 2024 11:00:00",

    console.log({
        title: newLesson.title,
        description: newLesson.description,
        startTime: (new Date(newLesson.startDate + " " + newLesson.startTime)),
        duration: (new Date(newLesson.startDate + " " + (newLesson.endTime - newLesson.startDate)).getMinutes()),
        isOpen: newLesson.isOpen,
        studentParticipators: newLesson.studentParticipators,
        tutorParticipators: newLesson.tutorParticipators
    })

    return (
        <div className='modal-holder'>
            <div className='heading'>
                Creation Lesson Form
            </div>
            <form>
                <div className='content'>
                    <div className='left-part'>
                        <div className='field-holder'>
                            <label htmlFor="title">Lesson Title</label>
                            <input name="title" type="text" onChange={(e) => setNewLesson({ ...newLesson, title: e.target.value })} required />
                        </div>
                        <div className='field-holder'>
                            <label htmlFor="description">Description</label>
                            <textarea name="description" cols="30" rows="10" onChange={(e) => setNewLesson({ ...newLesson, description: e.target.value })} />
                        </div>
                        <div className='field-holder'>
                            <label htmlFor="date">Date</label>
                            <input name="date" type="date" value={newLesson.startDate} onChange={(e) => setNewLesson({ ...newLesson, startDate: e.target.value })} required />
                        </div>
                        <div className='field-holder'>
                            <p>
                                Time
                            </p>
                            From <input type="time" onChange={(e) => setNewLesson({ ...newLesson, startTime: e.target.value })} required /> till <input type="time" onChange={(e) => setNewLesson({ ...newLesson, endTime: e.target.value })} required />
                        </div>
                    </div>
                    <div>
                        <div className='field-holder'>
                            <label htmlFor="invite_tutors">Invite another tutors</label>
                            <input name="invite_tutors" type="text" />
                        </div>
                        <div className='field-holder'>
                            <label htmlFor="invite_students">Invite students</label>
                            <input name="invite_students" type="text" />
                        </div>
                        <div className='field-holder'>
                            <label htmlFor="toggle">Make lesson closed</label>
                            <input name="toggle" type="radio" className="toggle" />
                        </div>
                    </div>
                    <div className='buttons'>
                        <button type='submit'>Create</button>
                        <button>Exit</button>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default CreateLessonModal;
