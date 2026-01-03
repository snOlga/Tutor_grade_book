import React, { useEffect, useState, useRef } from 'react';
import '../styles/chat_style.css'
import { getCurrentUserEmail } from '../App';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import Message from './Message';
import { refreshAccessToken } from '../services/auth'

function Chat({ chat, setOpenChat, setCurrentChat }) {
    const [allMessages, setMessages] = useState([])
    const [messageValue, setMessageValue] = useState("")
    const [messageForEditing, setMessageEditing] = useState(null)
    const [editingFlag, setEditingFlag] = useState(false)

    useEffect(() => {
        fetchAllMessages()
        connectWebSocket()
    }, [])

    useEffect(() => {
        if (messageForEditing != null) {
            setMessageValue(messageForEditing.text)
            setEditingFlag(true)
        }
    }, [messageForEditing])

    function fetchAllMessages() {
        fetch(process.env.REACT_APP_ROOT_PATH + 'messages/chat/' + chat.id, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setMessages(data)
            }).catch(() => refreshAccessToken())
    }

    function connectWebSocket() {
        const socket = new SockJS(process.env.REACT_APP_ROOT_PATH + 'ws-endpoint')
        let stompClient = Stomp.over(socket)

        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/topic/chat/' + chat.id, data => {
                setMessages(JSON.parse(data.body).body)
            })
        })
    }

    function sendNewMessage() {
        fetch(process.env.REACT_APP_ROOT_PATH + 'messages', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                chat: chat,
                author: {
                    email: getCurrentUserEmail()
                },
                sentTime: new Date().toISOString(),
                isEdited: false,
                text: messageValue,
                isDeleted: false
            })
        })
            .then(response => response.json())
            .then(data => {
                setMessageValue("")
                setEditingFlag(false)
            }).catch(() => refreshAccessToken())
    }

    function sendEditedMessage() {
        fetch(process.env.REACT_APP_ROOT_PATH + 'messages/' + messageForEditing.id, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                ...messageForEditing,
                text: messageValue
            })
        })
            .then(response => response.json())
            .then(data => {
                setMessageValue("")
                setEditingFlag(false)
            }).catch(() => refreshAccessToken())
    }

    return (
        <div>
            <div className='messages'>
                {
                    allMessages.map((msg, index) => {
                        if (index != 0 && new Date(msg.sentTime).getDate() != new Date(allMessages[index - 1].sentTime).getDate()) {
                            return (
                                <div>
                                    <div className='day-delimiter'>
                                        {new Date(msg.sentTime).toDateString()}
                                    </div>
                                    <Message message={msg} messageEditing={setMessageEditing} />
                                </div>
                            )
                        }
                        return (
                            <Message message={msg} messageEditing={setMessageEditing} />
                        )
                    })
                }
            </div>
            <div className='chat-input'>
                <button onClick={() => {
                    setOpenChat(false)
                    setCurrentChat(null)
                }}>{"<"}</button>
                <textarea
                    rows="1"
                    value={messageValue}
                    onChange={e => setMessageValue(e.target.value)} />
                {
                    !editingFlag &&
                    <button onClick={() => sendNewMessage()}>Send</button>
                }
                {
                    editingFlag &&
                    <button onClick={() => sendEditedMessage()}>Send</button>
                }
            </div>
        </div >
    );
};

export default Chat;
