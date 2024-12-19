import React, { useState } from 'react';
import '../styles/lesson_creation_modal_style.css'

function CreateLessonModal() {

    return (
        <div className='modal-holder'>
            <div className='heading'>
                Creation Lesson Form
                {/* <h1 className='heading'>Creation Lesson Form</h1> */}
            </div>
            <div className='content'>
                <div className='left-part'>
                    <div className='field-holder'>
                        <label for="title">Lesson Title</label>
                        <input name="title" type="text" />
                    </div>
                    <div className='field-holder'>
                        <label for="description">Description</label>
                        <textarea name="description" cols="30" rows="10" />
                    </div>
                    <div className='field-holder'>
                        <label for="date">Date</label>
                        <input name="date" type="date" />
                    </div>
                    <div className='field-holder'>
                        <p>
                            Time
                        </p>
                        From <input type="time" /> till <input type="time" />
                    </div>
                </div>
                <div>
                    <div className='field-holder'>
                        <label for="invite_tutors">Invite another tutors</label>
                        <input name="invite_tutors" type="text" />
                    </div>
                    <div className='field-holder'>
                        <label for="invite_students">Invite students</label>
                        <input name="invite_students" type="text" />
                    </div>
                    <div className='field-holder'>
                        <label for="toggle">Make lesson closed</label>
                        <input name="toggle" type="radio" className="toggle" />
                    </div>
                </div>
                <div className='buttons'>
                    <button>Create</button>
                    <button>Exit</button>
                </div>
            </div>
        </div>
    );
};

export default CreateLessonModal;
