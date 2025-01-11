import React, { useEffect, useState } from 'react';
import '../styles/chat_style.css'
import { getCurrentUserEmail } from '../App';

function ChatHolder() {
    const [allChats, setAllChats] = useState([])
    const [isChatOpen, setOpenChat] = useState(false)

    useEffect(() => {
        fetchAllChats()
    }, [])

    function fetchAllChats() {
        fetch('http://localhost:18018/chats/with_email/' + getCurrentUserEmail(), {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setAllChats(data)
            })
    }

    return (
        <div className='all-window-2'>
            {
                !isChatOpen &&
                allChats.map(chat => <div>{chat.id}</div>)
            }
            {
                isChatOpen
            }
        </div>
    );
};

export default ChatHolder;
