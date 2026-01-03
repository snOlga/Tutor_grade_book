import React, { useEffect, useState, useRef } from 'react';
import '../styles/chat_style.css'
import { getCurrentUserEmail, getRoles, ROLES } from '../App';
import Chat from './Chat';
import { BinIcon } from './cards/LessonCard';
import { refreshAccessToken } from '../services/auth'

function ChatHolder({ openChat, chat, setChat }) {
    const [allChats, setAllChats] = useState([])
    const [isChatOpen, setOpenChat] = useState(false)
    const [currentChat, setCurrentChat] = useState(chat)
    const [lastMessages, setLastMessages] = useState({})
    const isAdmin = getRoles().includes(ROLES.ADMIN)

    useEffect(() => {
        fetchAllChats()
    }, [])

    function fetchAllChats() {
        const urlToFetch = process.env.REACT_APP_ROOT_PATH + (isAdmin ? 'chats' : 'chats/' + getCurrentUserEmail())

        fetch(urlToFetch, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setAllChats(data)
                fetchLastMessages(data)
            }).catch(() => refreshAccessToken())
    }

    async function fetchLastMessages(chats) {
        const messages = {}
        for (const chat of chats) {
            const response = await fetch(process.env.REACT_APP_ROOT_PATH + 'messages/last/' + chat.id).catch(() => refreshAccessToken())
            const data = await response.json()
            messages[chat.id] = data.text || "Chat is empty!"
        }
        setLastMessages(messages);
    }

    function deleteChat(chat) {
        fetch(process.env.REACT_APP_ROOT_PATH + 'chats/' + chat.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                fetchAllChats()
            }).catch(() => refreshAccessToken())
    }

    return (
        <div className='all-window-2' onClick={() => {
            openChat(false)
            setChat(null)
        }}>
            <div className='lessons-requests-holder lessons-requests-content' onClick={e => e.stopPropagation()}>
                <div className='chats'>
                    {
                        (!isChatOpen && currentChat == null) &&
                        allChats.map(chat =>
                            <div className='chat'>
                                <div className='card-bin'>
                                    <div>
                                        {chat.id} Chat ID
                                    </div>
                                    {
                                        isAdmin &&
                                        <div onClick={() => deleteChat(chat)}>
                                            <BinIcon />
                                        </div>
                                    }
                                </div>
                                <div className='chat-w-pic' onClick={() => {
                                    setCurrentChat(chat)
                                    setOpenChat(true)
                                }}>
                                    <div>
                                        <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 24 24" height="4em" width="4em" xmlns="http://www.w3.org/2000/svg"><path fill="none" d="M0 0h24v24H0z"></path><path d="M16 11c1.66 0 2.99-1.34 2.99-3S17.66 5 16 5c-1.66 0-3 1.34-3 3s1.34 3 3 3zm-8 0c1.66 0 2.99-1.34 2.99-3S9.66 5 8 5C6.34 5 5 6.34 5 8s1.34 3 3 3zm0 2c-2.33 0-7 1.17-7 3.5V19h14v-2.5c0-2.33-4.67-3.5-7-3.5zm8 0c-.29 0-.62.02-.97.05 1.16.84 1.97 1.97 1.97 3.45V19h6v-2.5c0-2.33-4.67-3.5-7-3.5z"></path></svg>
                                    </div>
                                    <div className='users-and-msg'>
                                        <div className='users-in-chat'>
                                            {
                                                chat.users.map((user, index) => {
                                                    if (index <= 3)
                                                        return (<a href={'/account/' + user.humanReadableID} className='link'>{(user.name + " " + user.secondName)}</a>)
                                                    if (index == 3)
                                                        return (<div>...</div>)
                                                    if (index > 3)
                                                        return (<div></div>)
                                                })
                                            }
                                        </div>
                                        <div className='first-msg'>
                                            {lastMessages[chat.id] || "Loading..."}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        )
                    }
                    {
                        (isChatOpen || currentChat != null) &&
                        <div>
                            <Chat chat={currentChat} setOpenChat={setOpenChat} setCurrentChat={setCurrentChat} />
                        </div>
                    }
                </div>
            </div>
        </div>
    );
};

export default ChatHolder;
