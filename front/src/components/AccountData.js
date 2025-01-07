import React, { useState, useEffect } from 'react';
import '../styles/account_page.css'
import { getCurrentUserEmail } from '../App';
import { EditIcon } from './modals/InfoLessonModal';

function AccountData({ currentUser }) {
    const isCurrentUser = (getCurrentUserEmail() == currentUser.email)
    const [isEditState, setEdit] = useState(
        {
            name: false,
            secondName: false,
            email: false,
            phone: false,
            desciption: false
        }
    )

    return (
        <>
            <div className="calendar">
                <div className='account-content'>
                    <div className='account-heading'>
                        Account
                    </div>
                </div>
                <div className='data-holder'>
                    <div className='one-line-data-holder'>
                        <h4 className="lesson-heading info-edit-label">
                            <div>
                                ID
                            </div>
                        </h4>
                        <div>
                            {currentUser.humanReadableID}
                        </div>
                    </div>
                    <div className='one-line-data-holder'>
                        <h4 className="lesson-heading info-edit-label">
                            <div>
                                Name
                            </div>
                            {
                                (isCurrentUser && !isEditState.name) &&
                                <button className='button-no-style' onClick={() => setEdit({ ...isEditState, name: true })}>
                                    <EditIcon />
                                </button>
                            }
                        </h4>
                        {
                            (!isEditState.name) &&
                            <div>
                                {currentUser.name}
                            </div>
                        }
                        {
                            (isCurrentUser && isEditState.name) &&
                            <div className='edit-holder'>
                                <input type="text" placeholder={currentUser.name} />
                                <div className='edit-holder-buttons'>
                                    <button onClick={() => {
                                        // submitForm({ ...lessonDTO, heading: newLesson.title })
                                        setEdit({ ...isEditState, name: false })
                                    }}>
                                        Submit
                                    </button>
                                    <button onClick={() => setEdit({ ...isEditState, name: false })}>
                                        Cancel
                                    </button>
                                </div>
                            </div>
                        }
                    </div>
                    <div className='one-line-data-holder'>
                        <h4 className="lesson-heading info-edit-label">
                            <div>
                                Second Name
                            </div>
                            {
                                (isCurrentUser && !isEditState.secondName) &&
                                <button className='button-no-style' onClick={() => setEdit({ ...isEditState, secondName: true })}>
                                    <EditIcon />
                                </button>
                            }
                        </h4>
                        {
                            (!isEditState.secondName) &&
                            <div>
                                {currentUser.secondName}
                            </div>
                        }
                        {
                            (isCurrentUser && isEditState.secondName) &&
                            <div className='edit-holder'>
                                <input type="text" placeholder={currentUser.secondName} />
                                <div className='edit-holder-buttons'>
                                    <button onClick={() => {
                                        // submitForm({ ...lessonDTO, heading: newLesson.title })
                                        setEdit({ ...isEditState, secondName: false })
                                    }}>
                                        Submit
                                    </button>
                                    <button onClick={() => setEdit({ ...isEditState, secondName: false })}>
                                        Cancel
                                    </button>
                                </div>
                            </div>
                        }
                    </div>
                    <div className='one-line-data-holder'>
                        <h4 className="lesson-heading info-edit-label">
                            <div>
                                Email
                            </div>
                            {
                                (isCurrentUser && !isEditState.email) &&
                                <button className='button-no-style' onClick={() => setEdit({ ...isEditState, email: true })}>
                                    <EditIcon />
                                </button>
                            }
                        </h4>
                        {
                            (!isEditState.email) &&
                            <div>
                                {currentUser.email}
                            </div>
                        }
                        {
                            (isCurrentUser && isEditState.email) &&
                            <div className='edit-holder'>
                                <input type="text" placeholder={currentUser.email} />
                                <div className='edit-holder-buttons'>
                                    <button onClick={() => {
                                        // submitForm({ ...lessonDTO, heading: newLesson.title })
                                        setEdit({ ...isEditState, email: false })
                                    }}>
                                        Submit
                                    </button>
                                    <button onClick={() => setEdit({ ...isEditState, email: false })}>
                                        Cancel
                                    </button>
                                </div>
                            </div>
                        }
                    </div>
                    <div className='one-line-data-holder'>
                        <h4 className="lesson-heading info-edit-label">
                            <div>
                                Phone
                            </div>
                            {
                                (isCurrentUser && !isEditState.phone) &&
                                <button className='button-no-style' onClick={() => setEdit({ ...isEditState, phone: true })}>
                                    <EditIcon />
                                </button>
                            }
                        </h4>
                        {
                            (!isEditState.phone) &&
                            <div>
                                {currentUser.phone}
                            </div>
                        }
                        {
                            (isCurrentUser && isEditState.phone) &&
                            <div className='edit-holder'>
                                <input type="text" placeholder={currentUser.phone} />
                                <div className='edit-holder-buttons'>
                                    <button onClick={() => {
                                        // submitForm({ ...lessonDTO, heading: newLesson.title })
                                        setEdit({ ...isEditState, phone: false })
                                    }}>
                                        Submit
                                    </button>
                                    <button onClick={() => setEdit({ ...isEditState, phone: false })}>
                                        Cancel
                                    </button>
                                </div>
                            </div>
                        }
                    </div>
                    <div className='one-line-data-holder'>
                    <h4 className="lesson-heading info-edit-label">
                            <div>
                                Description
                            </div>
                            {
                                (isCurrentUser && !isEditState.desciption) &&
                                <button className='button-no-style' onClick={() => setEdit({ ...isEditState, desciption: true })}>
                                    <EditIcon />
                                </button>
                            }
                        </h4>
                        {
                            (!isEditState.desciption) &&
                            <div>
                                {currentUser.desciption}
                            </div>
                        }
                        {
                            (isCurrentUser && isEditState.desciption) &&
                            <div className='edit-holder'>
                                <textarea
                                        name="description"
                                        cols="30"
                                        rows="10"
                                        // onChange={(e) => setNewLesson({ ...newLesson, description: e.target.value })}
                                        placeholder={currentUser.desciption} />
                                <div className='edit-holder-buttons'>
                                    <button onClick={() => {
                                        // submitForm({ ...lessonDTO, heading: newLesson.title })
                                        setEdit({ ...isEditState, desciption: false })
                                    }}>
                                        Submit
                                    </button>
                                    <button onClick={() => setEdit({ ...isEditState, desciption: false })}>
                                        Cancel
                                    </button>
                                </div>
                            </div>
                        }
                    </div>
                </div>
            </div>
        </>
    );
};

export default AccountData;
