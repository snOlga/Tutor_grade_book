import React, { useEffect, useState, useRef } from 'react';
import '../styles/chat_style.css'
import { getCurrentUserEmail } from '../App';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import Message from './Message';

function Chat({ chat }) {
    const [allMessages, setMessages] = useState([])
    const [messageValue, setMessageValue] = useState("")
    const [messageForEditing, setMessageEditing] = useState({})
    const [editingFlag, setEditingFlag] = useState(false)

    useEffect(() => {
        fetchAllMessages()
        connectWebSocket()
    }, [])

    useEffect(() => {
        if (messageForEditing != {}) {
            setMessageValue(messageForEditing.text)
            setEditingFlag(true)
        }
    }, [messageForEditing])

    function fetchAllMessages() {
        fetch('http://localhost:18018/messages/with_chat_id/' + chat.id, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setMessages(data)
            })
    }

    function connectWebSocket() {
        const socket = new SockJS('http://localhost:18018/ws-endpoint')
        let stompClient = Stomp.over(socket)

        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/topic/chat/' + chat.id, data => {
                setMessages(JSON.parse(data.body).body)
            })
        })
    }

    function sendNewMessage() {
        fetch('http://localhost:18018/messages/create', {
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
            })
    }

    function sendEditedMessage() {
        fetch('http://localhost:18018/messages/update/' + messageForEditing.id, {
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
            })
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
                <textarea
                    rows="1"
                    value={messageValue}
                    onChange={e => setMessageValue(e.target.value)} />
                {
                    !editingFlag &&
                    < button onClick={() => sendNewMessage()}>Send</button>
                }
                {
                    editingFlag &&
                    < button onClick={() => sendEditedMessage()}>Send</button>
                }
            </div>
        </div >
    );
};

export default Chat;
