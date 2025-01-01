import React, { useEffect, useState } from 'react';
import '../../styles/lesson_creation_modal_style.css'
import '../../styles/lesson_info_modal_style.css'

function InfoLessonModal({ currentLesson, closeModal }) {
    const lessonDate = new Date(currentLesson.startTime)
    console.log(currentLesson.users)
    currentLesson.users.map(user => {
        console.log(user.name)
    })

    return (
        <div className='all-window' onClick={() => closeModal(false)}>
            <div className='modal-holder' onClick={e => e.stopPropagation()}>
                <div className='heading'>
                    Lesson
                </div>
                <form>
                    <div className='content'>
                        <div className='left-part'>
                            <div className='field-holder'>
                                <label htmlFor="title">
                                    Lesson Title
                                </label>
                                <div name="title" className='plain-text'>
                                    {currentLesson.heading}
                                </div>
                            </div>
                            <div className='field-holder'>
                                <label htmlFor="human-readable-id">Lesson ID</label>
                                <div name="human-readable-id" className='plain-text'>
                                    {currentLesson.humanReadableId}
                                </div>
                            </div>
                            <div className='field-holder'>
                                <label htmlFor="description">Description</label>
                                <div name="description" className='plain-text'>
                                    {currentLesson.description}
                                </div>
                            </div>
                            <div className='field-holder'>
                                <label htmlFor="subject">Subject</label>
                                <div name="subject" className='plain-text'>
                                    {currentLesson.subject.name}
                                </div>
                            </div>
                            <div className='field-holder'>
                                <label htmlFor="date">Date</label>
                                <div name="date" className='plain-text'>
                                    {lessonDate.toDateString()}
                                </div>
                            </div>
                            <div className='field-holder'>
                                <label htmlFor="time">Time</label>
                                <div name="time" className='plain-text'>
                                    {lessonDate.toLocaleTimeString().substring(0, 5) + " - " + (new Date(lessonDate.getTime() + currentLesson.durationInMinutes * 60 * 1000)).toLocaleTimeString().substring(0, 5)}
                                </div>
                            </div>
                        </div>
                        <div>
                            <div className='field-holder'>
                                <label htmlFor="users">Participators</label>
                                <div name="users">
                                    {currentLesson.users.map(user => {
                                        return (
                                            <div>
                                                <a href={'/' + user.humanReadableID} className='link'>{(user.name + " " + user.secondName)}</a>
                                            </div>
                                        )
                                    })}
                                </div>
                            </div>
                            <div className='field-holder'>
                                <label htmlFor="owner">Lesson owner</label>
                                <div name="owner">
                                    <a href={'/' + currentLesson.owner.humanReadableID} className='link'>{(currentLesson.owner.name + " " + currentLesson.owner.secondName)}</a>
                                </div>
                            </div>
                        </div>
                        <div className='buttons'>
                            <button type='button' onClick={() => closeModal(false)}>Exit</button>
                        </div>
                    </div>
                </form>
            </div>
        </div >
    );
};

export default InfoLessonModal;
