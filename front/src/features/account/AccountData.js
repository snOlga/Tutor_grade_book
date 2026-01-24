import React, { useState, useEffect } from 'react';
import '../../styles/account_page.css'
import { getCurrentUserEmail, getRoles, ROLES } from '../../utils/auth';
import { EditIcon } from '../lessons/modals/InfoLessonModal';
import { Navigate, useNavigate } from 'react-router-dom';
import { refreshAccessToken } from '../../services/auth'

function AccountData({ currentUser, openChatWithId }) {
    const isAdmin = getRoles().includes(ROLES.ADMIN)
    const isCurrentUser = (getCurrentUserEmail() == currentUser.email) || isAdmin
    const [isEditState, setEdit] = useState(
        {
            name: false,
            secondName: false,
            email: false,
            phone: false,
            desciption: false
        }
    )
    const [newUserInfo, setNewUserInfo] = useState(
        {
            name: "",
            secondName: "",
            email: "",
            phone: "",
            desciption: ""
        }
    )
    const navigate = useNavigate()

    function submitForm(struct) {
        fetch(process.env.REACT_APP_ROOT_PATH + 'participators/' + struct.humanReadableID, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(struct)
        })
            .then(response => response.json())
            .then(data => {
                navigate('/account/' + data.humanReadableID)
                window.location.reload()
            }).catch(() => refreshAccessToken())
    }

    function openChat() {
        fetch(process.env.REACT_APP_ROOT_PATH + 'chats/' + getCurrentUserEmail() + "/" + currentUser.email, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(async data => {
                if (data.id != null)
                    openChatWithId(data)
                else
                    await openChatWithId(createChat())
            }).catch(() => refreshAccessToken())
    }

    async function createChat() {
        return await fetch(process.env.REACT_APP_ROOT_PATH + 'chats', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                users: [
                    {
                        email: getCurrentUserEmail()
                    },
                    {
                        email: currentUser.email
                    }
                ]
            })
        }).then(response => response.json()).catch(() => refreshAccessToken())
    }

    function deleteUser() {
        fetch(process.env.REACT_APP_ROOT_PATH + 'participators/' + currentUser.email, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        }).catch(() => refreshAccessToken())
    }

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
                                <input
                                    type="text"
                                    placeholder={currentUser.name}
                                    onChange={(e) => setNewUserInfo({ ...newUserInfo, name: e.target.value })} />
                                <div className='edit-holder-buttons'>
                                    <button onClick={() => {
                                        submitForm({ ...currentUser, name: newUserInfo.name })
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
                                <input
                                    type="text"
                                    placeholder={currentUser.secondName}
                                    onChange={(e) => setNewUserInfo({ ...newUserInfo, secondName: e.target.value })} />
                                <div className='edit-holder-buttons'>
                                    <button onClick={() => {
                                        submitForm({ ...currentUser, secondName: newUserInfo.secondName })
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
                                <input
                                    type="text"
                                    placeholder={currentUser.email}
                                    onChange={(e) => setNewUserInfo({ ...newUserInfo, email: e.target.value })} />
                                <div className='edit-holder-buttons'>
                                    <button onClick={() => {
                                        submitForm({ ...currentUser, email: newUserInfo.email })
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
                                <input
                                    type="text"
                                    placeholder={currentUser.phone}
                                    onChange={(e) => setNewUserInfo({ ...newUserInfo, phone: e.target.value })} />
                                <div className='edit-holder-buttons'>
                                    <button onClick={() => {
                                        submitForm({ ...currentUser, phone: newUserInfo.phone })
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
                                    onChange={(e) => setNewUserInfo({ ...newUserInfo, desciption: e.target.value })}
                                    placeholder={currentUser.desciption} />
                                <div className='edit-holder-buttons'>
                                    <button onClick={() => {
                                        submitForm({ ...currentUser, desciption: newUserInfo.desciption })
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
                    {
                        !isCurrentUser &&
                        <div className='one-line-data-holder'>
                            <button onClick={() => openChat()}>Write me!</button>
                        </div>
                    }
                    {
                        isAdmin &&
                        <div>
                            <button onClick={() => deleteUser()}>Delete user</button>
                        </div>
                    }
                </div>
            </div>
        </>
    );
};

export default AccountData;
