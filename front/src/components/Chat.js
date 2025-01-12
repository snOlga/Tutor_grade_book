import React, { useEffect, useState } from 'react';
import '../styles/chat_style.css'
import { getCurrentUserEmail } from '../App';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import Message from './Message';

function Chat({ chat }) {
    const [allMessages, setMessages] = useState([])
    const [messageValue, setMessageValue] = useState("")
    // const [messageForEditing]

    useEffect(() => {
        fetchAllMessages()
        connectWebSocket()
    }, [])

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

    function sendMessage() {
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
                console.log(data)
            })
    }

    return (
        <div>
            <div className='messages'>
                {
                    allMessages.map(msg => {
                        return (
                            <Message message={msg} />
                        )
                    })
                }
            </div>
            <div className='chat-input'>
                <textarea
                    rows="1"
                    value={messageValue}
                    onChange={e => setMessageValue(e.target.value)} />
                <button onClick={() => sendMessage()}>Send</button>
            </div>
        </div>
    );
};

export default Chat;
