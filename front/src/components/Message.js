import React, { useEffect, useState } from 'react';
import '../styles/chat_style.css'
import { getCurrentUserEmail } from '../App';
import { EditIcon } from './modals/InfoLessonModal';

function Message({ message }) {
    const msgDate = new Date(message.sentTime)

    return (
        <div className={message.author.email == getCurrentUserEmail() ? "outcome" : "income"}>
            {
                message.author.email == getCurrentUserEmail() &&
                <div className='card-bin'>
                    <EditIcon />
                </div>
            }
            <div className='msg-author'>
                <a href={'/account/' + message.author.humanReadableID} className='link'>{(message.author.name + " " + message.author.secondName)}</a>
            </div>
            <div className='msg-text'>
                {message.text}
            </div>
            <div>
                <div>
                    {message.isEdited &&
                        <div>
                            edited
                        </div>}
                </div>
                <div className='msg-time'>
                    {msgDate.getHours() + ":" + msgDate.getMinutes()}
                </div>
            </div>
        </div>
    );
};

export default Message;
