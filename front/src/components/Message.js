import React, { useEffect, useState } from 'react';
import '../styles/chat_style.css'
import { getCurrentUserEmail, getRoles, ROLES } from '../App';
import { EditIcon } from './modals/InfoLessonModal';
import { BinIcon } from './cards/LessonCard';

function Message({ message, messageEditing }) {
    const msgDate = new Date(message.sentTime)
    const isAdmin = getRoles().includes(ROLES.ADMIN)
    const isAuthor = message.author.email == getCurrentUserEmail() || isAdmin
    const [showActions, setActions] = useState(false)

    function handleDeletion() {
        fetch(process.env.REACT_APP_ROOT_PATH + 'messages/' + message.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
    }

    return (
        <div className={message.author.email == getCurrentUserEmail() ? "outcome" : "income"}
            onMouseEnter={() => setActions(true)}
            onMouseLeave={() => setActions(false)}>
            {
                isAuthor && showActions &&
                <div>
                    <div className='editing-msg' onClick={() => messageEditing(message)}>
                        <EditIcon />
                    </div>
                    <div className='card-bin' onClick={() => handleDeletion()}>
                        <BinIcon />
                    </div>
                </div>
            }
            <div className='msg-author'>
                <a href={'/account/' + message.author.humanReadableID} className='link'>{(message.author.name + " " + message.author.secondName)}</a>
            </div>
            <div className='msg-text'>
                {message.text}
            </div>
            <div>
                <div className='msg-time'>
                    <div>
                        {message.isEdited &&
                            <div>
                                edited
                            </div>}
                    </div>
                    {msgDate.getHours() + ":" + msgDate.getMinutes()}
                </div>
            </div>
        </div>
    );
};

export default Message;
